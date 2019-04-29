package com.almosky.almoskylaundryApp.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.activity.OrderDetailsActivity;
import com.almosky.almoskylaundryApp.model.data1;
import com.almosky.almoskylaundryApp.viewholder.DryCleanRecyclerViewHolders1;

import java.util.ArrayList;


public class DryCleanRecyclerViewAdapter1 extends RecyclerView.Adapter<DryCleanRecyclerViewHolders1> {

    //    private List<UserLogCountResponse.DetailsBean> itemList;
    private Context context;
    private ArrayList<data1.Result> items;
    OrderDetailsActivity _activity;


    public DryCleanRecyclerViewAdapter1(Context context, ArrayList<data1.Result> data, OrderDetailsActivity activity) {
//        this.itemList = itemList;
        this.context = context;
        this.items=data;
        this._activity=activity;
    }

    @Override
    public DryCleanRecyclerViewHolders1 onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dry_clean_item, null);
        DryCleanRecyclerViewHolders1 rcv = new DryCleanRecyclerViewHolders1(layoutView, context,_activity);
        return rcv;
    }

    @Override
    public void onBindViewHolder(DryCleanRecyclerViewHolders1 holder, int position) {
//        UserLogCountResponse.DetailsBean status = itemList.get(position);
//        holder.bind(status);

        data1.Result item=items.get(position);
        holder.bind(item);

        holder.dryitem.setText(items.get(position).getItemName());
        holder.drycount.setText(String.valueOf(items.get(position).getQty()));
        // holder.drycount.setText(String.valueOf(items.get(position).getQty()));
        holder.dryamount.setText(String.valueOf((items.get(position).getQty())*(items.get(position).getPrice())));

    }


    @Override
    public int getItemCount() {
//        if (null == itemList)
//            return 0;
//        else
//            return this.itemList.size();
        return items.size();
    }
}
