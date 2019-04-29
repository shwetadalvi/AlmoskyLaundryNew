package com.almosky.almoskylaundryApp.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.almosky.almoskylaundryApp.R;

import androidx.appcompat.app.AppCompatActivity;

public class TermsActivity extends AppCompatActivity {
    private WebView terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);


        terms = (WebView) findViewById(R.id.wv_terms);
        terms.requestFocus();


        WebSettings webSettings = terms.getSettings();
        terms.setWebViewClient(new WebViewClient());
        webSettings.setJavaScriptEnabled(true);


        terms.loadUrl("http://www.almoskylandury.ae/term_and_condition.php");
        terms.setWebChromeClient(new WebChromeClient() {
            private ProgressDialog mProgress;

            @Override
            public void onProgressChanged(WebView view, int progress) {
                if (mProgress == null) {
                    mProgress = new ProgressDialog(TermsActivity.this);
                    mProgress.show();
                }
                mProgress.setMessage("Loading" + String.valueOf(progress) + "%");
                if (progress == 100) {
                    mProgress.dismiss();
                    mProgress = null;
                }
            }
        });



    }
}
