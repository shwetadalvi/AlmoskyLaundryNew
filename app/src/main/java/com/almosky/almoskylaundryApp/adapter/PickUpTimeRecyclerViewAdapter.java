package com.almosky.almoskylaundryApp.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.almosky.almoskylaundryApp.Almosky;
import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.interfaces.ClickListeners;
import com.almosky.almoskylaundryApp.model.time;
import com.almosky.almoskylaundryApp.viewholder.PickUpTimeRecyclerViewHolders;

import java.util.List;

public class PickUpTimeRecyclerViewAdapter extends RecyclerView.Adapter<PickUpTimeRecyclerViewHolders> {

    private List<String> _timeArray;
    //    private List<UserLogCountResponse.DetailsBean> itemList;
    private Context context;
    private int row_index;
    private String _activity;
    private ClickListeners.ItemClick itemClick;
    List<time> timeArray;

    public PickUpTimeRecyclerViewAdapter(Context context, List<String> _timeArray,String activity,ClickListeners.ItemClick itemClick,List<time> timeArray) {
//        this.itemList = itemList;
        this.context = context;
        this._timeArray = _timeArray;
        _activity=activity;
        this.timeArray = timeArray;
        this.itemClick = itemClick;
    }

    @Override
    public PickUpTimeRecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pickup_time_item, null);
        PickUpTimeRecyclerViewHolders rcv = new PickUpTimeRecyclerViewHolders(layoutView, context);
        return rcv;
    }

    @Override
    public void onBindViewHolder(PickUpTimeRecyclerViewHolders holder, final int position) {
//        UserLogCountResponse.DetailsBean status = itemList.get(position);
//        holder.bind(status);
        ConstraintLayout rowTime = holder.itemView.findViewById(R.id.rowTime);
        TextView time = holder.itemView.findViewById(R.id.time);
        time.setText(timeArray.get(position).getFromTime()+" - "+timeArray.get(position).getToTime());
        //  if(position==0) {

           /* if (_activity.equals("pickup")) {
                Almosky.getInst().setPickuptime(timeArray.get(0).getFromTime()+" - "+timeArray.get(position).getToTime());
           } if (_activity.equals("delivery")) {
                Almosky.getInst().setDeliverytime(timeArray.get(0).getFromTime()+" - "+timeArray.get(position).getToTime());
           }*/
        //   }
        rowTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;

                if(_activity.equals("pickup")){

                    Almosky.getInst().setPickuptime(timeArray.get(position).getFromTime()+" - "+timeArray.get(position).getToTime());
                    Log.d("Success-", "JSON:1" + "Inside pickup time:" + Almosky.getInst().getPickuptime());
                }
                if(_activity.equals("delivery")){
                    Log.d("Success-", "JSON:1" + "Inside pickup delivery:" + timeArray.get(position).getFromTime()+" - "+timeArray.get(position).getToTime());
                    Almosky.getInst().setDeliverytime(timeArray.get(position).getFromTime()+" - "+timeArray.get(position).getToTime());
                }

                notifyDataSetChanged();
            }
        });
        if (row_index == position) {
            rowTime.setBackgroundColor(Color.parseColor("#ff3d5afe"));
            time.setTextColor(Color.parseColor("#ffffff"));
        } else {
            rowTime.setBackgroundColor(Color.parseColor("#ffffff"));
            time.setTextColor(Color.parseColor("#000000"));
        }
    }


    @Override
    public int getItemCount() {
//        if (null == itemList)
//            return 0;
//        else
//            return this.itemList.size();
        return timeArray.size();
    }
}
