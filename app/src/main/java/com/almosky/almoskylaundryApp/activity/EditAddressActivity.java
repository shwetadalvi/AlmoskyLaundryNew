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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.almosky.almoskylaundryApp.Almosky;
import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.activity.neworder.AreaListActivity;
import com.almosky.almoskylaundryApp.common.BaseActivity;
import com.almosky.almoskylaundryApp.model.Addressdto;
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
import cn.pedant.SweetAlert.SweetAlertDialog;
import im.delight.android.location.SimpleLocation;


public class EditAddressActivity extends BaseActivity implements OnMapReadyCallback {

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
        setContentView(R.layout.activity_edit_address);

        // getAreaList();

        appPrefes = new AppPrefes(this);
        apiCalls = new ApiCalls();
        dialog = new SimpleArcDialog(this);

        // construct a new instance of SimpleLocation
        location = new SimpleLocation(this);
        mapCover = findViewById(R.id.mapCover);
        // if we can't access the location yet
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
        }

        ImageView backButton = findViewById(R.id.backArrow);
        backButton.setVisibility(View.VISIBLE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
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

        setAddressData();

        edtArea.setOnClickListener(v -> {
            Intent intent = new Intent(EditAddressActivity.this, AreaListActivity.class);
            startActivityForResult(intent, AREA_LIST);
        });
        Button btnStoreAddress = (Button) findViewById(R.id.btnStoreAddress);
        btnStoreAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editAddress();
            }
        });
        findViewById(R.id.currentLocationIc).setOnClickListener(v -> getAddress());

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300000,
                50, mLocationListener);

    }

    private void startPlacePicker() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(EditAddressActivity.this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            System.out.println("EXCEPTION============================ " + e);
            e.printStackTrace();
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

    private void showInMap(double latDbl, double lngDbl, String title) {
        LatLng location = new LatLng(latDbl, lngDbl);
        _googleMap.addMarker(new MarkerOptions().position(location).title(title));
//        Toast.makeText(this,getAddress(location.getLatitude(),location.getLongitude() ) , Toast.LENGTH_SHORT).show();
        _googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
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


    private void setAddressData() {

        Addressdto.Result adrs = Almosky.getInst().getSelectedAddress();

        addresname.setText(adrs.getAddressName());
        edtArea.setText(adrs.getArea());
        block.setText(adrs.getBlock());
        house.setText(adrs.getHouse());
        floor.setText(adrs.getFloor());
        aditional.setText(adrs.getAdditional());
        phone.setText(adrs.getMobile());
        lat = adrs.getLatitude();
        lon = adrs.getLongitude();


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

    private void editAddress() {

        //if (!validate(view.getResources())) return;
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
        params.put("longitude", lat);


        String url = ApiConstants.editAddressUrl;
        apiCalls.callApiPost(EditAddressActivity.this, params, dialog, url, 13);

    }



    @Override
    public void getResponse(String response, int requestId) {

        if (requestId == 13) {

            try {

                String object = new String(response);
                JSONObject jsonObject = new JSONObject(object);
                String result = jsonObject.getString("status");
                String message = jsonObject.getString("Message");

                if (result.equals("true")) {
                    new SweetAlertDialog(EditAddressActivity.this, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("Success")
                            .setContentText(message)
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {


                                    sDialog.dismissWithAnimation();

                                    Intent go = new Intent(EditAddressActivity.this, AddressListActivity.class);
                                    //   go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(go);


                                }
                            })
                            .show();
                    // Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                } else {
                    new SweetAlertDialog(EditAddressActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error")
                            .setContentText(message)
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {


                                    sDialog.dismissWithAnimation();


                                }
                            })
                            .show();
                }
            } catch (JSONException e) {
                //   Toast.makeText(getApplicationContext(), "msg", Toast.LENGTH_LONG).show();
            }


        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        //  Double lt=Double.valueOf(Almosky.getInst().getSelectedAddress().getLatitude());
        // Double ln=Double.valueOf(Almosky.getInst().getSelectedAddress().getLongitude());
        LatLng sydney = new LatLng(Double.valueOf(lat), Double.valueOf(lon));
        if (lat == null && lat.isEmpty() || lon == null && lon.isEmpty()) {
            //   sydney = new LatLng(location.getLatitude(), location.getLongitude());
        } else {
            sydney = new LatLng(Double.valueOf(lat), Double.valueOf(lon));

        }

        latDbl = location.getLatitude();
        lngDbl = location.getLongitude();
        _googleMap = googleMap;
        showInMap(latDbl, lngDbl, "");

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
        } else if (requestCode == AREA_LIST) {
            String selectedArea = data.getStringExtra("selectedArea");
            edtArea.setText(selectedArea);
        }


    }
}
