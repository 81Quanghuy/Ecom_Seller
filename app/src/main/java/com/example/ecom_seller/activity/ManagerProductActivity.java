package com.example.ecom_seller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;

import com.example.ecom_seller.R;
import com.example.ecom_seller.adapter.ProductListAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ManagerProductActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    Button btnAddProduct;
    ProductListAdapter productListAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_product);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.viewPager2_status_login);
        btnAddProduct.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(ManagerProductActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });
        productListAdapter = new ProductListAdapter(this);
        viewPager2.setAdapter(productListAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Đang kinh doanh");
                    break;
                case 1:
                    tab.setText("Tạm ngưng");
                    break;
                case 2:
                    tab.setText("Hết hàng");
                    break;
            }
        }).attach();
    }
}