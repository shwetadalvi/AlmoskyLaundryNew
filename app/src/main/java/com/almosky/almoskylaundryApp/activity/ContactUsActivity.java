package com.almosky.almoskylaundryApp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.almosky.almoskylaundryApp.R;

import androidx.appcompat.app.AppCompatActivity;

public class ContactUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        Button call=findViewById(R.id.btnCall);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:+97143440253"));
                startActivity(dialIntent);
            }
        });
    }
}
