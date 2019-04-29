package com.almosky.almoskylaundryApp.fragments;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.adapter.TabsPagerAdapter;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragments extends Fragment {


    private TabLayout tabLayout;
    private SliderLayout sliderLayout;
    ViewPager pager;
  //  TabsPagerAdapter adapter;

    FragmentPagerAdapter adapter;

    public HomeFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_fragments, container, false);
        tabLayout = view.findViewById(R.id.tabs);
         pager = view. findViewById(R.id.pager);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // adapter = new TabsPagerAdapter(getActivity().getSupportFragmentManager());
        adapter = new TabsPagerAdapter(getChildFragmentManager());

        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);

        sliderLayout = view.findViewById(R.id.imageBackdrop);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setScrollTimeInSec(1); //set scroll delay in seconds :

        setSliderViews();



    }

    private void setSliderViews() {

        for (int i = 0; i <=1; i++) {

            SliderView sliderView = new SliderView(getActivity());

            switch (i) {
                case 0:
                    sliderView.setImageDrawable(R.drawable.slider2);
                    break;
                case 1:
                    sliderView.setImageDrawable(R.drawable.slider2); break;
                case 2:
                    sliderView.setImageDrawable(R.mipmap.ic_home_banner); break;

            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
           // sliderView.setDescription("setDescription " + (i + 1));
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                   // Toast.makeText(MainActivity.this, "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();
                }
            });

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }

}
