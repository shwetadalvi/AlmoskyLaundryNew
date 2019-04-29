package com.almosky.almoskylaundryApp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.activity.SignupOrLoginActivity;
import com.almosky.almoskylaundryApp.activity.neworder.OrdertypeActivity;
import com.almosky.almoskylaundryApp.helper.ProgressLayout;
import com.almosky.almoskylaundryApp.utils.AppPrefes;
import com.almosky.almoskylaundryApp.utils.api.ApiCalls;
import com.almosky.almoskylaundryApp.utils.constants.ApiConstants;
import com.almosky.almoskylaundryApp.utils.constants.PrefConstants;
import com.google.firebase.iid.FirebaseInstanceId;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment implements SignupOrLoginActivity.FragmentResultInterface {

    private static final String ARG_PAGE_NUMBER = "page_number";
    public static final int SIGNUP = 1;
    private SignupOrLoginActivity SignUpActivity;
    private ApiCalls apiCalls;
    EditText fname, email, password, lname, phone, phoneExt;
    static boolean isVerified;
    ConstraintLayout otpContainer;
    private ProgressLayout mProgressLayout;
    private EditText confirmPassword;

    private AppPrefes appPrefes;
    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance(int page) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, page);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        SignUpActivity = (SignupOrLoginActivity) getActivity();
        SignUpActivity.setSignupListener(this);
        apiCalls = new ApiCalls();

        fname = view.findViewById(R.id.edt_first_name);
        lname = view.findViewById(R.id.edt_last_name);
        email = view.findViewById(R.id.edt_email);
        password = view.findViewById(R.id.edt_password);
        phone = view.findViewById(R.id.edt_phone);
        phoneExt = view.findViewById(R.id.edtPhoneExt);
        otpContainer = view.findViewById(R.id.otpContainer);
        mProgressLayout = view.findViewById(R.id.progress_layout);
        confirmPassword = view.findViewById(R.id.edt_confirm_password);

        Button register = view.findViewById(R.id.btn_create_account);
        appPrefes = new AppPrefes(SignUpActivity);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                   /* otpContainer.setVisibility(View.VISIBLE);
                    String phoneNUmber = phoneExt.getText().toString() + phoneExt.getText().toString();
                    SignupOrLoginActivity signupOrLoginActivity = (SignupOrLoginActivity) getActivity();
                    signupOrLoginActivity.sendOTP(phoneNUmber, SignUpFragment.this, view);*/
                    onRegister(view);
                }
            }
        });

        if (isVerified) {
            onRegister(view);
        }
        return view;
    }

    private boolean validateFields() {

        if (fname.getText().toString().equals("") || fname.getText().toString().equals(null)) {
            fname.requestFocus();

            Toast.makeText(getContext(), "Name cannot be blank", Toast.LENGTH_LONG).show();

            return false;

        }
        if (email.getText().toString().equals("") || email.getText().toString().equals(null)) {
            email.requestFocus();
            Toast.makeText(getContext(), "Email cannot be blank", Toast.LENGTH_LONG).show();

            return false;

        }
        if (phone.getText().toString().equals("") || phone.getText().toString().equals(null)) {
            phone.requestFocus();
            Toast.makeText(getContext(), "Mobile number cannot be blank", Toast.LENGTH_LONG).show();

            return false;
        }
        if (phoneExt.getText().toString().equals("") || phoneExt.getText().toString().equals(null)) {
            phoneExt.requestFocus();
            Toast.makeText(getContext(), "Phone code cannot be blank", Toast.LENGTH_LONG).show();

            return false;
        }
        if (password.getText().toString().equals("") || password.getText().toString().equals(null)) {
            password.requestFocus();
            Toast.makeText(getContext(), "password cannot be blank", Toast.LENGTH_LONG).show();

            return false;
        }
        if (confirmPassword.getText().toString().equals("") || confirmPassword.getText().toString().equals(null)) {
            confirmPassword.requestFocus();
            Toast.makeText(getContext(), "Name cannot be blank", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!confirmPassword.getText().toString().equals("") || !confirmPassword.getText().toString().equals(null) && !password.getText().toString().equals("") || !password.getText().toString().equals(null)) {
            if (!confirmPassword.getText().toString().equals(password.getText().toString())) {
                confirmPassword.requestFocus();
                Toast.makeText(getContext(), "Password does not match", Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }
        return true;
    }

    public void onRegister(View view) {

        RequestParams params = new RequestParams();

        // params.put("LoginEmail", "admin@gmail.com");
        params.put(ApiConstants.firstname, fname.getText().toString());
        params.put(ApiConstants.email, email.getText().toString());
        params.put(ApiConstants.password, password.getText().toString());
        params.put(ApiConstants.lastname, lname.getText().toString());
        params.put(ApiConstants.phone, phone.getText().toString());
        params.put(ApiConstants.registrationToken, FirebaseInstanceId.getInstance().getToken());

       /* Almosky.getInst().setRequestParams(params);
        String phoneNumber = phoneExt.getText().toString()+phone.getText().toString();
        Intent intent = new Intent(getContext(), ForgotPasswordActivity.class);
        intent.putExtra("otp", true);
        intent.putExtra("phone", phoneNumber);
        startActivityForResult(intent, 123);*/
        String url = ApiConstants.signUpUrl;
        apiCalls.callApiPost(SignUpActivity, params, SignUpActivity.mDialog, url, 1);
    }

    @Override
    public void fragmentResultInterface(String response, int requestId) {
        try {
            JSONObject objectResponse = new JSONObject(response);
            if (objectResponse.getString("status").equals("true")) {

                new SweetAlertDialog(SignUpActivity, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Success")
                        .setContentText(objectResponse.getString("Message"))
                        .setConfirmText("Ok")
                        .setConfirmClickListener(sDialog -> {
                            sDialog.dismissWithAnimation();
                            appPrefes.saveData(PrefConstants.email, email.getText().toString());
                            appPrefes.saveData(PrefConstants.name, fname.getText().toString());
                            appPrefes.saveData(PrefConstants.mobile, phone.getText().toString());
                            // appPrefes.saveIntData(PrefConstants.uid,userData.getProfile().get(0).getID());


                            appPrefes.saveBoolData(PrefConstants.isLogin, true);
                            startActivity(new Intent(SignUpActivity, OrdertypeActivity.class));
                            getActivity().finish();
                        })
                        .show();


            } else {

                new SweetAlertDialog(SignUpActivity, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Failed")
                        .setContentText(objectResponse.getString("Message"))
                        .show();

            }


        } catch (Exception e) {

        }
    }

    @Override public void onResume() {
        super.onResume();
        otpContainer.setVisibility(View.GONE);
    }
}
