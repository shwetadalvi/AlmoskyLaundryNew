package com.almosky.almoskylaundryApp.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.almosky.almoskylaundryApp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoreInfoFragment extends Fragment {

    private static final String ARG_PAGE_NUMBER = "page_number";

    public MoreInfoFragment() {
        // Required empty public constructor
    }

    public static MoreInfoFragment newInstance(int page) {
        MoreInfoFragment fragment = new MoreInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, page);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more_info, container, false);
        com.hedgehog.ratingbar.RatingBar ratingBar1 = view.findViewById(R.id.rating1);
        ratingBar1.setStarHalfDrawable(getResources().getDrawable(R.drawable.ic_star_half));
        ratingBar1.setStar(2.5f);
        ratingBar1.halfStar(true);
        com.hedgehog.ratingbar.RatingBar ratingBar2 = view.findViewById(R.id.rating2);
        ratingBar2.setStar(3f);
        ratingBar2.halfStar(true);
        com.hedgehog.ratingbar.RatingBar ratingBar3 = view.findViewById(R.id.rating3);
        ratingBar3.setStar(4f);
        ratingBar3.halfStar(true);
        return view;
    }

}
