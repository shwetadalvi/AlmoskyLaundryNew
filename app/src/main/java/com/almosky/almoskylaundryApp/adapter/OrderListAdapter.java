package com.almosky.almoskylaundryApp.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.TabHostActivity;
import com.almosky.almoskylaundryApp.model.OrderListdto;
import com.almosky.almoskylaundryApp.viewholder.OrderRecyclerViewHolders;

import java.util.ArrayList;


public class OrderListAdapter extends RecyclerView.Adapter<OrderRecyclerViewHolders> {

    //    private List<UserLogCountResponse.DetailsBean> itemList;
    private Context context;
    private ArrayList<OrderListdto.Result> items;
   // OrderConfirmationActivity _activity;

    TabHostActivity _activity;


    public OrderListAdapter(TabHostActivity context, ArrayList<OrderListdto.Result> data) {
//        this.itemList = itemList;
        this.context = context;
        this.items=data;
       this._activity=context;
    }

    @Override
    public OrderRecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, null);
        OrderRecyclerViewHolders rcv = new OrderRecyclerViewHolders(layoutView, _activity);
        return rcv;
    }

    @Override
    public void onBindViewHolder(OrderRecyclerViewHolders holder, int position) {
//        UserLogCountResponse.DetailsBean status = itemList.get(position);
        OrderListdto.Result status = items.get(position);
        holder.bind(status);

     //   data.Detail.Item item=items.get(position);
     //   holder.bind(item);



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
