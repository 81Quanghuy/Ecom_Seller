package com.example.ecom_seller.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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
    ProgressDialog mProgressDialog;
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

        if (btnChapNhanOrder.getText().toString() =="Khách hàng đã nhận hàng"){
            mProgressDialog.show();
            APIService.apiService.changeStatuss(id,StatusOrder.DAGIAO).enqueue(new Callback<Order>() {
                @Override
                public void onResponse(Call<Order> call, Response<Order> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(OrderItemActivity.this, "Thành công", Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Order> call, Throwable t) {
                    mProgressDialog.dismiss();
                    Toast.makeText(OrderItemActivity.this, "Có lỗi ", Toast.LENGTH_LONG).show();
                }
            });
        } else if (btnChapNhanOrder.getText().toString() =="Xóa đơn hàng") {
            showDeleteConfirmationDialog();

        } else{
            mProgressDialog.show();
            APIService.apiService.changeStatuss(id,StatusOrder.DANGGIAO).enqueue(new Callback<Order>() {
                @Override
                public void onResponse(Call<Order> call, Response<Order> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(OrderItemActivity.this, "Đã chấp nhận các đơn hàng trên", Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Order> call, Throwable t) {
                    mProgressDialog.dismiss();
                    Toast.makeText(OrderItemActivity.this, "Có lỗi ", Toast.LENGTH_LONG).show();
                }
            });
        }

    }
    private void showDeleteConfirmationDialog() {
        // Tạo AlertDialog.Builder và thiết lập các thuộc tính
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setTitle(R.string.alter_title);
        builder.setMessage(R.string.alter_message_product);
        builder.setIcon(R.drawable.baseline_delete_24);
        builder.setPositiveButton(R.string.alter_yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User đã nhấn "Delete"
                DeleteOrder();
            }
        });
        builder.setNegativeButton(R.string.alter_no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User đã nhấn "Cancel"
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Tạo và hiển thị AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void DeleteOrder() {
        mProgressDialog.show();
        Call<Void> call = APIService.apiService.deleteOrder(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(OrderItemActivity.this, "Đã xóa đơn hàng", Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(OrderItemActivity.this, "Có lỗi ", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void TuChoiOrder() {
        mProgressDialog.show();
        if (btnTuChoiOrder.getText().toString() =="Khách hàng hủy đơn"){
            APIService.apiService.changeStatuss(id,StatusOrder.HUY).enqueue(new Callback<Order>() {
                @Override
                public void onResponse(Call<Order> call, Response<Order> response) {
                    if(response.isSuccessful()){
                        mProgressDialog.dismiss();
                        Toast.makeText(OrderItemActivity.this, "Thành công", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Order> call, Throwable t) {
                    mProgressDialog.dismiss();
                    Toast.makeText(OrderItemActivity.this, "Có lỗi ", Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            APIService.apiService.changeStatuss(id,StatusOrder.TUCHOI).enqueue(new Callback<Order>() {
                @Override
                public void onResponse(Call<Order> call, Response<Order> response) {
                    if(response.isSuccessful()){
                        mProgressDialog.dismiss();
                        Toast.makeText(OrderItemActivity.this, "Đã từ chối các đơn hàng trên", Toast.LENGTH_LONG).show();

                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Order> call, Throwable t) {
                    mProgressDialog.dismiss();
                    Toast.makeText(OrderItemActivity.this, "Có lỗi ", Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    private void AnhXa() {
        rcOrderItem = findViewById(R.id.rc_OrderItemList);
        layoutBtn = findViewById(R.id.LayoutBtn);
        btnTuChoiOrder = findViewById(R.id.btnTuChoiOrderitem);
        btnChapNhanOrder = findViewById(R.id.btnChapNhanOrderitem);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Vui lòng đợi ...");
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
                btnTuChoiOrder.setText("Từ chối đơn hàng");
                btnChapNhanOrder.setText("Chấp nhận đơn hàng");
            } else if (StatusOrder.valueOf(Status)== StatusOrder.DANGGIAO) {
                layoutBtn.setVisibility(View.VISIBLE);
                btnTuChoiOrder.setText("Khách hàng hủy đơn");
                btnChapNhanOrder.setText("Khách hàng đã nhận hàng");
            } else {
                layoutBtn.setVisibility(View.VISIBLE);
                btnTuChoiOrder.setVisibility(View.GONE);
                btnChapNhanOrder.setText("Xóa đơn hàng");

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