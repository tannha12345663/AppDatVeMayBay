package com.example.appdatvemaybay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdatvemaybay.Country.Ticket;
import com.example.appdatvemaybay.Country.TicketAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChuyenDiCuaBanActivity extends AppCompatActivity implements TicketAdapter.Listener {
    ImageButton imgBack03;
    TextView tvLichTrinhdi;
    String DiemKH,DiemDen,MaTPdi,MaTPve,NgayDi,Soluongnguoi,NgayVe;
    List<Ticket> mTicketList = new ArrayList<>();
    TicketAdapter mTicketAdapter;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuyen_di_cua_ban);
        innitUI();
        innitListener();
    }

    private void innitListener() {
        imgBack03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getListTicket();
    }

    private void innitUI() {
        imgBack03=findViewById(R.id.imgBackHome4);
        tvLichTrinhdi=findViewById(R.id.tvLichTrinhdi);
         Intent intent= getIntent();
         MaTPdi = intent.getStringExtra("MaTPdi");
         MaTPve = intent.getStringExtra("MaTPve");
         DiemKH = intent.getStringExtra("DiemKH");
         DiemDen = intent.getStringExtra("DiemDen");
         NgayDi= intent.getStringExtra("NgayDi");
         NgayVe=intent.getStringExtra("NgayVe");
         Soluongnguoi = intent.getStringExtra("SoLuongNguoi");
         tvLichTrinhdi.setText(MaTPdi+" Đến " + MaTPve);
        recyclerView = findViewById(R.id.rcChuyenDi);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        mTicketAdapter = new TicketAdapter(mTicketList,ChuyenDiCuaBanActivity.this);
        recyclerView.setAdapter(mTicketAdapter);

    }

    private void getListTicket(){
        progressDialog = new ProgressDialog(ChuyenDiCuaBanActivity.this);
        progressDialog.setMessage("Đang tải danh sách vé \n Vui lòng đợi giây lát");
        progressDialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Ticket");
        
        myRef.child("ThanhPho").child(MaTPdi).child(NgayDi).child(MaTPve).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String MaVe = (String) snapshot.child("MaVe").getValue();
                String Hang = (String) snapshot.child("Hang").getValue();
                String GioDen = (String) snapshot.child("GioDen").getValue();
                String GioDi = (String) snapshot.child("GioBay").getValue();
                String GiaVe = (String) snapshot.child("GiaVe").getValue();
                Ticket ticket = new Ticket(GiaVe,GioDi,GioDen,Hang,MaVe);
                if (ticket!=null){
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
        Intent intent = new Intent(ChuyenDiCuaBanActivity.this,ChuyenVecuaBanActivity.class);
        intent.putExtra("SoLuongNguoi",Soluongnguoi);
        intent.putExtra("DiemKH",DiemKH);
        intent.putExtra("DiemVe",DiemDen);
        intent.putExtra("NgayDi",NgayDi);
        intent.putExtra("NgayVe",NgayVe);
        intent.putExtra("MaTPdi",MaTPdi);
        intent.putExtra("MaTPve",MaTPve);
        onPause();
        startActivity(intent);
    }
}