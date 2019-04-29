package com.almosky.almoskylaundryApp.activity.neworder;


import androidx.appcompat.app.AppCompatActivity;


public class MyBasketActivity extends AppCompatActivity {
    /*

    private MyBasketModel model;
    private ActivityMyBasketBinding binding;
    private DryCleanRecyclerViewAdapter dryCleanRecyclerViewAdapter;
    private WashIronRecyclerViewAdapter washIronRecyclerViewAdapter;
    private IroningRecyclerViewAdapter ironingRecyclerViewAdapter;
    private TextView title,subAmount,subcount;
    RelativeLayout washlyt,drylyt,ironlyt;
    public AppPrefes appPrefes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_basket);
        model = new MyBasketModel();
        binding.setModel(model);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarorder);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView title=(TextView)  findViewById(getResources().getIdentifier("toolbar_title", "id", getPackageName()));

        title.setText("New Order");
        ImageView backButton = (ImageView) findViewById(getResources().getIdentifier("back", "id", getPackageName()));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        appPrefes=new AppPrefes(this);


        setDryCleanAdapter();
        setWashIronAdapter();
        setIroningAdapter();
        updateTotal();

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Utility.isNetworkOnline(MyBasketActivity.this)){

                    new SweetAlertDialog(MyBasketActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("New Order")
                            .setContentText("Do You Agree To Submit?")
                            .setConfirmText("Yes,Agree")
                            .setCancelText("Not Now")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();

                                    submitData();


                                }
                            })
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();

                                }
                            })
                            .show();


                }else {
                    new SweetAlertDialog(MyBasketActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("No Internet")
                            .setContentText("Please Check Internet Connection")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();

                                }
                            })
                            .show();
                }

            }
        });
    }

    private void submitData() {


        ArrayList<data.Detail.Item> dry = Almosky.getInst().getDrycleanList();
        ArrayList<data.Detail.Item> wash = Almosky.getInst().getWashList();
        ArrayList<data.Detail.Item> iron = Almosky.getInst().getIronList();


        if (null != wash || null != dry || null != iron) {

            try {

                JSONArray mainArray = new JSONArray();
                JSONObject object = new JSONObject();


                object.put("uid",appPrefes.getIntData(PrefConstants.uid));
                object.put("orderNo", "c455");
                object.put("orderDate", appPrefes.getData("orderDate"));
                //  object.put("orderDate", "2014-12-5");

                object.put("orderTime", appPrefes.getData("orderTime"));
                //  object.put("orderTime", "6:30:00");


                object.put("delDate", appPrefes.getData("delDate"));
                // object.put("delDate", "2014-12-5");
                object.put("delTime", appPrefes.getData("delTime"));
                //object.put("delTime", "6:30:00");
                object.put("itemAmount", binding.subtotalPrice.getText().toString());

                JSONArray jsonArray = new JSONArray();
                JSONObject object1 = new JSONObject();
                object1.put("name", appPrefes.getData("pname"));
                object1.put("area", appPrefes.getData("parea"));
                object1.put("landmark", appPrefes.getData("plandmark"));
                object1.put("building", appPrefes.getData("pbuilding"));
                object1.put("street_no", appPrefes.getData("pstreet_no"));
                object1.put("flatno", appPrefes.getData("pflatno"));
                object1.put("mobile", appPrefes.getData("pmobile"));
                jsonArray.put(object1);

                object.put("pickaddress", jsonArray);

                JSONArray jsonArray2 = new JSONArray();
                JSONObject object2 = new JSONObject();
                //object2.put("name", appPrefes.getData("pname"));
                object2.put("area", appPrefes.getData("darea"));
                object2.put("landmark", appPrefes.getData("dlandmark"));
                object2.put("building", appPrefes.getData("dbuilding"));
                object2.put("street_no", appPrefes.getData("dstreet_no"));
                object2.put("flatno", appPrefes.getData("dflatno"));
                //  object2.put("mobile", appPrefes.getData("pmobile"));
                //object2.put("deliveryaddress",jsonArray2);

                jsonArray2.put(object2);

                object.put("deliveryaddress", jsonArray2);

                JSONArray jsonArray3 = new JSONArray();

                if(null!=dry){

                    for (int i=0;i<dry.size();i++){

                        JSONObject object3=new JSONObject();
                        object3.put("ItemId",dry.get(i).getItemId());
                        object3.put("ServiceId",Almosky.getInst().getServiceId());
                        object3.put("Qty",dry.get(i).getItemcount());
                        object3.put("Price",dry.get(i).getAmount());
                        object3.put("slno","1");
                        object3.put("Item_Name",dry.get(i).getItemName());

                        jsonArray3.put(object3);

                    }

                    //  object.put("Item",jsonArray3);
                }
                if(null!=wash){

                    for (int i=0;i<wash.size();i++){

                        JSONObject object3=new JSONObject();
                        object3.put("ItemId",wash.get(i).getItemId());
                        object3.put("ServiceId",Almosky.getInst().getServiceId());
                        object3.put("Qty",wash.get(i).getItemcount());
                        object3.put("Price",wash.get(i).getAmount());
                        object3.put("slno","1");
                        object3.put("Item_Name",wash.get(i).getItemName());


                        jsonArray3.put(object3);

                    }

                    //  object.put("Item",jsonArray3);
                }
                if(null!=iron){

                    for (int i=0;i<iron.size();i++){

                        JSONObject object3=new JSONObject();
                        object3.put("ItemId",iron.get(i).getItemId());
                        object3.put("ServiceId",Almosky.getInst().getServiceId());
                        object3.put("Qty",iron.get(i).getItemcount());
                        object3.put("Price",iron.get(i).getAmount());
                        object3.put("slno","1");
                        object3.put("Item_Name",iron.get(i).getItemName());


                        jsonArray3.put(object3);

                    }

                    //  object.put("Item",jsonArray3);
                }


                object.put("Item",jsonArray3);



                String Data = object.toString();
                StringEntity entity = null;
                final SimpleArcDialog dialog=new SimpleArcDialog(MyBasketActivity.this);
                try {
                    entity = new StringEntity(Data.toString());
                    entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    dialog.show();
                } catch(Exception e) {
//Exception
                }

                String url= ApiConstants.BaseUrl+ApiConstants.neworderUrl;

                new AsyncHttpClient().post(null,url,entity,"application/json", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                        try {
                            dialog.dismiss();
                            String object= new String(responseBody);
                            JSONObject jsonObject = new JSONObject(object);
                            String result = jsonObject.getString("result");

                            if(result.equals("Data Inserted"))
                            {
                                new SweetAlertDialog(MyBasketActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                        .setTitleText("Success")
                                        .setContentText("Success")
                                        .setConfirmText("Ok")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {

                                              //  updateAddress();

                                                sDialog.dismissWithAnimation();
                                                Almosky.getInst().setIronList(null);
                                                Almosky.getInst().setCartcount(0);
                                                Almosky.getInst().setWashList(null);
                                                Almosky.getInst().setDrycleanList(null);
                                                Almosky.getInst().setCartamount(0);
                                                Almosky.getInst().setServiceId(0);
                                                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                               // Intent go=new Intent(MyBasketActivity.this, NavigationDrawerActivity.class);
                                               // go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                               // startActivity(go);


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

            }
        }

    }

    /*

    private void updateAddress() {

        if(Almosky.getInst().getPickserviceType()==1){

            logindto ldto=new logindto();
            logindto.Home hdto=ldto. new Home();
            logindto.Home odto=ldto. new Home();
            ArrayList<logindto.Home> homelist=new ArrayList<>();
            ArrayList<logindto.Office> ofclist=new ArrayList<>();

            hdto.setArea(appPrefes.getData("parea"));
            hdto.setBuilding(appPrefes.getData("pbuilding"));
            hdto.setFlat(appPrefes.getData("pflatno"));
            hdto.setLandmark(appPrefes.getData("plandmark"));
            hdto.setPhone(appPrefes.getData("pmobile"));
            hdto.setStreetno(appPrefes.getData("pstreet_no"));

            homelist.add(hdto);
            Almosky.getInst().setHomeAddress(homelist);


        }
        if(Almosky.getInst().getPickserviceType()==2){

            logindto ldto=new logindto();
            //logindto.Home hdto=ldto. new Home();
            logindto.Office odto=ldto. new Office();
          //  ArrayList<logindto.Home> homelist=new ArrayList<>();
            ArrayList<logindto.Office> ofclist=new ArrayList<>();

            odto.setArea(appPrefes.getData("parea"));
            odto.setBuilding(appPrefes.getData("pbuilding"));
            odto.setFlat(appPrefes.getData("pflatno"));
            odto.setLandmark(appPrefes.getData("plandmark"));
          //  odto.setPhone(appPrefes.getData("pmobile"));
            odto.setStreetno(appPrefes.getData("pstreet_no"));

            ofclist.add(odto);
            Almosky.getInst().setOfficeAddress(ofclist);

        }

        if(Almosky.getInst().getDelserviceType()==1){

            logindto ldto=new logindto();
            logindto.Home hdto=ldto. new Home();
            logindto.Home odto=ldto. new Home();
            ArrayList<logindto.Home> homelist=new ArrayList<>();
            ArrayList<logindto.Office> ofclist=new ArrayList<>();

            hdto.setArea(appPrefes.getData("darea"));
            hdto.setBuilding(appPrefes.getData("dbuilding"));
            hdto.setFlat(appPrefes.getData("dflatno"));
            hdto.setLandmark(appPrefes.getData("dlandmark"));
            hdto.setPhone(appPrefes.getData("dmobile"));
            hdto.setStreetno(appPrefes.getData("dstreet_no"));

            homelist.add(hdto);
            Almosky.getInst().setDelhomeAddress(homelist);

        }
        if(Almosky.getInst().getDelserviceType()==2){

            logindto ldto=new logindto();
            //logindto.Home hdto=ldto. new Home();
            logindto.Office odto=ldto. new Office();
            //  ArrayList<logindto.Home> homelist=new ArrayList<>();
            ArrayList<logindto.Office> ofclist=new ArrayList<>();

            odto.setArea(appPrefes.getData("darea"));
            odto.setBuilding(appPrefes.getData("dbuilding"));
            odto.setFlat(appPrefes.getData("dflatno"));
            odto.setLandmark(appPrefes.getData("dlandmark"));
            //  odto.setPhone(appPrefes.getData("pmobile"));
            odto.setStreetno(appPrefes.getData("dstreet_no"));

            ofclist.add(odto);
            Almosky.getInst().setDelofficeAddress(ofclist);

        }


    } */

