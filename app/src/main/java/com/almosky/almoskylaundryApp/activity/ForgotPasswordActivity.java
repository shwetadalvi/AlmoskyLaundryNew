package com.almosky.almoskylaundryApp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.common.BaseActivity;
import com.almosky.almoskylaundryApp.databinding.ActivityForgotPasswordBinding;
import com.almosky.almoskylaundryApp.utils.api.ApiCalls;
import com.almosky.almoskylaundryApp.utils.constants.ApiConstants;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import androidx.databinding.DataBindingUtil;


public class ForgotPasswordActivity extends BaseActivity {

    private ActivityForgotPasswordBinding mBinding;
    ApiCalls mApiCalls;

    private SimpleArcDialog dialog;
    private boolean isOTP;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;
    private static final String TAG = "ForgotPasswordActivity";
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
        mApiCalls = new ApiCalls();
        dialog = new SimpleArcDialog(this);
        mBinding.fgtBtnSendVerification.setOnClickListener(v -> onClickedBtnSendVerification());
        mBinding.fgtBtnVerifyCode.setOnClickListener(v -> onClickedBtnStartVerification());
        mBinding.changePassword.setOnClickListener(v -> onClickedBtnChangePassword());

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            //OTP VERIFICATION
            isOTP = intent.getExtras().getBoolean("otp");
            phone = intent.getExtras().getString("phone");
            if (isOTP) {
                mBinding.codeContainer.setVisibility(View.VISIBLE);
                mBinding.emailContainer.setVisibility(View.GONE);
                mBinding.passwordContainer.setVisibility(View.GONE);
                sendOTP(phone);
            }
        }
    }

    private void onClickedBtnStartVerification() {
        if (!isOTP) {
            String code = mBinding.fgtCode.getText().toString();
            if (code.isEmpty()) {
                mBinding.fgtCodeCont.setErrorEnabled(true);
                mBinding.fgtCode.setError(getString(R.string.code_cannot_be_empty));
                return;
            }
            RequestParams params = new RequestParams();
            String url = ApiConstants.forgotPassCodeVerification;
            params.put(ApiConstants.code, code);
            params.put(ApiConstants.email, mBinding.fgtEmail.getText().toString());
            mApiCalls.callApiPost(this, params, dialog, url, 12);
        }else {
            sendOTP(phone);
        }
    }

    public void sendOTP(String phone){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            Log.d(TAG, "onVerificationCompleted:" + credential);
            signInWithPhoneAuthCredential(credential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.w(TAG, "onVerificationFailed", e);
            if (e instanceof FirebaseAuthInvalidCredentialsException) {

            } else if (e instanceof FirebaseTooManyRequestsException) {
            }
        }

        @Override
        public void onCodeSent(String verificationId,
                               PhoneAuthProvider.ForceResendingToken token) {
            Log.d(TAG, "onCodeSent:" + verificationId);
            mVerificationId = verificationId;
            mResendToken = token;
        }
    };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = task.getResult().getUser();

                    } else {
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        }
                    }
                });
    }

    private void onClickedBtnChangePassword() {
        String pass1 = mBinding.pass1.getText().toString();
        String pass2 = mBinding.pass2.getText().toString();
        if (pass1.isEmpty() || pass2.isEmpty()) {
            Toast.makeText(this, getString(R.string.fields_cannot_be_empty), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pass1.equals(pass2)) {
            Toast.makeText(this, getString(R.string.password_mismatch), Toast.LENGTH_SHORT).show();
            return;
        }
        RequestParams params = new RequestParams();
        String url = ApiConstants.forgotPasswordUpdation;
        params.put(ApiConstants.password, pass1);
        params.put(ApiConstants.code, mBinding.fgtCode.getText().toString());
        params.put(ApiConstants.email, mBinding.fgtEmail.getText().toString());
        mApiCalls.callApiPost(this, params, dialog, url, 13);
    }

    private void onClickedBtnSendVerification() {
        String email = mBinding.fgtEmail.getText().toString();
        if (email.isEmpty()) {
            mBinding.fgtEmailCont.setErrorEnabled(true);
            mBinding.fgtEmailCont.setError(getString(R.string.email_cannot_be_empty));
            return;
        }
        RequestParams params = new RequestParams();
        String url = ApiConstants.forgotPassEmailVerification;
        params.put(ApiConstants.email, email);
        mApiCalls.callApiPost(this, params, dialog, url, 11);
    }

    @Override public void getResponse(String response, int requestId) {
        super.getResponse(response, requestId);
        switch (requestId) {
            case 11:
                try {
                    JSONObject json = new JSONObject(response);
                    String result = json.get("Result").toString();
                    if (result.equals("email send")) {
                        mBinding.fgtStatus.setText("Code sent to yout email, and please verify it here..");
                        mBinding.emailContainer.setVisibility(View.GONE);
                        mBinding.codeContainer.setVisibility(View.VISIBLE);
                    }
                } catch (Exception ex) {
                    Toast.makeText(this, "Sending Code failed", Toast.LENGTH_SHORT).show();
                }
                break;
            case 12:
                try {
                    JSONObject json = new JSONObject(response);
                    String result = json.get("Result").toString();
                    if (result.equals("document found")) {
                        mBinding.fgtStatus.setText("");
                        mBinding.emailContainer.setVisibility(View.GONE);
                        mBinding.codeContainer.setVisibility(View.GONE);
                        mBinding.passwordContainer.setVisibility(View.VISIBLE);
                    }
                } catch (Exception ex) {
                    Toast.makeText(this, "Check your code again", Toast.LENGTH_SHORT).show();
                    ex.printStackTrace();
                }
                break;
            case 13:
                try {
                    Toast.makeText(this, getString(R.string.password_changed), Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toast.makeText(this, "Changing password failed", Toast.LENGTH_SHORT).show();
                }
                finish();
                break;

        }
    }
}
