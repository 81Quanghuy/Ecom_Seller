package com.example.ecom_seller.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ecom_seller.R;
import com.example.ecom_seller.adapter.OrderAdapter;
import com.example.ecom_seller.api.APIService;
import com.example.ecom_seller.model.Order;
import com.example.ecom_seller.model.StatusOrder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    StatusOrder mStatusOrder;
    SwipeRefreshLayout orderDetailFragment;
    OrderAdapter orderAdapter;
    RecyclerView rcOrder;
    LinearLayout layoutBtn;
    List<Order> listOrder;
    View view;
    Button btnTuChoiOrder,btnChapNhanOrder;
    public OrderDetailFragment(StatusOrder statusOrder) {
        // Required empty public constructor
        mStatusOrder = statusOrder;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_order_detail, container, false );
        AnhXa();
        return view;

    }
    private void AnhXa() {
        rcOrder = view.findViewById(R.id.rc_OrderList1);
        layoutBtn = view.findViewById(R.id.LayoutBtn1);
        btnTuChoiOrder = view.findViewById(R.id.btnTuChoiOrder1);
        btnChapNhanOrder = view.findViewById(R.id.btnChapNhanOrder1);
        orderDetailFragment = view.findViewById(R.id.orderDetailFragment);
        orderDetailFragment.setOnRefreshListener(this);
            if(mStatusOrder == StatusOrder.CHOXACNHAN){
                layoutBtn.setVisibility(View.VISIBLE);
            }
            else {
                layoutBtn.setVisibility(View.INVISIBLE);
            }
            getOrders(mStatusOrder);
    }

    private void getOrders(StatusOrder mStatusOrder) {
        APIService.apiService.getOrderList(mStatusOrder).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if(response.isSuccessful()){
                    listOrder = response.body();
                    orderAdapter = new OrderAdapter(getContext(), listOrder);
                    rcOrder.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),1);
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
