package com.almosky.almoskylaundryApp.viewholder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.activity.neworder.AreaListActivity;

public class AreaRecyclerViewHolders extends RecyclerView.ViewHolder {

    private View _itemView;
    private TextView textTitle;
    //    UserActionCountItemBinding binding;

    public AreaListActivity _activity;


    public AreaRecyclerViewHolders(View itemView, AreaListActivity activity) {
        super(itemView);
        _activity = activity;
        //  _activty=activity;

        _itemView = itemView;
        textTitle = itemView.findViewById(R.id.title);


//        binding = DataBindingUtil.bind(itemView);
    }


    public void bind(final String item) {
        textTitle.setText(item);
        //   itm=item;
        _itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _activity.setSelectedItem(item);
            }
        });
    }


}
