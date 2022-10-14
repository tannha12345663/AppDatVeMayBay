package com.example.appdatvemaybay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.appdatvemaybay.fragment.HomeFragment;
import com.example.appdatvemaybay.fragment.NotifyFragment;
import com.example.appdatvemaybay.fragment.QuestFragment;
import com.example.appdatvemaybay.fragment.SettingFragment;
import com.example.appdatvemaybay.fragment.ViewPagerAccAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class YourAccountFragment extends Fragment{
    TabLayout mtabLayout;
    ViewPager2 mViewPager;
    ViewPagerAccAdapter mViewPagerAccAdapter;
    View mview;
    Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview= inflater.inflate(R.layout.fragment_your_account,container,false);
        //Back toolbar

        //ViewPager 2
        mtabLayout =mview.findViewById(R.id.tab_layout);
        mViewPager =mview.findViewById(R.id.view_pager);
        // Set up Phương thức Control View Pager 2
        mViewPagerAccAdapter = new ViewPagerAccAdapter(this);
        mViewPager.setAdapter(mViewPagerAccAdapter);

        new TabLayoutMediator(mtabLayout, mViewPager, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setIcon(R.drawable.ic_baseline_account_circle_24);
                    tab.setText("THÔNG TIN TÀI KHOẢN");
                    break;
                case 1:
                    tab.setIcon(R.drawable.ic_baseline_local_airport_24);
                    tab.setText("LỊCH SỬ GIAO DỊCH");
                    break;
                case 2:
                    tab.setIcon(R.drawable.ic_baseline_restore_24);
                    tab.setText("LỊCH SỬ CHUYẾN BAY");
                    break;
            }
        }).attach();
        return mview;
    }
}
