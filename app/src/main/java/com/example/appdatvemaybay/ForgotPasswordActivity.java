package com.example.appdatvemaybay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    ImageButton imgBackHome8;
    TextInputEditText etEmailforgot;
    Button btnForrgot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        innitUI();
        innitListener();
    }

    private void innitUI() {
        imgBackHome8=findViewById(R.id.imgBackHome8);
        etEmailforgot=findViewById(R.id.etEmailforgot);
        btnForrgot=findViewById(R.id.btnForrgot);
    }
    private void innitListener(){
        imgBackHome8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        ProgressDialog progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
        btnForrgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Xin vui lòng đợi giây lát. Đang xác thực Email");
                progressDialog.show();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = etEmailforgot.getText().toString().trim();

                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(ForgotPasswordActivity.this, "Vui lòng kiểm tra lại email để lấy lại mật khẩu !", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(ForgotPasswordActivity.this, "Không có Email trong hệ thống. Vui lòng kiểm tra lại email !", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

    }
}