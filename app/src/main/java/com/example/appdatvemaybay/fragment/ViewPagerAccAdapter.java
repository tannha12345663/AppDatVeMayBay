package com.example.appdatvemaybay.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appdatvemaybay.fragment.FragmentAccount.HistoryAirFragment;
import com.example.appdatvemaybay.fragment.FragmentAccount.HistoryFragment;
import com.example.appdatvemaybay.fragment.FragmentAccount.InfoAccFragment;

public class ViewPagerAccAdapter extends FragmentStateAdapter {


    public ViewPagerAccAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
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
    public int getItemCount() {
        return 3;
    }


}
