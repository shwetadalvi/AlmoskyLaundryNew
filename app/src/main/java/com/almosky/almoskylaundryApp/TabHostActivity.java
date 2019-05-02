package com.almosky.almoskylaundryApp;


import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
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


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
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
    String currentVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tab_host);
        appPrefes = new AppPrefes(this);
        apiCalls = new ApiCalls();
        dialog = new SimpleArcDialog(this);
        init();
        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        new GetVersionCode().execute();
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
    class GetVersionCode extends AsyncTask<Void, String, String> {

        @Override

        protected String doInBackground(Void... voids) {

            String newVersion = null;

            try {
                Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + TabHostActivity.this.getPackageName()  + "&hl=en")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get();
                if (document != null) {
                    Elements element = document.getElementsContainingOwnText("Current Version");
                    for (Element ele : element) {
                        if (ele.siblingElements() != null) {
                            Elements sibElemets = ele.siblingElements();
                            for (Element sibElemet : sibElemets) {
                                newVersion = sibElemet.text();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newVersion;

        }


        @Override

        protected void onPostExecute(String onlineVersion) {

            super.onPostExecute(onlineVersion);

            if (onlineVersion != null && !onlineVersion.isEmpty()) {

                if (Float.valueOf(currentVersion) < Float.valueOf(onlineVersion)) {
                    showUpdateDialog();
                }

            }

            Log.d("update", "Current version " + currentVersion+ "playstore version " + onlineVersion);

        }
    }
    public void showUpdateDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TabHostActivity.this);

        alertDialogBuilder.setTitle(TabHostActivity.this.getString(R.string.app_name));
        alertDialogBuilder.setMessage(TabHostActivity.this.getString(R.string.update_message));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(R.string.update_now, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                dialog.cancel();
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                    finish();

                dialog.dismiss();
            }
        });
        alertDialogBuilder.show();
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
                    TextView textDiscount = (TextView) dialog.findViewById(R.id.textDiscount);
                    TextView textDate = (TextView) dialog.findViewById(R.id.textDate);
                    textDiscount.setText(String.valueOf(discount.getDiscAmt()+" %"));
                    textName.setText(discount.getDiscountText());
                   textDate.setText("Offer valid from "+discount.getExpFrom()+" To "+discount.getExpTo());

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
