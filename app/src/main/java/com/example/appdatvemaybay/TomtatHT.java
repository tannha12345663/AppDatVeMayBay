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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Formattable;
import java.util.List;
import java.util.Locale;

public class TomtatHT extends AppCompatActivity implements TicketAdapter.Listener{
    ImageButton imgBackHome7;
    RecyclerView rcTomTat;
    TextView tvChuyenDi, tvSoLuong,tvChuyenVe, tvSoLuong01,tvGiaTong, btnThanhToan;
    List<Ticket> mTicketList = new ArrayList<>();
    TicketAdapter mTicketAdapter;
    ProgressDialog progressDialog;
    Ticket ticketdi,ticketve;
    String MaveKH,MaveDi;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomtat_ht);
        innitUI();
        getListTicket();
        innitListener();
    }
    private void innitUI() {
        imgBackHome7=findViewById(R.id.imgBackHome7);
        tvChuyenDi=findViewById(R.id.tvChuyenDi);
        tvSoLuong=findViewById(R.id.tvSoLuong);
        tvChuyenVe=findViewById(R.id.tvChuyenVe);
        tvSoLuong01=findViewById(R.id.tvSoLuong01);
        tvGiaTong=findViewById(R.id.tvGiaTong);
        btnThanhToan=findViewById(R.id.btnThanhToan);
        rcTomTat=findViewById(R.id.rcTomTat);
    }
    private void innitListener() {
        imgBackHome7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcTomTat.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcTomTat.addItemDecoration(dividerItemDecoration);
        mTicketAdapter = new TicketAdapter(mTicketList,TomtatHT.this,1);
        rcTomTat.setAdapter(mTicketAdapter);
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("DSorder");
                myRef.removeValue();
                Toast.makeText(TomtatHT.this, "Chúc mừng bạn đã thanh toán thành công", Toast.LENGTH_SHORT).show();
                intent = new Intent(TomtatHT.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getListTicket() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("DSorder");
//        myRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                String MaVe = (String) snapshot.child("maVe").getValue();
//                String Hang = (String) snapshot.child("hang").getValue();
//                String GioDen = (String) snapshot.child("gioDen").getValue();
//                String GioBay = (String) snapshot.child("gioBay").getValue();
//                String GiaVe = (String) snapshot.child("giaVe").getValue();
//                String NgayDi = (String) snapshot.child("ngayDi").getValue();
//                String SLtong= (String) snapshot.child("soLuong").getValue();
//                String DiemKH = (String) snapshot.child("diemKH").getValue();
//                String DiemDen = (String) snapshot.child("diemDen").getValue();
//                String MaTPdi = (String) snapshot.child("maTPdi").getValue();
//                String MaTVve = (String) snapshot.child("maTPve").getValue();
//                if (MaVe !=null || Hang !=null ||GioDen !=null || GioDen!=null|| GiaVe!=null|| NgayDi!=null||SLtong!=null){
//                    Ticket ticket = new Ticket(GiaVe,GioBay,GioDen,Hang,MaVe,NgayDi,SLtong,DiemKH,DiemDen,MaTPdi,MaTVve);
//                    if (ticket !=null){
//                        Ticket ticket1 = new Ticket(GiaVe,GioBay,GioDen,Hang,MaVe,NgayDi,SLtong,DiemKH,DiemDen,MaTPdi,MaTVve);
//                    }
//                    mTicketList.add(ticket);
//                    mTicketAdapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Ticket ticket = snapshot.getValue(Ticket.class);
                mTicketList.add(0,ticket);
                mTicketAdapter.notifyDataSetChanged();
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
        myRef = database.getReference("DSorder");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    ticketdi = snapshot1.getValue(Ticket.class);
                    if (ticketve==null){
                        ticketve=snapshot1.getValue(Ticket.class);
                    }


                }
                intent=getIntent();
                Locale localeVN = new Locale("vi", "VN");
                NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                Toast.makeText(TomtatHT.this, "Thông tin vé đi : "+ticketdi.getMaVe(), Toast.LENGTH_SHORT).show();
                Toast.makeText(TomtatHT.this, "Thông vé về : "+ticketve.getMaVe(), Toast.LENGTH_SHORT).show();
                int flag ;
                Intent intent = getIntent();
                flag=intent.getIntExtra("flag",0);
                if (flag==1){
                    Integer giadi = Integer.parseInt(ticketdi.getGiaVe());
                    Integer sl = Integer.parseInt(ticketdi.getSoLuong());
                    Integer tong = giadi*sl;
                    tvChuyenDi.setText(ticketdi.getDiemKH()+"-"+ticketdi.getDiemDen()+":"+currencyVN.format(giadi));
                    tvSoLuong.setText("Số lượng: x"+ticketdi.getSoLuong());
                    tvGiaTong.setText("Giá cuối cùng: "+currencyVN.format(tong));
                    tvChuyenVe.setVisibility(View.INVISIBLE);
                    tvSoLuong01.setVisibility(View.INVISIBLE);
                }
                else {
                    Integer giadi = Integer.parseInt(ticketdi.getGiaVe());
                    Integer giave = Integer.parseInt(ticketve.getGiaVe());
                    Integer sl = Integer.parseInt(ticketdi.getSoLuong());
                    Integer tong = (giadi*sl)+(giave*sl);
                    tvChuyenDi.setText(ticketdi.getDiemKH()+"-"+ticketdi.getDiemDen()+":"+currencyVN.format(giadi));
                    tvSoLuong.setText("Số lượng: x"+ticketdi.getSoLuong());
                    tvChuyenVe.setText(ticketve.getDiemKH()+"-"+ticketve.getDiemDen()+" : "+currencyVN.format(giave));
                    tvSoLuong01.setText("Số lượng: x"+ticketve.getSoLuong());
                    tvGiaTong.setText("Giá cuối cùng: "+currencyVN.format(tong));
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onItemListener(Ticket ticket) {

    }
}