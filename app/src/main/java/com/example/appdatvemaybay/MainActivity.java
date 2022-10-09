package com.example.appdatvemaybay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;


import com.example.appdatvemaybay.fragment.HomeFragment;
import com.example.appdatvemaybay.fragment.NotifyFragment;
import com.example.appdatvemaybay.fragment.QuestFragment;
import com.example.appdatvemaybay.fragment.SettingFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;
    private List<Photo> mlistPhoto;
    private Timer mTimer; // auto click chuyển

    DrawerLayout mDrawerLayout;
    Toolbar toolbar;
    //Navigation View ánh xạ tới các menu nav
    public static final int FRAGMENT_HOME=1;
    public static final int FRAGMENT_NOTIFY=2;
    public static final int FRAGMENT_QUEST=3;
    public static final int FRAGMENT_SETTING=4;

    private int mCurrentFrament = FRAGMENT_HOME;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Set drawer layout
        mDrawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Navigation View
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Start app
        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);


        //Nhã Trương


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id==R.id.menu_toolbar){
            mDrawerLayout.openDrawer(GravityCompat.END);
        }

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id ==R.id.nav_home){
                replaceFragment(new HomeFragment());
        }else if (id==R.id.nav_notify){
            if (mCurrentFrament!=FRAGMENT_NOTIFY){
                replaceFragment(new NotifyFragment());
            }
        }else if (id==R.id.nav_quest){
            if (mCurrentFrament!= FRAGMENT_QUEST){
                replaceFragment(new QuestFragment());
            }
        }else if (id==R.id.nav_setting){
            if (mCurrentFrament!= FRAGMENT_SETTING){
                replaceFragment(new SettingFragment());
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.END);
        return true;
    }
    public void replaceFragment(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame,fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.END)){
            mDrawerLayout.closeDrawer(GravityCompat.END);
        }else {
            super.onBackPressed();
        }

    }
}
