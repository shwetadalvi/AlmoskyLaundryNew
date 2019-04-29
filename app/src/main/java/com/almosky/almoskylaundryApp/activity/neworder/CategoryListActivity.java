package com.almosky.almoskylaundryApp.activity.neworder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.almosky.almoskylaundryApp.Almosky;
import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.activity.OrderConfirmationActivity;
import com.almosky.almoskylaundryApp.activity.SignupOrLoginActivity;
import com.almosky.almoskylaundryApp.common.BaseActivity;
import com.almosky.almoskylaundryApp.model.OfferAndVatModel;
import com.almosky.almoskylaundryApp.model.categorydto;
import com.almosky.almoskylaundryApp.model.data;
import com.almosky.almoskylaundryApp.model.discountdto;
import com.almosky.almoskylaundryApp.utils.api.ApiCalls;
import com.almosky.almoskylaundryApp.utils.constants.ApiConstants;
import com.almosky.almoskylaundryApp.utils.constants.PrefConstants;
import com.google.gson.Gson;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.RequestParams;
import com.mindorks.placeholderview.ExpandablePlaceHolderView;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class CategoryListActivity extends BaseActivity {

    private Context mContext;
    private ExpandablePlaceHolderView mExpandableView;
    private ExpandablePlaceHolderView.OnScrollListener mOnScrollListener;
    private boolean mIsLoadingMore = false;
    private boolean mNoMoreToLoad = false;
    ApiCalls apiCalls;
    SimpleArcDialog dialog;
    public static final int CATEGORIES = 10;
    private TextView title;
    private RelativeLayout bottomLayout;

    public TextView cartcount, cartamount;
    ArrayList<data.Detail.Item> dry;
    ArrayList<data.Detail.Item> wash;
    ArrayList<data.Detail.Item> iron;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if(appPrefes.getBoolData(PrefConstants.isLogin)){





            if(Almosky.getInst().getOrderType().equals("easy")){
                Intent go=new Intent(CategoryListActivity.this,OrderConfirmationActivity.class);
                startActivity(go);
                finish();
            }else {
                apiCalls = new ApiCalls();
                dialog = new SimpleArcDialog(this);

                getDiscountData();
                getOfferAndVatData();

                ImageView backButton = findViewById(R.id.backArrow);
                backButton.setVisibility(View.VISIBLE);

                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

                bottomLayout = (RelativeLayout) findViewById(R.id.bottomLayout);

                dry = Almosky.getInst().getDrycleanList();
                wash = Almosky.getInst().getWashList();
                iron = Almosky.getInst().getIronList();

                if (null != dry || null != wash || null != iron) {
                    checkCart();
                }


                bottomLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(Almosky.getInst().isOffer()){
                            String splitAmount[]=cartamount.getText().toString().split("AED");
                            Double amount=Double.valueOf((splitAmount[1]));
                            float vatAmount = (float) (Almosky.getInst().getVatRate()/100);
                            double vat=(amount*vatAmount);
                            double subtotal= amount+vat;

                            if(Almosky.getInst().isOffer()){
                                subtotal=amount;
                            }

                            float nasabDiscount = (float) (Almosky.getInst().getNasabRate()/100);
                            double discount=(subtotal*nasabDiscount);
                            double discountAmount=subtotal-(subtotal*nasabDiscount);



                            if(discountAmount<Almosky.getInst().getNasabAmount()){


                                new SweetAlertDialog(CategoryListActivity.this, SweetAlertDialog.WARNING_TYPE)

                                        .setContentText("Minimum Order Amount Must be AED"+Almosky.getInst().getNasabAmount())
                                        .show();


                            }else {
                                Intent intent = new Intent(CategoryListActivity.this, OrderConfirmationActivity.class);

                                startActivity(intent);
                            }

                        }else {
                            String splitAmount[]=cartamount.getText().toString().split("AED");



                            Double amount=Double.valueOf((splitAmount[1]));


                            if(amount<Almosky.getInst().getMinAmount()){
                                new SweetAlertDialog(CategoryListActivity.this, SweetAlertDialog.WARNING_TYPE)

                                        .setContentText("Minimum Order Amount Must be AED"+Almosky.getInst().getMinAmount())
                                        .show();


                            }else{
                                Intent intent = new Intent(CategoryListActivity.this, OrderConfirmationActivity.class);

                                startActivity(intent);
                            }

                        }




                    }
                });


                getCategoryList();
                mContext = this.getApplicationContext();
                mExpandableView = (ExpandablePlaceHolderView) findViewById(R.id.expandableView);

            }




        }else {
            startActivity(new Intent(CategoryListActivity.this, SignupOrLoginActivity.class));

        }



