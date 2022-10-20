package com.example.appdatvemaybay.BottomSheerDialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.appdatvemaybay.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class DiaglogBottomSheetHK extends BottomSheetDialog {
    ImageView igThemNL, igGiamNL, igThemTE, igGiamTE, igThemEB, igGiamEB;
    TextView SLNL, SLTE, SLEB;
    ImageButton btnClose;
    View view;
    int slnl = 1,slte = 0,sleb =0;
    public DiaglogBottomSheetHK(@NonNull Context context) {
        super(context);
    }
    public void findView(){
        view = getLayoutInflater().inflate(R.layout.bottom_sheet_hanhkhach,null);
        innitUI();
        innitListenr();
        btnClose.setOnClickListener(v -> {
            this.dismiss();
        });
        setContentView(view);
    }

    private void innitListenr() {
        igThemNL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slnl = slnl+1;
                SLNL.setText(slnl);
            }
        });
        igGiamNL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slte <1 ){
                    return;
                }
                else {
                    slnl = slnl-1;
                    SLNL.setText(slnl);
                }
            }
        });
        igThemTE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slte = slte +1;
                SLTE.setText(slte);
            }
        });
        igGiamTE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slte < 0){
                    return;
                }
                else {
                    slte = slte-1;
                    SLTE.setText(slte);
                }
            }
        });
        igThemEB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sleb = sleb + 1;
                SLEB .setText(sleb);
            }
        });
        igGiamEB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sleb < 0){
                    return;
                }
                else {
                    sleb = sleb -1;
                    SLEB.setText(sleb);
                }
            }
        });

    }

    private void innitUI() {
        igThemNL = view.findViewById(R.id.igThemNL);
        igGiamNL=view.findViewById(R.id.igGiamNL);
        igThemTE = view.findViewById(R.id.igThemTE);
        igGiamTE=view.findViewById(R.id.igGiamTE);
        igThemEB = view.findViewById(R.id.igThemEB);
        igGiamEB=view.findViewById(R.id.igGiamEB);
        //Text View
        SLNL = view.findViewById(R.id.SLNL);
        SLTE = view.findViewById(R.id.SLTE);
        SLEB = view.findViewById(R.id.SLEB);
        //button
        btnClose=view.findViewById(R.id.closeBtn);
    }
}
