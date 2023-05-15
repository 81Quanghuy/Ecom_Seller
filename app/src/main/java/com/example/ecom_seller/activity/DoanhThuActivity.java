//package com.example.ecom_seller.activity;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.viewpager2.widget.ViewPager2;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//
//import com.example.ecom_seller.R;
//import com.example.ecom_seller.adapter.DoanhThuAdapter;
//import com.example.ecom_seller.adapter.OrderAnalysisAdapter;
//import com.google.android.material.tabs.TabLayout;
//import com.google.android.material.tabs.TabLayoutMediator;
//
//public class DoanhThuActivity extends AppCompatActivity {
//
//    TabLayout tabLayout;
//    ViewPager2 viewPager2;
//    DoanhThuAdapter doanhThuAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_doanh_thu);
//        tabLayout = findViewById(R.id.tab_DT);
//        viewPager2 = findViewById(R.id.viewPager2_DT);
//        doanhThuAdapter = new DoanhThuAdapter(this);
//        viewPager2.setAdapter(doanhThuAdapter);
//        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
//            switch (position) {
//                case 0:
//                    tab.setText("Theo ngày");
//                    break;
//                case 1:
//                    tab.setText("Theo tháng");
//                    break;
//                case 2:
//                    tab.setText("Theo năm");
//                    break;
//            }
//        }).attach();
//    }
//}