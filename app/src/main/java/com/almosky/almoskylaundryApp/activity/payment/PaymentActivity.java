package com.almosky.almoskylaundryApp.activity.payment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.common.BaseActivity;
import com.almosky.almoskylaundryApp.model.paymentRegmodel;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import androidx.annotation.Nullable;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

import static com.almosky.almoskylaundryApp.App.getInstance;

public class PaymentActivity extends BaseActivity {

    String total,orderData,orderId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Button payment=(Button)findViewById(R.id.btnPayment);

        String totalAmnt= getIntent().getStringExtra("amount");
        String splitAmount[]=totalAmnt.split("AED");
        total=splitAmount[0];
        orderData= getIntent().getStringExtra("orderData");
        orderId= getIntent().getStringExtra("orderId");
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    payment();
                }catch (Exception e){

                }

            }
        });
    }

    private void payment() throws NoSuchAlgorithmException, KeyManagementException {


        try {


            String js = "{\n" +
                    "\"Registration\":{\n" +
                    "\"Currency\": \"AED\",\n" +
                    "\"ReturnPath\": \"http://148.72.64.138:3006/payment/message\", \n" +
                    "\"TransactionHint\": \"CPT:Y;VCC:Y;\",\n" +
                    "\"OrderID\": \""+orderId+"\",\n" +
                    "\"Channel\": \"Web\",\n" +
                    "\"Amount\": \""+total+"\",\n" +
//                  "\"Customer\": \"ALMOSKY LAUNDRY\",\n" +
                    "\"OrderName\": \"Pay bill\"\n" +
//                   "\"UserName\":\"Almosky_Ahmed\",\n" +
//                    "\"Password\":\"Almoskylau@1983\"\n" +
                    "}}";


            StringEntity se = new StringEntity(js, "UTF-8");

            AsyncHttpClient client = new AsyncHttpClient();
         //   client.addHeader("Accept", "text/xml-standard-api");
           // client.addHeader("Content-Type", "application/json");
            //client.setSSLSocketFactory(SSLSocketFactory.getSocketFactory());
            // client.addHeader("Authorization", "key=" + "xxxxxxxxxxxx");
            client.post(getInstance(), "http://148.72.64.138:3006/payment/registration", se, "application/json;charset=utf-8", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.e("success_noti", new String(responseBody) + "");

                    String regResponse = new String(responseBody);


                    try{
                        final paymentRegmodel regData;
                        Gson gson = new Gson();
                        regData = gson.fromJson(regResponse, paymentRegmodel.class);


                        paymentRegmodel.Transaction data = regData.getTransaction();

                        String Tran =data.getTransactionID();
                        String Tran1 =data.getPaymentPage();



                        Intent intent = new Intent(PaymentActivity.this, PaymentViewActivity.class);
                        intent.putExtra("tranID", data.getTransactionID());
                        intent.putExtra("payPage", data.getPaymentPage());
                        intent.putExtra("orderData", orderData);
                        startActivity(intent);




                        //  get3dSecureHtmlData(data.getPaymentPage(),Tran);

                    }catch (Exception e){

                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e("fail_noti", new String(responseBody) + "");
                }
            });
        }catch (Exception e){

        }

    }

}
