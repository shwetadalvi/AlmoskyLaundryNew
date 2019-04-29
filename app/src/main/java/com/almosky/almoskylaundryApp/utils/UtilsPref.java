package com.almosky.almoskylaundryApp.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.common.BaseActivity;
import com.almosky.almoskylaundryApp.di.component.ActivityComponent;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;


import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import cz.msebera.android.httpclient.entity.StringEntity;


public class UtilsPref {
    private static final String EMAIL_REGEX = "^\\s*?(.+)@(.+?)\\s*$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    @Inject
    Context mContext;

    public UtilsPref(ActivityComponent appComponent) {
        appComponent.inject(this);
    }

    public void toLog(String value) {
        Log.d("DDCS", value);
    }

    public void toToast(String toastedString) {
        final Toast toast = Toast.makeText(mContext, "" + toastedString, Toast.LENGTH_SHORT);
        toast.show();
        new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                toast.show();
            }

            public void onFinish() {
                toast.cancel();
            }

        }.start();

    }

    public void toToast(View view, String toastedString) {
        Snackbar.make(view, toastedString, Snackbar.LENGTH_LONG)
                .show();
    }

    public void toToastError() {
        Toast.makeText(mContext, "Check you internet connection", Toast.LENGTH_SHORT).show();
    }

    public void hideKey(FragmentActivity context) {
        // TODO Auto-generated method stub
        try {
            InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(context.getCurrentFocus()
                    .getWindowToken(), 0);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


    public boolean isValid(String email) {
        Matcher emailMatcher = EMAIL_PATTERN.matcher("" + email);
        if (!emailMatcher.matches()) {
            return false;
        }
        return true;
    }

    public interface DialogComplete {
        void dialogComplete(boolean result);
    }


    public androidx.appcompat.app.AlertDialog showDialog(final String title, String message, Context context, final DialogComplete dialogComplete) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        String positiveText = context.getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        dialogComplete.dialogComplete(true);
                    }
                });

        String negativeText = context.getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogComplete.dialogComplete(false);
                        // negative button logic
                    }
                });

        androidx.appcompat.app.AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
        return dialog;
    }

    public StringEntity createJsonWithoutDataTag(JSONObject json) {

        StringEntity entity = null;
        try {
            entity = new StringEntity(json.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d("tag", "tag  " + json.toString());
        return entity;
    }

    public void setLoadingColor(BaseActivity mActivity) {
        final SimpleArcDialog mDialog = mActivity.getDialog(mActivity);
        ArcConfiguration configuration = new ArcConfiguration(mActivity);
        configuration.setColors(new int[]{mActivity.getResources().getColor(R.color.green)});
        configuration.setTextColor(mActivity.getResources().getColor(R.color.green));
        mDialog.setConfiguration(configuration);
        mDialog.show();
    }

}
