package com.example.appdatvemaybay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DemoActivity extends AppCompatActivity {
    Handler handler;
    FirebaseUser user;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        },2000);
    }

    private void nextActivity() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        //Check login
        if (user==null){
            //Chưa login
            intent = new Intent(this,SigninActivity.class);
            startActivity(intent);
        }

        else {
            //Đã login
            //Chuyển sang màn hình chính
            intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        finish();
    }
}