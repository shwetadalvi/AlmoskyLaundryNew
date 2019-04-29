package com.almosky.almoskylaundryApp.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.databinding.FragmentOtpBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class OTPFragment extends Fragment {
    private static androidx.fragment.app.Fragment fragment;
    private FirebaseAuth mAuth;
    private String phone;
    private FragmentOtpBinding mBinding;

    public OTPFragment() {
        // Required empty public constructor
    }

    public static OTPFragment newInstance(String phone, Fragment signUpfragment) {
        OTPFragment.fragment = signUpfragment;
        Bundle args = new Bundle();
        args.putString("phone", phone);
        OTPFragment fragment = new OTPFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private static final String TAG = "OTPFragment";
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_otp, container, false);
        mAuth = FirebaseAuth.getInstance();
        if (getArguments() != null) {
            phone = getArguments().getString("phone");
        }

        return mBinding.getRoot();
    }



}