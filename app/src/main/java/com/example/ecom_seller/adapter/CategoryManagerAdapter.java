package com.example.ecom_seller.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecom_seller.R;
import com.example.ecom_seller.activity.CategoryActivity;
import com.example.ecom_seller.activity.OrderItemActivity;
import com.example.ecom_seller.model.Category;

import java.util.List;

public class CategoryManagerAdapter extends RecyclerView.Adapter<CategoryManagerAdapter.MyViewHolder> {

    Context context;
    List<Category> array;


    public CategoryManagerAdapter(Context context, List<Category> array) {
        this.context = context;
        this.array =array;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_manager,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Category category = array.get(position);
        if(category!= null){
            holder.id = category.getId();
            holder.tenSp.setText(category.getName());
            Glide.with(context).load(category.getImage()).into(holder.images);
        }

    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView images;
        public TextView tenSp;

        String id;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            images = (ImageView) itemView.findViewById(R.id.image_product);
            tenSp = (TextView) itemView.findViewById(R.id.tvNameCategory);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("CateId", id);
                    Intent intent = new Intent(context, CategoryActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    
                }
            });
        }
    }

}
