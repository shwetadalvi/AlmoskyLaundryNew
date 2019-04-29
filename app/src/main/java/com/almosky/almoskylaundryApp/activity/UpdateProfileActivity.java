package com.almosky.almoskylaundryApp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.TabHostActivity;
import com.almosky.almoskylaundryApp.common.BaseActivity;
import com.almosky.almoskylaundryApp.utils.AppPrefes;
import com.almosky.almoskylaundryApp.utils.api.ApiCalls;
import com.almosky.almoskylaundryApp.utils.constants.ApiConstants;
import com.almosky.almoskylaundryApp.utils.constants.PrefConstants;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import androidx.annotation.Nullable;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class UpdateProfileActivity extends BaseActivity {
    EditText fname, email,lname, phone, phoneExt;
    private AppPrefes appPrefes;
    private ApiCalls apiCalls;
    private SimpleArcDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_profile);
        appPrefes = new AppPrefes(this);
        apiCalls = new ApiCalls();
        dialog = new SimpleArcDialog(this);

        fname =(EditText)findViewById(R.id.edt_first_name);
     //   lname =(EditText)findViewById(R.id.edt_last_name);
        email =(EditText)findViewById(R.id.edt_email);

        phoneExt= (EditText) findViewById(R.id.edtPhoneExt);
        phone =(EditText) findViewById(R.id.edt_phone);


        TextView title = findViewById(R.id.tb_titleTextView);

        title.setText("Update Profile");


        ImageView backButton = findViewById(R.id.backArrow);
        backButton.setVisibility(View.VISIBLE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        getProfile();

        Button update=(Button) findViewById(R.id.btn_update_account);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateFields()){
                    onUpdate(v);
                }

            }
        });

    }

    private boolean validateFields() {

        if (fname.getText().toString().equals("") || fname.getText().toString().equals(null)) {
            fname.requestFocus();

            Toast.makeText(UpdateProfileActivity.this, "Name cannot be blank", Toast.LENGTH_LONG).show();

            return false;

        }
        if (email.getText().toString().equals("") || email.getText().toString().equals(null)) {
            email.requestFocus();
            Toast.makeText(UpdateProfileActivity.this, "Email cannot be blank", Toast.LENGTH_LONG).show();

            return false;

        }
        if (phone.getText().toString().equals("") || phone.getText().toString().equals(null)) {
            phone.requestFocus();
            Toast.makeText(UpdateProfileActivity.this, "Mobile number cannot be blank", Toast.LENGTH_LONG).show();

            return false;
        }
        if (phoneExt.getText().toString().equals("") || phoneExt.getText().toString().equals(null)) {
            phoneExt.requestFocus();
            Toast.makeText(UpdateProfileActivity.this, "Phone code cannot be blank", Toast.LENGTH_LONG).show();

            return false;
        }

        return true;
    }


    private void getProfile() {

        phoneExt.setText("+971");
        email.setText(appPrefes.getData(PrefConstants.email));
        fname.setText(appPrefes.getData(PrefConstants.name));
        phone.setText(appPrefes.getData(PrefConstants.mobile));

      //  appPrefes.getData(PrefConstants.mobile);


    }


    public void onUpdate(View view) {

        RequestParams params = new RequestParams();

        // params.put("LoginEmail", "admin@gmail.com");
        params.put(ApiConstants.firstname, fname.getText().toString());
        params.put(ApiConstants.email, appPrefes.getData(PrefConstants.email));
        params.put(ApiConstants.phone, phone.getText().toString());
        params.put(ApiConstants.newemail, email.getText().toString());
        params.put("lastname", "");
     //   params.put(ApiConstants.registrationToken, FirebaseInstanceId.getInstance().getToken());

       /* Almosky.getInst().setRequestParams(params);
        String phoneNumber = phoneExt.getText().toString()+phone.getText().toString();
        Intent intent = new Intent(getContext(), ForgotPasswordActivity.class);
        intent.putExtra("otp", true);
        intent.putExtra("phone", phoneNumber);
        startActivityForResult(intent, 123);*/
        String url = ApiConstants.updateUrl;
        apiCalls.callApiPost(UpdateProfileActivity.this, params, mDialog, url, 1);
    }

    @Override
    public void getResponse(String response, int requestId) {

        try {
            JSONObject objectResponse = new JSONObject(response);
            if (objectResponse.getString("status").equals("true")) {

                new SweetAlertDialog(UpdateProfileActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Success")
                        .setContentText(objectResponse.getString("Message"))
                        .setConfirmText("Ok")
                        .setConfirmClickListener(sDialog -> {
                            sDialog.dismissWithAnimation();
                            appPrefes.saveData(PrefConstants.email, email.getText().toString());
                            appPrefes.saveData(PrefConstants.name, fname.getText().toString());
                            appPrefes.saveData(PrefConstants.mobile, phone.getText().toString());
                            appPrefes.saveData(PrefConstants.email, email.getText().toString());
                            //appPrefes.saveIntData(PrefConstants.uid,userData.getProfile().get(0).getID());
                            appPrefes.saveBoolData(PrefConstants.isLogin, true);
                         //  startActivity(new Intent(UpdateProfileActivity.this, TabHostActivity.class));
                            Intent go = new Intent(UpdateProfileActivity.this, TabHostActivity.class);
                            go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(go);

                        })
                        .show();


            } else {

                new SweetAlertDialog(UpdateProfileActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Failed")
                        .setContentText(objectResponse.getString("Message"))
                        .show();

            }


        } catch (Exception e) {

        }




    }
}
