package com.almosky.almoskylaundryApp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.TabHostActivity;
import com.almosky.almoskylaundryApp.activity.ForgotPasswordActivity;
import com.almosky.almoskylaundryApp.activity.SignupOrLoginActivity;
import com.almosky.almoskylaundryApp.model.Logindto;
import com.almosky.almoskylaundryApp.utils.AppPrefes;
import com.almosky.almoskylaundryApp.utils.api.ApiCalls;
import com.almosky.almoskylaundryApp.utils.constants.ApiConstants;
import com.almosky.almoskylaundryApp.utils.constants.PrefConstants;
import com.google.gson.Gson;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.RequestParams;

import androidx.fragment.app.Fragment;
import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements SignupOrLoginActivity.FragResultInterface {


    private static final String ARG_PAGE_NUMBER = "page_number";
    public static final int LOGIN = 2;
    private SignupOrLoginActivity SignUpActivity;
    private ApiCalls apiCalls;
    EditText email, password;


    SimpleArcDialog dialog;
    private AppPrefes appPrefes;
    private Button reset;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(int page) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        SignUpActivity = (SignupOrLoginActivity) getActivity();
        SignUpActivity.setListener(this);
        apiCalls = new ApiCalls();
        appPrefes = new AppPrefes(SignUpActivity);

        email = (EditText) view.findViewById(R.id.emailEditText);
        password = (EditText) view.findViewById(R.id.passwordEditText);
        reset = (Button) view.findViewById(R.id.btnRest);

        Button login = view.findViewById(R.id.btnLogin);
        login.setOnClickListener(v -> onLogin(v));

        reset.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ForgotPasswordActivity.class));
        });


        return view;
    }

    public void onLogin(View view) {
        String token = appPrefes.getData(PrefConstants.token);
        RequestParams params = new RequestParams();
        params.put(ApiConstants.email, email.getText().toString());
        params.put(ApiConstants.password, password.getText().toString());
        params.put(ApiConstants.registrationToken, token);


        String url = ApiConstants.loginUrl;
        apiCalls.callApiPost(SignUpActivity, params, SignUpActivity.mDialog, url, 2);
    }

    @Override
    public void fragResultInterface(String response, int requestId) {
        try {
            final Logindto userData;
            Gson gson = new Gson();
            userData = gson.fromJson(response, Logindto.class);
            try {
                if (userData.getResult() != null) {
                    appPrefes.saveData(PrefConstants.email, userData.getResult().get(0).getEmail());
                    appPrefes.saveData(PrefConstants.name, userData.getResult().get(0).getName());
                    appPrefes.saveData(PrefConstants.mobile, userData.getResult().get(0).getMobile());
                    appPrefes.saveBoolData(PrefConstants.isLogin, true);
                    Intent i = new Intent(getActivity(), TabHostActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);

                } else {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Login Error")
                            .show();
                }
            } catch (Exception e) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Failed")
                        .setContentText("Login Error")
                        .show();
                e.printStackTrace();
            }
        } catch (Exception e) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Failed")
                    .setContentText("Login Error")
                    .show();
            e.printStackTrace();
        }
    }
}
