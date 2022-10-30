package com.example.appdatvemaybay.Country;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdatvemaybay.R;

import java.util.ArrayList;
import java.util.List;

public class CountryVNAdapter extends RecyclerView.Adapter<CountryVNAdapter.CountryVH> {
    List<CountryVN> countryVNList;
    Listener listener;
    List<CountryVN> countryFilter;
    public CountryVNAdapter(List<CountryVN> countryVNList, Listener listener) {
        this.countryVNList = countryVNList;
        this.listener = listener;
        this.countryFilter=countryVNList;
    }
    public void setCountryFilter(List<CountryVN> filterCountry){
        this.countryVNList = filterCountry;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CountryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mview = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_row,parent,false);
        return new CountryVH(mview);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryVH holder, int position) {
        CountryVN countryVN = countryVNList.get(position);
        holder.txTenTP.setText(countryVN.getNameTP());
        holder.txSanBay.setText(countryVN.getSanBay());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemListener(countryVN);
            }
        });
    }

    @Override
    public int getItemCount() {
        return countryVNList.size();
    }


    class CountryVH extends RecyclerView.ViewHolder{
        TextView txTenTP, txSanBay;

        public CountryVH(@NonNull View itemView) {
            super(itemView);
            txTenTP=itemView.findViewById(R.id.txTenTP);
            txSanBay=itemView.findViewById(R.id.txSanBay);
        }
    }
     public interface Listener{
        void onItemListener(CountryVN countryVN);
    }

}
