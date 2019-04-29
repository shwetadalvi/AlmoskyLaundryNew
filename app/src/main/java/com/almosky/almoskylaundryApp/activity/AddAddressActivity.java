package com.almosky.almoskylaundryApp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.almosky.almoskylaundryApp.Almosky;
import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.activity.neworder.AreaListActivity;
import com.almosky.almoskylaundryApp.common.BaseActivity;
import com.almosky.almoskylaundryApp.utils.AppPrefes;
import com.almosky.almoskylaundryApp.utils.api.ApiCalls;
import com.almosky.almoskylaundryApp.utils.constants.ApiConstants;
import com.almosky.almoskylaundryApp.utils.constants.Constants;
import com.almosky.almoskylaundryApp.utils.constants.PrefConstants;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import cn.pedant.SweetAlert.SweetAlertDialog;
import im.delight.android.location.SimpleLocation;


public class AddAddressActivity extends BaseActivity implements OnMapReadyCallback {

    public static final int AREA_LIST = 123;
    private int PLACE_PICKER_REQUEST = 1;
    private GoogleMap _googleMap;
    private androidx.appcompat.widget.AppCompatEditText edtArea, block, house, floor, phone, aditional;
    ApiCalls apiCalls;
    SimpleArcDialog dialog;
    private AppPrefes appPrefes;
    androidx.appcompat.widget.AppCompatAutoCompleteTextView addresname;
    String lat;
    String lon;
    private SimpleLocation location;
    private double lngDbl;
    private double latDbl;
    private LocationManager mLocationManager;
    ConstraintLayout mapCover;

