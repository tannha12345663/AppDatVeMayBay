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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {
    TextView tvBackLogin;
    EditText edEmailDK,edPassDK,edPhoneDK,edFullName;
    Button btnDangKy;
    //FireBase ACC
    String strPhoneNumber, verificationId;
    FirebaseAuth mAuth;
    Intent intent;
    private String CountrySDT = "+84";
    private String Maverify="";
    private PhoneAuthProvider.ForceResendingToken mForceResendingToken;
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
        edPhoneDK=findViewById(R.id.etPhone_dk);

        btnDangKy=findViewById(R.id.btnDangKy);
        progressDialog = new ProgressDialog(SignUpActivity.this);
    }
    private void initListener(){
        //Ấn nút đăng ký
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edEmailDK.getText().toString().trim())|| TextUtils.isEmpty(edPassDK.getText().toString().trim())){
                    Toast.makeText(SignUpActivity.this, "Thông tin nhập bị thiếu hoặc sai định dạnh. Vui lòng nhập lại !", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(edPhoneDK.getText().toString().trim())){
                    Toast.makeText(SignUpActivity.this, "Vui lòng nhập thông tin số điện thoại", Toast.LENGTH_SHORT).show();
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
                            final String getMobile = edPhoneDK.getText().toString().trim();
                            final String getEmail = edEmailDK.getText().toString().trim();
                            //Send OTP
                            SendOTP();
                            //Opening OTP Verification Activity along with mobile and email
                            intent = new Intent(SignUpActivity.this,OtpActivity.class);
                            intent.putExtra("verification_id",Maverify);
                            intent.putExtra("mobile",getMobile);
                            intent.putExtra("email",getEmail);

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

    private void SendOTP() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(CountrySDT+""+edPhoneDK.getText().toString().trim())       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(SignUpActivity.this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                Toast.makeText(SignUpActivity.this, "OTP đã được xác thực thành công", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(SignUpActivity.this, "Mã OTP không đúng vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);

                                Maverify=s;
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }
}