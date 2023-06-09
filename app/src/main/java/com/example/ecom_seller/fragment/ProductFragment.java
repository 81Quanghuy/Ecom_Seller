package com.example.ecom_seller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecom_seller.R;
import com.example.ecom_seller.activity.AddProductActivity;
import com.example.ecom_seller.activity.ManagerCategoryActivity;
import com.example.ecom_seller.adapter.CategoryAdapter;
import com.example.ecom_seller.adapter.LastProductAdapter;
import com.example.ecom_seller.api.APIService;
import com.example.ecom_seller.model.Category;
import com.example.ecom_seller.model.Product;
import com.example.ecom_seller.util.LinePagerIndicatorDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductFragment extends Fragment {
    CategoryAdapter categoryAdapter;
    List<Category> categoryList;
    LastProductAdapter productAdapter;
    Button btnAddProductForCate,btnManagerCate;
    RecyclerView rcCate;
    List<Product> productList;
    RecyclerView rcProduct;
    //Hàm trả về view
    androidx.appcompat.widget.SearchView searchView;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product, container, false);
        AnhXa();
        GetCateNew();
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

    private void getProductsByCate(List<Product> productList) {
        productAdapter = new LastProductAdapter(getContext(),productList);
        rcProduct.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        rcProduct.setLayoutManager(layoutManager);
        rcProduct.setAdapter(productAdapter);
        rcProduct.addItemDecoration(new LinePagerIndicatorDecoration());
        searchView =view.findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterListener(newText);
                return false;
            }

            private void filterListener(String newText) {
                List<Product> list = new ArrayList<>();
                for (Product product: productList){
                    if(product.getName().toLowerCase().contains(newText.toLowerCase())){
                        list.add(product);
                    }
                }
                if(list.isEmpty()){
                    Toast.makeText(getContext(), "Không có", Toast.LENGTH_SHORT).show();
                }
                else{
                    productAdapter.setListenterList(list);
                }
            }
        });
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
                    rcProduct.addItemDecoration(new LinePagerIndicatorDecoration());
                    searchView =view.findViewById(R.id.searchView);
                    searchView.clearFocus();
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            filterListener(newText);
                            return false;
                        }
                    });
                    //Toast.makeText(getContext(), "Đã Load Product", Toast.LENGTH_LONG).show();
                }
                else {
                    int statusCode = response.code();
                }
            }

            private void filterListener(String newText) {
                List<Product> list = new ArrayList<>();
                for (Product product: productList){
                    if(product.getName().toLowerCase().contains(newText.toLowerCase())){
                        list.add(product);
                    }
                }
                if(list.isEmpty()){
                    Toast.makeText(getContext(), "Không có", Toast.LENGTH_SHORT).show();
                }
                else{
                    productAdapter.setListenterList(list);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                //Toast.makeText(getContext(), "Load Thất Bại", Toast.LENGTH_LONG).show();
                Log.e("TAG", t.getMessage());
            }
        });
    }

    public void GetCateNew(){
        APIService.apiService.getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    categoryList = response.body();
                    categoryAdapter = new CategoryAdapter(getContext(), categoryList, (position, nameCate) -> APIService.apiService.ListProductByCate(nameCate).enqueue(new Callback<List<Product>>() {

                        @Override
                        public void onResponse(Call<List<Product>> call1, Response<List<Product>> response1) {
                            if(response1.isSuccessful()){
                                List<Product> productList = (List<Product>) response1.body();
                                getProductsByCate(productList);
                            }
                            else{
                                Toast.makeText(getContext(), "loi server" , Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Product>> call1, Throwable t) {
                            Log.e("TAG",t.getMessage());
                            Toast.makeText(getContext(), "Fail" , Toast.LENGTH_SHORT).show();

                        }
                    }));
                    rcCate.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    rcCate.setLayoutManager(layoutManager);
                    rcCate.setAdapter(categoryAdapter);
                    categoryAdapter.notifyDataSetChanged();

                }
                else{
                    Toast.makeText(getContext(), "Success NO ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
