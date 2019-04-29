package com.almosky.almoskylaundryApp.fragments;


import android.content.Intent;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.almosky.almoskylaundryApp.Almosky;
import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.activity.SignupOrLoginActivity;
import com.almosky.almoskylaundryApp.activity.neworder.OrdertypeActivity;
import com.almosky.almoskylaundryApp.utils.AppPrefes;
import com.almosky.almoskylaundryApp.utils.constants.PrefConstants;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragments extends Fragment {

    private static final String ARG_PAGE_NUMBER = "page_number";
    AppPrefes appPrefes;

    public OrdersFragments() {
        // Required empty public constructor
    }

    public static OrdersFragments newInstance(int page) {
        OrdersFragments fragment = new OrdersFragments();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders_fragments, container, false);

        appPrefes=new AppPrefes(getActivity());



        ConstraintLayout easyOrderLyt = (ConstraintLayout) view.findViewById(R.id.easyOrderLyt);
        easyOrderLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Almosky.getInst().setOrderType("easy");
                Almosky.getInst().setAddressType("0");
                if(appPrefes.getBoolData(PrefConstants.isLogin)){
                    startActivity(new Intent(getActivity(), OrdertypeActivity.class));
                }else {
                    startActivity(new Intent(getActivity(), SignupOrLoginActivity.class));
                }

            }
        });
        ConstraintLayout normalOrderLyt = (ConstraintLayout) view.findViewById(R.id.normalOrderLyt);
        normalOrderLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           //     appPrefes.saveBoolData(PrefConstants.isLogin, true);

                Almosky.getInst().setAddressType("1");
                Almosky.getInst().setOrderType("enter");
                if(appPrefes.getBoolData(PrefConstants.isLogin)){
                    startActivity(new Intent(getActivity(), OrdertypeActivity.class));
                }else {
                    startActivity(new Intent(getActivity(), SignupOrLoginActivity.class));
                }

            }
        });
        return view;
    }

}
