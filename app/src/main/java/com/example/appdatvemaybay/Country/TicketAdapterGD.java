package com.example.appdatvemaybay.Country;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdatvemaybay.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TicketAdapterGD extends RecyclerView.Adapter<TicketAdapterGD.TicketVH>{
    List<Ticket> mTicketlist;
    int Flag;

    public TicketAdapterGD(List<Ticket> mTicketlist, Context context, int flag) {
        this.mTicketlist = mTicketlist;
        this.Flag=flag;
    }

    @NonNull
    @Override
    public TicketVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mview = LayoutInflater.from(parent.getContext()).inflate(R.layout.tiket_row,parent,false);
        return new TicketVH(mview);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketVH holder, int position) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        Ticket ticket = mTicketlist.get(position);
        Integer giave= Integer.parseInt(ticket.getGiaVe());
        holder.tvGiaVe.setText(currencyVN.format(giave));
        holder.tvGioBay.setText(ticket.getGioBay());
        holder.tvGioDen.setText(ticket.getGioDen());
        holder.tvHang.setText(ticket.getHang());
        holder.tvStartEnd.setText(ticket.getMaTPdi()+" - "+ticket.getMaTPve());
        if (Flag == 0){
            holder.btnChon.setVisibility(View.VISIBLE);
        }
        else
            holder.btnChon.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        if (mTicketlist!=null){
            return mTicketlist.size();
        }
        else return 0;
    }

    class TicketVH extends RecyclerView.ViewHolder{
        TextView txMaVe,tvGioBay,tvGioDen,tvGiaVe,tvHang,tvStartEnd;
        Button btnChon;
        public TicketVH(@NonNull View itemView) {
            super(itemView);
            tvStartEnd=itemView.findViewById(R.id.tvStartEnd);
            tvGioBay= itemView.findViewById(R.id.tvTimeKH);
            tvGioDen = itemView.findViewById(R.id.tvTimeVe);
            tvGiaVe = itemView.findViewById(R.id.tvGiaVe);
            tvHang= itemView.findViewById(R.id.tvHang);
            btnChon=itemView.findViewById(R.id.btnChon);
        }
    }

}
