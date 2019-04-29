package com.almosky.almoskylaundryApp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.almosky.almoskylaundryApp.Almosky;
import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.activity.neworder.CategoryListActivity;
import com.almosky.almoskylaundryApp.adapter.PickUpDateRecyclerViewAdapter;
import com.almosky.almoskylaundryApp.adapter.PickUpTimeRecyclerViewAdapter;
import com.almosky.almoskylaundryApp.common.BaseActivity;
import com.almosky.almoskylaundryApp.interfaces.ClickListeners;
import com.almosky.almoskylaundryApp.model.day;
import com.almosky.almoskylaundryApp.model.dayList;
import com.almosky.almoskylaundryApp.model.timeList;
import com.almosky.almoskylaundryApp.utils.AppPrefes;
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

public class DeliveryActivity extends BaseActivity implements ClickListeners.ItemClick<day> {

    private RecyclerView recyclerViewDates;
    private RecyclerView recyclerViewTimes;
    // List<String> timeArray = new ArrayList<>(Arrays.asList("11:30 AM - 12:30 PM", "12:30 PM - 01:30 PM", "01:30 PM - 02:30 PM", "02:30 PM - 03:30 PM", "03:30 PM - 04:30 PM", "04:30 PM - 05:30 PM", "05:30 PM - 06:30 PM", "06:30 PM - 07:30 PM", "07:30 PM - 08:30 PM", "08:30 PM - 09:30 PM", "09:30 PM - 10:30 PM", "10:30 PM - 11:30 PM"));
    List<String> timeArray = new ArrayList<>(Arrays.asList("10:00 AM - 11:00 AM", "11:00 AM - 12:00 PM", "12:00 PM - 01:00 PM", "01:00 PM - 02:00 PM", "02:00 PM - 03:00 PM", "03:00 PM - 04:00 PM", "04:00 PM - 05:00 PM", "05:00 PM - 06:00 PM", "06:00 PM - 07:00 PM", "07:00 PM - 08:00 PM", "08:00 PM - 09:00 PM", "09:00 PM - 10:00 PM"));
    List<String> dateArray = new ArrayList();
    //(Arrays.asList("2019.01.03", "2019.01.04", "2019.01.05", "2019.01.06"));
    private Button btnDone;
    ArrayList<String> dayname;
    private AppPrefes appPrefes;
    private ApiCalls apiCalls;
    private SimpleArcDialog dialog;
    String firstDateFromList = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        appPrefes = new AppPrefes(this);
        apiCalls = new ApiCalls();
        dialog = new SimpleArcDialog(this);
        dateArray.clear();
        clearTempData();


        dayname = new ArrayList<>();
        DateTime today = new DateTime();
        for (int i = 1; i < 8; i++) {

            if (Almosky.getInst().getDeliveryType().equals("2")) {
                DateTime tomorrow = today.plusDays(i + 1);
                String[] dates = String.valueOf(tomorrow).split("T");
                dateArray.add(dates[0]);


                DateTime.Property pDoW = today.plusDays(i + 1).dayOfWeek();
                dayname.add(pDoW.getAsText(Locale.getDefault()));
            } else {

                DateTime tomorrow = today.plusDays(i + 2);
                String[] dates = String.valueOf(tomorrow).split("T");
                dateArray.add(dates[0]);


                DateTime.Property pDoW = today.plusDays(i + 2).dayOfWeek();
                dayname.add(pDoW.getAsText(Locale.getDefault()));

            }


            // System.out.println("new date "+tomorrow);
            //  System.out.println(today.toString("yyyy-MM-dd"));
            //  System.out.println("day"+today.dayOfWeek().toString());
            // today.dayOfWeek().get
        }
        //  Almosky.getInst().setDeliverydaysname(dayname);
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
        //   Almosky.getInst().setDeliverydate(null);
        //     Almosky.getInst().setDeliverytime(null);


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
        btnDone = (Button) findViewById(R.id.btnDone);
       /* PickUpDateRecyclerViewAdapter mAdapterDate = new PickUpDateRecyclerViewAdapter(DeliveryActivity.this, dateArray, dayname, "delivery");
        RecyclerView.LayoutManager mLayoutManagerDate = new LinearLayoutManager(getApplicationContext());
        recyclerViewDates.setLayoutManager(mLayoutManagerDate);
        recyclerViewDates.setItemAnimator(new DefaultItemAnimator());
        recyclerViewDates.setAdapter(mAdapterDate);*/

