package com.almosky.almoskylaundryApp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.almosky.almoskylaundryApp.Almosky;
import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.adapter.PickUpDateRecyclerViewAdapter;
import com.almosky.almoskylaundryApp.adapter.PickUpTimeRecyclerViewAdapter;
import com.almosky.almoskylaundryApp.common.BaseActivity;
import com.almosky.almoskylaundryApp.interfaces.ClickListeners;
import com.almosky.almoskylaundryApp.model.GetTermsResponse;
import com.almosky.almoskylaundryApp.model.day;
import com.almosky.almoskylaundryApp.model.dayList;
import com.almosky.almoskylaundryApp.model.timeList;
import com.almosky.almoskylaundryApp.utils.AppPrefes;
import com.almosky.almoskylaundryApp.utils.Utility;
import com.almosky.almoskylaundryApp.utils.api.ApiCalls;
import com.almosky.almoskylaundryApp.utils.constants.ApiConstants;
import com.almosky.almoskylaundryApp.utils.constants.Constants;
import com.almosky.almoskylaundryApp.utils.constants.PrefConstants;
import com.google.gson.Gson;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.RequestParams;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class PickUpActivity extends BaseActivity implements ClickListeners.ItemClick<day> {

    private AppPrefes appPrefes;
    private ApiCalls apiCalls;
    private SimpleArcDialog dialog;
    private RecyclerView recyclerViewDates;
    private RecyclerView recyclerViewTimes;
    List<String> timeArray = new ArrayList<>(Arrays.asList("10:00 AM - 11:00 AM", "11:00 AM - 12:00 PM", "12:00 PM - 01:00 PM", "01:00 PM - 02:00 PM", "02:00 PM - 03:00 PM", "03:00 PM - 04:00 PM", "04:00 PM - 05:00 PM", "05:00 PM - 06:00 PM", "06:00 PM - 07:00 PM", "07:00 PM - 08:00 PM", "08:00 PM - 09:00 PM", "09:00 PM - 10:00 PM"));
    List<String> dateArray = new ArrayList();
    //(Arrays.asList("2019.01.02", "2019.01.03", "2019.01.04", "2019.01.05"));
    private Button btnNext;
    private TextView textTerms;
    ArrayList<String> dayname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up);
        textTerms = findViewById(R.id.textTerms);
        dialog = new SimpleArcDialog(this);
        appPrefes = new AppPrefes(this);
        apiCalls = new ApiCalls();
        dateArray.clear();
        clearTempData();
        if (Almosky.getInst().isNisabClub())
            getTerms();
        getDays();
        DateTime today = new DateTime();

        dayname = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            DateTime tomorrow = today.plusDays(i);
            String[] dates = String.valueOf(tomorrow).split("T");
            dateArray.add(dates[0]);

            DateTime.Property pDoW = today.plusDays(i).dayOfWeek();
            dayname.add(pDoW.getAsText(Locale.getDefault()));

            // System.out.println("new date "+tomorrow);
            //  System.out.println(today.toString("yyyy-MM-dd"));
            //  System.out.println("day"+today.dayOfWeek().toString());
            // today.dayOfWeek().get
        }
        //Almosky.getInst().setPickdaysname(dayname);

        init();
        clearTempData();

    }

    public static void clearTempData() {
        Almosky.getInst().setIronList(null);
        Almosky.getInst().setCartcount(0);
        Almosky.getInst().setWashList(null);
        Almosky.getInst().setDrycleanList(null);
        Almosky.getInst().setCartamount(0);
        // Almosky.getInst().setServiceId(0);
        //Almosky.getInst().setAddress("");

        // Almosky.getInst().setAddressType("");
        Almosky.getInst().setCartcount(0);
        Almosky.getInst().setCategoryList(null);
      /*  Almosky.getInst().setDeliverydate(null);
        Almosky.getInst().setDeliverytime(null);
        Almosky.getInst().setPickupdate(null);
        Almosky.getInst().setPickuptime(null);*/


      /* Almosky.getInst().setDrycleanpriceList(null);
       Almosky.getInst().setIroningpriceList(null);
       Almosky.getInst().setIroningpriceList(null);*/

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void init() {
        recyclerViewDates = (RecyclerView) findViewById(R.id.dates);
        recyclerViewTimes = (RecyclerView) findViewById(R.id.times);
        btnNext = (Button) findViewById(R.id.btnNext);

        if (Utility.isNetworkOnline(PickUpActivity.this)) {
            getDays();
        } else {

            new SweetAlertDialog(PickUpActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("No Internet")
                    .setContentText("Check Internet Connection")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();

                        }
                    }).show();


        }
       /* PickUpDateRecyclerViewAdapter mAdapterDate = new PickUpDateRecyclerViewAdapter(PickUpActivity.this, dateArray, dayname, "pickup");
        RecyclerView.LayoutManager mLayoutManagerDate = new LinearLayoutManager(getApplicationContext());
        recyclerViewDates.setLayoutManager(mLayoutManagerDate);
        recyclerViewDates.setItemAnimator(new DefaultItemAnimator());
        recyclerViewDates.setAdapter(mAdapterDate);
*/
        PickUpTimeRecyclerViewAdapter mAdapterTime;
        if (Almosky.getInst().isNisabClub()) {
            timeArray = new ArrayList<>(Arrays.asList(Almosky.getInst().getNasabTiming()));
        }
       /* mAdapterTime = new PickUpTimeRecyclerViewAdapter(PickUpActivity.this, timeArray, "pickup");
        RecyclerView.LayoutManager mLayoutManagerTime = new LinearLayoutManager(getApplicationContext());
        recyclerViewTimes.setLayoutManager(mLayoutManagerTime);
        recyclerViewTimes.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTimes.setAdapter(mAdapterTime);*/
        listeners();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // perform whatever you want on back arrow click
            }
        });
    }

    private void listeners() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PickUpActivity.this, DeliveryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void getResponse(String response, int requestId) {
        super.getResponse(response, requestId);
        if (requestId == 1) {
            try {
                final dayList userData;
                Gson gson = new Gson();
                userData = gson.fromJson(response, dayList.class);
                try {

                    PickUpDateRecyclerViewAdapter mAdapterDate = new PickUpDateRecyclerViewAdapter(PickUpActivity.this, dateArray, dayname, "pickup", this, userData.getDay());
                    RecyclerView.LayoutManager mLayoutManagerDate = new LinearLayoutManager(getApplicationContext());
                    recyclerViewDates.setLayoutManager(mLayoutManagerDate);
                    recyclerViewDates.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewDates.setAdapter(mAdapterDate);

                    Almosky.getInst().setPickupdate(userData.getDay().get(0).getDate());
                    getTimings(userData.getDay().get(0).getTimeId());
                } catch (Exception e) {

                    new SweetAlertDialog(PickUpActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Data Error")
                            .show();
                    e.printStackTrace();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestId == 2) {
            try {
                final timeList userData;
                Gson gson = new Gson();
                userData = gson.fromJson(response, timeList.class);
                try {
                    PickUpTimeRecyclerViewAdapter mAdapterTime;

                    mAdapterTime = new PickUpTimeRecyclerViewAdapter(PickUpActivity.this, timeArray, "pickup", this, userData.getResult());
                    RecyclerView.LayoutManager mLayoutManagerTime = new LinearLayoutManager(getApplicationContext());
                    recyclerViewTimes.setLayoutManager(mLayoutManagerTime);
                    recyclerViewTimes.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewTimes.setAdapter(mAdapterTime);
                    Almosky.getInst().setPickuptime(userData.getResult().get(0).getFromTime() + " - " + userData.getResult().get(0).getToTime());
                    Almosky.getInst().setPickupToTime(userData.getResult().get(0).getToTime());
                } catch (Exception e) {

                    new SweetAlertDialog(PickUpActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Data Error")
                            .show();
                    e.printStackTrace();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestId == 3) {
            try {
                final GetTermsResponse userData;
                Gson gson = new Gson();
                userData = gson.fromJson(response, GetTermsResponse.class);
                try {
                    textTerms.setVisibility(View.VISIBLE);
                    textTerms.setText(userData.getResult());
                } catch (Exception e) {
/*
                    new SweetAlertDialog(PickUpActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Data Error")
                            .show();*/
                    e.printStackTrace();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getTimings(int timeId) {
        Log.d("Success-", "JSON:" + "Inside Timings");
        String url = "";

        RequestParams params = new RequestParams();
        params.put(Constants.email, appPrefes.getData(PrefConstants.email));
        params.put("timeId", timeId);
        params.put("pickuptime", "");
        if (Almosky.getInst().isNisabClub())
            url = ApiConstants.getNasabTimings;
        else
            url = ApiConstants.getTimings;
        Log.d("Success-", "JSON:" + "Inside Timings :" + params.toString());
        apiCalls.callApiPost(PickUpActivity.this, params, dialog, url, 2);
    }

    private void getDays() {
        Log.d("Success-", "JSON:" + "Inside Days");
        RequestParams params = new RequestParams();
        params.put(Constants.email, appPrefes.getData(PrefConstants.email));
        params.put("pdate", "");
        params.put("deltype", Almosky.getInst().getDeliveryType());

        String url = ApiConstants.getDays;
        apiCalls.callApiPost(PickUpActivity.this, params, dialog, url, 1);
    }

    private void getTerms() {
        Log.d("Success-", "JSON:" + "Inside Terms");
        RequestParams params = new RequestParams();
        params.put(Constants.email, appPrefes.getData(PrefConstants.email));

        String url = ApiConstants.getNasabTerms;
        apiCalls.callApiPost(PickUpActivity.this, params, dialog, url, 3);
    }

    @Override
    public void onClickedItem(day item) {
        getTimings(item.getTimeId());
    }


}
