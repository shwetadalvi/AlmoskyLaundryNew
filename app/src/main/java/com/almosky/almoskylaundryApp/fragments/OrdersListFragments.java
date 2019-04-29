package com.almosky.almoskylaundryApp.fragments;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.TabHostActivity;
import com.almosky.almoskylaundryApp.activity.SignupOrLoginActivity;
import com.almosky.almoskylaundryApp.adapter.OrderListAdapter;
import com.almosky.almoskylaundryApp.model.OrderListdto;
import com.almosky.almoskylaundryApp.utils.AppPrefes;
import com.almosky.almoskylaundryApp.utils.api.ApiCalls;
import com.almosky.almoskylaundryApp.utils.constants.ApiConstants;
import com.almosky.almoskylaundryApp.utils.constants.PrefConstants;
import com.google.gson.Gson;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.RequestParams;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersListFragments extends Fragment implements TabHostActivity.FragmentOrderResultInterface{
    AppPrefes appPrefes;
    ApiCalls apiCalls;
    SimpleArcDialog dialog;
    TabHostActivity tabHostActivity;
    RecyclerView rvOrders;

    private static final String ARG_PAGE_NUMBER = "page_number";

    public OrdersListFragments() {
        // Required empty public constructor
    }

    public static OrdersListFragments newInstance(int page) {
        OrdersListFragments fragment = new OrdersListFragments();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_orderslist, container, false);
       tabHostActivity=(TabHostActivity)getActivity();
       tabHostActivity.setOrderListener(this);
        rvOrders=(RecyclerView)view.findViewById(R.id.rvorderList);
        apiCalls=new ApiCalls();
        appPrefes=new AppPrefes(tabHostActivity);
        dialog=new SimpleArcDialog(tabHostActivity);

        if(appPrefes.getBoolData(PrefConstants.isLogin)){
            getOrders();
        }else {
            startActivity(new Intent(getActivity(), SignupOrLoginActivity.class));
        }




     /*   ConstraintLayout easyOrderLyt = (ConstraintLayout) view.findViewById(R.id.easyOrderLyt);
        easyOrderLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Almosky.getInst().setOrderType("easy");
                startActivity(new Intent(getActivity(), OrdertypeActivity.class));
            }
        });
        ConstraintLayout normalOrderLyt = (ConstraintLayout) view.findViewById(R.id.normalOrderLyt);
        normalOrderLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Almosky.getInst().setOrderType("enter");
                startActivity(new Intent(getActivity(), OrdertypeActivity.class));
            }
        });*/
        return view;
    }

    private void getOrders() {

        // if (!validate(view.getResources())) return;
        RequestParams params = new RequestParams();

        params.put("email",  appPrefes.getData(PrefConstants.email));
      //  params.put("status",  "All");

        // params.put(ApiConstants.uid, appPrefes.getData(PrefConstants.uid));
        // params.put(ApiConstants.uid, 1);
        //  params.put(ApiConstants.status, "Pending");


        String url = ApiConstants.orderListUrl;
        apiCalls.callApiPost(tabHostActivity, params, dialog, url, 1);


    }

    @Override
    public void fragmentorderResultInterface(String response, int requestId) {

        try{
            Gson gson = new Gson();
            final OrderListdto orderList = gson.fromJson(response, OrderListdto.class);




            if(null==orderList.getResult()){
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)

                        .setContentText("No Orders")
                        .show();

            }else {
                OrderListAdapter mAdapter = new OrderListAdapter(tabHostActivity, orderList.getResult());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(tabHostActivity);
                rvOrders.setLayoutManager(mLayoutManager);
                rvOrders.setItemAnimator(new DefaultItemAnimator());
                rvOrders.setAdapter(mAdapter);
            }



        }catch (Exception e){

        }
    }


}