        PickUpTimeRecyclerViewAdapter mAdapterTime;
        if (Almosky.getInst().isNisabClub()) {
            timeArray = new ArrayList<>(Arrays.asList(Almosky.getInst().getNasabTiming()));
        }
     /*   mAdapterTime = new PickUpTimeRecyclerViewAdapter(DeliveryActivity.this, timeArray, "delivery");

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
        getDays();
    }

    private void listeners() {
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DeliveryActivity.this, CategoryListActivity.class);
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

                    PickUpDateRecyclerViewAdapter mAdapterDate = new PickUpDateRecyclerViewAdapter(DeliveryActivity.this, dateArray, dayname, "delivery", this, userData.getDay());
                    RecyclerView.LayoutManager mLayoutManagerDate = new LinearLayoutManager(getApplicationContext());
                    recyclerViewDates.setLayoutManager(mLayoutManagerDate);
                    recyclerViewDates.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewDates.setAdapter(mAdapterDate);
                    Almosky.getInst().setDeliverydate(userData.getDay().get(0).getDate());
                    firstDateFromList = userData.getDay().get(0).getDate();
                    getTimings(userData.getDay().get(0).getTimeId(), true);

                } catch (Exception e) {

                    new SweetAlertDialog(DeliveryActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                    mAdapterTime = new PickUpTimeRecyclerViewAdapter(DeliveryActivity.this, timeArray, "delivery", this, userData.getResult());
                    RecyclerView.LayoutManager mLayoutManagerTime = new LinearLayoutManager(getApplicationContext());
                    recyclerViewTimes.setLayoutManager(mLayoutManagerTime);
                    recyclerViewTimes.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewTimes.setAdapter(mAdapterTime);
                    Almosky.getInst().setDeliverytime(userData.getResult().get(0).getFromTime() + " - " + userData.getResult().get(0).getToTime());
                } catch (Exception e) {

                    new SweetAlertDialog(DeliveryActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Data Error")
                            .show();
                    e.printStackTrace();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getTimings(int timeId, boolean isPickupSelected) {
        Log.d("Success-", "JSON:" + "Inside Timings");
        String url = "";
        RequestParams params = new RequestParams();
        params.put(Constants.email, appPrefes.getData(PrefConstants.email));
        params.put("timeId", timeId);
        if (isPickupSelected)
            params.put("pickuptime", Almosky.getInst().getPickupToTime());
        else
            params.put("pickuptime", "");
        if( Almosky.getInst().isNisabClub())
            url = ApiConstants.getNasabTimings;
        else
            url = ApiConstants.getTimings;
        Log.d("Success-", "JSON:" + "Inside Timings :" + params.toString());
        apiCalls.callApiPost(DeliveryActivity.this, params, dialog, url, 2);
    }

    private void getDays() {
        Log.d("Success-", "JSON:" + "Inside Days");
        RequestParams params = new RequestParams();

        params.put(Constants.email, appPrefes.getData(PrefConstants.email));
        params.put("pdate", Almosky.getInst().getPickupdate());
        if( Almosky.getInst().isNisabClub())
            params.put("deltype", 3);
        else
            params.put("deltype", Almosky.getInst().getDeliveryType());
        String url = ApiConstants.getDays;


        apiCalls.callApiPost(DeliveryActivity.this, params, dialog, url, 1);
    }

    @Override
    public void onClickedItem(day item) {
        if (item.getDate().equals(firstDateFromList))
            getTimings(item.getTimeId(), true);
        else
            getTimings(item.getTimeId(), false);
    }
}
