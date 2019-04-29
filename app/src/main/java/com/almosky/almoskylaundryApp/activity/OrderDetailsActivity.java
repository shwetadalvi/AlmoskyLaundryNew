package com.almosky.almoskylaundryApp.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.almosky.almoskylaundryApp.Almosky;
import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.activity.payment.PaymentActivity;
import com.almosky.almoskylaundryApp.adapter.DryCleanRecyclerViewAdapter1;
import com.almosky.almoskylaundryApp.adapter.IroningRecyclerViewAdapter1;
import com.almosky.almoskylaundryApp.adapter.WashIronRecyclerViewAdapter1;
import com.almosky.almoskylaundryApp.common.BaseActivity;
import com.almosky.almoskylaundryApp.databinding.ActivityOrderDetailsBinding;
import com.almosky.almoskylaundryApp.model.OrderDetailsModel;
import com.almosky.almoskylaundryApp.model.OrderListdto;
import com.almosky.almoskylaundryApp.model.data1;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class OrderDetailsActivity extends BaseActivity {

    private ActivityOrderDetailsBinding binding;
    private OrderDetailsModel model;
    private DryCleanRecyclerViewAdapter1 dryCleanRecyclerViewAdapter;
    private WashIronRecyclerViewAdapter1 washIronRecyclerViewAdapter;
    private IroningRecyclerViewAdapter1 ironingRecyclerViewAdapter;

    String grandTotal;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_order_details);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_details);
        model = new OrderDetailsModel();
        binding.setModel(model);


        if(null!=Almosky.getInst().getSelectedOrder()){

            setDetails();

        }

        binding.btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go=new Intent(OrderDetailsActivity.this, PaymentActivity.class);
                go.putExtra("amount", binding.subtotalPrice.getText().toString());
                go.putExtra("orderData","");
                go.putExtra("orderId", Almosky.getInst().getSelectedOrder().getOrderId());
                startActivity(go);
            }
        });

    }

    public static String PerfectDecimal(String str, int MAX_BEFORE_POINT, int MAX_DECIMAL) {
        if (str.charAt(0) == '.') str = "0" + str;
        int max = str.length();

        String rFinal = "";
        boolean after = false;
        int i = 0, up = 0, decimal = 0;
        char t;
        while (i < max) {
            t = str.charAt(i);
            if (t != '.' && after == false) {
                up++;
                if (up > MAX_BEFORE_POINT) return rFinal;
            } else if (t == '.') {
                after = true;
            } else {
                decimal++;
                if (decimal > MAX_DECIMAL)
                    return rFinal;
            }
            rFinal = rFinal + t;
            i++;
        }
        return rFinal;
    }


    private void setDetails() {

        double total = 0.0;

        String SplitPickDate[]=Almosky.getInst().getSelectedOrder().getPickupDate().split("T");
        String SplitDelDate[]=Almosky.getInst().getSelectedOrder().getDelDate().split("T");

        binding.textPickUpDate.setText(SplitPickDate[0]+"\n"+Almosky.getInst().getSelectedOrder().getPickupTime());
        //  binding.textPickUpTime.setText(Almosky.getInst().getPickuptime());
        binding.textDeliveryDate.setText(SplitDelDate[0]+"\n"+Almosky.getInst().getSelectedOrder().getDelTime());
        //binding.textDeliveryTime.setText(Almosky.getInst().getDeliverytime());
        // binding.textAddress.setText(address);

        // String s=Almosky.getInst().getSelectedOrder().getDeliveryType();

        if(Almosky.getInst().getSelectedOrder().getDeliveryType()!=null){
            if(Almosky.getInst().getSelectedOrder().getDeliveryType()==1){
                binding.textDeliveryType.setText("Delivery Type :  Normal");
            }
            if(Almosky.getInst().getSelectedOrder().getDeliveryType()==2){
                binding.textDeliveryType.setText("Delivery Type :  Fast");
            }
        }

        if(Almosky.getInst().getSelectedOrder().getPayment_status()==0){

            binding.lytPayment.setVisibility(View.VISIBLE);
        }

        OrderListdto.Result orderdata;

        orderdata=Almosky.getInst().getSelectedOrder();

        ArrayList<data1.Result> drylist = new ArrayList<>();
        ArrayList<data1.Result> washlist = new ArrayList<>();
        ArrayList<data1.Result> ironlist = new ArrayList<>();


        OrderListdto obj=new OrderListdto();
        OrderListdto.Result obj2=obj. new Result();
        OrderListdto.Result.Result_ obj3 = obj2.new Result_();
        data1 ob=new data1();
        for (int i = 0; i < orderdata.getResult().size(); i++) {

            if (orderdata.getResult().get(i).getServiceID().equals("1")) {


                ArrayList<OrderListdto.Result.Result_.Item> items=orderdata.getResult().get(i).getItems();

                for(int j=0;j<items.size();j++){
                    data1.Result subdto=ob. new Result();

                    subdto.setQty(items.get(j).getQty());
                    subdto.setItemID(items.get(j).getItemID());
                    subdto.setOrderID(items.get(j).getOrderID());
                    subdto.setPrice(items.get(j).getPrice());
                    subdto.setItemName(items.get(j).getItemName());
                    subdto.setServiceID(items.get(j).getServiceID());
                    subdto.setOrderSubID(items.get(j).getOrderSubID());

                    total = total + (items.get(j).getPrice() * items.get(j).getQty());

                    drylist.add(subdto);
                }




            }
            Almosky.getInst().setDrycleanList1(drylist);
        }





        for (int i = 0; i < orderdata.getResult().size(); i++) {

            if (orderdata.getResult().get(i).getServiceID().equals("2")) {


                ArrayList<OrderListdto.Result.Result_.Item> items=orderdata.getResult().get(i).getItems();

                for(int j=0;j<items.size();j++){
                    data1.Result subdto=ob. new Result();
                    subdto.setQty(items.get(j).getQty());
                    subdto.setItemID(items.get(j).getItemID());
                    subdto.setOrderID(items.get(j).getOrderID());
                    subdto.setPrice(items.get(j).getPrice());
                    subdto.setItemName(items.get(j).getItemName());
                    subdto.setServiceID(items.get(j).getServiceID());
                    subdto.setOrderSubID(items.get(j).getOrderSubID());

                    total = total + (items.get(j).getPrice() * items.get(j).getQty());

                    washlist.add(subdto);
                }




            }
            Almosky.getInst().setWashList1(washlist);
        }


        for (int i = 0; i < orderdata.getResult().size(); i++) {

            try{
                if (orderdata.getResult().get(i).getServiceID().equals("3")) {


                    ArrayList<OrderListdto.Result.Result_.Item> items=orderdata.getResult().get(i).getItems();

                    for(int j=0;j<items.size();j++){
                        data1.Result subdto=ob. new Result();
                        subdto.setQty(items.get(j).getQty());
                        subdto.setItemID(items.get(j).getItemID());
                        subdto.setOrderID(items.get(j).getOrderID());
                        subdto.setPrice(items.get(j).getPrice());
                        subdto.setItemName(items.get(j).getItemName());
                        subdto.setServiceID(items.get(j).getServiceID());
                        subdto.setOrderSubID(items.get(j).getOrderSubID());

                        total = total + (items.get(j).getPrice() * items.get(j).getQty());

                        ironlist.add(subdto);
                    }




                }
                Almosky.getInst().setIronList1(ironlist);
            }catch (Exception e){

            }


        }





        if (null != Almosky.getInst().getDrycleanList1()) {


            binding.easyOrderDetailsLayout.setVisibility(View.GONE);
            setDryCleanAdapter();
            // setWashIronAdapter();
            //setIroningAdapter();
            //   updateTotal();
        }
        if (null != Almosky.getInst().getIronList1()) {


            binding.easyOrderDetailsLayout.setVisibility(View.GONE);
            setIroningAdapter();
            // setWashIronAdapter();
            //setIroningAdapter();
            //   updateTotal();
        }
        if (null != Almosky.getInst().getWashList1()) {


            binding.easyOrderDetailsLayout.setVisibility(View.GONE);
            setWashIronAdapter();
            // setWashIronAdapter();
            //setIroningAdapter();
            //   updateTotal();
        } else {
            binding.detailsLayout.setVisibility(View.GONE);
            binding.lytTotal.setVisibility(View.GONE);
        }

        if (null == Almosky.getInst().getWashList1()) {

            binding.washIronHeaderLayout.setVisibility(View.GONE);
        }
        if (null == Almosky.getInst().getIronList1()) {

            binding.ironingHeaderLayout.setVisibility(View.GONE);
        }

        if (null == Almosky.getInst().getDrycleanList1()) {

            binding.dryCleanHeaderLayout.setVisibility(View.GONE);
        }

        binding.total.setText("AED" + String.valueOf(total));
        binding.vattotalPrice.setText("AED" + PerfectDecimal(String.valueOf(total * 0.05), 2, 2));

        binding.subtotalPrice.setText(String.valueOf(total + (total * 0.05))+"AED");

        //   grandTotal=("AED" + String.valueOf(total + (total * 0.05)));



    }

    private void setDryCleanAdapter() {

        if (null != Almosky.getInst().getDrycleanList1()) {

            dryCleanRecyclerViewAdapter = new DryCleanRecyclerViewAdapter1(OrderDetailsActivity.this, Almosky.getInst().getDrycleanList1(), this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(OrderDetailsActivity.this);
            binding.dryCleanRecyclerView.setNestedScrollingEnabled(false);
            binding.dryCleanRecyclerView.setLayoutManager(mLayoutManager);
            binding.dryCleanRecyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.dryCleanRecyclerView.setAdapter(dryCleanRecyclerViewAdapter);
        } else {
            binding.dryCleanLayout.setVisibility(View.GONE);

        }

    }

    private void setWashIronAdapter() {

        if (null != Almosky.getInst().getWashList1()) {

            washIronRecyclerViewAdapter = new WashIronRecyclerViewAdapter1(OrderDetailsActivity.this, Almosky.getInst().getWashList1(), this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(OrderDetailsActivity.this);
            binding.washIronRecyclerView.setNestedScrollingEnabled(false);
            binding.washIronRecyclerView.setLayoutManager(mLayoutManager);
            binding.washIronRecyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.washIronRecyclerView.setAdapter(washIronRecyclerViewAdapter);
        } else {

            binding.washIronLayout.setVisibility(View.GONE);
        }

    }

    private void setIroningAdapter() {

        if (null != Almosky.getInst().getIronList1()) {

            ironingRecyclerViewAdapter = new IroningRecyclerViewAdapter1(OrderDetailsActivity.this, Almosky.getInst().getIronList1(), this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(OrderDetailsActivity.this);
            binding.ironingRecyclerView.setNestedScrollingEnabled(false);
            binding.ironingRecyclerView.setLayoutManager(mLayoutManager);
            binding.ironingRecyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.ironingRecyclerView.setAdapter(ironingRecyclerViewAdapter);

        } else {
            binding.ironingLayout.setVisibility(View.GONE);

        }

    }

}
