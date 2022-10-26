package com.example.appdatvemaybay;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdatvemaybay.Account_User.BienTam;
import com.example.appdatvemaybay.Country.Ticket;
import com.example.appdatvemaybay.Country.TicketAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChuyenVecuaBanActivity extends AppCompatActivity implements TicketAdapter.Listener{
    ImageButton imgBack04;
    TextView tvLichTrinhve;
    String DiemKH,DiemDen,MaTPdi,MaTPve,NgayDi,Soluongnguoi,NgayVe;
    BienTam bienTam,bienTamVe;
    List<Ticket> mTicketList = new ArrayList<>();
    TicketAdapter mTicketAdapter;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    //Xử lý dữ liệu lên firebase
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseUser user;
    int SLTong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuyen_ve_cua_ban);
        innitUI();
        innitListener();

    }

    private void innitListener() {
        imgBack04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getListTicket();
    }


    private void innitUI() {
        user= FirebaseAuth.getInstance().getCurrentUser();
        imgBack04=findViewById(R.id.imgBack4);
        tvLichTrinhve=findViewById(R.id.tvLichTrinhdive);
        Intent intent= getIntent();
        bienTam = (BienTam) intent.getSerializableExtra("BienTam");
        MaTPdi = bienTam.getMaTPdi();
        MaTPve = bienTam.getMaTPve();
        DiemKH = bienTam.getDiemKH();
        DiemDen = bienTam.getDiemDen();
        NgayDi= bienTam.getNgayDi();
        NgayVe= bienTam.getNgayVe();
        Soluongnguoi = bienTam.getSoluongnguoi();
        SLTong = intent.getIntExtra("TongSL",0);
        tvLichTrinhve.setText(MaTPve+" Đến " + MaTPdi);
        recyclerView = findViewById(R.id.rxChuyenVe);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        mTicketAdapter = new TicketAdapter(mTicketList,ChuyenVecuaBanActivity.this,0);
        recyclerView.setAdapter(mTicketAdapter);
    }
    private void getListTicket() {

        progressDialog = new ProgressDialog(ChuyenVecuaBanActivity.this);
        progressDialog.setMessage("Đang tải danh sách vé \n Vui lòng đợi giây lát");
        progressDialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Ticket");

        myRef.child("ThanhPho").child(MaTPve).child(NgayVe).child(MaTPdi).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String MaVe = (String) snapshot.child("MaVe").getValue();
                String Hang = (String) snapshot.child("Hang").getValue();
                String GioDen = (String) snapshot.child("GioVe").getValue();
                String GioDi = (String) snapshot.child("GioBay").getValue();
                String GiaVe = (String) snapshot.child("GiaVe").getValue();
                String SLtong= String.valueOf(SLTong);
                Ticket ticket = new Ticket(GiaVe, GioDi, GioDen, Hang, MaVe,NgayDi,SLtong,DiemKH,DiemDen,MaTPdi,MaTPve);
                if (ticket != null) {
                    progressDialog.dismiss();
                    mTicketList.add(ticket);
                    mTicketAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onItemListener(Ticket ticket) {
        Toast.makeText(this, ""+ticket.getMaVe(), Toast.LENGTH_SHORT).show();
        if (user !=null){
            //Push vé
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("MaVe");
            myRef.child(ticket.getMaVe()).child(user.getUid()).setValue(SLTong);

        }
        else {
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("MaVe");
            myRef.child(ticket.getMaVe()).setValue(ticket);
        }
        myRef=database.getReference("DSorder");
        myRef.child(ticket.getMaVe()).setValue(ticket);
        Intent intent = new Intent(ChuyenVecuaBanActivity.this,ThongtinKH.class);
        startActivity(intent);

    }
}