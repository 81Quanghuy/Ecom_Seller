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
import com.example.ecom_seller.model.Photo;
import com.example.ecom_seller.model.Product;

import java.util.List;

public class LastProductAdapter extends RecyclerView.Adapter<LastProductAdapter.MyViewHolder> {
    Context context;
    List<Product> array;

    private void setData(List<Product> products){
        array = products;
        notifyDataSetChanged();
    }

    public LastProductAdapter(Context context, List<Product> array){
        this.array = array;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product lastProduct = array.get(position);
        holder.tenSp.setText(lastProduct.getName());
        holder.id = lastProduct.getId();
        holder.tvRating.setText(lastProduct.getRating().toString());
        holder.count.setText(lastProduct.getSold().toString());
        holder.tvGiaChuaGiam.setText(String.format( "%,.0f",lastProduct.getPrice())+ "đ");
        holder.tvGiaChuaGiam.setPaintFlags(holder.tvGiaChuaGiam.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        Photo img = lastProduct.getListPhoto().get(0);
        Glide.with(context)
                .load(img.getResources())
                .into(holder.images);
        holder.tvGia.setText(String.format( "%,.0f",lastProduct.getPromotionaprice())+ "đ");
    }

    @Override
    public int getItemCount() {
        return array == null ? 0: array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView images;
        public TextView tenSp;
        public TextView tvGia, tvGiaChuaGiam;

        TextView tvRating;
        private String id;

        TextView count;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            images = (ImageView) itemView.findViewById(R.id.imgProduct);
            tenSp = (TextView) itemView.findViewById(R.id.tvTenSp);
            tvGia = itemView.findViewById(R.id.tvGia);
            tvRating = itemView.findViewById(R.id.tvSao);
            count = itemView.findViewById(R.id.tvLuotMua);
            tvGiaChuaGiam = itemView.findViewById(R.id.tvGiaChuaGiam);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("ProductId", id);
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity (intent);
                }
            });

        }
    }



}