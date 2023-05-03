package com.example.ecom_seller.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecom_seller.R;
import com.example.ecom_seller.activity.OrderDetailActivity;
import com.example.ecom_seller.activity.ProductDetailActivity;
import com.example.ecom_seller.adapter.OrderAdapter;
import com.example.ecom_seller.api.APIService;
import com.example.ecom_seller.model.Order;
import com.example.ecom_seller.model.OrderItem;
import com.example.ecom_seller.model.StatusOrder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderFragment extends Fragment {
    List<Order> orderListHuy;
    List<Order> orderListTuchoi;
    List<Order> orderListChoXacNhan;
    List<Order> orderListDangGiao;
    List<Order> orderListDaGiao;
    OrderAdapter orderAdapter;
    RecyclerView rcOrderHuy,rcOrderDagiao,rcOrderDanggiao,rcChoXacNhan,rcTuChoi;
    View view;
    TextView detailHuy,detailChoXacNhan,detailDangGiao,detailDaGiao,detailTuChoi;
    //Hàm trả về view
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);
        AnhXa();
        getOrder();
        detailTuChoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("Status", "TUCHOI");
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        detailHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("Status", "HUY");
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        detailDangGiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("Status", "DANGGIAO");
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        detailDaGiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("Status", "DAGIAO");
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        detailChoXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("Status", "CHOXACNHAN");
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;

    }

    private void AnhXa() {

        rcTuChoi = view.findViewById(R.id.rc_orderTuchoi);
        rcOrderHuy = view.findViewById(R.id.rc_orderHuy);
        rcOrderDagiao  =view.findViewById(R.id.rc_orderDaGiao);
        rcOrderDanggiao = view.findViewById(R.id.rc_orderDangGiao);
        rcChoXacNhan = view.findViewById(R.id.rc_orderChoXacNhan);
        detailTuChoi = view.findViewById(R.id.detailTuChoi);
        detailChoXacNhan = view.findViewById(R.id.detailChoXacNhan);
        detailDaGiao = view.findViewById(R.id.detailDaGiao);
        detailDangGiao = view.findViewById(R.id.detalDangGiao);
        detailHuy = view.findViewById(R.id.detailHuy);
    }

    private void getOrder() {

        APIService.apiService.getOrderList(StatusOrder.CHOXACNHAN).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if(response.isSuccessful()){
                    orderListChoXacNhan = response.body();
                    orderAdapter = new OrderAdapter(getContext(), orderListChoXacNhan);
                    rcChoXacNhan.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    rcChoXacNhan.setLayoutManager(layoutManager);
                    rcChoXacNhan.setAdapter(orderAdapter);
                    orderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.d("TAG", t.getMessage());
                Toast.makeText(getContext().getApplicationContext(), "Thất Bại", Toast.LENGTH_LONG).show();

            }
        });
        APIService.apiService.getOrderList(StatusOrder.HUY).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if(response.isSuccessful()){
                    orderListHuy = response.body();
                    orderAdapter = new OrderAdapter(getContext(), orderListHuy);
                    rcOrderHuy.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    rcOrderHuy.setLayoutManager(layoutManager);
                    rcOrderHuy.setAdapter(orderAdapter);
                    orderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.d("TAG", t.getMessage());
                Toast.makeText(getContext().getApplicationContext(), "Thất Bại", Toast.LENGTH_LONG).show();
            }
        });
        APIService.apiService.getOrderList(StatusOrder.DANGGIAO).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if(response.isSuccessful()){
                    orderListDangGiao = response.body();
                    orderAdapter = new OrderAdapter(getContext(), orderListDangGiao);
                    rcOrderDanggiao.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    rcOrderDanggiao.setLayoutManager(layoutManager);
                    rcOrderDanggiao.setAdapter(orderAdapter);
                    orderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.d("TAG", t.getMessage());
                Toast.makeText(getContext().getApplicationContext(), "Thất Bại", Toast.LENGTH_LONG).show();

            }
        });
        APIService.apiService.getOrderList(StatusOrder.DAGIAO).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if(response.isSuccessful()){
                    orderListDaGiao = response.body();
                    orderAdapter = new OrderAdapter(getContext(), orderListDaGiao);
                    rcOrderDagiao.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    rcOrderDagiao.setLayoutManager(layoutManager);
                    rcOrderDagiao.setAdapter(orderAdapter);
                    orderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.d("TAG", t.getMessage());
                Toast.makeText(getContext().getApplicationContext(), "Thất Bại", Toast.LENGTH_LONG).show();

            }
        });
        APIService.apiService.getOrderList(StatusOrder.TUCHOI).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if(response.isSuccessful()){
                    orderListTuchoi = response.body();
                    orderAdapter = new OrderAdapter(getContext(), orderListTuchoi);
                    rcTuChoi.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    rcTuChoi.setLayoutManager(layoutManager);
                    rcTuChoi.setAdapter(orderAdapter);
                    orderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.d("TAG", t.getMessage());
                Toast.makeText(getContext().getApplicationContext(), "Thất Bại", Toast.LENGTH_LONG).show();

            }
        });
    }
}
