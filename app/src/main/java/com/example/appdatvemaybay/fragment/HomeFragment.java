package com.example.appdatvemaybay.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.appdatvemaybay.Photo;
import com.example.appdatvemaybay.PhotoAdapter;
import com.example.appdatvemaybay.R;

import java.util.ArrayList;
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

    View mview;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview= inflater.inflate(R.layout.fragment_home,container,false);
        viewPager= mview.findViewById(R.id.viewpager);
        circleIndicator=mview.findViewById(R.id.circle_indicator);
        mlistPhoto = getListPhoto();
        photoAdapter = new PhotoAdapter(mview.getContext(),mlistPhoto);
        viewPager.setAdapter(photoAdapter);
        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSlideImaged();
        return mview;
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
