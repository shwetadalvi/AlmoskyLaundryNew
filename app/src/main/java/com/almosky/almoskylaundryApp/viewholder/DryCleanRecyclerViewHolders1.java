package com.almosky.almoskylaundryApp.viewholder;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.activity.OrderDetailsActivity;
import com.almosky.almoskylaundryApp.model.data1;

public class DryCleanRecyclerViewHolders1 extends RecyclerView.ViewHolder {

//    UserActionCountItemBinding binding;
    public TextView dryitem,drycount,dryamount;
    data1.Result itm;
    public OrderDetailsActivity _activty;
    public final Context ctx;

    public DryCleanRecyclerViewHolders1(View itemView, Context context, OrderDetailsActivity activity) {
        super(itemView);
        ctx = context;
        _activty=activity;


        dryitem=itemView.findViewById(R.id.tv_dryitem);
        dryamount=itemView.findViewById(R.id.tv_damount);
        ImageView minus = itemView.findViewById(R.id.minus);
        ImageView plus = itemView.findViewById(R.id.plus);
        drycount = itemView.findViewById(R.id.count);




//        binding = DataBindingUtil.bind(itemView);
    }
/*
    private void updateData(data.Detail.Item itm,int count) {

        ArrayList<data.Detail.Item> drycleanList= Almosky.getInst().getDrycleanList();


        for(int i=0;i<drycleanList.size();i++) {
            if (drycleanList.get(i).getItemId().equals(itm.getItemId())) {
                Almosky.getInst().getDrycleanList().get(i).setItemcount(count);
                Almosky.getInst().getDrycleanList().get(i).setTotal(String.valueOf(Double.parseDouble(Almosky.getInst().getDrycleanList().get(i).getAmount())*count));
                dryamount.setText(Almosky.getInst().getDrycleanList().get(i).getTotal());
                _activty.updateTotal();

            }
        }

    }*/

    public void bind(data1.Result item) {
         itm=item;
    }


}
