package com.almosky.almoskylaundryApp;


import androidx.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatDelegate;


import com.almosky.almoskylaundryApp.common.BaseActivity;
import com.almosky.almoskylaundryApp.databinding.ActivityTabHostBinding;
import com.almosky.almoskylaundryApp.fragments.HomeFragments;
import com.almosky.almoskylaundryApp.fragments.OrdersListFragments;
import com.almosky.almoskylaundryApp.fragments.ProfileFragments;
import com.almosky.almoskylaundryApp.utils.Utility;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;


import java.util.ArrayList;

public class TabHostActivity extends BaseActivity {

    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    ActivityTabHostBinding binding;
    private Fragment fragment;
    private FragmentResultInterface listener;
    private FragmentOrderResultInterface orderlistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tab_host);
        init();
        Utility.clearTempData();
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

       // listener.fragmentResultInterface(response,requestId);



    }
}
