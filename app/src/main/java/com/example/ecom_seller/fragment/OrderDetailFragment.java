package com.example.ecom_seller.fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ecom_seller.R;
import com.example.ecom_seller.adapter.OrderAdapter;
import com.example.ecom_seller.api.APIService;
import com.example.ecom_seller.model.Order;
import com.example.ecom_seller.model.StatusOrder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    StatusOrder mStatusOrder;
    SwipeRefreshLayout orderDetailFragment;
    OrderAdapter orderAdapter;
    RecyclerView rcOrder;
    List<Order> listOrder = new ArrayList<>();
    Button btnDeleteOrder;
    View view;
    ProgressDialog mProgressDialog;
    public OrderDetailFragment(StatusOrder statusOrder) {
        // Required empty public constructor
        mStatusOrder = statusOrder;
    }
    public OrderDetailFragment() {
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_order_detail, container, false );
        AnhXa();
        btnDeleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XacNhan();

            }
        });
        return view;

    }

    private void XacNhan(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Thông báo");
        alert.setMessage("Xóa đơn hàng sẽ ảnh hưởng đến thống kê. Bạn muốn tiếp tục không ?");
        alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lệnh nút có
                DeleteOrder();
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
        Call<Void> call = APIService.apiService.deleteOrderByStatus(mStatusOrder);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                mProgressDialog.dismiss();
                if(response.isSuccessful()){
                    Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(getContext(), "Xóa  thất bại", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void AnhXa() {
        mProgressDialog = new ProgressDialog(getContext() );
        mProgressDialog.setMessage("Vui lòng đợi ...");

        rcOrder = view.findViewById(R.id.rc_OrderList1);
        btnDeleteOrder = view.findViewById(R.id.btnDeleteOrder);
        orderDetailFragment = view.findViewById(R.id.orderDetailFragment);
        orderDetailFragment.setOnRefreshListener(this);
        if(mStatusOrder == StatusOrder.DAGIAO|| mStatusOrder == StatusOrder.HUY || mStatusOrder == StatusOrder.TUCHOI){
            btnDeleteOrder.setVisibility(View.VISIBLE);
        }
        else {
            btnDeleteOrder.setVisibility(View.INVISIBLE);
        }
            getOrders(mStatusOrder);
    }

    private void getOrders(StatusOrder mStatusOrder) {
        APIService.apiService.getOrderList(mStatusOrder).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if(response.isSuccessful()){

                    listOrder = response.body();
                    Collections.reverse(listOrder);
                    orderAdapter = new OrderAdapter(getContext(), listOrder);
                    rcOrder.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),1);
                    rcOrder.setLayoutManager(layoutManager);
                    rcOrder.setAdapter(orderAdapter);
                    orderAdapter.notifyDataSetChanged();
                    if(mStatusOrder!= StatusOrder.CHOXACNHAN && mStatusOrder != StatusOrder.DANGGIAO){
                        if(listOrder.size()>0){
                            btnDeleteOrder.setVisibility(View.VISIBLE);
                        }
                        else {
                            btnDeleteOrder.setVisibility(View.INVISIBLE);
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onRefresh() {
        getOrders(mStatusOrder);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                orderDetailFragment.setRefreshing(false);
            }
        }, 2000);
    }
}
