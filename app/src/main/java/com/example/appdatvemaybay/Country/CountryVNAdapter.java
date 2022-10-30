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

public class CountryVNAdapter extends RecyclerView.Adapter<CountryVNAdapter.CountryVH> implements Filterable {
    List<CountryVN> countryVNList;
    Listener listener;
    List<CountryVN> countryFilter;
    public CountryVNAdapter(List<CountryVN> countryVNList, Listener listener) {
        this.countryVNList = countryVNList;
        this.listener = listener;
        this.countryFilter=countryVNList;
    }
    @NonNull
    @Override
    public CountryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mview = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_row,parent,false);
        return new CountryVH(mview);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryVH holder, int position) {
        CountryVN countryVN = countryFilter.get(position);
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
        return countryFilter.size();
    }

    @Override
    public Filter getFilter() {
        return new CountryFilter();
    }
    private class CountryFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String charString = constraint.toString();
            if (charString.isEmpty()){
                countryFilter=countryVNList;
            }
            else {
                List<CountryVN> filterList = new ArrayList<>();
                for (CountryVN row : countryVNList){
                    if (row.getNameTP().toLowerCase().contains(charString.toLowerCase())
                            || row.getSanBay().toLowerCase().contains(charString.toLowerCase())){
                        filterList.add(row);
                    }
                }
                countryFilter = filterList;
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values=countryFilter;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            countryFilter = (List<CountryVN>) results.values;
            notifyDataSetChanged();
        }
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
