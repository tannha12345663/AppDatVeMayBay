package com.example.appdatvemaybay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SignPhone extends AppCompatActivity {
    EditText etPhonenume;
    Button btnSendOTP;
    FirebaseAuth mAuth;
    String VerifyOTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_phone);
        initUI();
        initListener();
    }

    private void initListener() {
        String phoneNume= etPhonenume.getText().toString().trim();
        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendOTP(phoneNume);
            }
        });
    }

    private void initUI() {
        etPhonenume=findViewById(R.id.EnterPhone);
        btnSendOTP=findViewById(R.id.VerifyPhone);

    }
    private void SendOTP(String phoneNumber){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(SignPhone.this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                                Toast.makeText(SignPhone.this, "Gửi mã thành công !", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignPhone.this,OtpActivity.class);
                                intent.putExtra("mobile",phoneNumber);
                                intent.putExtra("verification_id",VerifyOTP);
                                startActivity(intent);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                VerifyOTP=s;

                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignPhone.this, "signInWithCredential:success", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(SignPhone.this, "signInWithCredential:failur", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(SignPhone.this, "The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}