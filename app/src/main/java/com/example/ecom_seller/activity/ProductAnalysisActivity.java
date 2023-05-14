package com.example.ecom_seller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.ecom_seller.R;
import com.example.ecom_seller.adapter.ProductAnalysisAdapter;
import com.example.ecom_seller.adapter.ProductListAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ProductAnalysisActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ProductAnalysisAdapter productAnalysisAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_analysis);
        tabLayout = findViewById(R.id.tab_productAnlysis);
        viewPager2 = findViewById(R.id.viewPager2_alProduct);
        productAnalysisAdapter = new ProductAnalysisAdapter(this);
        viewPager2.setAdapter(productAnalysisAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Theo ngày");
                    break;
                case 1:
                    tab.setText("Theo tháng");
                    break;
            }
        }).attach();
    }
}