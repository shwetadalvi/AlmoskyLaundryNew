package com.almosky.almoskylaundryApp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.common.BaseActivity;
import com.almosky.almoskylaundryApp.model.OrderListdto;
import com.almosky.almoskylaundryApp.utils.AppPrefes;
import com.almosky.almoskylaundryApp.utils.api.ApiCalls;
import com.almosky.almoskylaundryApp.utils.constants.ApiConstants;
import com.almosky.almoskylaundryApp.utils.constants.PrefConstants;
import com.google.gson.Gson;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.RequestParams;

import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class OrderDetailsActivity1 extends BaseActivity {
    AppPrefes appPrefes;
    ApiCalls apiCalls;
    SimpleArcDialog dialog;
    RecyclerView rvOrders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);
        rvOrders=(RecyclerView)findViewById(R.id.rvorderList);
        ImageView backButton = findViewById(R.id.backArrow);
        backButton.setVisibility(View.VISIBLE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        getOrders();
    }



    private void getOrders() {

       //  if (!validate(view.getResources())) return;
        RequestParams params = new RequestParams();

        params.put("email",  appPrefes.getData(PrefConstants.email));
        params.put("status",  "All");

        params.put(ApiConstants.uid, appPrefes.getData(PrefConstants.uid));
         params.put(ApiConstants.uid, 1);
          params.put(ApiConstants.status, "Pending");


        String url = ApiConstants.orderListUrl;
        apiCalls.callApiPost(OrderDetailsActivity1.this, params, dialog, url, 1);





    }

    @Override
    public void getResponse(String response, int requestId) {

        try{
            Gson gson = new Gson();
            final OrderListdto orderList = gson.fromJson(response, OrderListdto.class);
            if(null!=orderList.getResult()){

                new SweetAlertDialog(OrderDetailsActivity1.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Empty")
                        .setContentText("No Orders")
                        .setConfirmText("Ok")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {



                                sDialog.dismissWithAnimation();


                            }
                        })
                        .show();

            }else {
            /*    OrderListAdapter mAdapter = new OrderListAdapter(OrderDetailsActivity.this, orderList.getResult());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                rvOrders.setLayoutManager(mLayoutManager);
                rvOrders.setItemAnimator(new DefaultItemAnimator());
                rvOrders.setAdapter(mAdapter);*/

            }


        }catch (Exception e){

        }





            }
        }




