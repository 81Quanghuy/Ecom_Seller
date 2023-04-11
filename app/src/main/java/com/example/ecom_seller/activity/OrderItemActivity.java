package com.example.ecom_seller.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderItemActivity extends AppCompatActivity {
    RecyclerView rcProduct;

//    LastProductAdapter productAdapter;
//
//    List<Product> productList;

    ImageView btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_category);
//        rcProduct = findViewById(R.id.rc_product);
//        btnBack = findViewById(R.id.btn_back_to_home);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
       // getProducts();
    }

//    private void getProducts() {
//        APIService.apiService.getProducts().enqueue(new Callback<List<Product>>() {
//            @Override
//            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
//                if(response.isSuccessful()){
//                    productList = response.body();
//                    productAdapter = new LastProductAdapter(CategoryActivity.this,productList);
//                    rcProduct.setHasFixedSize(true);
//                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(CategoryActivity.this,3);
//                    rcProduct.setLayoutManager(layoutManager);
//                    rcProduct.setAdapter(productAdapter);
//                    productAdapter.notifyDataSetChanged();
//                }
//                else {
//                    int statusCode = response.code();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Product>> call, Throwable t) {
//
//            }
//        });
//    }
}
