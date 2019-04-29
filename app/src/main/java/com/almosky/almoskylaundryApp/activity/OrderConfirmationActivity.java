package com.almosky.almoskylaundryApp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.almosky.almoskylaundryApp.Almosky;
import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.TabHostActivity;
import com.almosky.almoskylaundryApp.activity.payment.PaymentActivity;
import com.almosky.almoskylaundryApp.adapter.DryCleanRecyclerViewAdapter;
import com.almosky.almoskylaundryApp.adapter.IroningRecyclerViewAdapter;
import com.almosky.almoskylaundryApp.adapter.WashIronRecyclerViewAdapter;
import com.almosky.almoskylaundryApp.common.BaseActivity;
import com.almosky.almoskylaundryApp.databinding.ActivityOrderConfirmationBinding;
import com.almosky.almoskylaundryApp.model.Discount;
import com.almosky.almoskylaundryApp.model.OfferAndVatModel;
import com.almosky.almoskylaundryApp.model.OrderConfirmationModel;
import com.almosky.almoskylaundryApp.model.data;
import com.almosky.almoskylaundryApp.utils.AppPrefes;
import com.almosky.almoskylaundryApp.utils.api.ApiCalls;
import com.almosky.almoskylaundryApp.utils.constants.ApiConstants;
import com.almosky.almoskylaundryApp.utils.constants.Constants;
import com.almosky.almoskylaundryApp.utils.constants.PrefConstants;
import com.google.gson.Gson;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class OrderConfirmationActivity extends BaseActivity {

    private AppPrefes appPrefes;
    private ActivityOrderConfirmationBinding binding;
    private DryCleanRecyclerViewAdapter dryCleanRecyclerViewAdapter;
    private WashIronRecyclerViewAdapter washIronRecyclerViewAdapter;
    private IroningRecyclerViewAdapter ironingRecyclerViewAdapter;
    private OrderConfirmationModel model;

    SimpleArcDialog dialog;
    ApiCalls apiCalls;

    private double adminDiscount = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_confirmation);
        model = new OrderConfirmationModel();
        binding.setModel(model);


        ImageView backButton = findViewById(R.id.backArrow);
        backButton.setVisibility(View.VISIBLE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        appPrefes=new AppPrefes(this);
        apiCalls=new ApiCalls();
        dialog=new SimpleArcDialog(this);
        binding.textPickUpDate.setText(Almosky.getInst().getPickupdate());
        binding.textPickUpTime.setText(Almosky.getInst().getPickuptime());
        binding.textDeliveryDate.setText(Almosky.getInst().getDeliverydate());
        binding.textDeliveryTime.setText(Almosky.getInst().getDeliverytime());
        binding.textAddress.setText(Almosky.getInst().getAddress());

        getOfferAndVatData();


        if(Almosky.getInst().getOrderType().equals("enter")){


            binding.easyOrderDetailsLayout.setVisibility(View.GONE);
            setDryCleanAdapter();
            setWashIronAdapter();
            setIroningAdapter();
            updateTotal();
        }
        if(Almosky.getInst().getOrderType().equals("easy")){

            binding.detailsLayout.setVisibility(View.GONE);
            binding.lytTotal.setVisibility(View.GONE);


        }

        if(Almosky.getInst().getDeliveryType().equals("1")){
            binding.textDeliveryType.setText("Delivery Type :  Normal");
        }
        if(Almosky.getInst().getDeliveryType().equals("2")){
            binding.textDeliveryType.setText("Delivery Type :  Fast");
        }


        Button submit=(Button)findViewById(R.id.btnPlaceOrder);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Almosky.getInst().getOrderType().equals("easy")){
                    submitEasyOrder();
                }
                if(Almosky.getInst().getOrderType().equals("enter")){

                    if(Almosky.getInst().isOffer()){
                        String splitAmount[]=binding.subtotalPrice.getText().toString().split("AED");
                        Double amount=Double.valueOf((splitAmount[0]));
                        double vat=(amount*0.05);
                        double subtotal= amount+vat;

                        double discount=(subtotal*0.3);
                        double discountAmount=subtotal-(subtotal*0.3);

                        if(amount<Almosky.getInst().getNasabAmount()){


                            new SweetAlertDialog(OrderConfirmationActivity.this, SweetAlertDialog.WARNING_TYPE)

                                   .setContentText("Minimum Order Amount Must be AED"+Almosky.getInst().getNasabAmount())
                                   .show();


                        }else {
                            new SweetAlertDialog(OrderConfirmationActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Payment")


                                    .setCancelText("PayNow")
                                    .setConfirmText("CashOnDelivery")
                                    .showCancelButton(true)
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                            submitNormalOrder();
                                        }
                                    })
                                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.cancel();
                                       /*     Intent go=new Intent(OrderConfirmationActivity.this,PaymentActivity.class);
                                            go.putExtra("amount", binding.subtotalPrice.getText().toString());
                                            startActivity(go); */
                                            submitNormalOrderForPayment();


                                        }

                                    })
                                    .show();

                        }

                    }else{


                        String splitAmount[]=binding.subtotalPrice.getText().toString().split("AED");
                        Double amount=Double.valueOf((splitAmount[0]));
                        double vat=(amount*0.05);
                        double subtotal= amount+vat;

                        double discount=(subtotal*0.3);
                        double discountAmount=subtotal-(subtotal*0.3);



                        if(amount<Almosky.getInst().getMinAmount()){


                            new SweetAlertDialog(OrderConfirmationActivity.this, SweetAlertDialog.WARNING_TYPE)

                                    .setContentText("Minimum Order Amount Must be AED"+Almosky.getInst().getMinAmount())
                                   .show();


                        }else {
                            new SweetAlertDialog(OrderConfirmationActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Payment")


                                    .setCancelText("PayNow")
                                    .setConfirmText("CashOnDelivery")
                                    .showCancelButton(true)
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                            submitNormalOrder();
                                        }
                                    })
                                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.cancel();
                                         /*   Intent go=new Intent(OrderConfirmationActivity.this, PaymentActivity.class);
                                            startActivity(go);*/

                                            submitNormalOrderForPayment();

                                        }
                                    })
                                    .show();
                        }
                    }

                }
            }
        });


    }



    private void getOfferAndVatData() {

        RequestParams params = new RequestParams();

        // params.put("email",  appPrefes.getData(PrefConstants.email));

        // params.put(ApiConstants.uid, appPrefes.getData(PrefConstants.uid));
        // params.put(ApiConstants.uid, 1);
        //  params.put(ApiConstants.status, "Pending");


        String url = ApiConstants.general;
        apiCalls.callApiGet(OrderConfirmationActivity.this, dialog, url, 77);


    }
    private void getDiscount() {

        RequestParams params = new RequestParams();

        params.put("email",  appPrefes.getData(PrefConstants.email));


        String url = ApiConstants.getDiscount;
        apiCalls.callApiPost(OrderConfirmationActivity.this,params, dialog, url, 1);


    }
    public static String RoundFloatByTwo(Float amount){

        return String.format("%.2f",amount);

    }

    private void submitNormalOrderForPayment() {

        ArrayList<data.Detail.Item> dry = Almosky.getInst().getDrycleanList();
        ArrayList<data.Detail.Item> wash = Almosky.getInst().getWashList();
        ArrayList<data.Detail.Item> iron = Almosky.getInst().getIronList();


        if (null != wash || null != dry || null != iron) {

            try {

                JSONArray mainArray = new JSONArray();
                JSONObject object = new JSONObject();


                object.put(Constants.email, appPrefes.getData(PrefConstants.email));
                object.put("pickdate", Almosky.getInst().getPickupdate());
                object.put("picktime", Almosky.getInst().getPickuptime());
                //  object.put("orderDate", "2014-12-5");

                //  object.put("dedate", appPrefes.getData("orderTime"));
                //  object.put("orderTime", "6:30:00");


                object.put("deldate", Almosky.getInst().getDeliverydate());
                // object.put("delDate", "2014-12-5");
                object.put("deltime", Almosky.getInst().getDeliverytime());
                //object.put("delTime", "6:30:00");
                object.put("totalamount", binding.subtotalPrice.getText().toString());

                object.put("addressId", Almosky.getInst().getAddressId());
                object.put("deliveryType", Almosky.getInst().getDeliveryType());
                object.put("itemAmount",binding.totalPrice.getText().toString());
                object.put("nasabDiscountAmount",binding.tvDiscountNisab.getText().toString());
                object.put("vatAmount",binding.vattotalPrice.getText().toString());
                //object.put("remarksremarks", binding.edtNote.getText().toString());
                object.put("PaymentMode", "Online");


                JSONArray jsonArray3 = new JSONArray();

                if (null != dry) {

                    for (int i = 0; i < dry.size(); i++) {

                        JSONObject object3 = new JSONObject();
                        object3.put("ItemId", dry.get(i).getItemId());
                        object3.put("ServiceId", 1);
                        object3.put("Qty", dry.get(i).getItemcount());
                        object3.put("Price", dry.get(i).getAmount());
                        //  object3.put("slno", "1");
                        object3.put("Item_Name", dry.get(i).getItemName());

                        jsonArray3.put(object3);

                    }

                    //  object.put("Item",jsonArray3);
                }
                if (null != wash) {

                    for (int i = 0; i < wash.size(); i++) {

                        JSONObject object3 = new JSONObject();
                        object3.put("ItemId", wash.get(i).getItemId());
                        object3.put("ServiceId", 2);
                        object3.put("Qty", wash.get(i).getItemcount());
                        object3.put("Price", wash.get(i).getAmount());
                        //  object3.put("slno", "1");
                        object3.put("Item_Name", wash.get(i).getItemName());


                        jsonArray3.put(object3);

                    }

                    //  object.put("Item",jsonArray3);
                }
                if (null != iron) {

                    for (int i = 0; i < iron.size(); i++) {

                        JSONObject object3 = new JSONObject();
                        object3.put("ItemId", iron.get(i).getItemId());
                        object3.put("ServiceId", 3);
                        object3.put("Qty", iron.get(i).getItemcount());
                        object3.put("Price", iron.get(i).getAmount());
                        //  object3.put("slno", "1");
                        object3.put("Item_Name", iron.get(i).getItemName());


                        jsonArray3.put(object3);

                    }

                    //  object.put("Item",jsonArray3);
                }


                object.put("orders", jsonArray3);

                String Data = object.toString();





                StringEntity entity = null;
                final SimpleArcDialog dialog = new SimpleArcDialog(OrderConfirmationActivity.this);
                try {
                    entity = new StringEntity(Data.toString());
                    entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    dialog.show();
                } catch (Exception e) {
//Exception
                }

                String url = ApiConstants.BaseUrl + ApiConstants.normalorderUrl;

                new AsyncHttpClient().post(null, url, entity, "application/json", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                        try {
                            dialog.dismiss();
                            String object = new String(responseBody);
                            JSONObject jsonObject = new JSONObject(object);
                            String result = jsonObject.getString("result");
                            String orderId = jsonObject.getString("order_id");

                            if (result.equals("Data Inserted")) {

                                Almosky.getInst().setIronList(null);
                                Almosky.getInst().setCartcount(0);
                                Almosky.getInst().setWashList(null);
                                Almosky.getInst().setDrycleanList(null);
                                Almosky.getInst().setCartamount(0);
                                // Almosky.getInst().setServiceId(0);
                                Almosky.getInst().setAddress("");
                                Almosky.getInst().setNisabClub(false);

//                                                Intent go = new Intent(OrderConfirmationActivity.this, TabHostActivity.class);
//                                                go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                                startActivity(go);

                                Intent go=new Intent(OrderConfirmationActivity.this,PaymentActivity.class);
                                go.putExtra("amount", binding.subtotalPrice.getText().toString());
                                go.putExtra("orderData",Data);
                                go.putExtra("orderId", orderId);
                                startActivity(go);

//                                new SweetAlertDialog(OrderConfirmationActivity.this, SweetAlertDialog.NORMAL_TYPE)
//                                        .setTitleText("Success")
//                                        .setContentText("Success")
//                                        .setConfirmText("Ok")
//                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                            @Override
//                                            public void onClick(SweetAlertDialog sDialog) {
//
//
//                                                sDialog.dismissWithAnimation();
//
//
//
//
//                                            }
//                                        })
//                                        .show();
                                // Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "msg", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();

                Toast.makeText(getApplicationContext(), "msg", Toast.LENGTH_LONG).show();

            }
        }

    }

    private void submitNormalOrder() {

        ArrayList<data.Detail.Item> dry = Almosky.getInst().getDrycleanList();
        ArrayList<data.Detail.Item> wash = Almosky.getInst().getWashList();
        ArrayList<data.Detail.Item> iron = Almosky.getInst().getIronList();


        if (null != wash || null != dry || null != iron) {

            try {

                JSONArray mainArray = new JSONArray();
                JSONObject object = new JSONObject();


                object.put(Constants.email, appPrefes.getData(PrefConstants.email));
                object.put("pickdate", Almosky.getInst().getPickupdate());
                object.put("picktime", Almosky.getInst().getPickuptime());
                //  object.put("orderDate", "2014-12-5");

                //  object.put("dedate", appPrefes.getData("orderTime"));
                //  object.put("orderTime", "6:30:00");


                object.put("deldate", Almosky.getInst().getDeliverydate());
                // object.put("delDate", "2014-12-5");
                object.put("deltime", Almosky.getInst().getDeliverytime());
                //object.put("delTime", "6:30:00");
                object.put("totalamount", binding.subtotalPrice.getText().toString());

                object.put("addressId", Almosky.getInst().getAddressId());
                object.put("deliveryType", Almosky.getInst().getDeliveryType());
                object.put("itemAmount",binding.totalPrice.getText().toString());
                object.put("nasabDiscountAmount",binding.tvDiscountNisab.getText().toString());
                object.put("vatAmount",binding.vattotalPrice.getText().toString());
               //object.put("remarks", binding.edtNote.getText().toString());
                object.put("remarks", binding.edtNote.getText().toString());
                object.put("PaymentMode", "COD");

                JSONArray jsonArray3 = new JSONArray();

                if (null != dry) {

                    for (int i = 0; i < dry.size(); i++) {

                        JSONObject object3 = new JSONObject();
                        object3.put("ItemId", dry.get(i).getItemId());
                        object3.put("ServiceId", 1);
                        object3.put("Qty", dry.get(i).getItemcount());
                        object3.put("Price", dry.get(i).getAmount());
                        //  object3.put("slno", "1");
                        object3.put("Item_Name", dry.get(i).getItemName());

                        jsonArray3.put(object3);

                    }

                    //  object.put("Item",jsonArray3);
                }
                if (null != wash) {

                    for (int i = 0; i < wash.size(); i++) {

                        JSONObject object3 = new JSONObject();
                        object3.put("ItemId", wash.get(i).getItemId());
                        object3.put("ServiceId", 2);
                        object3.put("Qty", wash.get(i).getItemcount());
                        object3.put("Price", wash.get(i).getAmount());
                        //  object3.put("slno", "1");
                        object3.put("Item_Name", wash.get(i).getItemName());


                        jsonArray3.put(object3);

                    }

                    //  object.put("Item",jsonArray3);
                }
                if (null != iron) {

                    for (int i = 0; i < iron.size(); i++) {

                        JSONObject object3 = new JSONObject();
                        object3.put("ItemId", iron.get(i).getItemId());
                        object3.put("ServiceId", 3);
                        object3.put("Qty", iron.get(i).getItemcount());
                        object3.put("Price", iron.get(i).getAmount());
                        //  object3.put("slno", "1");
                        object3.put("Item_Name", iron.get(i).getItemName());


                        jsonArray3.put(object3);

                    }

                    //  object.put("Item",jsonArray3);
                }


                object.put("orders", jsonArray3);

                String Data = object.toString();



                StringEntity entity = null;
                final SimpleArcDialog dialog = new SimpleArcDialog(OrderConfirmationActivity.this);
                try {
                    entity = new StringEntity(Data.toString());
                    entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    dialog.show();
                } catch (Exception e) {
//Exception
                }

                String url = ApiConstants.BaseUrl + ApiConstants.normalorderUrl;

                new AsyncHttpClient().post(null, url, entity, "application/json", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                        try {
                            dialog.dismiss();
                            String object = new String(responseBody);
                            JSONObject jsonObject = new JSONObject(object);
                            String result = jsonObject.getString("result");


                            if (result.equals("Data Inserted")) {
                                new SweetAlertDialog(OrderConfirmationActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                        .setTitleText("Your request has been submitted")
                                        .setContentText("CHECK ”ORDERS ” SECTION FOR YOUR ORDER UPDATES")
                                        .setConfirmText("Ok")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {


                                                sDialog.dismissWithAnimation();

                                                Almosky.getInst().setIronList(null);
                                                Almosky.getInst().setCartcount(0);
                                                Almosky.getInst().setWashList(null);
                                                Almosky.getInst().setDrycleanList(null);
                                                Almosky.getInst().setCartamount(0);
                                                // Almosky.getInst().setServiceId(0);
                                                Almosky.getInst().setAddress("");
                                                Almosky.getInst().setNisabClub(false);

                                                Intent go = new Intent(OrderConfirmationActivity.this, TabHostActivity.class);
                                               // Intent go = new Intent(OrderConfirmationActivity.this, PaymentActivity.class);
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

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();

                Toast.makeText(getApplicationContext(), "msg", Toast.LENGTH_LONG).show();

            }
        }

    }

    private void submitEasyOrder() {





        // if (!validate(view.getResources())) return;
        RequestParams params = new RequestParams();

        params.put(Constants.email, appPrefes.getData(PrefConstants.email));
        params.put("pickdate", Almosky.getInst().getPickupdate());
        params.put("picktime", Almosky.getInst().getPickuptime());
        params.put("deldate", Almosky.getInst().getDeliverydate());
        params.put("deltime", Almosky.getInst().getDeliverytime());
        params.put("addressId", Almosky.getInst().getAddressId());
        params.put("remarks", binding.edtNote.getText().toString());
        params.put("orderTypeId", 1);
        params.put("deliveryType",Almosky.getInst().getDeliveryType());

        String url = ApiConstants.easyorderUrl;
        apiCalls.callApiPost(OrderConfirmationActivity.this, params, dialog, url,7);
/*
             JSONArray mainArray = new JSONArray();
            JSONObject object = new JSONObject();
            object.put(Constants.email,appPrefes.getData(PrefConstants.email));
            object.put("pickdate", Almosky.getInst().getPickupdate());
            object.put("picktime", Almosky.getInst().getPickuptime());

            object.put("delDate", Almosky.getInst().getDeliverydate());
            // object.put("delDate", "2014-12-5");
            object.put("delTime", Almosky.getInst().getDeliverytime());
            //object.put("delTime", "6:30:00");
            object.put("addressId", 1);
            object.put("orderNote","note");
            object.put("orderTypeId",1);

            String Data = object.toString();


            StringEntity entity = null;
            final SimpleArcDialog dialog=new SimpleArcDialog(OrderConfirmationActivity.this);
            try {
                entity = new StringEntity(Data);
                entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                dialog.show();
            } catch(Exception e) {
//Exception
            }

            String url= ApiConstants.BaseUrl+ApiConstants.easyorderUrl;

            new AsyncHttpClient().post(null,url,entity,"application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                    try {
                        dialog.dismiss();
                        String object= new String(responseBody);
                        JSONObject jsonObject = new JSONObject(object);
                        String result = jsonObject.getString("status");

                        if(result.equals("true"))
                        {
                            new SweetAlertDialog(OrderConfirmationActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("Success")
                                    .setContentText("Success")
                                    .setConfirmText("Ok")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {



                                            sDialog.dismissWithAnimation();

                                             Intent go=new Intent(OrderConfirmationActivity.this, TabHostActivity.class);
                                            go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

                }
            });




        }catch (Exception e){
            e.printStackTrace();

                        Toast.makeText(getApplicationContext(), "msg", Toast.LENGTH_LONG).show();

                    }
*/
    }


    @Override
    public void getResponse(String response, int requestId) {

        if(requestId==7) {
            try {

                String object = new String(response);
                JSONObject jsonObject = new JSONObject(object);
                String result = jsonObject.getString("status");

                if (result.equals("true")) {
                    new SweetAlertDialog(OrderConfirmationActivity.this, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("Your request has been submitted")
                            .setContentText(" CHECK ”ORDERS ” SECTION FOR YOUR ORDER UPDATES")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {

                                    Almosky.getInst().setIronList(null);
                                    Almosky.getInst().setCartcount(0);
                                    Almosky.getInst().setWashList(null);
                                    Almosky.getInst().setDrycleanList(null);
                                    Almosky.getInst().setCartamount(0);
                                    Almosky.getInst().setIroningpriceList(null);
                                    Almosky.getInst().setWashironpriceList(null);
                                    Almosky.getInst().setDrycleanpriceList(null);
                                    Almosky.getInst().setDeliveryType(null);
                                    Almosky.getInst().setAddressId(0);
                                    Almosky.getInst().setPickupToTime(null);
                                    Almosky.getInst().setDeliverytime(null);
                                    Almosky.getInst().setAddressType(null);
                                    Almosky.getInst().setCategoryList(null);
                                    Almosky.getInst().setPickuptime(null);
                                    Almosky.getInst().setDeliverytime(null);
                                    Almosky.getInst().setDeliverydate(null);
                                    Almosky.getInst().setDelserviceType(0);
                                    Almosky.getInst().setDelserviceType(0);
                                    Almosky.getInst().setPickupdate(null);
                                    Almosky.getInst().setPickuptime(null);
                                    Almosky.getInst().setCartamount(0);
                                    Almosky.getInst().setCartcount(0);
                                    Almosky.getInst().setAddress(null);
                                    Almosky.getInst().setOrderType(null);
                                    Almosky.getInst().setSelectedAddress(null);
                                    Almosky.getInst().setNasabRate(0);
                                    Almosky.getInst().setVatRate(0);
                                    Almosky.getInst().setAdminDiscount(0);

                                    //   Almosky.getInst().setServiceId(0);


                                    sDialog.dismissWithAnimation();

                                    Intent go = new Intent(OrderConfirmationActivity.this, TabHostActivity.class);
                                    go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(go);


                                }
                            })
                            .show();
                    // Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                // Toast.makeText(getApplicationContext(), "msg", Toast.LENGTH_LONG).show();
            }
        }
        if (requestId == 77) {

            try {

                final OfferAndVatModel offerData;
                Gson gson = new Gson();
                offerData = gson.fromJson(response, OfferAndVatModel.class);

                if(offerData.getStatus()){
                    Almosky.getInst().setNasabRate(offerData.getResult().get(0).getNASABDISCPER());
                    Almosky.getInst().setVatRate(offerData.getResult().get(0).getVATPer());
                }


            } catch (Exception e) {

            }


        }

        if (requestId == 1) {

            try {

                final Discount discount;
                Gson gson = new Gson();
                discount = gson.fromJson(response, Discount.class);

                if(discount.getStatus().equalsIgnoreCase("success")){
                    Almosky.getInst().setAdminDiscount(discount.getDiscount());

                }


            } catch (Exception e) {

            }


        }

    }

    private void setDryCleanAdapter() {

        if(null!= Almosky.getInst().getDrycleanList()){

            dryCleanRecyclerViewAdapter = new DryCleanRecyclerViewAdapter(OrderConfirmationActivity.this, Almosky.getInst().getDrycleanList(),this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(OrderConfirmationActivity.this);
            binding.dryCleanRecyclerView.setNestedScrollingEnabled(false);
            binding.dryCleanRecyclerView.setLayoutManager(mLayoutManager);
            binding.dryCleanRecyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.dryCleanRecyclerView.setAdapter(dryCleanRecyclerViewAdapter);
        }
        else {
            binding.dryCleanLayout.setVisibility(View.GONE);

        }

    }

    private void setWashIronAdapter() {

        if(null!=Almosky.getInst().getWashList()){

            washIronRecyclerViewAdapter = new WashIronRecyclerViewAdapter(OrderConfirmationActivity.this,Almosky.getInst().getWashList(),this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(OrderConfirmationActivity.this);
            binding.washIronRecyclerView.setNestedScrollingEnabled(false);
            binding.washIronRecyclerView.setLayoutManager(mLayoutManager);
            binding.washIronRecyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.washIronRecyclerView.setAdapter(washIronRecyclerViewAdapter);
        }else {

            binding.washIronLayout.setVisibility(View.GONE);
        }

    }
    private void setIroningAdapter() {

        if(null!=Almosky.getInst().getIronList()){

            ironingRecyclerViewAdapter = new IroningRecyclerViewAdapter(OrderConfirmationActivity.this,Almosky.getInst().getIronList(),this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(OrderConfirmationActivity.this);
            binding.ironingRecyclerView.setNestedScrollingEnabled(false);
            binding.ironingRecyclerView.setLayoutManager(mLayoutManager);
            binding.ironingRecyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.ironingRecyclerView.setAdapter(ironingRecyclerViewAdapter);

        }else{
            binding.ironingLayout.setVisibility(View.GONE);

        }

    }

    public void updateTotal() {
        double drycount=0,dryamount=0,washcount=0,washamount=0,ironcount=0,ironamount=0;
        ArrayList<data.Detail.Item> dry=Almosky.getInst().getDrycleanList();
        ArrayList<data.Detail.Item> wash=Almosky.getInst().getWashList();
        ArrayList<data.Detail.Item> iron=Almosky.getInst().getIronList();



        if(null!=dry){
            for (int i=0;i<dry.size();i++){
                drycount=drycount+dry.get(i).getItemcount();
                dryamount=dryamount+ Double.parseDouble(dry.get(i).getTotal());
            }

        }
        if(null!=wash){
            for (int i=0;i<wash.size();i++){

                washcount=washcount+wash.get(i).getItemcount();
                washamount=washamount+ Double.parseDouble(wash.get(i).getTotal());
            }

        }
        if(null!=iron){

            for (int i=0;i<iron.size();i++) {
                ironcount = ironcount + iron.get(i).getItemcount();
                ironamount=ironamount+ Double.parseDouble(iron.get(i).getTotal());
            }

        }

        double totalcount=drycount+washcount+ironcount;
        double totalamount=dryamount+washamount+ironamount;
        double vat=(totalamount*(Almosky.getInst().getVatRate()*0.01));
        double subtotal=0.0;

        if(Almosky.getInst().isOffer()){

            subtotal= Double.valueOf(totalamount);

        }else {
            subtotal= Double.valueOf(totalamount)+vat;
        }
        //double subtotal1= Double.valueOf(totalamount)+vat;
        //double subtotal= Double.valueOf(totalamount)+vat;

        double discount=(subtotal*(Almosky.getInst().getNasabRate()*0.01));
        double discountAmount=subtotal-(subtotal*(Almosky.getInst().getNasabRate()*0.01));
        if(Almosky.getInst().getAdminDiscount() > 0)
            adminDiscount=(subtotal*(Almosky.getInst().getAdminDiscount()*0.01));

        Double roundedAmount= new BigDecimal(discountAmount).setScale(2, RoundingMode.HALF_UP).doubleValue();

        if(totalcount==0) {

            binding.totalPrice.setText("0"+"AED");
            //binding.totalCount.setText("0");
            binding.vattotalPrice.setVisibility(View.INVISIBLE);

            binding.subtotalPrice.setText("0"+"AED");


        }
        else if(totalamount>0 && totalcount>0){
            binding.totalPrice.setText(String.valueOf(totalamount).toString()+"AED");
            // binding.totalCount.setText(String.valueOf(totalcount));
            binding.vattotalPrice.setText(PerfectDecimal(String.valueOf((vat)),6,2)+"AED");

            if(Almosky.getInst().isOffer()){

                binding.lytDiscount.setVisibility(View.VISIBLE);
                binding.tvDiscountNisab.setText(PerfectDecimal(String.valueOf(discount).toString(),6,2));
                //  binding.tvDiscountNisab.setText(String.valueOf(discount).toString());
                discountAmount=discountAmount+vat;
                binding.subtotalPrice.setText(PerfectDecimal(String.valueOf(discountAmount).toString(),6,2)+"AED");
                // binding.subtotalPrice.setText(String.valueOf(discountAmount).toString()+"AED");



            }else {
                if(adminDiscount > 0) {
                    binding.lytDiscount1.setVisibility(View.VISIBLE);
                    binding.tvDiscount.setText(PerfectDecimal(String.valueOf(adminDiscount).toString(),6,2));
                    subtotal = subtotal - adminDiscount;
                }
                binding.subtotalPrice.setText(String.valueOf(subtotal).toString()+"AED");
            }

        }else{

        }
/*
        int drycount=0,dryamount=0,washcount=0,washamount=0,ironcount=0,ironamount=0;
        ArrayList<data.Detail.Item> dry=Almosky.getInst().getDrycleanList();
        ArrayList<data.Detail.Item> wash=Almosky.getInst().getWashList();
        ArrayList<data.Detail.Item> iron=Almosky.getInst().getIronList();



        if(null!=dry){
            for (int i=0;i<dry.size();i++){
                drycount=drycount+dry.get(i).getItemcount();
                dryamount=dryamount+ Integer.parseInt(dry.get(i).getTotal());
            }

        }
        if(null!=wash){
            for (int i=0;i<wash.size();i++){

                washcount=washcount+wash.get(i).getItemcount();
                washamount=washamount+ Integer.parseInt(wash.get(i).getTotal());
            }

        }
        if(null!=iron){

            for (int i=0;i<iron.size();i++) {
                ironcount = ironcount + iron.get(i).getItemcount();
                ironamount=ironamount+ Integer.parseInt(iron.get(i).getTotal());
            }

        }

        int totalcount=drycount+washcount+ironcount;
        int totalamount=dryamount+washamount+ironamount;
        double vat=(totalamount*0.05);
        double subtotal= Double.valueOf(totalamount)+vat;

        double discount=(subtotal*0.3);
        double discountAmount=subtotal-(subtotal*0.3);

        Double roundedAmount= new BigDecimal(discountAmount).setScale(2, RoundingMode.HALF_UP).doubleValue();

        if(totalcount==0) {

            binding.totalPrice.setText("0"+"AED");
            //binding.totalCount.setText("0");
            binding.vattotalPrice.setVisibility(View.INVISIBLE);

            binding.subtotalPrice.setText("0"+"AED");


        }
        else if(totalamount>0 && totalcount>0){
            binding.totalPrice.setText(String.valueOf(totalamount).toString()+"AED");
           // binding.totalCount.setText(String.valueOf(totalcount));
            binding.vattotalPrice.setText(PerfectDecimal(String.valueOf((vat)),6,2)+"AED");

            if(Almosky.getInst().isOffer()){

                binding.lytDiscount.setVisibility(View.VISIBLE);
                binding.tvDiscountNisab.setText(PerfectDecimal(String.valueOf(discount).toString(),6,2));
              //  binding.tvDiscountNisab.setText(String.valueOf(discount).toString());
                binding.subtotalPrice.setText(PerfectDecimal(String.valueOf(discountAmount).toString(),6,2)+"AED");
               // binding.subtotalPrice.setText(String.valueOf(discountAmount).toString()+"AED");



            }else {
                binding.subtotalPrice.setText(String.valueOf(subtotal).toString()+"AED");
            }


        }else{

        }
        */

    }

    public static String PerfectDecimal(String str, int MAX_BEFORE_POINT, int MAX_DECIMAL){
        if(str.charAt(0) == '.') str = "0"+str;
        int max = str.length();

        String rFinal = "";
        boolean after = false;
        int i = 0, up = 0, decimal = 0; char t;
        while(i < max){
            t = str.charAt(i);
            if(t != '.' && after == false){
                up++;
                if(up > MAX_BEFORE_POINT) return rFinal;
            }else if(t == '.'){
                after = true;
            }else{
                decimal++;
                if(decimal > MAX_DECIMAL)
                    return rFinal;
            }
            rFinal = rFinal + t;
            i++;
        }return rFinal;
    }


}
