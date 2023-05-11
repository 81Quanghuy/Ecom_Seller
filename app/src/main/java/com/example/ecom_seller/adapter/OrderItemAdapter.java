package com.example.ecom_seller.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecom_seller.R;
import com.example.ecom_seller.activity.ProductDetailActivity;
import com.example.ecom_seller.model.OrderItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.MyViewHolder>{

    Context context;
    List<OrderItem> array;

    public OrderItemAdapter(Context context, List<OrderItem> array) {
        this.context = context;
        this.array =array;
    }
    @NonNull
    @Override
    public OrderItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_product,null);
        MyViewHolder myViewHolder = new OrderItemAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemAdapter.MyViewHolder holder, int position) {
        OrderItem orderitem = array.get(position);
        holder.id= orderitem.getProduct().getId();
        holder.tenSp.setText(orderitem.getProduct().getName());
        holder.SalePrice.setText(String.format( "%,.0f",orderitem.getProduct().getPromotionaprice())+ "đ");

        holder.count.setText(orderitem.getCount().toString());
        holder.price.setText(orderitem.getProduct().getPrice().toString());
        holder.price.setPaintFlags(holder.SalePrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        Glide.with(context).load(orderitem.getProduct().getListPhoto().get(0).getResources()).into(holder.images);
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView images;
        public TextView tenSp,count, price, SalePrice;

        public  String id;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            images = (ImageView) itemView.findViewById(R.id.imgOrder);
            tenSp = (TextView) itemView.findViewById(R.id.tvNameProduct);
            count = (TextView) itemView.findViewById(R.id.tvCountOrder);
            price = (TextView) itemView.findViewById(R.id.PriceProductNoSale);
            SalePrice = (TextView) itemView.findViewById(R.id.PriceProductSale);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putString("ProductId", id);
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }
}
