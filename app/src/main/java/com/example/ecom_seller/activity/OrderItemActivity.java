package com.example.ecom_seller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ecom_seller.R;
import com.example.ecom_seller.adapter.OrderAdapter;
import com.example.ecom_seller.adapter.OrderItemAdapter;
import com.example.ecom_seller.adapter.UserAdapter;
import com.example.ecom_seller.api.APIService;
import com.example.ecom_seller.model.Order;
import com.example.ecom_seller.model.OrderItem;
import com.example.ecom_seller.model.StatusOrder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderItemActivity extends AppCompatActivity {

    String id, Status;
    OrderItemAdapter orderItemAdapter;
    RecyclerView rcOrderItem;
    List<OrderItem> listOrderItem;
    LinearLayout layoutBtn;
    Button btnTuChoiOrder,btnChapNhanOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_item);
        AnhXa();
        btnTuChoiOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TuChoiOrder();
            }
        });
        btnChapNhanOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChapNhanOrder();
            }
        });
    }

    private void ChapNhanOrder() {
        APIService.apiService.changeStatuss(id,StatusOrder.DANGGIAO).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if(response.isSuccessful()){
                    Toast.makeText(OrderItemActivity.this, "Đã chấp nhận các đơn hàng trên", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(OrderDetailActivity.this, MainActivity.class);
//                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Toast.makeText(OrderItemActivity.this, "Có lỗi ", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void TuChoiOrder() {
        APIService.apiService.changeStatuss(id,StatusOrder.TUCHOI).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if(response.isSuccessful()){
                    Toast.makeText(OrderItemActivity.this, "Đã từ chối các đơn hàng trên", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(OrderDetailActivity.this, MainActivity.class);
//                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Toast.makeText(OrderItemActivity.this, "Có lỗi ", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void AnhXa() {
        rcOrderItem = findViewById(R.id.rc_OrderItemList);
        layoutBtn = findViewById(R.id.LayoutBtn);
        btnTuChoiOrder = findViewById(R.id.btnTuChoiOrderitem);
        btnChapNhanOrder = findViewById(R.id.btnChapNhanOrderitem);
        //lấy dữ liệu từ activity khác
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        //kiểm tra xem có lấy đc ko
        if (bundle != null) {
            id = bundle.getString("OrderId");
            Status = bundle.getString("Status");
            Log.d("TAG", "AnhXa: "+Status);
            Log.d("TAG", "ID: "+id);
            if(StatusOrder.valueOf(Status)== StatusOrder.CHOXACNHAN){
                layoutBtn.setVisibility(View.VISIBLE);
            }
            else {
                layoutBtn.setVisibility(View.INVISIBLE);
            }

                getOrderItem(id);
        }
    }

    private void getOrderItem(String id) {
        APIService.apiService.getOrderItemList(id).enqueue(new Callback<List<OrderItem>>() {
            @Override
            public void onResponse(Call<List<OrderItem>> call, Response<List<OrderItem>> response) {
                if(response.isSuccessful()){
                    Log.e("TAG","IDSUCCES"+ id);
                    listOrderItem = response.body();
                    orderItemAdapter = new OrderItemAdapter(OrderItemActivity.this, listOrderItem);
                    rcOrderItem.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(OrderItemActivity.this,1);
                    rcOrderItem.setLayoutManager(layoutManager);
                    rcOrderItem.setAdapter(orderItemAdapter);
                    orderItemAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<OrderItem>> call, Throwable t) {
                Log.e("TAG","IDFAIL"+ id);
            }
        });
    }
}