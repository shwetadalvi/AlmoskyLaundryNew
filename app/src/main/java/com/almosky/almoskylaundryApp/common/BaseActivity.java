package com.almosky.almoskylaundryApp.common;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.almosky.almoskylaundryApp.App;
import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.helper.MyCustomDialog;
import com.almosky.almoskylaundryApp.notification.MyFirebaseInstanceIDService;
import com.almosky.almoskylaundryApp.receiver.ConnectionReceiver;
import com.almosky.almoskylaundryApp.utils.AppPrefes;
import com.almosky.almoskylaundryApp.utils.UtilsPref;
import com.almosky.almoskylaundryApp.utils.api.ApiCalls;
import com.almosky.almoskylaundryApp.utils.constants.ApiConstants;
import com.almosky.almoskylaundryApp.utils.constants.PrefConstants;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.RequestParams;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.almosky.almoskylaundryApp.utils.constants.PrefConstants.isLogin;


public class BaseActivity extends AppCompatActivity implements ConnectionReceiver.ConnectionReceiverListener, MyFirebaseInstanceIDService.onFirebaseIdReceivedListener {

    public SimpleArcDialog mDialog;
    @Inject
    public UtilsPref utilsPref;
    @Inject
    public AppPrefes appPrefes;

    private Dialog noNwAlert;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.getInstance().setConnectionListener(this);
    }

    protected App getApp() {
        return (App) getApplicationContext();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialog = getDialog(this);
        getApp().getActivityComponent().inject(this);
        new MyFirebaseInstanceIDService().setOnFirebaseIdReceived(this);
        FirebaseMessaging.getInstance().subscribeToTopic("Laundry");
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        return;
                    }
                    String token = task.getResult().getToken();
                    appPrefes.saveData(PrefConstants.token, token);
                });
    }

    public void addFragment(int fragment_container, Fragment fragment, boolean addBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(fragment_container, fragment);
        transaction.addToBackStack(addBackStack ? fragment.getClass().getName() : null);
        transaction.commit();
    }

    public void replaceFragment(int fragment_container, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(fragment_container, fragment);
        transaction.commit();
    }

    public SimpleArcDialog getDialog(Context mContext) {
        SimpleArcDialog mDialog = new SimpleArcDialog(mContext);
        mDialog.setConfiguration(new ArcConfiguration(mContext));
        return mDialog;
    }

   /* public void onBackButton() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable upArrow = getResources().getDrawable(R.drawable.ic_menu_camera);
        upArrow.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    } */

    public void onLog(String message) {
        Log.d("message", "-- " + message);
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected) {
            noNwAlert = openAlertDialogue("Check internet connection");
            //show a No Internet Alert or Dialog

        } else {
            if (null != noNwAlert)
                noNwAlert.dismiss();
            // dismiss the dialog or refresh the activity
        }
    }

    public Dialog openAlertDialogue(String messageText) {
        final MyCustomDialog builder = new MyCustomDialog(BaseActivity.this, messageText);
        final android.app.AlertDialog dialog = builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }

        }).create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(BaseActivity.this.getResources().getColor(R.color.green));
            }
        });

        dialog.show();

        dialog.show();
        return dialog;
    }

    public void getResponse(String response, int requestId) {
        if (requestId == 1002) {

        }
    }

    @Override public void onFirebaseIdRecived(String token) {
        appPrefes.saveData(PrefConstants.token, token);
        if (appPrefes.getBoolData(isLogin)) {
            ApiCalls apiCalls = new ApiCalls();
            RequestParams params = new RequestParams();
            params.put(ApiConstants.registrationToken, token);
            String url = ApiConstants.firebaseTokenId;
            apiCalls.callApiPost((BaseActivity) getApplicationContext(), params, null, url, 1002);
        }

    }

}
