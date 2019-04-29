package com.almosky.almoskylaundryApp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.almosky.almoskylaundryApp.Almosky;
import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.TabHostActivity;
import com.almosky.almoskylaundryApp.activity.AboutUsActivity;
import com.almosky.almoskylaundryApp.activity.AddressListActivity;
import com.almosky.almoskylaundryApp.activity.ContactUsActivity;
import com.almosky.almoskylaundryApp.activity.SignupOrLoginActivity;
import com.almosky.almoskylaundryApp.activity.TermsActivity;
import com.almosky.almoskylaundryApp.activity.UpdateProfileActivity;
import com.almosky.almoskylaundryApp.utils.AppPrefes;
import com.almosky.almoskylaundryApp.utils.constants.PrefConstants;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;



public class ProfileFragments extends Fragment {

    ImageView profile,icedit;
    TextView name,loginText,logout;
    AppPrefes appPrefes;
    RelativeLayout logoutlyt,contactLyt,terms,about;


    public ProfileFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_fragments, container, false);
        ConstraintLayout profileHeader = view.findViewById(R.id.profileHeader);
        RelativeLayout lyt_address = view.findViewById(R.id.lyt_address);
        profileHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!appPrefes.getBoolData(PrefConstants.isLogin)){
                    Intent intent = new Intent(getActivity(), SignupOrLoginActivity.class);
                    startActivity(intent);
                }

            }
        });
        lyt_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Almosky.getInst().setAddressType("0");
                Intent intent = new Intent(getActivity(), AddressListActivity.class);
                startActivity(intent);
            }
        });

        appPrefes =new AppPrefes(getActivity());

        name=view.findViewById(R.id.name);
        loginText=view.findViewById(R.id.textLogin);
        profile=view.findViewById(R.id.profileimage);
        logoutlyt=view.findViewById(R.id.lyt_logout);
        contactLyt=view.findViewById(R.id.lyt_contactus);
        about=view.findViewById(R.id.lyt_aboutus);
        terms=view.findViewById(R.id.lyt_terms);

        icedit=view.findViewById(R.id.btnEdit);

        icedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go=new Intent(getActivity(), UpdateProfileActivity.class);
                startActivity(go);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), AboutUsActivity.class);
                // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getActivity(), TermsActivity.class);
                // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);


              /*  Intent i=new Intent(getActivity(), PaymentActivity.class);
                // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);*/


            }
        });
        contactLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), ContactUsActivity.class);
               // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

            }
        });
        icedit=view.findViewById(R.id.btnEdit);

        if(appPrefes.getBoolData(PrefConstants.isLogin)){

            loginText.setVisibility(View.INVISIBLE);
            name.setText(appPrefes.getData(PrefConstants.name));

        }else {
            name.setVisibility(View.GONE);
            profile.setVisibility(View.VISIBLE);
            loginText.setVisibility(View.VISIBLE);
            icedit.setVisibility(View.GONE);
            lyt_address.setVisibility(View.GONE);
            logoutlyt.setVisibility(View.GONE);
        }

        logoutlyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                appPrefes.saveData(PrefConstants.email,"");
                appPrefes.saveData(PrefConstants.name, "");
                appPrefes.saveBoolData(PrefConstants.isLogin, false);

                Intent i=new Intent(getActivity(), TabHostActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);


            }
        });

        return view;
    }

}
