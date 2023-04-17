package com.example.ecom_seller.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecom_seller.R;
import com.example.ecom_seller.activity.OrderItemActivity;
import com.example.ecom_seller.model.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder>{

    Context context;
    List<Order> array;
    public OrderAdapter(Context context, List<Order> array) {
        this.context = context;
        this.array =array;
    }
    @NonNull
    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order,null);
        OrderAdapter.MyViewHolder myViewHolder = new OrderAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.MyViewHolder holder, int position) {
        Order order = array.get(position);
        if(order != null){
            holder.id = order.getId();
        holder.address.setText(order.getAddress());
        holder.phone.setText(order.getPhone());
        holder.nameUser.setText(order.getUser().getFullName());
        }
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView address,phone,nameUser;
        private String id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            address = (TextView) itemView.findViewById(R.id.tvNameAddress);
            phone = (TextView) itemView.findViewById(R.id.tvNamPhone);
            nameUser =(TextView) itemView.findViewById(R.id.tvNameNameUser);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("OrderId", id);
                    Intent intent = new Intent(context, OrderItemActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }
}
