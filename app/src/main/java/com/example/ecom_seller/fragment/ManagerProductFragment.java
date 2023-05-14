package com.example.ecom_seller.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ecom_seller.R;
import com.example.ecom_seller.adapter.LastProductAdapter;
import com.example.ecom_seller.adapter.ManagerProductAdapter;
import com.example.ecom_seller.adapter.OrderAdapter;
import com.example.ecom_seller.api.APIService;
import com.example.ecom_seller.model.Order;
import com.example.ecom_seller.model.Product;
import com.example.ecom_seller.model.StatusOrder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerProductFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    String status;
    SwipeRefreshLayout orderDetailFragment;
    ManagerProductAdapter productAdapter;
    RecyclerView rcProduct;
    List<Product> productList = new ArrayList<>();
    Button btnDeleteOrder;
    View view;
    ProgressDialog mProgressDialog;
    public ManagerProductFragment(String status) {
        // Required empty public constructor
        this.status = status;
    }
    public ManagerProductFragment() {
        // Required empty public constructor
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_manager_product, container, false );
        AnhXa();
        LoadData();
        return view;

    }

    private void LoadData() {
        APIService.apiService.getProductByStatus(status).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productList = response.body();
                Collections.reverse(productList);
                productAdapter = new ManagerProductAdapter(getContext(), productList);
                rcProduct.setAdapter(productAdapter);
                rcProduct.setLayoutManager(new GridLayoutManager(getContext(), 1));
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    private void XacNhan(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Thông báo");
        alert.setMessage("Bạn có muốn xóa tất cả không ?");
        alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lệnh nút có
                DeleteOrder();
                //DataLocalManager.loggout();
            }
        });
        alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lệnh nút không
            }
        });
        alert.show();
    }

    private void DeleteOrder() {
        mProgressDialog.show();

    }

    private void AnhXa() {
        mProgressDialog = new ProgressDialog(getContext() );
        mProgressDialog.setMessage("Vui lòng đợi ...");
        rcProduct = view.findViewById(R.id.rc_ProductList);
        orderDetailFragment =view.findViewById(R.id.ProductFragment);
        orderDetailFragment.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        LoadData();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                orderDetailFragment.setRefreshing(false);
            }
        }, 2000);
    }
}