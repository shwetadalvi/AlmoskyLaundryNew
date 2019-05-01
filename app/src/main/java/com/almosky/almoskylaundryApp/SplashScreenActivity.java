package com.almosky.almoskylaundryApp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.almosky.almoskylaundryApp.activity.DeliveryActivity;
import com.almosky.almoskylaundryApp.helper.GooglePlayStoreAppVersionNameLoader;
import com.almosky.almoskylaundryApp.interfaces.WSCallerVersionListener;
import com.almosky.almoskylaundryApp.utils.AppPrefes;
import com.almosky.almoskylaundryApp.utils.constants.ApiConstants;
import com.almosky.almoskylaundryApp.utils.constants.Constants;
import com.almosky.almoskylaundryApp.utils.constants.PrefConstants;
import com.loopj.android.http.RequestParams;


public class SplashScreenActivity extends AppCompatActivity implements WSCallerVersionListener {

    boolean isForceUpdate = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow(); // in TermsActivity's onCreate() for instance
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_splash);

        Almosky.getInst().setUpdatePriceList(false);

        new GooglePlayStoreAppVersionNameLoader(getApplicationContext(), this).execute();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, TabHostActivity.class));
                finish();
            }
        }, 3000);


    }

    @Override
    public void onGetResponse(boolean isUpdateAvailable) {
        Log.d("ResultAPPMAIN","Inside ResultAPPMAIN" +String.valueOf(isUpdateAvailable));
        Log.d("new Version","Inside isUpdateAvailable");
        if (isUpdateAvailable) {
            Log.d("new Version","Inside onGetResponse");
            showUpdateDialog();
        }
    }
    public void showUpdateDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SplashScreenActivity.this);

        alertDialogBuilder.setTitle(SplashScreenActivity.this.getString(R.string.app_name));
        alertDialogBuilder.setMessage(SplashScreenActivity.this.getString(R.string.update_message));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(R.string.update_now, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                dialog.cancel();
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isForceUpdate) {
                    finish();
                }
                dialog.dismiss();
            }
        });
        alertDialogBuilder.show();
    }
}