    @RequiresApi(api = Build.VERSION_CODES.M) @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add_address);

        appPrefes = new AppPrefes(this);
        apiCalls = new ApiCalls();
        dialog = new SimpleArcDialog(this);

        // construct a new instance of SimpleLocation
        location = new SimpleLocation(this);

        // if we can't access the location yet
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
        }

        TextView title = findViewById(R.id.tb_titleTextView);
        title.setText("Address List");

        ImageView backButton = findViewById(R.id.backArrow);
        backButton.setVisibility(View.VISIBLE);
        mapCover = findViewById(R.id.mapCover);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ImageView pin = (ImageView) findViewById(R.id.pin);
        pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlacePicker();
            }
        });
        mapCover.setOnClickListener(v -> startPlacePicker());
        addresname = findViewById(R.id.edtAddressName);
        edtArea = findViewById(R.id.edtArea);
        block = findViewById(R.id.edtBlock);
        house = findViewById(R.id.edtHouse);
        floor = findViewById(R.id.edtFloor);
        aditional = findViewById(R.id.edtAdditional);
        phone = findViewById(R.id.edtPhone);
        phone.setText(appPrefes.getData(PrefConstants.mobile));

        edtArea.setOnClickListener(v -> {
            Intent intent = new Intent(AddAddressActivity.this, AreaListActivity.class);
            startActivityForResult(intent, AREA_LIST);
        });
        Button btnStoreAddress = (Button) findViewById(R.id.btnStoreAddress);
        btnStoreAddress.setOnClickListener(v -> {

            if (validateFields()) {
                addNewAddress();
            }


        });
        aditional.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                if (validateFields()) {
                    addNewAddress();
                }
            }
            return false;
        });
        findViewById(R.id.currentLocationIc).setOnClickListener(v -> getAddress());
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300000, 50, mLocationListener);
        }
    }
    private void startPlacePicker() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(AddAddressActivity.this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            System.out.println("EXCEPTION============================ " + e);
            e.printStackTrace();
        }
    }

    private double currentLat;
    private double currentLng;
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            currentLat = location.getLatitude();
            currentLng = location.getLongitude();
        }

        @Override public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override public void onProviderEnabled(String provider) {

        }

        @Override public void onProviderDisabled(String provider) {

        }
    };

    private boolean validateFields() {

        if (addresname.getText().toString().equals("") || addresname.getText().toString().equals(null)) {
            addresname.requestFocus();

            Toast.makeText(AddAddressActivity.this, "Addres Name cannot be blank", Toast.LENGTH_LONG).show();

            return false;

        }
        if (edtArea.getText().toString().equals("") || edtArea.getText().toString().equals(null)) {
            edtArea.requestFocus();
            Toast.makeText(AddAddressActivity.this, "Area cannot be blank", Toast.LENGTH_LONG).show();

            return false;

        }
        if (block.getText().toString().equals("") || block.getText().toString().equals(null)) {
            block.requestFocus();
            Toast.makeText(AddAddressActivity.this, "Block Name cannot be blank", Toast.LENGTH_LONG).show();

            return false;

        }
        if (house.getText().toString().equals("") || house.getText().toString().equals(null)) {
            house.requestFocus();
            Toast.makeText(AddAddressActivity.this, "Building Name cannot be blank", Toast.LENGTH_LONG).show();

            return false;

        }
        if (floor.getText().toString().equals("") || floor.getText().toString().equals(null)) {
            floor.requestFocus();
            Toast.makeText(AddAddressActivity.this, "Floor Name cannot be blank", Toast.LENGTH_LONG).show();

            return false;

        }
        if (phone.getText().toString().equals("") || house.getText().toString().equals(null)) {
            phone.requestFocus();
            Toast.makeText(AddAddressActivity.this, "Mobile cannot be blank", Toast.LENGTH_LONG).show();

            return false;

        }
        if (phone.getText().toString().length() != 9) {

            phone.requestFocus();
            Toast.makeText(AddAddressActivity.this, "Check Mobile number", Toast.LENGTH_LONG).show();
            return false;

        }

        if (lat.equals("") || lat.equals(null)) {


            return false;

        }
        if (lon.equals("") || lon.equals(null)) {


            return false;

        }


        return true;
    }

    private void addNewAddress() {

        // if (!validate(view.getResources())) return;
        RequestParams params = new RequestParams();

        params.put(Constants.email, appPrefes.getData(PrefConstants.email));
        params.put("addressname", addresname.getText().toString());
        params.put("area", edtArea.getText().toString());
        params.put("block", block.getText().toString());
        params.put("house", house.getText().toString());
        params.put("floor", floor.getText().toString());
        params.put("mobile", phone.getText().toString());
        params.put("additional", aditional.getText().toString());
        params.put("latitude", lat);
        params.put("longitude", lon);

        String url = ApiConstants.addAddressUrl;
        apiCalls.callApiPost(AddAddressActivity.this, params, dialog, url, 7);


    }

    @Override
    protected void onPause() {
        super.onPause();
        location.endUpdates();

    }

    @Override
    protected void onResume() {
        super.onResume();

        location.beginUpdates();
    }

    @Override
    public void getResponse(String response, int requestId) {

        if (requestId == 11) {


        } else {

            try {

                String object = new String(response);
                JSONObject jsonObject = new JSONObject(object);
                String result = jsonObject.getString("status");

                if (result.equals("true")) {
                    new SweetAlertDialog(AddAddressActivity.this, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("Success")
                            .setContentText("Success")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {


                                    sDialog.dismissWithAnimation();

                                    Intent go = new Intent(AddAddressActivity.this, AddressListActivity.class);
                                    go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(go);


                                }
                            })
                            .show();
                    // Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "msg", Toast.LENGTH_LONG).show();
            }


        }

    }

    public String getAddress() {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        if (currentLat == 0 || currentLng == 0) {
            currentLat = latDbl;
            currentLng = lngDbl;
        }
        try {
            List<Address> addresses = geocoder.getFromLocation(currentLat, currentLng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0).split("-")[0];
            addresname.setText(stripStringTolines(add));
            showInMap(currentLat, currentLng, "");
            return add;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private String stripStringTolines(String string) {
        StringBuilder sb = new StringBuilder(string);

        int i = 0;
        while ((i = sb.indexOf(" ", i + 15)) != -1) {
            sb.replace(i, i + 1, "\n");
        }

        return sb.toString();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());

        latDbl = location.getLatitude();
        lngDbl = location.getLongitude();
        lat = String.valueOf(latDbl);
        lon = String.valueOf(lngDbl);
        _googleMap = googleMap;
        showInMap(latDbl, lngDbl, "");
    }

    private void showInMap(double latDbl, double lngDbl, String title) {
        LatLng location = new LatLng(latDbl, lngDbl);
        _googleMap.addMarker(new MarkerOptions().position(location).title(title));
//        Toast.makeText(this,getAddress(location.getLatitude(),location.getLongitude() ) , Toast.LENGTH_SHORT).show();
        _googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                StringBuilder stBuilder = new StringBuilder();
                String placename = String.format("%s", place.getName());
                lat = String.valueOf(place.getLatLng().latitude);
                lon = String.valueOf(place.getLatLng().longitude);
                String address = String.format("%s", place.getAddress());
                stBuilder.append("Name: ");
                stBuilder.append(placename);
                stBuilder.append("\n");
                stBuilder.append("Latitude: ");
                stBuilder.append(lat);
                stBuilder.append("\n");
                stBuilder.append("Logitude: ");
                stBuilder.append(lon);
                stBuilder.append("\n");
                stBuilder.append("Address: ");
                stBuilder.append(address);
                System.out.println("ADDRESS================ " + stBuilder.toString());
                showInMap(place.getLatLng().latitude, place.getLatLng().longitude, address);
                addresname.setText(address.split("-")[0]);
            }
        } else if (requestCode == AREA_LIST && resultCode == RESULT_OK) {
            String selectedArea = data.getStringExtra("selectedArea");
            edtArea.setText(selectedArea);
            if (edtArea.getText().toString().equals("NASAB")) {
                addresname.setText(Almosky.getInst().getNasabAddress1());
                house.setText(Almosky.getInst().getNasabAddress2());
                block.setText("-");
                floor.setText("-");
            }
        }


    }

    void getCurrentLocation() {

    }
}
