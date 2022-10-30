package com.example.appdatvemaybay;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.appdatvemaybay.Account_User.Dsorder;
import com.example.appdatvemaybay.Country.Ticket;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ThongtinKH extends AppCompatActivity {

    ImageButton imgBackHome6;
    TextInputEditText edtPhone,edtName,edtEmail,edtCMND,edtBirthday;
    Button btnConfirm;
    int mYear,mMonth,mDay;
    FirebaseDatabase database;
    DatabaseReference myData;
    FirebaseUser user;
    Ticket ticketdi,ticketve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtin_kh);

        innitUI();
        innitListener();
    }

    private void innitUI() {
        btnConfirm=findViewById(R.id.btnConfirm);
        edtName = findViewById(R.id.edtName);
        edtBirthday=findViewById(R.id.edtBirthday);
        edtCMND=findViewById(R.id.edtCMND);
        edtEmail=findViewById(R.id.edtEmail);
        edtPhone=findViewById(R.id.edtPhone);
        imgBackHome6=findViewById(R.id.imgBackHome7);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            edtEmail.setText(user.getEmail());
             database = FirebaseDatabase.getInstance();
             myData = database.getReference("ListUser");
             myData.child(user.getUid()).child("name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String name = (String) snapshot.getValue();
                    edtName.setText(name);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            myData.child(user.getUid()).child("phone").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String phone = (String) snapshot.getValue();
                    edtPhone.setText(phone);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        edtBirthday.setOnClickListener(view -> {
            if (view == edtBirthday) {
                final Calendar calendar = Calendar.getInstance ();
                mYear = calendar.get ( Calendar.YEAR );
                mMonth = calendar.get ( Calendar.MONTH );
                mDay = calendar.get ( Calendar.DAY_OF_MONTH );

                //show dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog ( ThongtinKH.this, new DatePickerDialog.OnDateSetListener () {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        edtBirthday.setText ( dayOfMonth + "/" + String.format("%02d",month+1) + "/" + year );
                    }
                }, mYear, mMonth, mDay );
                datePickerDialog.show ();
            }
        });


    }

    private void innitListener() {
        imgBackHome6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myData=database.getReference("DSorder");
                myData.child(ticketve.getMaVe()).removeValue();
                Intent intent = new Intent();
                intent.putExtra("Ticketdi",ticketdi.getMaVe());
                intent.putExtra("flag",1);
                setResult(RESULT_OK,intent);
                onBackPressed();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag;
                Intent intent=getIntent();
                flag=intent.getIntExtra("flag",0);
                if (flag==1){
                    Intent intent1 = new Intent();
                    intent1=new Intent(ThongtinKH.this,TomtatHT.class);
                    intent1.putExtra("flag",1);
                    startActivity(intent1);
                }
                else {
                    Intent intent2 = new Intent();
                    intent2=new Intent(ThongtinKH.this,TomtatHT.class);
                    startActivity(intent2);
                }


            }
        });
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("DSorder");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    ticketdi = snapshot1.getValue(Ticket.class);
                    if (ticketve==null){
                        ticketve=snapshot1.getValue(Ticket.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}