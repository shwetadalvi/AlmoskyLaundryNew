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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

import java.util.ArrayList;

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
    ArrayList<String> orderArrayList ;
    private static final String ARG_PAGE_NUMBER = "page_number";
    private String selectedOrder;
private Spinner spinner;
    ArrayList<OrderListdto.Result> activeOrderList = new ArrayList<>();
    ArrayList<OrderListdto.Result> notAcceptedOrderList = new ArrayList<>();
    ArrayList<OrderListdto.Result> acceptedOrderList = new ArrayList<>();
    ArrayList<OrderListdto.Result> confirmOrderList = new ArrayList<>();
    ArrayList<OrderListdto.Result> inProgressOrderList = new ArrayList<>();
    ArrayList<OrderListdto.Result> completedOrderList = new ArrayList<>();
    ArrayList<OrderListdto.Result> outForDeliverysOrderList = new ArrayList<>();
    ArrayList<OrderListdto.Result> deliveredOrderList = new ArrayList<>();

    OrderListAdapter mAdapter;
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
        spinner = (Spinner)view.findViewById(R.id.spinner);
        apiCalls=new ApiCalls();
        appPrefes=new AppPrefes(tabHostActivity);
        dialog=new SimpleArcDialog(tabHostActivity);

        orderArrayList = new ArrayList<String>();
        orderArrayList.add("Select");
        orderArrayList.add("Not Accepted Yet");
        orderArrayList.add("Accepted");
        orderArrayList.add("Confirm");
        orderArrayList.add("In Progress");
        orderArrayList.add("Completed");
        orderArrayList.add("Out For Delivery");
        orderArrayList.add("Delivered");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, orderArrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                selectedOrder = orderArrayList.get(position);
                showFilteredData(selectedOrder);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
              //  selectedOrder = orderArrayList.get(1);
            }
        });



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

    private void showFilteredData(String selectedOrder) {
        if(selectedOrder.equalsIgnoreCase("Select"))
        mAdapter = new OrderListAdapter(tabHostActivity, activeOrderList);
        if(selectedOrder.equalsIgnoreCase("Not Accepted Yet"))
            mAdapter = new OrderListAdapter(tabHostActivity, notAcceptedOrderList);
        if(selectedOrder.equalsIgnoreCase("Accepted"))
            mAdapter = new OrderListAdapter(tabHostActivity, acceptedOrderList);
        if(selectedOrder.equalsIgnoreCase("Confirm"))
            mAdapter = new OrderListAdapter(tabHostActivity, confirmOrderList);
        if(selectedOrder.equalsIgnoreCase("In Progress"))
            mAdapter = new OrderListAdapter(tabHostActivity, inProgressOrderList);
        if(selectedOrder.equalsIgnoreCase("Completed"))
            mAdapter = new OrderListAdapter(tabHostActivity, completedOrderList);
        if(selectedOrder.equalsIgnoreCase("Out For Delivery"))
            mAdapter = new OrderListAdapter(tabHostActivity, outForDeliverysOrderList);
        if(selectedOrder.equalsIgnoreCase("Delivered"))
            mAdapter = new OrderListAdapter(tabHostActivity, deliveredOrderList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(tabHostActivity);
        rvOrders.setLayoutManager(mLayoutManager);
        rvOrders.setItemAnimator(new DefaultItemAnimator());
        rvOrders.setAdapter(mAdapter);
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

                for (OrderListdto.Result item : orderList.getResult()){
                    if(item.getOrderStatus() == 0 || item.getOrderStatus() == 1 || item.getOrderStatus() == 2|| item.getOrderStatus() == 3 || item.getOrderStatus() == 5)
                        activeOrderList.add(item);
                    if(item.getOrderStatus() == 0)
                        notAcceptedOrderList.add(item);
                    if(item.getOrderStatus() == 1)
                        acceptedOrderList.add(item);
                    if(item.getOrderStatus() == 2)
                        confirmOrderList.add(item);
                    if(item.getOrderStatus() == 3)
                        inProgressOrderList.add(item);
                    if(item.getOrderStatus() == 4)
                        completedOrderList.add(item);
                    if(item.getOrderStatus() == 5)
                        outForDeliverysOrderList.add(item);
                    if(item.getOrderStatus() == 6)
                        deliveredOrderList.add(item);
                }

                mAdapter = new OrderListAdapter(tabHostActivity, activeOrderList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(tabHostActivity);
                rvOrders.setLayoutManager(mLayoutManager);
                rvOrders.setItemAnimator(new DefaultItemAnimator());
                rvOrders.setAdapter(mAdapter);
            }



        }catch (Exception e){

        }
    }


}
