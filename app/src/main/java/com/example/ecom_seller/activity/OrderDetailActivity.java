package com.example.ecom_seller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.ecom_seller.R;
import com.example.ecom_seller.adapter.OrderAdapter;
import com.example.ecom_seller.adapter.OrderItemAdapter;
import com.example.ecom_seller.api.APIService;
import com.example.ecom_seller.model.Order;
import com.example.ecom_seller.model.OrderItem;
import com.example.ecom_seller.model.StatusOrder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {

    String Status;
    OrderAdapter orderAdapter;
    RecyclerView rcOrder;
    LinearLayout layoutBtn;
    List<Order> listOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        AnhXa();
    }

    private void AnhXa() {
        rcOrder = findViewById(R.id.rc_OrderList);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Status = bundle.getString("Status");
        layoutBtn = findViewById(R.id.LayoutBtn);
        Log.e("Status",Status.toString());
        if(StatusOrder.valueOf(Status)== StatusOrder.CHOXACNHAN){
            layoutBtn.setVisibility(View.VISIBLE);
        }
        else {
            layoutBtn.setVisibility(View.INVISIBLE);
        }
        if(Status != null){
            getOrders(Status);
        }
    }

    private void getOrders(String Status) {
        APIService.apiService.getOrderList(StatusOrder.valueOf(Status)).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if(response.isSuccessful()){
                    listOrder = response.body();
                    orderAdapter = new OrderAdapter(OrderDetailActivity.this, listOrder);
                    rcOrder.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(OrderDetailActivity.this,1);
                    rcOrder.setLayoutManager(layoutManager);
                    rcOrder.setAdapter(orderAdapter);
                    orderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });

    }
}