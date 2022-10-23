package com.example.appdatvemaybay.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.appdatvemaybay.BottomSheerDialog.DiaglogBottomSheetHK;
import com.example.appdatvemaybay.ChuyenDiCuaBanActivity;
import com.example.appdatvemaybay.DiemDenActivity;
import com.example.appdatvemaybay.DiemKhoiHanhActivity;
import com.example.appdatvemaybay.Photo;
import com.example.appdatvemaybay.PhotoAdapter;
import com.example.appdatvemaybay.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    TextInputEditText etChonNgayDi, etChonNgayVe,etChonDiemKH,etChonDiemDen;
    String SanbayDi,SanBayVe;
    Button btnTimChuyenBay;
    RadioGroup khuhoi_motchieu;
    public TextInputEditText etNhapSoLuongHK;
    //Khai báo Interface để truyền dữ liệu sang Fragment Bottom Sheet HK
    int SLNL, SLTE, SLEB;
    private static final int REQUEST_CODE_EXAMPLE = 0x9345;
    ActivityResultLauncher<Intent> mlauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == DiemKhoiHanhActivity.RESULT_OK) {
                        if (result.getData().getIntExtra("flag", 0) == 1) {
                            etChonDiemKH.setText(result.getData().getStringExtra("DiemKH") + " (" + result.getData().getStringExtra("SanBaydi") + ")");
                            SanbayDi = result.getData().getStringExtra("SanBaydi");

                        }
                        else if (result.getData().getIntExtra("flag", 0) == 0){
                            etChonDiemDen.setText(result.getData().getStringExtra("DiemVe") + " (" + result.getData().getStringExtra("SanBayVe") + ")");
                            SanBayVe=result.getData().getStringExtra("SanBayVe");
                        }

                    }
                }
            });
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
        calendar = Calendar.getInstance ();
        mYear = calendar.get ( Calendar.YEAR );
        mMonth = calendar.get ( Calendar.MONTH );
        mDay = calendar.get ( Calendar.DAY_OF_MONTH );

        khuhoi_motchieu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId ) {
                if (checkedId == R.id.rbKhuhoi){
                    etChonNgayDi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //show dialog
                            datePickerDialog = new DatePickerDialog ( getActivity(), new DatePickerDialog.OnDateSetListener () {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                    etChonNgayDi.setText ( dayOfMonth + "-" + String.format("%02d",month+1) + "-" + year );
                                    mYear1 = year;
                                    mMonth1 = month;
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

                            //show dialog
                            datePickerDialog = new DatePickerDialog ( getActivity(), new DatePickerDialog.OnDateSetListener () {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                    etChonNgayVe.setText ( dayOfMonth + "-" + String.format("%02d",month+1) + "-" + year );
                                }
                            }, mYear1, mMonth1, mDay1 );
                            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                            datePickerDialog.show ();

                        }
                    });

                } else if (checkedId == R.id.rbMotChieu){
                    etChonNgayVe.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    etChonNgayDi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //show dialog
                            datePickerDialog = new DatePickerDialog ( getActivity(), new DatePickerDialog.OnDateSetListener () {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                    etChonNgayDi.setText ( dayOfMonth + "-" + String.format("%02d",month+1) + "-" + year );
                                    mYear1 = year;
                                    mMonth1 = month ;
                                    mDay1 = dayOfMonth;
                                    calendar.set(mYear1,mMonth1-1,mDay1);
                                }
                            }, mYear, mMonth, mDay );
                            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                            datePickerDialog.show ();
                        }
                    });
                }
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
        etChonDiemKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(),DiemKhoiHanhActivity.class);
                mlauncher.launch(intent);

            }
        });
        etChonDiemDen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), DiemDenActivity.class);
                mlauncher.launch(intent);
            }
        });
        btnTimChuyenBay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Soluongnguoi = etNhapSoLuongHK.getText().toString().trim();
                String DiemKH = etChonDiemKH.getText().toString().trim();
                String DiemDen = etChonDiemDen.getText().toString().trim();
                Intent intent = new Intent(getActivity(), ChuyenDiCuaBanActivity.class);
                intent.putExtra("SoLuongNguoi",Soluongnguoi);
                intent.putExtra("DiemKH",DiemKH);
                intent.putExtra("DiemVe",DiemDen);
                intent.putExtra("NgayDi",etChonNgayDi.getText().toString().trim());
                intent.putExtra("NgayVe",etChonNgayVe.getText().toString().trim());
                intent.putExtra("MaTPdi",SanbayDi);
                intent.putExtra("MaTPve",SanBayVe);
                startActivity(intent);
            }
        });
    }



    public void RecieveFromFragmentBottmSheet(String SLNL1, String SLTE1, String SLEB1){
        etNhapSoLuongHK.setText(SLNL1 +" Người lớn, "+SLTE1+" trẻ em, "+ SLEB1 + " em bé.");
        SLNL = Integer.parseInt(SLNL1);
        SLTE = Integer.parseInt(SLTE1);
        SLEB =Integer.parseInt(SLEB1);
    }

    private void clickOpenBottomSheetFragment() {
        DiaglogBottomSheetHK diaglogBottomSheetHK = new DiaglogBottomSheetHK(mview.getContext());
        diaglogBottomSheetHK.show(getActivity().getSupportFragmentManager(), diaglogBottomSheetHK.getTag());

    }

    private void innitUI() {
        etChonNgayDi = mview.findViewById(R.id.etChonNgayDi);
        etChonNgayVe = mview.findViewById(R.id.etChonNgayVe);
        btnTimChuyenBay = mview.findViewById(R.id.btnTimChuyenBay);
        etNhapSoLuongHK = mview.findViewById(R.id.etNhapSoLuongHK);
        etChonDiemKH = mview.findViewById(R.id.etDiemKhoiHanh);
        etChonDiemDen = mview.findViewById(R.id.etDiemen);
        khuhoi_motchieu = mview.findViewById(R.id.rgPercentage);
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
