package com.almosky.almoskylaundryApp.activity.neworder;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.ImageView;
import android.widget.TextView;

import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.model.categorydto;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.ChildPosition;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;


@Layout(R.layout.feed_item)
public class InfoView {


    @ParentPosition
    private int mParentPosition;

    @ChildPosition
    private int mChildPosition;

    @View(R.id.titleTxt)
    private TextView titleTxt;

    @View(R.id.item)
    private ConstraintLayout itemView;

/*    @View(R.id.captionTxt)
    private TextView captionTxt;*/


    @View(R.id.imageView)
    private ImageView imageView;

    private categorydto.Detail.Item mInfo;
    private Context mContext;

    public InfoView(Context context, categorydto.Detail.Item info) {
        mContext = context;
        mInfo = info;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) @Resolve
    private void onResolved() {
        // titleTxt.setText(mInfo.getItemName());
        // captionTxt.setText("100");
        titleTxt.setText(mInfo.getItemName());
        imageView.setImageDrawable(mContext.getDrawable(R.mipmap.logo));
        Glide.with(mContext).load("http://almosky.abrappsworld.com/ItemImages/" + mInfo.getItemImage()).into(imageView);
        //   Glide.with(mContext).load("http://innosyz.com/itemimages/"+mInfo.getItemImage()).into(imageView);
        System.out.println("http://almosky.abrappsworld.com/ItemImages/" + mInfo.getItemImage());


        imageView.setOnClickListener(v -> onCLickedItem());
        titleTxt.setOnClickListener(v -> onCLickedItem());
        itemView.setOnClickListener(v -> onCLickedItem());

    }

    private void onCLickedItem() {

        Intent intent = new Intent(mContext, ItemDetailsAddActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("catId", mInfo.getCategoryId());
        intent.putExtra("catname", mInfo.getCategoryName());
        intent.putExtra("itemId", mInfo.getItemId());
        intent.putExtra("itemname", mInfo.getItemName());
        intent.putExtra("url", "http://almosky.abrappsworld.com/ItemImages/" + mInfo.getItemImage());
        mContext.startActivity(intent);
    }
}
