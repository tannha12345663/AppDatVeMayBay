package com.example.appdatvemaybay;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChuyenVecuaBanActivity extends AppCompatActivity implements TicketAdapter.Listener{
    ImageButton imgBack04;
    TextView tvLichTrinhve;
    String DiemKH,DiemDen,MaTPdi,MaTPve,NgayDi,Soluongnguoi,NgayVe,MaVe,GiaVe;
    BienTam bienTam,bienTamVe;
    List<Ticket> mTicketList = new ArrayList<>();
    TicketAdapter mTicketAdapter;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    //Xử lý dữ liệu lên firebase
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseUser user;
    Ticket ticket;
    int SLTong;
    String Ticketdi="";
    ActivityResultLauncher<Intent> mlauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == DiemKhoiHanhActivity.RESULT_OK) {
                        if (result.getData().getIntExtra("flag", 0) == 1) {
                            Ticketdi=result.getData().getStringExtra("Ticketdi");

                        }
                    }
                }
            });
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
                user= FirebaseAuth.getInstance().getCurrentUser();
                database = FirebaseDatabase.getInstance();
                myRef=database.getReference("DSorder");
                if (Ticketdi.isEmpty()){
                    myRef.removeValue();
                }
                else {
                    if (user!=null){
                        myRef.child(user.getUid()).child(Ticketdi).removeValue();
                    }
                    else {
                        String m_androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                        myRef.child(m_androidId).child(Ticketdi).removeValue();
                    }

                }
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
                MaVe = (String) snapshot.child("MaVe").getValue();
                String Hang = (String) snapshot.child("Hang").getValue();
                String GioDen = (String) snapshot.child("GioDen").getValue();
                String GioDi = (String) snapshot.child("GioBay").getValue();
                GiaVe = (String) snapshot.child("GiaVe").getValue();
                String SLtong= String.valueOf(SLTong);
                ticket = new Ticket(GiaVe, GioDi, GioDen, Hang, MaVe,NgayVe,SLtong,DiemDen,DiemKH,MaTPve,MaTPdi);
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
        if (ticket == null){
            progressDialog.dismiss();
            Toast.makeText(ChuyenVecuaBanActivity.this, "Không tìm thấy thông tin chuyến bay này", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onItemListener(Ticket ticket) {
        Toast.makeText(this, ""+ticket.getMaVe(), Toast.LENGTH_SHORT).show();
        user= FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef=database.getReference("DSorder");
        if (user!=null){
            myRef.child(user.getUid()).child(ticket.getMaVe()).setValue(ticket);
        }
        else {
            String m_androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            myRef.child(m_androidId).child(ticket.getMaVe()).setValue(ticket);
        }
        Intent intent = new Intent(ChuyenVecuaBanActivity.this,ThongtinKH.class);
        intent.putExtra("MaveKH",MaVe);
        mlauncher.launch(intent);

    }
}