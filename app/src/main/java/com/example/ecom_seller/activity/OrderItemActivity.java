package com.example.ecom_seller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.ecom_seller.R;
import com.example.ecom_seller.adapter.OrderAdapter;
import com.example.ecom_seller.adapter.OrderItemAdapter;
import com.example.ecom_seller.adapter.UserAdapter;
import com.example.ecom_seller.api.APIService;
import com.example.ecom_seller.model.OrderItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderItemActivity extends AppCompatActivity {

    String id;
    OrderItemAdapter orderItemAdapter;
    RecyclerView rcOrderItem;
    List<OrderItem> listOrderItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_item);
        AnhXa();
    }

    private void AnhXa() {
        rcOrderItem = findViewById(R.id.rc_OrderItemList);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getString("OrderId");
        Log.e("OrderId",id.toString());

        if(id != null){
            getOrderItem(id);
        }
    }

    private void getOrderItem(String id) {
        APIService.apiService.getOrderItemList(id).enqueue(new Callback<List<OrderItem>>() {
            @Override
            public void onResponse(Call<List<OrderItem>> call, Response<List<OrderItem>> response) {
                if(response.isSuccessful()){
                    listOrderItem = response.body();
                    orderItemAdapter = new OrderItemAdapter(OrderItemActivity.this, listOrderItem);
                    rcOrderItem.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(OrderItemActivity.this,2);
                    rcOrderItem.setLayoutManager(layoutManager);
                    rcOrderItem.setAdapter(orderItemAdapter);
                    orderItemAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<OrderItem>> call, Throwable t) {

            }
        });
    }
}