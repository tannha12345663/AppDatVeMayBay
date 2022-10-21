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
import com.example.appdatvemaybay.fragment.HomeFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class DiemKhoiHanhActivity extends AppCompatActivity implements CountryVNAdapter.Listener {
    ImageButton imgBackHome;
    RecyclerView recyclerView;
    List<CountryVN> countryVNS;
    CountryVNAdapter countryVNAdapter;
    TextInputEditText etTenTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diem_khoi_hanh);
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

    private void innitUI() {
        imgBackHome=findViewById(R.id.imgBackHome);
        etTenTP=findViewById(R.id.etTenTP);
        recyclerView = findViewById(R.id.rcCountryKH);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        countryVNS = new ArrayList<>();
        countryVNS= InnitDataCountry.ininitCountry();
        countryVNAdapter = new CountryVNAdapter(countryVNS,DiemKhoiHanhActivity.this);
        recyclerView.setAdapter(countryVNAdapter);

    }

    @Override
    public void onItemListener(CountryVN countryVN) {
        //etTenTP.setText(countryVN.getNameTP()+" \t" + countryVN.getSanBay());
        String tp = countryVN.getNameTP();
        String sb = countryVN.getSanBay();
        Intent intent = new Intent(DiemKhoiHanhActivity.this, MainActivity.class);
        intent.putExtra("DiemKH",tp);
        intent.putExtra("SanBay",sb);
        startActivity(intent);
    }
}