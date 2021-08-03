package com.aman.sdk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActionActivity2 extends FragmentActivity {

    Button btnYes,btnNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action2);

        btnNo=findViewById(R.id.btnNo1);
        btnYes=findViewById(R.id.btnYes1);

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.onConnected();
                finish();
            }
        });

    }

}