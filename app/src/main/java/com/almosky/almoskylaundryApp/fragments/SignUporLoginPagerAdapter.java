package com.almosky.almoskylaundryApp.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class SignUporLoginPagerAdapter  extends FragmentPagerAdapter {

    public SignUporLoginPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        if (position == 0)
            return SignUpFragment.newInstance(position + 1);
        else
            return LoginFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0)
            return "Signup";
        else
            return "Login";

    }
}
