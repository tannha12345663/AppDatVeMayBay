package com.example.appdatvemaybay.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appdatvemaybay.fragment.FragmentAccount.HistoryAirFragment;
import com.example.appdatvemaybay.fragment.FragmentAccount.HistoryFragment;
import com.example.appdatvemaybay.fragment.FragmentAccount.InfoAccFragment;

public class ViewPagerAccAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAccAdapter(@NonNull FragmentManager fm, int behavior) {

        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return new HistoryFragment();
            case 2:
                return new HistoryAirFragment();
            default:
                return new InfoAccFragment();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title ="Thông tin tài khoản";
            case 1:
                title ="Lịch sử giao dịch";
            case 2:
                title ="Lịch sử chuyến bay";
        }
        return title;
    }
}
