package com.almosky.almoskylaundryApp.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.activity.neworder.AreaListActivity;
import com.almosky.almoskylaundryApp.viewholder.AreaRecyclerViewHolders;

import java.util.List;

public class AreasListAdapter extends RecyclerView.Adapter<AreaRecyclerViewHolders> {

    //    private List<UserLogCountResponse.DetailsBean> itemList;
    private AreaListActivity activity;
    private List<String> items;
    // OrderConfirmationActivity _activity;


    public AreasListAdapter(AreaListActivity activity, List<String> data) {
//        this.itemList = itemList;
        this.activity = activity;
        this.items=data;
        //   this._activity=activity;
    }

    @Override
    public AreaRecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area, null);
        AreaRecyclerViewHolders rcv = new AreaRecyclerViewHolders(layoutView, activity);
        return rcv;
    }

    @Override
    public void onBindViewHolder(AreaRecyclerViewHolders holder, int position) {
//        UserLogCountResponse.DetailsBean status = itemList.get(position);
        String status = items.get(position);
        holder.bind(status);

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
