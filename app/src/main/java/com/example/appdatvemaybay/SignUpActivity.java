package com.example.appdatvemaybay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    TextView tvBackLogin;
    EditText edEmailDK,edPassDK;
    Button btnDangKy;
    //FireBase ACC
    FirebaseAuth mAuth;
    Intent intent;
    //Xử lý diaglog
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUI();
        tvBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(SignUpActivity.this,SigninActivity.class);
                startActivity(intent);
            }
        });
        initListener();

    }
    private void initUI(){
        tvBackLogin=findViewById(R.id.tvBackLogin);
        edEmailDK=findViewById(R.id.etEmail_dk);
        edPassDK=findViewById(R.id.etPass_dk);
        btnDangKy=findViewById(R.id.btnDangKy);

        progressDialog = new ProgressDialog(SignUpActivity.this);
    }
    private void initListener(){
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edEmailDK.getText().toString().trim())|| TextUtils.isEmpty(edPassDK.getText().toString().trim())){
                    Toast.makeText(SignUpActivity.this, "Thông tin nhập bị thiếu hoặc sai định dạnh. Vui lòng nhập lại !", Toast.LENGTH_SHORT).show();
                }
                else {
                    onClickDangKy();
                }
            }
        });

    }

    private void onClickDangKy() {
        String email = edEmailDK.getText().toString().trim();
        String password= edPassDK.getText().toString().trim();
        mAuth=FirebaseAuth.getInstance(); // Khởi tạo firebase Account API
        progressDialog.setMessage("Đang xử lý vui lòng đợi giây lát");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            intent = new Intent(SignUpActivity.this,MainActivity.class);
                            startActivity(intent);
                            //Đóng các Activity
                            finishAffinity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "Đăng ký thất bại.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}