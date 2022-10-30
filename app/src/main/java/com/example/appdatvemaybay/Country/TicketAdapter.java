package com.example.appdatvemaybay.Country;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdatvemaybay.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketVH>{
    List<Ticket> mTicketlist;
    TicketAdapter.Listener listener;
    int Flag;

    public TicketAdapter(List<Ticket> mTicketlist, FragmentActivity listener, int flag) {
        this.mTicketlist = mTicketlist;
        this.listener = (Listener) listener;
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
        holder.tvNgayDi.setText("Ngày đi: "+ticket.getNgayDi());
        holder.tvSoLuong.setText("Số lượng : x"+ticket.getSoLuong());
        holder.tvMaVe.setText("Mã vé: "+ticket.getMaVe());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemListener(ticket);
            }
        });
        if (Flag == 0){
            holder.btnChon.setVisibility(View.VISIBLE);
            holder.tvSoLuong.setVisibility(View.INVISIBLE);
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
        TextView txMaVe,tvGioBay,tvGioDen,tvGiaVe,tvHang,tvStartEnd,tvNgayDi,tvSoLuong,tvMaVe;
        Button btnChon;
        public TicketVH(@NonNull View itemView) {
            super(itemView);
            tvStartEnd=itemView.findViewById(R.id.tvStartEnd);
            tvGioBay= itemView.findViewById(R.id.tvTimeKH);
            tvGioDen = itemView.findViewById(R.id.tvTimeVe);
            tvGiaVe = itemView.findViewById(R.id.tvGiaVe);
            tvHang= itemView.findViewById(R.id.tvHang);
            btnChon=itemView.findViewById(R.id.btnChon);
            tvNgayDi=itemView.findViewById(R.id.tvNgayDi);
            tvSoLuong = itemView.findViewById(R.id.tvSLve);
            tvMaVe=itemView.findViewById(R.id.tvMaVe);
        }
    }
    public interface Listener{
        void onItemListener(Ticket ticket);
    }
}
