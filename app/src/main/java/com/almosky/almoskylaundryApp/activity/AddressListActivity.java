package com.almosky.almoskylaundryApp.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.almosky.almoskylaundryApp.Almosky;
import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.activity.neworder.CategoryListActivity;
import com.almosky.almoskylaundryApp.adapter.AddressListAdapter;
import com.almosky.almoskylaundryApp.common.BaseActivity;
import com.almosky.almoskylaundryApp.model.Addressdto;
import com.almosky.almoskylaundryApp.model.OfferAndVatModel;
import com.almosky.almoskylaundryApp.utils.AppPrefes;
import com.almosky.almoskylaundryApp.utils.api.ApiCalls;
import com.almosky.almoskylaundryApp.utils.constants.ApiConstants;
import com.almosky.almoskylaundryApp.utils.constants.PrefConstants;
import com.google.gson.Gson;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class AddressListActivity extends BaseActivity {

    AppPrefes appPrefes;
    ApiCalls apiCalls;
    SimpleArcDialog dialog;
    RecyclerView rvAddress;
    Addressdto addressList;
    boolean stat;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        Button addAddress = findViewById(R.id.addAddress);
        clearTempData();


        //  Intent intent = new Intent(AddressListActivity.this, CategoryListActivity.class);
        //   startActivity(intent);

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stat = checkLocationPermission();
                if (stat) {
                    Intent intent = new Intent(AddressListActivity.this, AddAddressActivity.class);
                    startActivity(intent);
                }

            }
        });

        appPrefes = new AppPrefes(this);
        dialog = new SimpleArcDialog(this);
        rvAddress = (RecyclerView) findViewById(R.id.rvaddressList);
        apiCalls = new ApiCalls();
        String url = ApiConstants.general;
        apiCalls.callApiGet(AddressListActivity.this, dialog, url, 100);

        TextView title = findViewById(R.id.tb_titleTextView);

        if (Almosky.getInst().getAddressType().equals("0")) {
            title.setText("Address List");
        } else {
            title.setText("Select Address");
        }

        ImageView backButton = findViewById(R.id.backArrow);
        backButton.setVisibility(View.VISIBLE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        if (appPrefes.getBoolData(PrefConstants.isLogin)) {
            getAddressList();


            if (!Almosky.getInst().getAddressType().equals("0")) {

                if (Almosky.getInst().getAddressType().equals("0")) {
                    Almosky.getInst().setAddress("");
                    Almosky.getInst().setNisabClub(false);
                    Intent intent = new Intent(AddressListActivity.this, AddressListActivity.class);
                    startActivity(intent);
                } else {

                    getAddressList();

                    Almosky.getInst().setAddress("NASAB Club");
                    Almosky.getInst().setNisabClub(true);
                    Intent intent = new Intent(AddressListActivity.this, CategoryListActivity.class);
                    //  startActivity(intent);
                }


       /*         new SweetAlertDialog(AddressListActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("")
                        .setContentText("Do You Want Set NASAB Club As Delivery Address?")
                        .setConfirmText("Yes")
                        .setCancelText("No")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                sDialog.dismissWithAnimation();

                                if(Almosky.getInst().getAddressType().equals("0")){
                                    Almosky.getInst().setAddress("");
                                    Almosky.getInst().setNisabClub(false);
                                    Intent intent = new Intent(AddressListActivity.this, AddressListActivity.class);
                                    startActivity(intent);
                                }else {
                                    Almosky.getInst().setAddress("NASAB Club");
                                    Almosky.getInst().setNisabClub(true);
                                    Intent intent = new Intent(AddressListActivity.this, CategoryListActivity.class);
                                    startActivity(intent);
                                }

                            }
                        })
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                                getAddressList();
                            }
                        })
                        .show();  */

            }


        } else {
            startActivity(new Intent(AddressListActivity.this, SignupOrLoginActivity.class));
        }


    }


    public static void clearTempData() {
        Almosky.getInst().setIronList(null);
        Almosky.getInst().setCartcount(0);
        Almosky.getInst().setWashList(null);
        Almosky.getInst().setDrycleanList(null);
        Almosky.getInst().setCartamount(0);
        // Almosky.getInst().setServiceId(0);
        Almosky.getInst().setAddress("");

        // Almosky.getInst().setAddressType("");
        Almosky.getInst().setCartcount(0);
        Almosky.getInst().setCategoryList(null);
        Almosky.getInst().setDeliverydate(null);
        Almosky.getInst().setDeliverytime(null);
        Almosky.getInst().setPickupdate(null);
        Almosky.getInst().setPickuptime(null);


      /* Almosky.getInst().setDrycleanpriceList(null);
       Almosky.getInst().setIroningpriceList(null);
       Almosky.getInst().setIroningpriceList(null);*/

    }

    @Override
    protected void onResume() {
        if (isOnClikedButton){
            Almosky.getInst().setDeliveryType("2");
        }
        super.onResume();
        clearTempData();
    }

    public void editAddress(int position) {

        Almosky.getInst().setSelectedAddress(addressList.getResult().get(position));
        Intent go = new Intent(AddressListActivity.this, EditAddressActivity.class);
        startActivity(go);
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Almosky Laundry")
                        .setMessage("Location Permission")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(AddressListActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:

                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    private void getAddressList() {

        // if (!validate(view.getResources())) return;
        RequestParams params = new RequestParams();

        params.put("email", appPrefes.getData(PrefConstants.email));

        // params.put(ApiConstants.uid, appPrefes.getData(PrefConstants.uid));
        // params.put(ApiConstants.uid, 1);
        //  params.put(ApiConstants.status, "Pending");

        String url = ApiConstants.addresslistUrl;
        apiCalls.callApiPost(AddressListActivity.this, params, dialog, url, 1);

    }

    public void onDeleteAddress(String addressId) {

        // if (!validate(view.getResources())) return;
        RequestParams params = new RequestParams();

        params.put("email", appPrefes.getData(PrefConstants.email));

        params.put("addressId", addressId);

        String url = ApiConstants.deleteAddressUrl;
        apiCalls.callApiPost(AddressListActivity.this, params, dialog, url, 14);

    }

    public void onClickAddress(String area) {

      /*  if(Almosky.getInst().getAddressType().equals("1")){
            Intent go=new Intent(AddressListActivity.this,OrderConfirmationActivity.class);
            startActivity(go);
        } */

        String ad = Almosky.getInst().getAddressType();
        if (Almosky.getInst().getAddressType().equals("0")) {
            Intent intent = new Intent(AddressListActivity.this, AddressListActivity.class);
            startActivity(intent);
        } else {
            if (area.equals("NASAB")) {
                Almosky.getInst().setNisabClub(true);
                Almosky.getInst().setOffer(true);
                if (Almosky.getInst().getDeliveryType().equals("2")) {
                    final SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
                    dialog.setContentText("Nasab Customer will not get fast service");
                    dialog.setConfirmButton("OK", sweetAlertDialog -> {
                        isOnClikedButton = true;
                        Almosky.getInst().setDeliveryType("1");
                        Intent intent = new Intent(AddressListActivity.this, PickUpActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    });
                    dialog.show();
                    dialog.setOnDismissListener(dialog1 -> {
                        if (!isOnClikedButton){
                            Almosky.getInst().setDeliveryType("2");
                        }
                    });
                } else {
                    Intent intent = new Intent(AddressListActivity.this, PickUpActivity.class);
                    startActivity(intent);
                }
            } else {
                Almosky.getInst().setOffer(false);
                Almosky.getInst().setNisabClub(false);
                Intent intent = new Intent(AddressListActivity.this, PickUpActivity.class);
                startActivity(intent);
            }
        }
    }

    boolean isOnClikedButton = false;
    @Override
    public void getResponse(String response, int requestId) {
        if (requestId == 14) {
            try {
                String object = new String(response);
                JSONObject jsonObject = new JSONObject(object);
                String result = jsonObject.getString("status");
                String message = jsonObject.getString("Message");
                if (result.equals("true")) {
                    new SweetAlertDialog(AddressListActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Success")
                            .setContentText(message)
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();

                                    Intent go = new Intent(AddressListActivity.this, AddressListActivity.class);
                                    startActivity(go);
                                }
                            })
                            .show();
                } else {
                    new SweetAlertDialog(AddressListActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error")
                            .setContentText(message)
                            .setConfirmText("Ok")
                            .setConfirmClickListener(sDialog -> sDialog.dismissWithAnimation())
                            .show();
                }
            } catch (JSONException e) {
            }
        } else if (requestId == 100) {
            try {
                final OfferAndVatModel offerData;
                Gson gson = new Gson();
                offerData = gson.fromJson(response, OfferAndVatModel.class);
                if (offerData.getStatus()) {
                    Almosky.getInst().setNasabAddress1(offerData.getResult().get(0).getNasabAddress1());
                    Almosky.getInst().setNasabAddress2(offerData.getResult().get(0).getNasabAddress2());
                    Almosky.getInst().setNasabTiming(offerData.getResult().get(0).getNasabTiming());
                }
            } catch (Exception ex) {
            }
        } else {
            try {
                Gson gson = new Gson();
                addressList = gson.fromJson(response, Addressdto.class);
                AddressListAdapter mAdapter = new AddressListAdapter(AddressListActivity.this, addressList.getResult(), this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                rvAddress.setLayoutManager(mLayoutManager);
                rvAddress.setItemAnimator(new DefaultItemAnimator());
                rvAddress.setAdapter(mAdapter);
            } catch (Exception e) {

            }
        }


    }
}
