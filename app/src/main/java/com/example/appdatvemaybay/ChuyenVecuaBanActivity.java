package com.example.appdatvemaybay;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ChuyenVecuaBanActivity extends AppCompatActivity {
    ImageButton imgBack03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuyen_ve_cua_ban);
        innitUI();
        innitListener();
    }

    private void innitListener() {
        imgBack03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void innitUI() {
        imgBack03=findViewById(R.id.imgBackHome4);
    }
}