 /*   private void setDryCleanAdapter() {

        if(null!=Almosky.getInst().getDrycleanList()){

            dryCleanRecyclerViewAdapter = new DryCleanRecyclerViewAdapter(OrderConfirmationActivity.this, Almosky.getInst().getDrycleanList(),this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyBasketActivity.this);
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

            washIronRecyclerViewAdapter = new WashIronRecyclerViewAdapter(MyBasketActivity.this,Almosky.getInst().getWashList(),this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyBasketActivity.this);
            binding.washIronRecyclerView.setNestedScrollingEnabled(false);
            binding.washIronRecyclerView.setLayoutManager(mLayoutManager);
            binding.washIronRecyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.washIronRecyclerView.setAdapter(washIronRecyclerViewAdapter);
        }else {

            binding.washIronLayout.setVisibility(View.GONE);
        }

    }



    public void updateTotal() {

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

        if(totalcount==0) {

            binding.totalPrice.setText("0"+"AED");
            binding.totalCount.setText("0");
            binding.vattotalPrice.setVisibility(View.INVISIBLE);
            binding.subtotalPrice.setText("0"+"AED");


        }
        else if(totalamount>0 && totalcount>0){
                binding.totalPrice.setText(String.valueOf(totalamount).toString()+"AED");
                binding.totalCount.setText(String.valueOf(totalcount));
                binding.vattotalPrice.setText(String.valueOf(vat)+"AED");
                binding.subtotalPrice.setText(String.valueOf(subtotal).toString()+"AED");




            }else{

            }








    }


    private void setIroningAdapter() {

        if(null!=Almosky.getInst().getIronList()){

            ironingRecyclerViewAdapter = new IroningRecyclerViewAdapter(MyBasketActivity.this,Almosky.getInst().getIronList(),this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyBasketActivity.this);
            binding.ironingRecyclerView.setNestedScrollingEnabled(false);
            binding.ironingRecyclerView.setLayoutManager(mLayoutManager);
            binding.ironingRecyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.ironingRecyclerView.setAdapter(ironingRecyclerViewAdapter);

        }else{
            binding.ironingLayout.setVisibility(View.GONE);

        }

    } */
}
