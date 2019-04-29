package com.almosky.almoskylaundryApp.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.almosky.almoskylaundryApp.Almosky;
import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.interfaces.ClickListeners;
import com.almosky.almoskylaundryApp.model.day;
import com.almosky.almoskylaundryApp.viewholder.PickUpDateRecyclerViewHolders;

import java.util.ArrayList;
import java.util.List;

public class PickUpDateRecyclerViewAdapter extends RecyclerView.Adapter<PickUpDateRecyclerViewHolders> {

    private final List<String> _dateArray;
    private final ArrayList<String> _dayArray;

    //    private List<UserLogCountResponse.DetailsBean> itemList;
    private Context context;
    private int row_index;
    private String _activity;
    private ClickListeners.ItemClick itemClick;
    List<day> daysArray;
    public PickUpDateRecyclerViewAdapter(Context context, List<String> dateArray, ArrayList<String> days,String activity,ClickListeners.ItemClick itemClick,List<day> daysArray) {
//        this.itemList = itemList;
        this.context = context;
        _dateArray = dateArray;
        this._activity=activity;
        this._dayArray=days;
        this.daysArray = daysArray;
        this.itemClick = itemClick;
    }

    @Override
    public PickUpDateRecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pickup_date_item, null);
        PickUpDateRecyclerViewHolders rcv = new PickUpDateRecyclerViewHolders(layoutView, context);

        return rcv;
    }

    @Override
    public void onBindViewHolder(PickUpDateRecyclerViewHolders holder, final int position) {
//        UserLogCountResponse.DetailsBean status = itemList.get(position);
        LinearLayout rowDate = holder.itemView.findViewById(R.id.rowDate);
        TextView day = holder.itemView.findViewById(R.id.day);
        TextView date = holder.itemView.findViewById(R.id.date);
        //day.setText(_dateArray.get(position));
        day.setText(daysArray.get(position).getTimeDay());
        date.setText(daysArray.get(position).getDate());

        // if(position==0) {

      /*   if (_activity.equals("pickup")) {
                Almosky.getInst().setPickupdate(daysArray.get(0).getDate());
            }if (_activity.equals("delivery")) {
                Almosky.getInst().setDeliverydate(daysArray.get(0).getDate());
         }*/
        // }

        rowDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;

                if(_activity.equals("pickup")){
                    Log.d("Success-", "JSON:1" + "Inside pickup :" + daysArray.get(row_index).getDate());
                    Almosky.getInst().setPickupdate(daysArray.get(row_index).getDate());
                }
                if(_activity.equals("delivery")){
                    Log.d("Success-", "JSON:1" + "Inside delivery :" + daysArray.get(row_index).getDate());
                    Almosky.getInst().setDeliverydate(daysArray.get(row_index).getDate());
                }
                itemClick.onClickedItem(daysArray.get(row_index));
                notifyDataSetChanged();
            }
        });
        if (row_index == position) {
            rowDate.setBackgroundColor(Color.parseColor("#ff3d5afe"));
            day.setTextColor(Color.parseColor("#ffffff"));
            date.setTextColor(Color.parseColor("#ffffff"));
        } else {
            rowDate.setBackgroundColor(Color.parseColor("#ffffff"));
            day.setTextColor(Color.parseColor("#000000"));
            date.setTextColor(Color.parseColor("#000000"));
        }
//        holder.bind();
    }


    @Override
    public int getItemCount() {
//        if (null == itemList)
//            return 0;
//        else
//            return this.itemList.size();
        return _dateArray.size();
    }
}
