package com.almosky.almoskylaundryApp.activity.neworder;

import android.content.Context;
import android.widget.TextView;

import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.model.categoryItemPrice;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.ChildPosition;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;


@Layout(R.layout.item_price)
public class InfoView1 {


    @ParentPosition
    private int mParentPosition;

    @ChildPosition
    private int mChildPosition;

    @View(R.id.title)
    private TextView titleTxt;

    @View(R.id.normal)
    private TextView normal;


    @View(R.id.fast)
    private TextView fast;

    @View(R.id.text_normal_price)
    private TextView text_normal_price;


    @View(R.id.text_fast_price)
    private TextView text_fast_price;

    private categoryItemPrice mInfo;
    private Context mContext;

    public InfoView1(Context context, categoryItemPrice info) {
        mContext = context;
        mInfo = info;
    }


    @Resolve
    private void onResolved() {
        titleTxt.setText(mInfo.getName());
        normal.setText(mInfo.getNormal());
        fast.setText(mInfo.getFast());
        if(mChildPosition==0){
            text_normal_price.setVisibility(android.view.View.VISIBLE);
            text_fast_price.setVisibility(android.view.View.VISIBLE);
        }else{
            text_normal_price.setVisibility(android.view.View.GONE);
            text_fast_price.setVisibility(android.view.View.GONE);
        }

    }
}
