package com.almosky.almoskylaundryApp.activity.payment;


import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.activity.paymentSummary;
import com.almosky.almoskylaundryApp.common.BaseActivity;
import com.almosky.almoskylaundryApp.interfaces.JavaScriptInterface;

import androidx.annotation.Nullable;


public class PaymentViewActivity extends BaseActivity {

    String orderData,tranId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentview);

        WebView wv=(WebView)findViewById(R.id.wv_payment);

        wv.setWebViewClient(new CustomWebViewClient());
        WebSettings webSetting = wv.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(true);
        wv.setVerticalScrollBarEnabled(true);



        wv.addJavascriptInterface(new JavaScriptInterface(this,wv ),"MyHandler" );
         tranId= getIntent().getStringExtra("tranID");
        String payPage= getIntent().getStringExtra("payPage");
        orderData= getIntent().getStringExtra("orderData");

        String html ="<html>\n" +
                "\n" +
                "<body onload=\"document.frm1.submit()\">\n" +
                "\n" +
                "\n" +
                "<form name=\"frm1\" action=\""+payPage+"\" method=\"post\"> \n" +
                "  <input type='Hidden' name='TransactionID' value='"+tranId+"'/>\n" +
                "  <input type=\"submit\" value=\"Redirecting....\">\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>";

        wv.loadData(html, "text/html", "utf-8");

        wv.setWebViewClient(new WebViewClient() {


            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                //  wv.loadUrl("javascript: " +
                //     "window.androidObj.textToAndroid = function(message) { " +
                //   JAVASCRIPT_OBJ + ".textFromWeb(message) }");

                if(url.equals("http://148.72.64.138:3006/payment/message")) {
                    wv.loadUrl("javascript:window.MyHandler.setResult(document.getElementById('encrypted_data').value)");
                }

            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }
        });


    }


    public void javascriptCallFinished(final String val) {
        Log.v("mylog", "MyActivity.javascriptCallFinished is called : " + val);
      //  Toast.makeText(this, "Callback got val: " + val, Toast.LENGTH_SHORT).show();
        System.out.println("Final Resp"+val);

        Intent go = new Intent(PaymentViewActivity.this, paymentSummary.class);
        go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        go.putExtra("payment", val);
        go.putExtra("tranId", tranId);
        startActivity(go);




    }
    private class CustomWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
