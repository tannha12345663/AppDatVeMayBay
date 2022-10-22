package com.example.appdatvemaybay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.appdatvemaybay.Country.CountryVN;
import com.example.appdatvemaybay.Country.CountryVNAdapter;
import com.example.appdatvemaybay.Country.InnitDataCountry;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class DiemDenActivity extends AppCompatActivity implements CountryVNAdapter.Listener {
    ImageButton imgBackHome;
    TextInputEditText etTenTPDiemDen;
    RecyclerView rcCountryKH;
    List<CountryVN> countryDiemden;
    CountryVNAdapter countryVNAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diem_den);

        innitUI();
        innitListener();
    }

    private void innitListener() {
        imgBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private  void innitUI(){
        imgBackHome=findViewById(R.id.imgBackHome2);
        etTenTPDiemDen = findViewById(R.id.etTenTPDiemDen);
        etTenTPDiemDen=findViewById(R.id.etTenTPDiemDen);
        rcCountryKH = findViewById(R.id.rcCountryKHDD);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcCountryKH.setLayoutManager(linearLayoutManager);
        countryDiemden = new ArrayList<>();
        countryDiemden= InnitDataCountry.ininitCountry();
        countryVNAdapter = new CountryVNAdapter(countryDiemden,DiemDenActivity.this);
        rcCountryKH.setAdapter(countryVNAdapter);
    }

    @Override
    public void onItemListener(CountryVN countryVN) {
        String tp = countryVN.getNameTP();
        String sb = countryVN.getSanBay();
        Intent intent = new Intent(DiemDenActivity.this, MainActivity.class);
        intent.putExtra("DiemKH",tp);
        intent.putExtra("SanBay",sb);
        intent.putExtra("flag",0);
        setResult(RESULT_OK,intent);
        this.onBackPressed();
    }
}