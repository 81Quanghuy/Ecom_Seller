package com.example.ecom_seller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.ecom_seller.R;
import com.example.ecom_seller.activity.AddProductActivity;
import com.example.ecom_seller.activity.ManagerCategoryActivity;
import com.example.ecom_seller.adapter.CategoryAdapter;
import com.example.ecom_seller.adapter.LastProductAdapter;
import com.example.ecom_seller.adapter.ViewPagerAdapter;
import com.example.ecom_seller.api.APIService;
import com.example.ecom_seller.model.Category;
import com.example.ecom_seller.model.Photo;
import com.example.ecom_seller.model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductFragment extends Fragment implements CategoryAdapter.OnResetFragmentListener {
    CategoryAdapter categoryAdapter;
    BottomNavigationView mBottom_nav;
    ViewPager mViewPager;
    List<Category> categoryList;
    List<Photo> mListPhoto;
    LastProductAdapter productAdapter;
    Button btnAddProductForCate,btnManagerCate;
    RecyclerView rcCate;
    List<Product> productList;
    RecyclerView rcProduct;
    //Hàm trả về view

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product, container, false);
        AnhXa();
        getCategories();
        getProducts();
        btnAddProductForCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddProductActivity.class);
                startActivity(intent);
            }
        });
        btnManagerCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ManagerCategoryActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void AnhXa() {
        btnAddProductForCate = view.findViewById(R.id.btnAddProductForCatet);
        btnManagerCate = view.findViewById(R.id.btnAddCategory);
        rcCate = view.findViewById(R.id.rc_category);
        rcProduct = view.findViewById(R.id.rc_category_product);
    }
    private void getProducts() {
        APIService.apiService.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    productList = response.body();
                    productAdapter = new LastProductAdapter(getContext(),productList);
                    rcProduct.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
                    rcProduct.setLayoutManager(layoutManager);
                    rcProduct.setAdapter(productAdapter);
                    productAdapter.notifyDataSetChanged();
                    //Toast.makeText(getContext(), "Đã Load Product", Toast.LENGTH_LONG).show();
                }
                else {
                    int statusCode = response.code();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                //Toast.makeText(getContext(), "Load Thất Bại", Toast.LENGTH_LONG).show();
                Log.e("TAG", t.getMessage());
            }
        });
    }
    private void getCategories() {
        APIService.apiService.getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    categoryList = response.body();
                    categoryAdapter = new CategoryAdapter(getContext(), categoryList,ProductFragment.this);
                    rcCate.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
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

    @Override
    public void onResetFragment() {

    }
}
