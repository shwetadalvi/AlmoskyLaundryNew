package com.almosky.almoskylaundryApp.viewholder;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.almosky.almoskylaundryApp.Almosky;
import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.activity.OrderConfirmationActivity;
import com.almosky.almoskylaundryApp.model.data;

import java.util.ArrayList;

public class DryCleanRecyclerViewHolders  extends RecyclerView.ViewHolder {

//    UserActionCountItemBinding binding;
    public TextView dryitem,drycount,dryamount;
    data.Detail.Item itm;
    public OrderConfirmationActivity _activty;
    public final Context ctx;




    public DryCleanRecyclerViewHolders(View itemView, Context context,OrderConfirmationActivity activity) {
        super(itemView);
        ctx = context;
        _activty=activity;


        dryitem=itemView.findViewById(R.id.tv_dryitem);
        dryamount=itemView.findViewById(R.id.tv_damount);
        ImageView minus = itemView.findViewById(R.id.minus);
        ImageView plus = itemView.findViewById(R.id.plus);
        drycount = itemView.findViewById(R.id.count);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int countValue = Integer.parseInt(drycount.getText().toString());
                if (countValue > 0) {
                    countValue = countValue - 1;
                    drycount.setText("" + countValue);

                    itm.setItemcount(countValue);
                    updateData(itm,countValue);
                }
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int countValue = Integer.parseInt(drycount.getText().toString());
                countValue = countValue + 1;
                drycount.setText("" + countValue);
                itm.setItemcount(countValue);
                updateData(itm,countValue);
            }
        });


//        binding = DataBindingUtil.bind(itemView);
    }

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

    }

    public void bind(data.Detail.Item item) {
         itm=item;
    }


}
