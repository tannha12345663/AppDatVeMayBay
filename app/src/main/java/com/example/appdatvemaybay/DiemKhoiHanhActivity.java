package com.example.appdatvemaybay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import com.example.appdatvemaybay.fragment.HomeFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DiemKhoiHanhActivity extends AppCompatActivity implements CountryVNAdapter.Listener {
    ImageButton imgBackHome;
    RecyclerView recyclerView;
    List<CountryVN> countryVNS;
    CountryVNAdapter countryVNAdapter;
    SearchView searchViewTP;
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
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        countryVNS = new ArrayList<>();
        countryVNS= InnitDataCountry.ininitCountry();
        countryVNAdapter = new CountryVNAdapter(countryVNS,DiemKhoiHanhActivity.this);
        recyclerView.setAdapter(countryVNAdapter);

        searchViewTP.clearFocus();
        searchViewTP.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fileList(newText);
                return true;
            }
        });


    }

    private void fileList(String newText) {
        String charString = newText.toString().trim();
        List<CountryVN> filterdList = new ArrayList<>();
        for (CountryVN countryVN : countryVNS){
            if (countryVN.getNameTP().toLowerCase().contains(charString.toLowerCase())
            || countryVN.getSanBay().toLowerCase().contains(charString.toLowerCase())){
                filterdList.add(countryVN);
            }
        }
        if (filterdList.isEmpty()){
            Toast.makeText(this, "Không tìm thấy dữ liệu", Toast.LENGTH_SHORT).show();
            countryVNAdapter.setCountryFilter(filterdList);
        }else {
            countryVNAdapter.setCountryFilter(filterdList);
        }
    }


    private void innitUI() {
        imgBackHome=findViewById(R.id.imgBackHome);
        searchViewTP=findViewById(R.id.searchTP);
        recyclerView = findViewById(R.id.rcCountryKH);
    }

    @Override
    public void onItemListener(CountryVN countryVN) {
        String tp = countryVN.getNameTP();
        String sb = countryVN.getSanBay();
        Intent intent = new Intent(DiemKhoiHanhActivity.this, MainActivity.class);
        intent.putExtra("DiemKH",tp);
        intent.putExtra("SanBaydi",sb);
        intent.putExtra("flag",1);
        setResult(RESULT_OK,intent);
        this.onBackPressed();
    }
}