//        setLoadMoreListener(mExpandableView);
    }
    private void getDiscountData() {
//
        //          String token = appPrefes.getData(PrefConstants.token);
        RequestParams params = new RequestParams();

        String url = ApiConstants.discountUrl;
        apiCalls.callApiPost(this, params, dialog, url, 777);

    }

    private void getOfferAndVatData() {

        RequestParams params = new RequestParams();

        // params.put("email",  appPrefes.getData(PrefConstants.email));

        // params.put(ApiConstants.uid, appPrefes.getData(PrefConstants.uid));
        // params.put(ApiConstants.uid, 1);
        //  params.put(ApiConstants.status, "Pending");


        String url = ApiConstants.general;
        apiCalls.callApiGet(CategoryListActivity.this, dialog, url, 77);


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

    private void checkCart() {
        double drycount = 0, dryamount = 0, washcount = 0, washamount = 0, ironcount = 0, ironamount = 0;


        if (null != dry) {
            for (int i = 0; i < dry.size(); i++) {
                drycount = drycount + dry.get(i).getItemcount();
                dryamount = dryamount + Double.parseDouble(dry.get(i).getTotal());
            }

        }
        if (null != wash) {
            for (int i = 0; i < wash.size(); i++) {

                washcount = washcount + wash.get(i).getItemcount();
                washamount = washamount + Double.parseDouble(wash.get(i).getTotal());
            }

        }
        if (null != iron) {

            for (int i = 0; i < iron.size(); i++) {
                ironcount = ironcount + iron.get(i).getItemcount();
                ironamount = ironamount + Double.parseDouble(iron.get(i).getTotal());
            }

        }

        double totalcount = drycount + washcount + ironcount;
        double totalamount = dryamount + washamount + ironamount;

        if (totalamount > 0 && totalcount > 0) {

            bottomLayout.setVisibility(View.VISIBLE);
            cartcount = (TextView) findViewById(R.id.tv_cartcount);
            cartamount = (TextView) findViewById(R.id.tv_cartamount);
            cartcount.setText("Your Basket(" + String.valueOf(totalcount).toString() + ")");
            cartamount.setText("AED" + String.valueOf(totalamount).toString());

        } else {

        }
/*
        int drycount = 0, dryamount = 0, washcount = 0, washamount = 0, ironcount = 0, ironamount = 0;


        if (null != dry) {
            for (int i = 0; i < dry.size(); i++) {
                drycount = drycount + dry.get(i).getItemcount();
                dryamount = dryamount + Integer.parseInt(dry.get(i).getTotal());
            }

        }
        if (null != wash) {
            for (int i = 0; i < wash.size(); i++) {

                washcount = washcount + wash.get(i).getItemcount();
                washamount = washamount + Integer.parseInt(wash.get(i).getTotal());
            }

        }
        if (null != iron) {

            for (int i = 0; i < iron.size(); i++) {
                ironcount = ironcount + iron.get(i).getItemcount();
                ironamount = ironamount + Integer.parseInt(iron.get(i).getTotal());
            }

        }

        int totalcount = drycount + washcount + ironcount;
        int totalamount = dryamount + washamount + ironamount;

        if (totalamount > 0 && totalcount > 0) {

            bottomLayout.setVisibility(View.VISIBLE);
            cartcount = (TextView) findViewById(R.id.tv_cartcount);
            cartamount = (TextView) findViewById(R.id.tv_cartamount);
            cartcount.setText("Your Basket(" + String.valueOf(totalcount).toString() + ")");
            cartamount.setText("AED" + String.valueOf(totalamount).toString());

        } else {

        }

*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != dry || null != wash || null != iron) {
            checkCart();
        }
    }

    private void setData() {


    }

    private void getCategoryList() {

        // if (!validate(view.getResources())) return;
        RequestParams params = new RequestParams();

        // params.put("LoginEmail", "admin@gmail.com");

        // params.put(ApiConstants.uid, appPrefes.getData(PrefConstants.uid));
        // params.put(ApiConstants.uid, 1);
        //  params.put(ApiConstants.status, "Pending");


        String url = ApiConstants.categoryUrl;
        apiCalls.callApiPost(CategoryListActivity.this, params, dialog, url, CATEGORIES);
    }




    @Override
    public void getResponse(String response, int requestId) {

        if (requestId == 777) {
            try {

                final discountdto offerData;
                Gson gson = new Gson();
                offerData = gson.fromJson(response, discountdto.class);
                Almosky.getInst().setMinAmount(offerData.getResult().get(0).getMinAmt());
                Almosky.getInst().setNasabAmount(offerData.getResult().get(0).getNasabMinAmount());

                System.out.println("##minAmount from web"+offerData.getResult().get(0).getMinAmt());
                System.out.println("##minNasabAmount from web"+offerData.getResult().get(0).getNasabMinAmount());


            } catch (Exception e) {

            }


        }

        else if (requestId == 77) {

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


        }else{
            try {

                Gson gson = new Gson();
                final categorydto catList = gson.fromJson(response, categorydto.class);

                if (0 != catList.getDetail().size()) {
                    for (int i = 0; i < catList.getDetail().size(); i++) {


                        mExpandableView.addView(new HeadingView(mContext, catList.getDetail().get(i).getCategoryName(),catList.getDetail().get(i).getCategoryIcons(),1));
                        for (int j = 0; j < catList.getDetail().get(i).getItems().size(); j++) {
                            mExpandableView.addView(new InfoView(mContext, catList.getDetail().get(i).getItems().get(j)));
                        }
                    }

                }

            } catch (Exception e) {

            }

        }

    }
/*
    private void setLoadMoreListener(ExpandablePlaceHolderView expandableView) {
        mOnScrollListener =
                new PlaceHolderView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        ExpandablePlaceHolderView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                        if (layoutManager instanceof LinearLayoutManager) {
                            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                            int totalItemCount = linearLayoutManager.getItemCount();
                            int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                            if (!mIsLoadingMore
                                    && !mNoMoreToLoad
                                    && totalItemCount > 0
                                    && totalItemCount == lastVisibleItem + 1) {
                                mIsLoadingMore = true;
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {

                                        // do api call to fetch data

                                        // example of loading from file:
                                        for (Feed feed : Utils.loadFeeds(getApplicationContext())) {
                                            mExpandableView.addView(new HeadingView(mContext, feed.getHeading()));
                                            for (Info info : feed.getInfoList()) {
                                                mExpandableView.addView(new InfoView(mContext, info));
                                            }
                                        }
                                        mIsLoadingMore = false;
                                    }
                                });
                            }
                        }
                    }
                };
        expandableView.addOnScrollListener(mOnScrollListener);
    }  */
}