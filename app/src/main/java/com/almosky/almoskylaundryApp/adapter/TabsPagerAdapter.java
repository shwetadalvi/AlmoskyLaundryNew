package com.almosky.almoskylaundryApp.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.almosky.almoskylaundryApp.fragments.MoreInfoFragment;
import com.almosky.almoskylaundryApp.fragments.OrdersFragments;
import com.almosky.almoskylaundryApp.fragments.PriceListFragment;


public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        if (position == 0)
            return OrdersFragments.newInstance(position + 1);
        else if (position == 1)
            return PriceListFragment.newInstance(position + 1);
        else
            return MoreInfoFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0)
            return "Order";
        else if (position == 1)
            return "Price List";
        else
            return "More Info";

    }
}
