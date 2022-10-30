package com.example.appdatvemaybay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.appdatvemaybay.Country.CountryVN;
import com.example.appdatvemaybay.Country.CountryVNAdapter;
import com.example.appdatvemaybay.Country.InnitDataCountry;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class DiemDenActivity extends AppCompatActivity implements CountryVNAdapter.Listener {
    ImageButton imgBackHome;
    SearchView searchTPden;
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

        searchTPden.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fileList(newText);
                return false;
            }
        });
    }

    private void fileList(String newText) {
        String charString = newText.toString().trim();
        List<CountryVN> filterdList = new ArrayList<>();
        for (CountryVN countryVN : countryDiemden){
            if (countryVN.getNameTP().toLowerCase().contains(charString.toLowerCase())
                    || countryVN.getSanBay().contains(charString)){
                filterdList.add(countryVN);
            }
        }
        if (filterdList.isEmpty()){
            Toast.makeText(this, "Không tìm thấy dữ liệu", Toast.LENGTH_SHORT).show();
        }else {
            countryVNAdapter.setCountryFilter(filterdList);
        }
    }

    private  void innitUI(){
        imgBackHome=findViewById(R.id.imgBackHome4);
        searchTPden=findViewById(R.id.searchTP);
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
        intent.putExtra("DiemVe",tp);
        intent.putExtra("SanBayVe",sb);
        intent.putExtra("flag",0);
        setResult(RESULT_OK,intent);
        this.onBackPressed();
    }
}