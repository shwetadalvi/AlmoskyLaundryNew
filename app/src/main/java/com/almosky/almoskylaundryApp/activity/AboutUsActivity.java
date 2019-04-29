package com.almosky.almoskylaundryApp.activity;

import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.almosky.almoskylaundryApp.R;

public class AboutUsActivity extends AppCompatActivity {

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


        terms.loadUrl("http://www.almoskylandury.ae/about.php");
        terms.setWebChromeClient(new WebChromeClient() {
            private ProgressDialog mProgress;

            @Override
            public void onProgressChanged(WebView view, int progress) {
                if (mProgress == null) {
                    mProgress = new ProgressDialog(AboutUsActivity.this);
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
