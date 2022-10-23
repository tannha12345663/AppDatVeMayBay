package com.example.appdatvemaybay.Country;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdatvemaybay.R;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketVH>{
    List<Ticket> mTicketlist;
    TicketAdapter.Listener listener;

    public TicketAdapter(List<Ticket> mTicketlist, Listener listener) {
        this.mTicketlist = mTicketlist;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TicketVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mview = LayoutInflater.from(parent.getContext()).inflate(R.layout.tiket_row,parent,false);
        return new TicketVH(mview);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketVH holder, int position) {
        Ticket ticket = mTicketlist.get(position);
        holder.tvGiaVe.setText(ticket.getGiaVe());
        holder.tvGioBay.setText(ticket.getGioBay());
        holder.tvGioDen.setText(ticket.getGioDen());
        holder.tvHang.setText(ticket.getHang());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemListener(ticket);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mTicketlist!=null){
            return mTicketlist.size();
        }
        else return 0;
    }

    class TicketVH extends RecyclerView.ViewHolder{
        TextView txMaVe,tvGioBay,tvGioDen,tvGiaVe,tvHang;

        public TicketVH(@NonNull View itemView) {
            super(itemView);

            tvGioBay= itemView.findViewById(R.id.tvTimeKH);
            tvGioDen = itemView.findViewById(R.id.tvTimeVe);
            tvGiaVe = itemView.findViewById(R.id.tvGiaVe);
            tvHang= itemView.findViewById(R.id.tvHang);
        }
    }
    public interface Listener{
        void onItemListener(Ticket ticket);
    }
}
