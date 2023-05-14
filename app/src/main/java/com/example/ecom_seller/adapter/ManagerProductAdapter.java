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

public class ManagerProductAdapter extends RecyclerView.Adapter<ManagerProductAdapter.MyViewHolder> {
    Context context;
    List<Product> array;

    private void setData(List<Product> products){
        array = products;
        notifyDataSetChanged();
    }

    public ManagerProductAdapter(Context context, List<Product> array){
        this.array = array;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_list, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product lastProduct = array.get(position);
        holder.tenSp.setText(lastProduct.getName());
        holder.id = lastProduct.getId();
        holder.nameCategory.setText(lastProduct.getCategory().getName());
        holder.quantity.setText(lastProduct.getQuantity().toString());
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
        public TextView tvGia, tvGiaChuaGiam, nameCategory, quantity;

        TextView tvRating;
        private String id;

        TextView count;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            images = (ImageView) itemView.findViewById(R.id.imgProductList);
            tenSp = (TextView) itemView.findViewById(R.id.tvProductName);
            nameCategory = itemView.findViewById(R.id.nameCateByProduct);
            tvGia = itemView.findViewById(R.id.tvPriceSaleProduct);
            quantity = itemView.findViewById(R.id.tvQuantityProduct);
            tvRating = itemView.findViewById(R.id.tvRatingProduct);
            count = itemView.findViewById(R.id.tvCountProduct);
            tvGiaChuaGiam = itemView.findViewById(R.id.tvPriceProductList);

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
    public void setListenterList(List<Product> iconModels){
        this.array = iconModels;
        notifyDataSetChanged();
    }


}