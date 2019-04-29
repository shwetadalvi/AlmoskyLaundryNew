package com.almosky.almoskylaundryApp.activity.neworder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.almosky.almoskylaundryApp.Almosky;
import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.activity.SignupOrLoginActivity;
import com.almosky.almoskylaundryApp.common.BaseActivity;
import com.almosky.almoskylaundryApp.model.data;
import com.almosky.almoskylaundryApp.model.priceListdto;
import com.almosky.almoskylaundryApp.utils.AppPrefes;
import com.almosky.almoskylaundryApp.utils.api.ApiCalls;
import com.almosky.almoskylaundryApp.utils.constants.ApiConstants;
import com.almosky.almoskylaundryApp.utils.constants.PrefConstants;
import com.google.gson.Gson;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

public class ItemDetailsAddActivity extends BaseActivity {

    private TextView title;
    String catName,itemName;
    int itemId,catId;
    private ArrayList<data.Detail.Item> dry;
    private ArrayList<data.Detail.Item> wash;
    private ArrayList<data.Detail.Item> iron;
    private AppPrefes appPrefes;
    private ApiCalls apiCalls;
    SimpleArcDialog dialog;
    ArrayList<priceListdto.Detail> priceDetails;
    TextView dryprice,washprice,ironprice;
    private TextView dryCleanCount;
    private TextView ironingCount;
    private TextView washIronCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details_add1);


        appPrefes=new AppPrefes(this);
        apiCalls=new ApiCalls();
        dialog=new SimpleArcDialog(this);
        getPriceList();

        if(!appPrefes.getBoolData(PrefConstants.isLogin)){
            startActivity(new Intent(ItemDetailsAddActivity.this, SignupOrLoginActivity.class));
        }


        ImageView backButton = findViewById(R.id.backArrow);
        backButton.setVisibility(View.VISIBLE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView dryCleanMinus = (ImageView) findViewById(R.id.dryCleanMinus);
        ImageView dryCleanPlus = (ImageView) findViewById(R.id.dryCleanPlus);
         dryCleanCount = (TextView) findViewById(R.id.dryCleanCount);



        ImageView washIronMinus = (ImageView) findViewById(R.id.washIronMinus);
        ImageView washIronPlus = (ImageView) findViewById(R.id.washIronPlus);
        washIronCount = (TextView) findViewById(R.id.washIronCount);

        ImageView ironingPlus = (ImageView) findViewById(R.id.ironingPlus);
        ImageView ironingMinus = (ImageView) findViewById(R.id.ironingMinus);
        ironingCount = (TextView) findViewById(R.id.ironingCount);

         dryprice=(TextView) findViewById(R.id.tv_dry_price);
         washprice=(TextView) findViewById(R.id.tv_wash_price);
         ironprice=(TextView) findViewById(R.id.tv_iron_price);

        TextView productTitle=(TextView) findViewById(R.id.tv_title);






        ImageView iv_detail = (ImageView) findViewById(R.id.iv_detail);
        TextView addToBasketId = (TextView) findViewById(R.id.addToBasketId);
        String url = getIntent().getExtras().getString("url");
        catId=getIntent().getExtras().getInt("catId");
        catName=getIntent().getExtras().getString("catname");
        itemId=getIntent().getExtras().getInt("itemId");
        itemName=getIntent().getExtras().getString("itemname");

        productTitle.setText(itemName.toString());


       // int amt=getAmount(itemId,1);

   //     dryprice.setText(String.valueOf(getAmount(itemId,1)));


        dry= Almosky.getInst().getDrycleanList();
        wash=Almosky.getInst().getWashList();
        iron=Almosky.getInst().getIronList();

        if(null!=dry){

            for(int i=0;i<dry.size();i++){

                if(dry.get(i).getItemId().equals(itemId)){
                    dryCleanCount.setText(dry.get(i).getItemcount().toString());
                  //  dryprice.setText(String.valueOf(getAmount(dry.get(i).getItemId(),1)));
                }

            }
        }
        if(null!=wash){

            for(int i=0;i<wash.size();i++){

                if(wash.get(i).getItemId().equals(itemId)){
                    washIronCount.setText(wash.get(i).getItemcount().toString());
                }

            }
        }

        if(null!=iron){

            for(int i=0;i<iron.size();i++){

                if(iron.get(i).getItemId().equals(itemId)){
                    ironingCount.setText(iron.get(i).getItemcount().toString());
                }

            }
        }



//        Glide.with(this).load(url).into(iv_detail);
        addToBasketId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ItemDetailsAddActivity.this, CategoryListActivity.class);
                intent.putExtra("className", "ItemDetailsAddActivity");
               // intent.putExtra("count", 1);
               // intent.putExtra("price", "7");
                startActivity(intent);
            }
        });

        dryCleanMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dryprice.getText().toString().equals(null) || dryprice.getText().toString().equals("")){

                }else {
                    int countDryClean = Integer.parseInt(dryCleanCount.getText().toString());
                    if (countDryClean > 0) {
                        countDryClean = countDryClean - 1;
                        dryCleanCount.setText("" + countDryClean);
                        int totcount=Almosky.getInst().getCartcount()-1;
                        Almosky.getInst().setCartcount(totcount);
                        updateData(itemId,itemName,countDryClean,"dryclean");
                    }
                }

            }
        });
        dryCleanPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dryprice.getText().toString().equals(null) || dryprice.getText().toString().equals(""))
                    {

                    }else {
                    int countDryClean = Integer.parseInt(dryCleanCount.getText().toString());
                    countDryClean = countDryClean + 1;
                    dryCleanCount.setText("" + countDryClean);

                    updateData(itemId, itemName, countDryClean, "dryclean");
                }
                }
        });

        washIronMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(washprice.getText().toString().equals(null) || washprice.getText().toString().equals("")){


            }
            else {
                    int countWashIron = Integer.parseInt(washIronCount.getText().toString());
                    if (countWashIron > 0) {
                        countWashIron = countWashIron - 1;
                        washIronCount.setText("" + countWashIron);
                        updateData(itemId,itemName,countWashIron,"washiron");
                    }

                }
            }
        });
        washIronPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(washprice.getText().toString().equals(null) || washprice.getText().toString().equals("")){


                }else {
                    int countWashIron = Integer.parseInt(washIronCount.getText().toString());
                    countWashIron = countWashIron + 1;
                    washIronCount.setText("" + countWashIron);
                    updateData(itemId,itemName,countWashIron,"washiron");
                }
            }
        });

        ironingMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ironprice.getText().toString().equals(null) || ironprice.getText().toString().equals("")){




            }else {
                    int countIroning = Integer.parseInt(ironingCount.getText().toString());
                    if (countIroning > 0) {
                        countIroning = countIroning - 1;
                        ironingCount.setText("" + countIroning);
                        updateData(itemId,itemName,countIroning,"iron");
                    }
                }

            }
        });
        ironingPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String iro=ironprice.getText().toString();
              //  Toast.makeText(getApplicationContext(),""+iro,Toast.LENGTH_LONG).show();

                if(ironprice.getText().toString().equals(null) || ironprice.getText().toString().equals("")){


                }else{

                    int countIroning = Integer.parseInt(ironingCount.getText().toString());
                    countIroning = countIroning + 1;
                    ironingCount.setText("" + countIroning);
                    updateData(itemId,itemName,countIroning,"iron");
                }
            }
        });

    }

    private void init() {
    }

    @Override
    public void getResponse(String response, int requestId) {

        try {
            Gson gson = new Gson();

            final priceListdto priceList = gson.fromJson(response, priceListdto.class);
            priceListdto dto=new priceListdto();
            priceListdto.Detail detaildto=dto. new Detail();

            priceDetails=priceList.getDetail();

            Almosky.getInst().setItempriceList(priceDetails);


            if(getAmount(itemId,1)!=0){
                dryprice.setText(String.valueOf("AED"+getAmount(itemId,1)));
            }
            if(getAmount(itemId,2)!=0){
                washprice.setText(String.valueOf("AED"+getAmount(itemId,2)));
            }

            if(getAmount(itemId,3)!=0){
                ironprice.setText(String.valueOf("AED"+getAmount(itemId,3)));
            }



        }catch (Exception e){

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
      //  getPriceList();
    }

    private void getPriceList() {
        // if (!validate(view.getResources())) return;
        RequestParams params = new RequestParams();

        // params.put("LoginEmail", "admin@gmail.com");

        // params.put(ApiConstants.uid, appPrefes.getData(PrefConstants.uid));
        // params.put(ApiConstants.uid, 1);
        //  params.put(ApiConstants.status, "Pending");


        String url = ApiConstants.getPriceListUrl;
        apiCalls.callApiPost(ItemDetailsAddActivity.this, params, dialog, url, 13);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
/*
        int dryCount=Integer.parseInt(dryCleanCount.getText().toString());
        int washCount=Integer.parseInt(washIronCount.getText().toString());
        int ironCount=Integer.parseInt(ironingCount.getText().toString());

        if(dryCount!=0){

            updateData(itemId,itemName,dryCount,"dryclean");

        }
        if(washCount!=0){

            updateData(itemId,itemName,dryCount,"washiron");

        }
        if(ironCount!=0){

            updateData(itemId,itemName,ironCount,"iron");

        }
        */

    }

    private void updateData(int itemId, String itemName, int count, String type) {



        if(type.equals("dryclean")){
            int isPresent=0;
            ArrayList<data.Detail.Item> drycleanList=Almosky.getInst().getDrycleanList();
            if(null!=drycleanList){


                for(int i=0;i<drycleanList.size();i++){
                    if(drycleanList.get(i).getItemId().equals(itemId)){
                       // drycleanList.get(i).setItemcount(count);
                        Almosky.getInst().getDrycleanList().get(i).setItemcount(count);
                        //int amount= Integer.parseInt(Almosky.getInst().getDrycleanList().get(i).getAmount());
                        double amount= getAmount(itemId,1);
                        Almosky.getInst().getDrycleanList().get(i).setTotal(String.valueOf(amount*count));
                        isPresent=1;
                    }


                }
                if(isPresent==0){

                    ArrayList<data.Detail.Item> drylst=new ArrayList<>();

                    data obj=new data();
                    data.Detail detailobj=obj. new Detail();
                    detailobj.setType(type);

                    data.Detail.Item itemObj=detailobj. new Item();

                    itemObj.setItemId(itemId);
                    itemObj.setAmount(String.valueOf(getAmount(itemId,1)));
                    itemObj.setItemName(itemName);
                    itemObj.setItemcount(count);



                    itemObj.setTotal(String.valueOf(Double.parseDouble(String.valueOf(getAmount(itemId,1)))*count));
                    drylst.add(itemObj);

                    if(null!=drycleanList){
                        drycleanList.add(itemObj);
                    }
                    Almosky.getInst().setDrycleanList(drycleanList);

                }

            }else {


                data obj=new data();
                ArrayList<data.Detail.Item> drylst=new ArrayList<>();
            //    ArrayList<data.Detail.Item> items=new ArrayList<>();

                try{

                    //  JSONArray mainArray=new JSONArray();
                    //  JSONObject mainobj=new JSONObject();
                    data.Detail detailobj=obj. new Detail();
                    detailobj.setType(type);

                    data.Detail.Item itemObj=detailobj. new Item();

                    itemObj.setItemId(itemId);
                    itemObj.setAmount(String.valueOf(getAmount(itemId,1)));
                    itemObj.setItemName(itemName);
                    itemObj.setItemcount(count);

                    itemObj.setTotal(String.valueOf(Double.parseDouble(String.valueOf(getAmount(itemId,1)))*count));
                    drylst.add(itemObj);

                    Almosky.getInst().setDrycleanList(drylst);
                }catch (Exception e){

                    e.printStackTrace();
                }

            }
        }

        //washiron list
        else if(type.equals("washiron")){
            int isPresent=0;
            ArrayList<data.Detail.Item> washironList=Almosky.getInst().getWashList();
            if(null!=washironList){


                for(int i=0;i<washironList.size();i++){
                    if(washironList.get(i).getItemId().equals(itemId)){
                        washironList.get(i).setItemcount(count);
                       // int amount= Integer.parseInt(Almosky.getInst().getWashList().get(i).getAmount());
                        double amount= getAmount(itemId,2);
                        Almosky.getInst().getWashList().get(i).setTotal(String.valueOf(amount*count));
                        isPresent=1;
                    }


                }
                if(isPresent==0){

                    ArrayList<data.Detail.Item> wlst=new ArrayList<>();
                    data obj=new data();
                    data.Detail detailobj=obj. new Detail();
                    detailobj.setType(type);

                    data.Detail.Item itemObj=detailobj. new Item();

                    itemObj.setItemId(itemId);
                    itemObj.setAmount(String.valueOf(getAmount(itemId,2)));
                    itemObj.setItemName(itemName);
                    itemObj.setItemcount(count);
                    itemObj.setTotal(String.valueOf(getAmount(itemId,2)*count));

                    wlst.add(itemObj);

                    if(null!=washironList){
                        washironList.add(itemObj);

                    }
                    Almosky.getInst().setWashList(washironList);



                }

            }else {

                ArrayList<data.Detail.Item> wlst=new ArrayList<>();

                data obj=new data();
                //    ArrayList<data.Detail.Item> items=new ArrayList<>();

                try{

                    //  JSONArray mainArray=new JSONArray();
                    //  JSONObject mainobj=new JSONObject();
                    data.Detail detailobj=obj. new Detail();
                    detailobj.setType(type);

                    data.Detail.Item itemObj=detailobj. new Item();

                    itemObj.setItemId(itemId);
                    itemObj.setAmount(String.valueOf(getAmount(itemId,2)));
                    itemObj.setItemName(itemName);
                    itemObj.setItemcount(count);

                    itemObj.setTotal(String.valueOf((getAmount(itemId,2))*count));
                    wlst.add(itemObj);

                    Almosky.getInst().setWashList(wlst);
                }catch (Exception e){

                    e.printStackTrace();
                }

            }
        }

        //iron

        else if(type.equals("iron")){
            int isPresent=0;
            ArrayList<data.Detail.Item> ironList=Almosky.getInst().getIronList();
            if(null!=ironList){


                for(int i=0;i<ironList.size();i++){
                    if(ironList.get(i).getItemId().equals(itemId)){
                        ironList.get(i).setItemcount(count);
                        //int amount= Integer.parseInt(Almosky.getInst().getIronList().get(i).getAmount());
                        double amount= getAmount(itemId,3);
                        Almosky.getInst().getIronList().get(i).setTotal(String.valueOf(amount*count));
                        isPresent=1;
                    }


                }
                if(isPresent==0){
                    ArrayList<data.Detail.Item> ilst=new ArrayList<>();

                    data obj=new data();
                    data.Detail detailobj=obj. new Detail();
                    detailobj.setType(type);

                    data.Detail.Item itemObj=detailobj. new Item();

                    itemObj.setItemId(itemId);
                    itemObj.setAmount(String.valueOf(getAmount(itemId,3)));
                    itemObj.setItemName(itemName);
                    itemObj.setItemcount(count);
                    itemObj.setTotal(String.valueOf(getAmount(itemId,3)*count));
                    ilst.add(itemObj);

                    if(null!=ironList){
                        ironList.add(itemObj);

                    }
                    Almosky.getInst().setIronList(ironList);



                    //Almosky.getInst().setIronList(ilst);

                }

            }else {

                ArrayList<data.Detail.Item> ilst=new ArrayList<>();

                data obj=new data();
                //    ArrayList<data.Detail.Item> items=new ArrayList<>();

                try{


                    //  JSONArray mainArray=new JSONArray();
                    //  JSONObject mainobj=new JSONObject();
                    data.Detail detailobj=obj. new Detail();
                    detailobj.setType(type);

                    data.Detail.Item itemObj=detailobj. new Item();

                    itemObj.setItemId(itemId);
                    itemObj.setAmount(String.valueOf(getAmount(itemId,3)));
                    itemObj.setItemName(itemName);
                    itemObj.setItemcount(count);
                    itemObj.setTotal(String.valueOf(getAmount(itemId,3)*count));

                    ilst.add(itemObj);

                    Almosky.getInst().setIronList(ilst);
                }catch (Exception e){

                    e.printStackTrace();
                }

            }
        }


    }

    private double getAmount(int itemId,int serviceId) {

        System.out.println("Item"+itemId+"Service"+serviceId);


        double amount=0;
        for(int i=0;i<Almosky.getInst().getItempriceList().size();i++){
            if(serviceId==Almosky.getInst().getItempriceList().get(i).getServiceId()){
                ArrayList<priceListdto.Detail.Item> curntlist=Almosky.getInst().getItempriceList().get(i).getItems();

                String type=Almosky.getInst().getDeliveryType();
                System.out.println("find type"+type);

                for(int j=0; j<curntlist.size(); j++){
                   // if(Almosky.getInst().getDeliveryType().equals("normal")) {
                    if(Almosky.getInst().getDeliveryType().equals("1")) {

                        String ctype = curntlist.get(j).getDeliveryType();
                      //  System.out.println("find curent" + ctype);
                        if (itemId == curntlist.get(j).getItemId() && curntlist.get(j).getDeliveryType().equals("NORMAL")) {
                            amount = Double.parseDouble(curntlist.get(j).getPrice());
                            System.out.println("normalamounc"+amount);

                            return amount;
                        }
                    }

                      //  if(Almosky.getInst().getDeliveryType().equals("fast")){
                        if(Almosky.getInst().getDeliveryType().equals("2")){
                            String cctype=curntlist.get(j).getDeliveryType();
                          //  System.out.println("find curentfast"+cctype);
                            if(itemId == curntlist.get(j).getItemId() && !curntlist.get(j).getDeliveryType().equals("NORMAL")){
                                amount=Double.parseDouble(curntlist.get(j).getPrice());
                                System.out.println("fastamounc"+amount);

                                return amount;
                            }

                        }





                }

        /*        for(int j=0; j<curntlist.size(); j++){
                    if(Almosky.getInst().getDeliveryType().equals("normal")){
                        String ctype=curntlist.get(j).getDeliveryType();
                        System.out.println("find curent"+ctype);
                        if(itemId==curntlist.get(j).getItemId() && curntlist.get(j).getDeliveryType().equals("NORMAL")){
                            amount=curntlist.get(j).getPrice();

                            return amount;
                        }

                        if(Almosky.getInst().getDeliveryType().equals("fast")){
                            String cctype=curntlist.get(j).getDeliveryType();
                            System.out.println("find curentfast"+cctype);
                            if(itemId==curntlist.get(j).getItemId() && curntlist.get(j).getDeliveryType().equals("FAST SERVICE")){
                                amount=curntlist.get(j).getPrice();

                                return amount;
                            }

                        }


                    }


                }  */

            }



        }

        return amount;
    }
}
