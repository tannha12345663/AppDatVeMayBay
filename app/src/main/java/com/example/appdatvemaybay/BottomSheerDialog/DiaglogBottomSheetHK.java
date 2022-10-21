package com.example.appdatvemaybay.BottomSheerDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appdatvemaybay.Account_User.SLhanhKhach;
import com.example.appdatvemaybay.R;
import com.example.appdatvemaybay.fragment.HomeFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class DiaglogBottomSheetHK extends BottomSheetDialogFragment {
    private SLhanhKhach sLhanhKhach;
    Context context;

    ImageView igThemNL, igGiamNL, igThemTE, igGiamTE, igThemEB, igGiamEB;
    TextView SLNL, SLTE, SLEB;
    ImageButton btnClose;
    View view;
    Button btnContinue;
    int slnl = 1,slte = 0,sleb =0;

    public OnInputListener mOnInputListener;
    public DiaglogBottomSheetHK( Context context) {
        this.context = context;
    }

    private ISendDataListener mISendDataListener;
    public interface ISendDataListener{
        void sendData(String SLNL,String SLTE, String SLEB);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //Ép kiểu
        mISendDataListener = (ISendDataListener) getActivity();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog= (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_hanhkhach,null);
        bottomSheetDialog.setContentView(view);
        innitUI();
        innitListenr();
        btnClose.setOnClickListener(v -> {
            this.dismiss();
        });
        return bottomSheetDialog;
    }

    private void innitListenr() {
        igThemNL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slnl = slnl+1;
                SLNL.setText(""+slnl);
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
                    SLNL.setText(""+slnl);
                }
            }
        });
        igThemTE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slte = slte +1;
                SLTE.setText(""+slte);
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
                    SLTE.setText(""+slte);
                }
            }
        });
        igThemEB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sleb = sleb + 1;
                SLEB .setText(""+sleb);
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
                    SLEB.setText(""+sleb);
                }
            }
        });
        btnContinue.setOnClickListener(v -> {

            sendDatatoFramentHome();
            this.dismiss();
        });

    }


    private void sendDatatoFramentHome() {
        String nl = SLNL.getText().toString().trim();
        String te = SLTE.getText().toString().trim();
        String eb = SLEB.getText().toString().trim();
        mISendDataListener.sendData(nl,te,eb);
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
        btnContinue=view.findViewById(R.id.btnContinue);
    }
    public interface OnInputListener {
        void sendInput(String input);
    }
}
