package com.almosky.almoskylaundryApp.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.activity.AddressListActivity;
import com.almosky.almoskylaundryApp.model.Addressdto;
import com.almosky.almoskylaundryApp.viewholder.AddressRecyclerViewHolders;

import java.util.ArrayList;


public class AddressListAdapter extends RecyclerView.Adapter<AddressRecyclerViewHolders> {

    //    private List<UserLogCountResponse.DetailsBean> itemList;
    private Context context;
    private ArrayList<Addressdto.Result> items;
    AddressListActivity _activity;
   // OrderConfirmationActivity _activity;


    public AddressListAdapter(Context context, ArrayList<Addressdto.Result> data,AddressListActivity activity) {
//        this.itemList = itemList;
        this.context = context;
        this.items=data;
        this._activity=activity;
    }

    @Override
    public AddressRecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, null);
        AddressRecyclerViewHolders rcv = new AddressRecyclerViewHolders(layoutView, context,_activity);
        return rcv;
    }

    @Override
    public void onBindViewHolder(AddressRecyclerViewHolders holder, int position) {
//        UserLogCountResponse.DetailsBean status = itemList.get(position);
        Addressdto.Result status = items.get(position);
        holder.bind(status,position);


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
