package com.example.appdatvemaybay.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.appdatvemaybay.Account_User.SLhanhKhach;
import com.example.appdatvemaybay.BottomSheerDialog.DiaglogBottomSheetHK;
import com.example.appdatvemaybay.MainActivity;
import com.example.appdatvemaybay.Photo;
import com.example.appdatvemaybay.PhotoAdapter;
import com.example.appdatvemaybay.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;
    private List<Photo> mlistPhoto;
    private Timer mTimer; // auto click chuyển
    //Thanh chức năng đặt vé máy bay
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int mYear, mMonth, mDay, mYear1, mMonth1, mDay1;
    TextInputEditText etChonNgayDi, etChonNgayVe;
    Button btnTimChuyenBay;
    public TextInputEditText etNhapSoLuongHK;
    String minput;

    public void sendInput(String input){
        minput = input;
        setInputtoTextView(input);
    }
    View mview;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview= inflater.inflate(R.layout.fragment_home,container,false);
        innitUI();
        viewPager= mview.findViewById(R.id.viewpager);
        circleIndicator=mview.findViewById(R.id.circle_indicator);
        mlistPhoto = getListPhoto();
        photoAdapter = new PhotoAdapter(mview.getContext(),mlistPhoto);
        viewPager.setAdapter(photoAdapter);
        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSlideImaged();
        innitListen();
        return mview;
    }

    private void innitListen() {
        etChonNgayDi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    calendar = Calendar.getInstance ();
                    mYear = calendar.get ( Calendar.YEAR );
                    mMonth = calendar.get ( Calendar.MONTH );
                    mDay = calendar.get ( Calendar.DAY_OF_MONTH );

                    //show dialog
                    datePickerDialog = new DatePickerDialog ( getActivity(), new DatePickerDialog.OnDateSetListener () {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            etChonNgayDi.setText ( dayOfMonth + "/" + String.format("%02d",month+1) + "/" + year );
                            mYear1 = year;
                            mMonth1 = month +1;
                            mDay1 = dayOfMonth;
                            calendar.set(mYear1,mMonth1,mDay1);
                        }
                    }, mYear, mMonth, mDay );
                    datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                    datePickerDialog.show ();
            }
        });
        etChonNgayVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(mYear1,mMonth1,mDay1);
                //show dialog
                datePickerDialog = new DatePickerDialog ( getActivity(), new DatePickerDialog.OnDateSetListener () {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        etChonNgayVe.setText ( dayOfMonth + "/" + String.format("%02d",month+1) + "/" + year );
                    }
                }, mYear1, mMonth1, mDay1 );
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                datePickerDialog.show ();

            }
        });
        btnTimChuyenBay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        etNhapSoLuongHK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOpenBottomSheetFragment();

            }
        });

    }

    private void clickOpenBottomSheetFragment() {
        DiaglogBottomSheetHK diaglogBottomSheetHK = new DiaglogBottomSheetHK(mview.getContext());
        diaglogBottomSheetHK.show(getActivity().getSupportFragmentManager(), diaglogBottomSheetHK.getTag());

    }

    public void   setInputtoTextView(String s){
        etNhapSoLuongHK.setText(s);
    }
    private void innitUI() {
        etChonNgayDi = mview.findViewById(R.id.etChonNgayDi);
        etChonNgayVe = mview.findViewById(R.id.etChonNgayVe);
        btnTimChuyenBay = mview.findViewById(R.id.btnTimChuyenBay);
        etNhapSoLuongHK = mview.findViewById(R.id.etNhapSoLuongHK);
    }


    private List<Photo>getListPhoto(){
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.img01));
        list.add(new Photo(R.drawable.img02));
        list.add(new Photo(R.drawable.img03));
        list.add(new Photo(R.drawable.img04));
        list.add(new Photo(R.drawable.img05));
        return list;
    }
    //Bộ đếm tự động chuyển slide image
    private void autoSlideImaged(){
        if (mlistPhoto==null || mlistPhoto.isEmpty()|| viewPager==null){
            return;
        }
        //Khởi tạo timer
        if (mTimer==null){
            mTimer=new Timer();

        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int totalItem = mlistPhoto.size()-1;
                        if (currentItem < totalItem){
                            currentItem ++;
                            viewPager.setCurrentItem(currentItem);
                        }
                        else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });

            }
        },500,3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer !=null){
            mTimer.cancel();
            mTimer=null;
        }
    }
}
