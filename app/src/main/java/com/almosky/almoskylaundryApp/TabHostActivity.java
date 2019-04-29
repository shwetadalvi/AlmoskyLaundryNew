package com.almosky.almoskylaundryApp;


import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatDelegate;


import com.almosky.almoskylaundryApp.activity.DeliveryActivity;
import com.almosky.almoskylaundryApp.common.BaseActivity;
import com.almosky.almoskylaundryApp.databinding.ActivityTabHostBinding;
import com.almosky.almoskylaundryApp.fragments.HomeFragments;
import com.almosky.almoskylaundryApp.fragments.OrdersListFragments;
import com.almosky.almoskylaundryApp.fragments.ProfileFragments;
import com.almosky.almoskylaundryApp.model.CheckDiscount;
import com.almosky.almoskylaundryApp.model.Discount;
import com.almosky.almoskylaundryApp.utils.AppPrefes;
import com.almosky.almoskylaundryApp.utils.Utility;
import com.almosky.almoskylaundryApp.utils.api.ApiCalls;
import com.almosky.almoskylaundryApp.utils.constants.ApiConstants;
import com.almosky.almoskylaundryApp.utils.constants.Constants;
import com.almosky.almoskylaundryApp.utils.constants.PrefConstants;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.RequestParams;


import java.util.ArrayList;

public class TabHostActivity extends BaseActivity {
    private AppPrefes appPrefes;
    private ApiCalls apiCalls;
    private SimpleArcDialog dialog;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    ActivityTabHostBinding binding;
    private Fragment fragment;
    private FragmentResultInterface listener;
    private FragmentOrderResultInterface orderlistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tab_host);
        appPrefes = new AppPrefes(this);
        apiCalls = new ApiCalls();
        dialog = new SimpleArcDialog(this);
        init();
        Utility.clearTempData();
        checkForDiscount();
    }

    private void checkForDiscount() {
        RequestParams params = new RequestParams();

        params.put(Constants.email, appPrefes.getData(PrefConstants.email));

        String url = ApiConstants.checkDiscount;


        apiCalls.callApiPost(TabHostActivity.this, params, dialog, url, 5);
    }
    private void init() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        }
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Home", R.mipmap.home);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Orders", R.mipmap.orders);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Profile", R.mipmap.profile);

        binding.bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);

        binding.bottomNavigation.addItems(bottomNavigationItems);

        binding.bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        binding.bottomNavigation.setTranslucentNavigationEnabled(true);
        binding.bottomNavigation.setBackgroundResource(R.drawable.tab_item_selector);
        binding.bottomNavigation.setBackground(getResources().getDrawable(R.drawable.tab_item_selector));
        binding.bottomNavigation.setAccentColor(getResources().getColor(R.color.colorPrimary));
        fragment = new HomeFragments();
        replaceFragment(R.id.container, fragment);
        listeners();
    }

    private void listeners() {
        binding.bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 0:
                        fragment = new HomeFragments();
                        break;
                    case 1:
                        fragment = new OrdersListFragments();
                        break;
                    case 2:
                        fragment = new ProfileFragments();
                        break;

                }
                replaceFragment(R.id.container, fragment);
                return true;
            }
        });
    }

    public void replaceFragment(int fragment_container, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(fragment_container, fragment);
        transaction.commitAllowingStateLoss();

    }

    public interface FragmentResultInterface{
        void fragmentResultInterface(String response, int requestId);
    }
    public void setListener(FragmentResultInterface fragmentResultInterface){
        this.listener = fragmentResultInterface;
    }

    public interface FragmentOrderResultInterface{
        void fragmentorderResultInterface(String response, int requestId);
    }
    public void setOrderListener(FragmentOrderResultInterface fragmentorderResultInterface){
        this.orderlistener = fragmentorderResultInterface;
    }


    @Override
    public void getResponse(String response, int requestId) {

        if(requestId==1){
            orderlistener.fragmentorderResultInterface(response,requestId);
        }
        if(requestId==9){
            listener.fragmentResultInterface(response,requestId);
        }
        if(requestId==10){
            listener.fragmentResultInterface(response,requestId);
        }
        if (requestId == 5) {
            Log.d("Success-", "JSON:" + "Inside Discount"+response);
            try {

                final CheckDiscount discount;
                Gson gson = new Gson();
                discount = gson.fromJson(response, CheckDiscount.class);

                if(discount.getDiscount().equalsIgnoreCase("yes")){
                    final Dialog dialog = new Dialog(TabHostActivity.this);
                    dialog.setContentView(R.layout.dialog_discount);
                    // dialog.setTitle("Title...");


                    TextView textName = (TextView) dialog.findViewById(R.id.textOffer);
                    textName.setText(discount.getDiscountText());
                    ImageView imageView = dialog.findViewById(R.id.image);
                    Glide.with(TabHostActivity.this).load(discount.getDiscountImageURL()).into(imageView);

                  /*  Button dialogButtonEdit = (Button) dialog.findViewById(R.id.buttonEdit);
                    Button dialogButtonDelete = (Button) dialog.findViewById(R.id.buttonDelete);
                    // if button is clicked, close the custom dialog
                    dialogButtonEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();

                        }
                    });
                   */
                    dialog.show();

                }


            } catch (Exception e) {

            }


        }
       // listener.fragmentResultInterface(response,requestId);



    }
}
