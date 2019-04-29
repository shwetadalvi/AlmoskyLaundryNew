package com.almosky.almoskylaundryApp.activity;

import android.os.Bundle;
import android.view.View;

import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.fragments.SignUporLoginPagerAdapter;
import com.almosky.almoskylaundryApp.common.BaseActivity;
import com.almosky.almoskylaundryApp.fragments.LoginFragment;
import com.almosky.almoskylaundryApp.fragments.SignUpFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;


public class SignupOrLoginActivity extends BaseActivity{

    private static final String TAG = "SignupOrLoginActivity";

    private String phone;
    private TabLayout tabLayout;
    private FragResultInterface listener;
    private FragmentResultInterface signlistener;
    private SignUpFragment mFragment;
    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_or_login_activity);
        tabLayout = findViewById(R.id.tabs);
        ViewPager pager = findViewById(R.id.pager);
        SignUporLoginPagerAdapter adapter = new SignUporLoginPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
    }


    public interface FragResultInterface {
        void fragResultInterface(String response, int requestId);
    }

    public void setListener(FragResultInterface fragResultInterface) {
        this.listener = fragResultInterface;
    }

    public interface FragmentResultInterface {
        void fragmentResultInterface(String response, int requestId);
    }

    public void setSignupListener(FragmentResultInterface fragmentResultInterface) {
        this.signlistener = fragmentResultInterface;
    }

    @Override
    public void getResponse(String response, int requestId) {
        super.getResponse(response, requestId);
        if (requestId == SignUpFragment.SIGNUP) {
            signlistener.fragmentResultInterface(response, requestId);
        }
        if (requestId == LoginFragment.LOGIN) {
            listener.fragResultInterface(response, requestId);
        }


    }


}
