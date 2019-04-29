package com.almosky.almoskylaundryApp.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.almosky.almoskylaundryApp.Almosky;
import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.TabHostActivity;
import com.almosky.almoskylaundryApp.activity.neworder.HeadingView;
import com.almosky.almoskylaundryApp.activity.neworder.InfoView1;
import com.almosky.almoskylaundryApp.model.categoryItemPrice;
import com.almosky.almoskylaundryApp.model.categoryPriceList;
import com.almosky.almoskylaundryApp.model.categorydto;
import com.almosky.almoskylaundryApp.model.priceListdto;
import com.almosky.almoskylaundryApp.utils.api.ApiCalls;
import com.almosky.almoskylaundryApp.utils.constants.ApiConstants;
import com.google.gson.Gson;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.RequestParams;
import com.mindorks.placeholderview.ExpandablePlaceHolderView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class PriceListFragment extends Fragment implements TabHostActivity.FragmentResultInterface {

    private static final String ARG_PAGE_NUMBER = "page_number";
    ArrayList<categoryPriceList> categoryPriceListsArray = new ArrayList<>();
    SimpleArcDialog dialog;
    TabHostActivity _activity;
    ArrayList<categorydto.Detail> detailList;
    ArrayList<categoryPriceList> priceList;
    private ExpandablePlaceHolderView mdryExpandableView, mwashExpandableView, mironExpandableView;
    private Button dryCleanButton;
    private Button washIronButton;
    private Button ironingButton;
    private ApiCalls apiCalls;
    private boolean mIsVisibleToUser;

    public PriceListFragment() {
        // Required empty public constructor

    }

    public static PriceListFragment newInstance(int page) {
        PriceListFragment fragment = new PriceListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, page);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        //  View view = inflater.inflate(R.layout.fragment_price_list, container, false);
        View view = inflater.inflate(R.layout.fragment_price_list, container, false);

/*
        terms = (WebView) view.findViewById(R.id.wv_terms);
      //  tb=(Toolbar) view.findViewById(R.id.toolbar);
       // tb.setVisibility(View.GONE);
        terms.requestFocus();

        terms.setHorizontalScrollBarEnabled(false);




        WebSettings webSettings = terms.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        terms.setWebViewClient(new WebViewClient());
        webSettings.setJavaScriptEnabled(true);


        terms.loadUrl("http://www.almoskylandury.ae/price.php");  */
      /*  terms.setWebChromeClient(new WebChromeClient() {
            private ProgressDialog mProgress;

            @Override
            public void onProgressChanged(WebView view, int progress) {
                if (mProgress == null) {
                    mProgress = new ProgressDialog(getActivity());
                    mProgress.show();
                }
                mProgress.setMessage("Loading Price List" + String.valueOf(progress) + "%");
                if (progress == 100) {
                    mProgress.dismiss();
                    mProgress = null;
                }
            }
        }); */


        mdryExpandableView = (ExpandablePlaceHolderView) view.findViewById(R.id.dryexpandableView);
        mwashExpandableView = (ExpandablePlaceHolderView) view.findViewById(R.id.washexpandableView);
        mironExpandableView = (ExpandablePlaceHolderView) view.findViewById(R.id.ironexpandableView);

        dryCleanButton = (Button) view.findViewById(R.id.dryCleanButton);
        washIronButton = (Button) view.findViewById(R.id.washIronButton);
        ironingButton = (Button) view.findViewById(R.id.ironingButton);
      /*  dryCleanButton.setTransformationMethod(null);
        washIronButton.setTransformationMethod(null);
        ironingButton.setTransformationMethod(null);
        dryCleanButton.setBackgroundResource(R.drawable.ic_btn_blue_background);
        washIronButton.setBackgroundResource(R.drawable.ic_button_selectable);
        ironingButton.setBackgroundResource(R.drawable.ic_button_selectable);
        dryCleanButton.setTextColor(Color.WHITE);
        washIronButton.setTextColor(Color.BLACK);
        ironingButton.setTextColor(Color.BLACK); */
        apiCalls = new ApiCalls();
        dialog = new SimpleArcDialog(getActivity());
        _activity = (TabHostActivity) getActivity();
        _activity.setListener(this);

        Almosky.getInst().setDrycleanpriceList(null);

        priceList = new ArrayList<>();


        //  listeners();

        if (Almosky.getInst().isUpdatePriceList()) {

            setUpListData();

        } else {
            getCategoryList();
        }


        dryCleanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setUpDryCleanListView();


            }
        });
        washIronButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mdryExpandableView.setVisibility(View.GONE);
                mironExpandableView.setVisibility(View.GONE);
                mwashExpandableView.setVisibility(View.VISIBLE);


                addItemsToWashListView(Almosky.getInst().getWashironpriceList(), 2);
                dryCleanButton.setBackgroundResource(R.drawable.ic_button_selectable);
                washIronButton.setBackgroundResource(R.drawable.ic_btn_blue_background);
                ironingButton.setBackgroundResource(R.drawable.ic_button_selectable);
                dryCleanButton.setTextColor(Color.BLACK);
                washIronButton.setTextColor(Color.WHITE);
                ironingButton.setTextColor(Color.BLACK);

            }
        });
        ironingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mdryExpandableView.setVisibility(View.GONE);
                mironExpandableView.setVisibility(View.VISIBLE);
                mwashExpandableView.setVisibility(View.GONE);


                addItemsToIronListView(Almosky.getInst().getIroningpriceList(), 3);
                dryCleanButton.setBackgroundResource(R.drawable.ic_button_selectable);
                washIronButton.setBackgroundResource(R.drawable.ic_button_selectable);
                ironingButton.setBackgroundResource(R.drawable.ic_btn_blue_background);
                dryCleanButton.setTextColor(Color.BLACK);
                washIronButton.setTextColor(Color.BLACK);
                ironingButton.setTextColor(Color.WHITE);


            }
        });




      /*  mdryExpandableView.setVisibility(View.VISIBLE);
        mironExpandableView.setVisibility(View.GONE);
        mwashExpandableView.setVisibility(View.GONE);

        addItemsToDrycleanListView(Almosky.getInst().getDrycleanpriceList(),1);
        dryCleanButton.setBackgroundResource(R.drawable.ic_btn_blue_background);
        washIronButton.setBackgroundResource(R.drawable.ic_button_selectable);
        ironingButton.setBackgroundResource(R.drawable.ic_button_selectable);
        dryCleanButton.setTextColor(Color.WHITE);
        washIronButton.setTextColor(Color.BLACK);
        ironingButton.setTextColor(Color.BLACK); */

        return view;
    }

    private void setUpDryCleanListView() {
        mdryExpandableView.setVisibility(View.VISIBLE);
        mironExpandableView.setVisibility(View.GONE);
        mwashExpandableView.setVisibility(View.GONE);

        addItemsToDrycleanListView(Almosky.getInst().getDrycleanpriceList(), 1);
        dryCleanButton.setBackgroundResource(R.drawable.ic_btn_blue_background);
        washIronButton.setBackgroundResource(R.drawable.ic_button_selectable);
        ironingButton.setBackgroundResource(R.drawable.ic_button_selectable);
        dryCleanButton.setTextColor(Color.WHITE);
        washIronButton.setTextColor(Color.BLACK);
        ironingButton.setTextColor(Color.BLACK);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !mIsVisibleToUser) {
            setUpDryCleanListView();
            mIsVisibleToUser = isVisibleToUser;
            mIsVisibleToUser = false;
        } else {
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    private void setUpListData() {


        try {

            ArrayList<priceListdto.Detail> priceDetails = Almosky.getInst().getPriceDetails();
            ArrayList<priceListdto.Detail.Item> detail = new ArrayList<>();

            ArrayList<categoryPriceList> drycleanpriceList = new ArrayList<>();

            ArrayList<categoryItemPrice> drycleanItemList = new ArrayList<>();
            ArrayList<categoryItemPrice> washironpriceList = new ArrayList<>();
            ArrayList<categoryItemPrice> ironingpriceList = new ArrayList<>();


            for (int j = 0; j < priceDetails.size(); j++) {

                if (priceDetails.get(j).getServiceId().equals(1)) {

                    for (int k = 0; k < priceDetails.get(j).getItems().size(); k++) {

                        priceListdto.Detail.Item itmList = priceDetails.get(j).getItems().get(k);

                        setupPriceData(itmList);
                    }

                }
            }

            for (int j = 0; j < priceDetails.size(); j++) {
                if (priceDetails.get(j).getServiceId().equals(2)) {

                    System.out.println("wash pricexlist");

                    for (int k = 0; k < priceDetails.get(j).getItems().size(); k++) {

                        priceListdto.Detail.Item itmList = priceDetails.get(j).getItems().get(k);

                        setupWashPriceData(itmList);
                    }

                }
            }

            for (int j = 0; j < priceDetails.size(); j++) {
                if (priceDetails.get(j).getServiceId().equals(3)) {
                    System.out.println("iron pricexlist");

                    for (int k = 0; k < priceDetails.get(j).getItems().size(); k++) {

                        priceListdto.Detail.Item itmList = priceDetails.get(j).getItems().get(k);

                        setupIronPriceData(itmList);
                    }

                }

            }
            addItemsToWashListView(Almosky.getInst().getDrycleanpriceList(), 1);
            //  addItemsToListView(Almosky.getInst().getWashironpriceList(),2);
            //  addItemsToListView(Almosky.getInst().getIroningpriceList(),3);
            //ArrayList<categoryPriceList> list = ;

        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    @Override
    public void onResume() {
        super.onResume();


    }

    private void getPriceList() {
        // if (!validate(view.getResources())) return;
        RequestParams params = new RequestParams();

        String url = ApiConstants.getPriceListUrl;
        apiCalls.callApiPost(_activity, params, dialog, url, 9);

    }

    private void getCategoryList() {

        // if (!validate(view.getResources())) return;
        RequestParams params = new RequestParams();

        // params.put("LoginEmail", "admin@gmail.com");

        // params.put(ApiConstants.uid, appPrefes.getData(PrefConstants.uid));
        // params.put(ApiConstants.uid, 1);
        //  params.put(ApiConstants.status, "Pending");


        String url = ApiConstants.categoryUrl;
        apiCalls.callApiPost(_activity, params, dialog, url, 10);
    }


    private void listeners() {
    /*    dryCleanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dryCleanButton.setBackgroundResource(R.drawable.ic_btn_blue_background);
                washIronButton.setBackgroundResource(R.drawable.ic_button_selectable);
                ironingButton.setBackgroundResource(R.drawable.ic_button_selectable);
                dryCleanButton.setTextColor(Color.WHITE);
                washIronButton.setTextColor(Color.BLACK);
                ironingButton.setTextColor(Color.BLACK);
            }
        });
        washIronButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dryCleanButton.setBackgroundResource(R.drawable.ic_button_selectable);
                washIronButton.setBackgroundResource(R.drawable.ic_btn_blue_background);
                ironingButton.setBackgroundResource(R.drawable.ic_button_selectable);
                dryCleanButton.setTextColor(Color.BLACK);
                washIronButton.setTextColor(Color.WHITE);
                ironingButton.setTextColor(Color.BLACK);
            }
        });
        ironingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dryCleanButton.setBackgroundResource(R.drawable.ic_button_selectable);
                washIronButton.setBackgroundResource(R.drawable.ic_button_selectable);
                ironingButton.setBackgroundResource(R.drawable.ic_btn_blue_background);
                dryCleanButton.setTextColor(Color.BLACK);
                washIronButton.setTextColor(Color.BLACK);
                ironingButton.setTextColor(Color.WHITE);
            }
        });  */
    }


    @Override
    public void fragmentResultInterface(String response, int requestId) {

        if (requestId == 10) {

            Gson gson = new Gson();
            final categorydto catList = gson.fromJson(response, categorydto.class);

            //categorydto.Detail detail=catList.getDetail();

            detailList = catList.getDetail();
            getPriceList();


        }
        if (requestId == 9) {

            try {
                Gson gson = new Gson();

                final priceListdto priceList = gson.fromJson(response, priceListdto.class);
                priceListdto dto = new priceListdto();
                priceListdto.Detail detaildto = dto.new Detail();

                Almosky.getInst().setPriceDetails(priceList.getDetail());

                ArrayList<priceListdto.Detail> priceDetails = priceList.getDetail();
                ArrayList<priceListdto.Detail.Item> detail = new ArrayList<>();

                ArrayList<categoryPriceList> drycleanpriceList = new ArrayList<>();

                ArrayList<categoryItemPrice> drycleanItemList = new ArrayList<>();
                ArrayList<categoryItemPrice> washironpriceList = new ArrayList<>();
                ArrayList<categoryItemPrice> ironingpriceList = new ArrayList<>();


                for (int j = 0; j < priceDetails.size(); j++) {

                    if (priceDetails.get(j).getServiceId().equals(1)) {

                        for (int k = 0; k < priceDetails.get(j).getItems().size(); k++) {

                            priceListdto.Detail.Item itmList = priceDetails.get(j).getItems().get(k);

                            setupPriceData(itmList);
                        }

                    }
                }

                for (int j = 0; j < priceDetails.size(); j++) {
                    if (priceDetails.get(j).getServiceId().equals(2)) {

                        System.out.println("wash pricexlist");

                        for (int k = 0; k < priceDetails.get(j).getItems().size(); k++) {

                            priceListdto.Detail.Item itmList = priceDetails.get(j).getItems().get(k);

                            setupWashPriceData(itmList);
                        }

                    }
                }

                for (int j = 0; j < priceDetails.size(); j++) {
                    if (priceDetails.get(j).getServiceId().equals(3)) {
                        System.out.println("iron pricexlist");

                        for (int k = 0; k < priceDetails.get(j).getItems().size(); k++) {

                            priceListdto.Detail.Item itmList = priceDetails.get(j).getItems().get(k);

                            setupIronPriceData(itmList);
                        }

                    }

                }
                addItemsToWashListView(Almosky.getInst().getDrycleanpriceList(), 1);
                //  addItemsToListView(Almosky.getInst().getWashironpriceList(),2);
                //  addItemsToListView(Almosky.getInst().getIroningpriceList(),3);
                //ArrayList<categoryPriceList> list = ;

            } catch (Exception e) {
                e.printStackTrace();

            }

            Almosky.getInst().setUpdatePriceList(true);

        }


    }

    private void setupPriceData(priceListdto.Detail.Item itmList) {

        ArrayList<categoryPriceList> mainlist = new ArrayList<>();


        mainlist = Almosky.getInst().getDrycleanpriceList();

        if (null != mainlist) {

            boolean iscatpresent = false;
            int icount = 0;

            for (int i = 0; i < mainlist.size(); i++) {

                if (mainlist.get(i).getCategoryId().equals(String.valueOf(itmList.getCategoryId()))) {

                    iscatpresent = true;
                    icount = i;

                }
            }

            if (iscatpresent) {


                ArrayList<categoryItemPrice> itemlist = mainlist.get(icount).getDrycleanprice();


                boolean ispresent = false;
                int jcount = 0;
                for (int j = 0; j < itemlist.size(); j++) {
                    if (Integer.parseInt(itemlist.get(j).getItemid()) == (itmList.getItemId())) { //if items present in this list

                        ispresent = true;
                        jcount = j;
                    }
                }

                if (ispresent) {  //item present
                    if (itmList.getDeliveryType().equals("NORMAL")) {
                        mainlist.get(icount).getDrycleanprice().get(jcount).setNormal(String.valueOf(itmList.getPrice()));
                        // itmprice.setNormal(String.valueOf(itmList.getPrice()));
                    }
                    if (itmList.getDeliveryType().equals("FAST SERVICE")) {
                        mainlist.get(icount).getDrycleanprice().get(jcount).setFast(String.valueOf(itmList.getPrice()));

                    }
                } else {

                    ArrayList<categoryItemPrice> itmpriceList = new ArrayList<>();

                    categoryItemPrice itmprice = new categoryItemPrice();
                    itmprice.setName(itmList.getItemName());
                    itmprice.setItemid(String.valueOf(itmList.getItemId()));

                    if (itmList.getDeliveryType().equals("NORMAL")) {
                        itmprice.setNormal(String.valueOf(itmList.getPrice()));
                    }
                    if (itmList.getDeliveryType().equals("FAST SERVICE")) {
                        itmprice.setFast(String.valueOf(itmList.getPrice()));
                    }
                    itmpriceList.add(itmprice);
                    mainlist.get(icount).getDrycleanprice().addAll(itmpriceList);

                }


            } else {  //category not present


                categoryPriceList catpriceList = new categoryPriceList();

                ArrayList<categoryPriceList> list = new ArrayList<>();

                ArrayList<categoryItemPrice> itmpriceList = new ArrayList<>();

                catpriceList.setCategoryId(String.valueOf(itmList.getCategoryId()));
                catpriceList.setCategoryName(String.valueOf(itmList.getCategoryName()));


                categoryItemPrice itmprice = new categoryItemPrice();
                itmprice.setName(itmList.getItemName());
                itmprice.setItemid(String.valueOf(itmList.getItemId()));

                if (itmList.getDeliveryType().equals("NORMAL")) {
                    itmprice.setNormal(String.valueOf(itmList.getPrice()));
                }
                if (itmList.getDeliveryType().equals("FAST SERVICE")) {
                    itmprice.setFast(String.valueOf(itmList.getPrice()));
                }
                itmpriceList.add(itmprice);
                catpriceList.setDrycleanprice(itmpriceList);
                list.add(catpriceList);
                // mainlist.addAll(list);
                Almosky.getInst().getDrycleanpriceList().addAll(list);


            }


        } else {  //no item in main list

            categoryPriceList catpriceList = new categoryPriceList();
            ArrayList<categoryPriceList> list = new ArrayList<>();

            ArrayList<categoryItemPrice> itmpriceList = new ArrayList<>();

            catpriceList.setCategoryId(String.valueOf(itmList.getCategoryId()));
            catpriceList.setCategoryName(String.valueOf(itmList.getCategoryName()));


            categoryItemPrice itmprice = new categoryItemPrice();
            itmprice.setName(itmList.getItemName());
            itmprice.setItemid(String.valueOf(itmList.getItemId()));

            if (itmList.getDeliveryType().equals("NORMAL")) {
                itmprice.setNormal(String.valueOf(itmList.getPrice()));
            }
            if (itmList.getDeliveryType().equals("FAST SERVICE")) {
                itmprice.setFast(String.valueOf(itmList.getPrice()));
            }
            itmpriceList.add(itmprice);
            catpriceList.setDrycleanprice(itmpriceList);
            list.add(catpriceList);

            Almosky.getInst().setDrycleanpriceList(list);


        }


    }

    private void setupWashPriceData(priceListdto.Detail.Item itmList) {

        ArrayList<categoryPriceList> mainlist = new ArrayList<>();


        mainlist = Almosky.getInst().getWashironpriceList();

        if (null != mainlist) {

            boolean iscatpresent = false;
            int icount = 0;

            for (int i = 0; i < mainlist.size(); i++) {

                if (mainlist.get(i).getCategoryId().equals(String.valueOf(itmList.getCategoryId()))) {

                    iscatpresent = true;
                    icount = i;

                }
            }

            if (iscatpresent) {


                ArrayList<categoryItemPrice> itemlist = mainlist.get(icount).getWashironprice();


                boolean ispresent = false;
                int jcount = 0;
                for (int j = 0; j < itemlist.size(); j++) {
                    if (Integer.parseInt(itemlist.get(j).getItemid()) == (itmList.getItemId())) { //if items present in this list

                        ispresent = true;
                        jcount = j;
                    }
                }

                if (ispresent) {  //item present
                    if (itmList.getDeliveryType().equals("NORMAL")) {
                        mainlist.get(icount).getWashironprice().get(jcount).setNormal(String.valueOf(itmList.getPrice()));
                        // itmprice.setNormal(String.valueOf(itmList.getPrice()));
                    }
                    if (itmList.getDeliveryType().equals("FAST SERVICE")) {
                        mainlist.get(icount).getWashironprice().get(jcount).setFast(String.valueOf(itmList.getPrice()));

                    }
                } else {

                    ArrayList<categoryItemPrice> itmpriceList = new ArrayList<>();

                    categoryItemPrice itmprice = new categoryItemPrice();
                    itmprice.setName(itmList.getItemName());
                    itmprice.setItemid(String.valueOf(itmList.getItemId()));

                    if (itmList.getDeliveryType().equals("NORMAL")) {
                        itmprice.setNormal(String.valueOf(itmList.getPrice()));
                    }
                    if (itmList.getDeliveryType().equals("FAST SERVICE")) {
                        itmprice.setFast(String.valueOf(itmList.getPrice()));
                    }
                    itmpriceList.add(itmprice);
                    mainlist.get(icount).getWashironprice().addAll(itmpriceList);

                }


            } else {  //category not present


                categoryPriceList catpriceList = new categoryPriceList();

                ArrayList<categoryPriceList> list = new ArrayList<>();

                ArrayList<categoryItemPrice> itmpriceList = new ArrayList<>();

                catpriceList.setCategoryId(String.valueOf(itmList.getCategoryId()));
                catpriceList.setCategoryName(String.valueOf(itmList.getCategoryName()));


                categoryItemPrice itmprice = new categoryItemPrice();
                itmprice.setName(itmList.getItemName());
                itmprice.setItemid(String.valueOf(itmList.getItemId()));

                if (itmList.getDeliveryType().equals("NORMAL")) {
                    itmprice.setNormal(String.valueOf(itmList.getPrice()));
                }
                if (itmList.getDeliveryType().equals("FAST SERVICE")) {
                    itmprice.setFast(String.valueOf(itmList.getPrice()));
                }
                itmpriceList.add(itmprice);
                catpriceList.setWashironprice(itmpriceList);
                list.add(catpriceList);
                // mainlist.addAll(list);
                Almosky.getInst().getWashironpriceList().addAll(list);


            }

        } else {  //no item in main list

            categoryPriceList catpriceList = new categoryPriceList();
            ArrayList<categoryPriceList> list = new ArrayList<>();

            ArrayList<categoryItemPrice> itmpriceList = new ArrayList<>();

            catpriceList.setCategoryId(String.valueOf(itmList.getCategoryId()));
            catpriceList.setCategoryName(String.valueOf(itmList.getCategoryName()));


            categoryItemPrice itmprice = new categoryItemPrice();
            itmprice.setName(itmList.getItemName());
            itmprice.setItemid(String.valueOf(itmList.getItemId()));

            if (itmList.getDeliveryType().equals("NORMAL")) {
                itmprice.setNormal(String.valueOf(itmList.getPrice()));
            }
            if (itmList.getDeliveryType().equals("FAST SERVICE")) {
                itmprice.setFast(String.valueOf(itmList.getPrice()));
            }
            itmpriceList.add(itmprice);
            catpriceList.setWashironprice(itmpriceList);
            list.add(catpriceList);

            Almosky.getInst().setWashironpriceList(list);


        }


    }

    private void setupIronPriceData(priceListdto.Detail.Item itmList) {

        ArrayList<categoryPriceList> mainlist = new ArrayList<>();


        mainlist = Almosky.getInst().getIroningpriceList();

        if (null != mainlist) {

            boolean iscatpresent = false;
            int icount = 0;

            for (int i = 0; i < mainlist.size(); i++) {

                if (mainlist.get(i).getCategoryId().equals(String.valueOf(itmList.getCategoryId()))) {

                    iscatpresent = true;
                    icount = i;

                }
            }

            if (iscatpresent) {


                ArrayList<categoryItemPrice> itemlist = mainlist.get(icount).getIroningprice();


                boolean ispresent = false;
                int jcount = 0;
                for (int j = 0; j < itemlist.size(); j++) {
                    if (Integer.parseInt(itemlist.get(j).getItemid()) == (itmList.getItemId())) { //if items present in this list

                        ispresent = true;
                        jcount = j;
                    }
                }

                if (ispresent) {  //item present
                    if (itmList.getDeliveryType().equals("NORMAL")) {
                        mainlist.get(icount).getIroningprice().get(jcount).setNormal(String.valueOf(itmList.getPrice()));
                        // itmprice.setNormal(String.valueOf(itmList.getPrice()));
                    }
                    if (itmList.getDeliveryType().equals("FAST SERVICE")) {
                        mainlist.get(icount).getIroningprice().get(jcount).setFast(String.valueOf(itmList.getPrice()));

                    }
                } else {

                    ArrayList<categoryItemPrice> itmpriceList = new ArrayList<>();

                    categoryItemPrice itmprice = new categoryItemPrice();
                    itmprice.setName(itmList.getItemName());
                    itmprice.setItemid(String.valueOf(itmList.getItemId()));

                    if (itmList.getDeliveryType().equals("NORMAL")) {
                        itmprice.setNormal(String.valueOf(itmList.getPrice()));
                    }
                    if (itmList.getDeliveryType().equals("FAST SERVICE")) {
                        itmprice.setFast(String.valueOf(itmList.getPrice()));
                    }
                    itmpriceList.add(itmprice);
                    mainlist.get(icount).getIroningprice().addAll(itmpriceList);

                }


            } else {  //category not present


                categoryPriceList catpriceList = new categoryPriceList();

                ArrayList<categoryPriceList> list = new ArrayList<>();

                ArrayList<categoryItemPrice> itmpriceList = new ArrayList<>();

                catpriceList.setCategoryId(String.valueOf(itmList.getCategoryId()));
                catpriceList.setCategoryName(String.valueOf(itmList.getCategoryName()));


                categoryItemPrice itmprice = new categoryItemPrice();
                itmprice.setName(itmList.getItemName());
                itmprice.setItemid(String.valueOf(itmList.getItemId()));

                if (itmList.getDeliveryType().equals("NORMAL")) {
                    itmprice.setNormal(String.valueOf(itmList.getPrice()));
                }
                if (itmList.getDeliveryType().equals("FAST SERVICE")) {
                    itmprice.setFast(String.valueOf(itmList.getPrice()));
                }
                itmpriceList.add(itmprice);
                catpriceList.setIroningprice(itmpriceList);
                list.add(catpriceList);
                // mainlist.addAll(list);
                Almosky.getInst().getIroningpriceList().addAll(list);


            }


        } else {  //no item in main list

            categoryPriceList catpriceList = new categoryPriceList();
            ArrayList<categoryPriceList> list = new ArrayList<>();

            ArrayList<categoryItemPrice> itmpriceList = new ArrayList<>();

            catpriceList.setCategoryId(String.valueOf(itmList.getCategoryId()));
            catpriceList.setCategoryName(String.valueOf(itmList.getCategoryName()));


            categoryItemPrice itmprice = new categoryItemPrice();
            itmprice.setName(itmList.getItemName());
            itmprice.setItemid(String.valueOf(itmList.getItemId()));

            if (itmList.getDeliveryType().equals("NORMAL")) {
                itmprice.setNormal(String.valueOf(itmList.getPrice()));
            }
            if (itmList.getDeliveryType().equals("FAST SERVICE")) {
                itmprice.setFast(String.valueOf(itmList.getPrice()));
            }
            itmpriceList.add(itmprice);
            catpriceList.setIroningprice(itmpriceList);
            list.add(catpriceList);

            Almosky.getInst().setIroningpriceList(list);


        }


    }


    private void addItemsToIronListView(ArrayList<categoryPriceList> list, int type) {

        System.out.print("showing list");

        mironExpandableView.removeAllViews();

        if (null != list) {
            for (int i = 0; i < list.size(); i++) {


                mironExpandableView.addView(new HeadingView(getActivity(), list.get(i).getCategoryName(), "", 2));

                for (int j = 0; j < list.get(i).getIroningprice().size(); j++) {

                    mironExpandableView.addView(new InfoView1(getActivity(), list.get(i).getIroningprice().get(j)));
                }


            }
            // mExpandableView.refresh();
        }

    }

    private void addItemsToWashListView(ArrayList<categoryPriceList> list, int type) {

        System.out.print("showing list");

        mwashExpandableView.removeAllViews();
        if (null != list) {
            for (int i = 0; i < list.size(); i++) {


                mwashExpandableView.addView(new HeadingView(getActivity(), list.get(i).getCategoryName(), "", 2));

                for (int j = 0; j < list.get(i).getWashironprice().size(); j++) {

                    mwashExpandableView.addView(new InfoView1(getActivity(), list.get(i).getWashironprice().get(j)));
                }


            }
            // mExpandableView.refresh();
        }

    }

    private void addItemsToDrycleanListView(ArrayList<categoryPriceList> list, int type) {

        System.out.print("showing list");

        mdryExpandableView.removeAllViews();
        if (null != list) {
            for (int i = 0; i < list.size(); i++) {


                mdryExpandableView.addView(new HeadingView(getActivity(), list.get(i).getCategoryName(), "", 2));

                for (int j = 0; j < list.get(i).getDrycleanprice().size(); j++) {

                    mdryExpandableView.addView(new InfoView1(getActivity(), list.get(i).getDrycleanprice().get(j)));
                }


            }

            //  mExpandableView.refresh();
        }

    }

/*
    private void addItemsToListView(ArrayList<categoryPriceList> list,int type) {

        System.out.print("showing list");


        if (null != list) {
            for (int i = 0; i < list.size(); i++) {


                mExpandableView.addView(new HeadingView(getActivity(), list.get(i).getCategoryName(), "", 2));

                if(type==1){
                    for (int j = 0; j < list.get(i).getDrycleanprice().size(); j++) {

                        mExpandableView.addView(new InfoView1(getActivity(), list.get(i).getDrycleanprice().get(j)));
                    }
                }

                if(type==2){
                    for (int j = 0; j < list.get(i).getWashironprice().size(); j++) {

                        mExpandableView.addView(new InfoView1(getActivity(), list.get(i).getWashironprice().get(j)));
                    }
                }

                if(type==3){
                    for (int j = 0; j < list.get(i).getIroningprice().size(); j++) {

                        mExpandableView.addView(new InfoView1(getActivity(), list.get(i).getIroningprice().get(j)));
                    }
                }


            }
        }

    }  */


}
