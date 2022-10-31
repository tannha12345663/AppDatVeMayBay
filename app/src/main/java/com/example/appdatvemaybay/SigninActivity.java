package com.example.appdatvemaybay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SigninActivity extends AppCompatActivity {
    TextView tvDangKy,tv_skip,tvLoginPhone,tvForgotPass;
    Button btnDangNhap;
    Intent intent;
    EditText etEmail,etPass;
    //Firebase
    FirebaseAuth mAuth;
    //Thông báo load
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        etEmail=findViewById(R.id.etEmail);
        etPass=findViewById(R.id.etPass);
        tv_skip=findViewById(R.id.tvSkip);
        tvForgotPass=findViewById(R.id.tvForgotPass);
        tvDangKy = findViewById(R.id.tvSignup);
        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(SigninActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(SigninActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnDangNhap=findViewById(R.id.btnDangNhap);
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Kiểm tra thông tin nhập có đúng định dạng không
                if (TextUtils.isEmpty(etEmail.getText().toString().trim())|| TextUtils.isEmpty(etPass.getText().toString().trim())){
                    Toast.makeText(SigninActivity.this, "Thông tin nhập bị thiếu hoặc sai định dạnh. Vui lòng nhập lại !", Toast.LENGTH_SHORT).show();
                }
                else {
                    onClickDangNhap();
                }

            }
        });
        tvLoginPhone=findViewById(R.id.tvLoginSDT);
        tvLoginPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SigninActivity.this,SignPhone.class);
                startActivity(intent);
            }
        });
        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigninActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
        progressDialog = new ProgressDialog(SigninActivity.this);
    }

    private void onClickDangNhap() {
        String email=etEmail.getText().toString().trim();
        String password = etPass.getText().toString().trim();
        mAuth=FirebaseAuth.getInstance();
        progressDialog.setMessage("Xin vui lòng đợi giây lát");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            intent=new Intent(SigninActivity.this,MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SigninActivity.this, "Đăng nhập không thành công. Vui lòng xem lại Email và Pass !" ,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}