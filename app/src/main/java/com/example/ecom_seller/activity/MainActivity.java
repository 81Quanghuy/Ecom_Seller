package com.example.ecom_seller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ecom_seller.R;
import com.example.ecom_seller.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    RecyclerView rcvData;
    SwipeRefreshLayout swipeRefreshLayout; //refresh lại trang


    //Nhấn 2 lần để thoát
    private long backPressTime;
    Toast mToast;
    BottomNavigationView mBottom_nav;
    ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        setUpViewPager();
        setUpNavigationView();
    }
    private void setUpNavigationView() {
        mBottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.action_live:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.action_notification:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.action_account:
                        mViewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });
    }
    private void AnhXa() {
        mBottom_nav = findViewById(R.id.bottom_nav);
        mViewPager = findViewById(R.id.viewPager);

    }
    private void setUpViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(viewPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //sự kiện chuyển page khi xử lý page
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0 :
                        mBottom_nav.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;
                    case 1 :
                        mBottom_nav.getMenu().findItem(R.id.action_live).setChecked(true);
                        break;
                    case 2 :
                        mBottom_nav.getMenu().findItem(R.id.action_notification).setChecked(true);
                        break;
                    case 3 :
                        mBottom_nav.getMenu().findItem(R.id.action_account).setChecked(true);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        if(backPressTime + 2000 > System.currentTimeMillis()){
            mToast.cancel();
            super.onBackPressed();
            return;
        }else {
            mToast = Toast.makeText(MainActivity.this, "Nhấn lần nữa để thoát", Toast.LENGTH_LONG);
            mToast.show();
        }
        backPressTime = System.currentTimeMillis();
    }
}