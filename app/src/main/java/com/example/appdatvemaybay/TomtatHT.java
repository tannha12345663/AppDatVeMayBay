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
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdatvemaybay.Country.Ticket;
import com.example.appdatvemaybay.Country.TicketAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    //Firebase
    FirebaseUser user;
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
        user= FirebaseAuth.getInstance().getCurrentUser();
    }
    private void innitListener() {
        imgBackHome7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("Ticketve",ticketve.getMaVe());
                intent.putExtra("Ticketdi",ticketdi.getMaVe());
                intent.putExtra("flag",1);
                setResult(RESULT_OK,intent);
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
                if (user!=null){
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("DSorder");
                    myRef.removeValue();
                    luugiaodich();
                    Toast.makeText(TomtatHT.this, "Chúc mừng bạn đã thanh toán thành công", Toast.LENGTH_SHORT).show();
                    intent = new Intent(TomtatHT.this,MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(TomtatHT.this, "Chúc mừng bạn đã thanh toán thành công \n Vui lòng kiểm tra lại email của bạn", Toast.LENGTH_SHORT).show();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("DSorder");
                    myRef.removeValue();
                    intent = new Intent(TomtatHT.this,MainActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void luugiaodich() {
        user= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("GiaoDich");
        mTicketList.size();
        for (Ticket ticket : mTicketList) {
            myRef.child(user.getUid()).child(ticket.getMaVe()).setValue(ticket);
        }

    }

    private void getListTicket() {
        user= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("DSorder");
        if (user!=null){
            myRef.child(user.getUid()).addChildEventListener(new ChildEventListener() {
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
            myRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
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
        else {
            String m_androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            myRef.child(m_androidId).addChildEventListener(new ChildEventListener() {
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
            myRef.child(m_androidId).addValueEventListener(new ValueEventListener() {
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

    }

    @Override
    public void onItemListener(Ticket ticket) {

    }
}