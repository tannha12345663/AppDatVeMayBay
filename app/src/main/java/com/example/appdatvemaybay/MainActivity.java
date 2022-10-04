package com.example.appdatvemaybay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;
    private List<Photo> mlistPhoto;
    private Timer mTimer; // auto click chuyển
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Nhã Trương
        viewPager= findViewById(R.id.viewpager);
        circleIndicator=findViewById(R.id.circle_indicator);
        mlistPhoto = getListPhoto();
        photoAdapter = new PhotoAdapter(this,mlistPhoto);
        viewPager.setAdapter(photoAdapter);
        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSlideImaged();


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
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer !=null){
            mTimer.cancel();
            mTimer=null;
        }
    }
}