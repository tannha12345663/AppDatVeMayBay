package com.example.appdatvemaybay;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.appdatvemaybay.fragment.FragmentAccount.InfoAccFragment;
import com.example.appdatvemaybay.fragment.HomeFragment;
import com.example.appdatvemaybay.fragment.NotifyFragment;
import com.example.appdatvemaybay.fragment.QuestFragment;
import com.example.appdatvemaybay.fragment.SettingFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.List;
import java.util.Timer;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    //Profile
    public static final int MY_REQUEST_CODE = 10;
    //
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;
    private List<Photo> mlistPhoto;
    private Timer mTimer; // auto click chuyển
    //Phan Header Navigation
    ImageView logout;
    CircleImageView circleImageView_menu_header,img_acc;
    TextView tenAcc, ten_Acc;
    //Firebase USER
    FirebaseUser user;
    //InfoAccFragment
    InfoAccFragment infoAccFragment = new InfoAccFragment();
    // Phan khac
    Intent intent;
    DrawerLayout mDrawerLayout;
    Toolbar toolbar;

    //Activity
//    final private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult
//            (new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//        @Override
//        public void onActivityResult(ActivityResult result) {
//            if (result.getResultCode()==RESULT_OK){
//                Intent intent =result.getData();
//                if (intent == null){
//                    return;
//                }
//                Uri uri = intent.getData();
//                infoAccFragment.setUri(uri);
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
//                    infoAccFragment.setBitmapImageView(bitmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
//    });
    //Navigation View ánh xạ tới các menu nav
    public static final int FRAGMENT_HOME=1;
    public static final int FRAGMENT_NOTIFY=2;
    public static final int FRAGMENT_QUEST=3;
    public static final int FRAGMENT_SETTING=4;
    public static final int FRAGMENT_YOURACCOUNT=5;
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
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
        //Start app
        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        //Nhã Trương
        innitUI();
        showUserInformation();
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
    public void innitUI(){
        user=FirebaseAuth.getInstance().getCurrentUser();
        View header_view = navigationView.getHeaderView(0);
        circleImageView_menu_header = header_view.findViewById(R.id.imgAcc);
        circleImageView_menu_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user == null){
                    intent=new Intent(MainActivity.this,SigninActivity.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawer(GravityCompat.END);
                }
                else {
                    replaceFragment(new YourAccountFragment());
                    mDrawerLayout.closeDrawer(GravityCompat.END);
                }

            }
        });
        tenAcc= header_view.findViewById(R.id.tenAcc);
        logout=header_view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                intent=new Intent(MainActivity.this,SigninActivity.class);
                startActivity(intent);
                finish();
            }
        });
        tenAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity();
            }
        });
        ten_Acc=findViewById(R.id.ten_Acc);
        ten_Acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity();
            }
        });
        img_acc = findViewById(R.id.img_acc);
        img_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null){
                    intent=new Intent(MainActivity.this,SigninActivity.class);
                    startActivity(intent);
                }
                else {
                    replaceFragment(new YourAccountFragment());
                }
            }
        });

    }
    //Show user information
    public void showUserInformation(){
        user=FirebaseAuth.getInstance().getCurrentUser();
        if (user==null){
            return;
        }
        else {
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            tenAcc.setText(name);
            ten_Acc.setText(name);
            //Set ảnh nếu lỗi sẽ default hình có sẵn
            Glide.with(this).load(photoUrl).error(R.drawable.ic_baseline_account_circle_24).into(circleImageView_menu_header);
            Glide.with(this).load(photoUrl).error(R.drawable.ic_baseline_account_circle_24).into(img_acc);

        }
    }
    //Chuyển tiếp Activity
    private void nextActivity() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        //Check login
        if (user==null){
            //Chưa login
            intent = new Intent(MainActivity.this,SigninActivity.class);
            startActivity(intent);
        }
        else {
            //Chuyển sang trang thông tin tài khoản
//            intent = new Intent(this,MainActivity.class);
//            startActivity(intent);
            mDrawerLayout.openDrawer(GravityCompat.END);
        }
    }


    //Phần xử lý cấp quyền truy cập file
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == MY_REQUEST_CODE){
//            if (grantResults.length >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                //Cho phép mở nơi chứa ảnh
//                openGallery();
//            } else {
//                Toast.makeText(this, "Không thể mở ảnh . Vui lòng cấp quyền !", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

}
