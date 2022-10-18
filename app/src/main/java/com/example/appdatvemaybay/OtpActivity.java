package com.example.appdatvemaybay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {

    private EditText otpET1,otpET2,otpET3,otpET4,otpET5,otpET6;
    private boolean resendEnable = false;
    private int selectedETPosition = 0;
    //FireBase lấy thông tin tk
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        innitUI();
    }

    private void innitUI() {
        otpET1=findViewById(R.id.otpET1);
        otpET2=findViewById(R.id.otpET2);
        otpET3=findViewById(R.id.otpET3);
        otpET4=findViewById(R.id.otpET4);
        otpET5=findViewById(R.id.otpET5);
        otpET6=findViewById(R.id.otpET6);

        final Button btnVerify =findViewById(R.id.Btn_verify);
        final TextView otpEmail = findViewById(R.id.otpEmail);
        final TextView otpMobile = findViewById(R.id.otpMobile);
        //Lấy thông tin Email và sđt từ form đăng ký
        final String getEmail = getIntent().getStringExtra("email");
        final String getMobile = getIntent().getStringExtra("mobile");
        ;
        //Thiết lập settext cho Email và Mobile
        otpEmail.setText(getEmail);
        otpMobile.setText(getMobile);

        otpET1.addTextChangedListener(textWatcher);
        otpET2.addTextChangedListener(textWatcher);
        otpET3.addTextChangedListener(textWatcher);
        otpET4.addTextChangedListener(textWatcher);
        otpET5.addTextChangedListener(textWatcher);
        otpET6.addTextChangedListener(textWatcher);

        //Mặc định mở bàn phím ở otpET1
        showKeyboard(otpET1);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String generateOtp = otpET1.getText().toString().trim()+otpET2.getText().toString().trim()
                        + otpET3.getText().toString().trim() + otpET4.getText().toString().trim()
                        +otpET5.getText().toString().trim()+otpET6.getText().toString().trim();
                final String getVerify =getIntent().getStringExtra("verification_id");
                if (generateOtp.length() == 6){
                    //Xác thực mã OTP
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(getVerify, generateOtp);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

    }

    private void showKeyboard(EditText otpET){
        otpET.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otpET, InputMethodManager.SHOW_IMPLICIT);
    }
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length()>0){
                if (selectedETPosition == 0){
                    selectedETPosition = 1;
                    showKeyboard(otpET2);

                }
                else if (selectedETPosition == 1){
                    selectedETPosition = 2;
                    showKeyboard(otpET3);

                }else if (selectedETPosition == 2){
                    selectedETPosition = 3;
                    showKeyboard(otpET4);
                }else if (selectedETPosition == 3) {
                    selectedETPosition = 4;
                    showKeyboard(otpET5);
                }else if (selectedETPosition == 4){
                    selectedETPosition = 5;
                    showKeyboard(otpET6);
                }
            }
        }
    };

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL){
            if (selectedETPosition == 6){
                selectedETPosition =5;
                showKeyboard(otpET6);
            }
            else if (selectedETPosition == 5){
                selectedETPosition =4;
                showKeyboard(otpET5);
            }
            else if (selectedETPosition == 4){
                selectedETPosition =3;
                showKeyboard(otpET4);
            }
            else if (selectedETPosition == 3){
                selectedETPosition =2;
                showKeyboard(otpET3);
            }
            else if (selectedETPosition == 2){
                selectedETPosition = 1;
                showKeyboard(otpET2);
            }
            else if (selectedETPosition == 1){
                selectedETPosition = 0;
                showKeyboard(otpET1);
            }
            return true;
        }
        else {
            return super.onKeyUp(keyCode, event);
        }

    }
    //Xác thực đăng nhập
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(OtpActivity.this, "signInWithCredential:success", Toast.LENGTH_SHORT).show();

                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                            Intent intent = new Intent();
                            intent = new Intent(OtpActivity.this,MainActivity.class);
                            startActivity(intent);
                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(OtpActivity.this, "signInWithCredential:failure", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(OtpActivity.this, "The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

}