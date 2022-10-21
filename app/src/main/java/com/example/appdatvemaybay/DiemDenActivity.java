package com.example.appdatvemaybay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class DiemDenActivity extends AppCompatActivity {
    ImageButton imgBackHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diem_den);
        innitUI();
        innitListener();
    }

    private void innitListener() {
        imgBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private  void innitUI(){
        imgBackHome=findViewById(R.id.imgBackHome1);
    }
}