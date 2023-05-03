package com.example.ecom_seller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ecom_seller.R;
import com.example.ecom_seller.adapter.CategoryAdapter;
import com.example.ecom_seller.adapter.CategoryManagerAdapter;
import com.example.ecom_seller.api.APIService;
import com.example.ecom_seller.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerCategoryActivity extends AppCompatActivity {
    CategoryManagerAdapter categoryAdapter;

    Button btn_add;
    List<Category> categoryList;
    RecyclerView rcCate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_category);
        AnhXa();
        getCategories();
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getCategories() {
        APIService.apiService.getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    categoryList = response.body();
                    categoryAdapter = new CategoryManagerAdapter(ManagerCategoryActivity.this, categoryList);
                    rcCate.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ManagerCategoryActivity.this,2);
                    rcCate.setLayoutManager(layoutManager);
                    rcCate.setAdapter(categoryAdapter);
                    categoryAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
    }

    private void AnhXa() {
        btn_add= findViewById(R.id.btnAddCate);
        rcCate = findViewById(R.id.rc_category);
    }
}