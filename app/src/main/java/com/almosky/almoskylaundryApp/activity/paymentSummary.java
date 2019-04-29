package com.almosky.almoskylaundryApp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.TabHostActivity;
import com.almosky.almoskylaundryApp.model.paymentdto;
import com.google.gson.Gson;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class paymentSummary extends AppCompatActivity {

    private String tranId,payment;

    TextView orderId,transactionId,amount,status;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_summary);

        orderId=(TextView)findViewById(R.id.orderId);
        transactionId=(TextView)findViewById(R.id.tranId);
        amount=(TextView)findViewById(R.id.amount);
        status=(TextView)findViewById(R.id.status);

        tranId= getIntent().getStringExtra("tranId");
        payment= getIntent().getStringExtra("payment");

        final paymentdto paymentData;
        Gson gson = new Gson();
        paymentData = gson.fromJson(payment, paymentdto.class);

        paymentdto.Transaction tranDto=paymentData.getTransaction();

        System.out.println("stat"+tranDto.getResponseClassDescription());

        orderId.setText(tranDto.getOrderID().toString());
        amount.setText("AED"+tranDto.getAmount().getValue().toString());
        status.setText(tranDto.getResponseClassDescription());
        transactionId.setText(tranId);


        Button done=(Button)findViewById(R.id.btnDone);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(paymentSummary.this, TabHostActivity.class);
                go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
               // go.putExtra("payment", val);
                startActivity(go);
            }
        });

    }
}
