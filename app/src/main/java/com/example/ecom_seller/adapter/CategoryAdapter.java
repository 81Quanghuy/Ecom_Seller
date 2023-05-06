package com.example.ecom_seller.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecom_seller.R;
import com.example.ecom_seller.activity.ProductByCategory;
import com.example.ecom_seller.api.APIService;
import com.example.ecom_seller.fragment.ProductFragment;
import com.example.ecom_seller.model.Category;
import com.example.ecom_seller.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder>{

    private static final int VIEW_TYPE_COUNT = 1; // Số lượng view type
    Context context;
    List<Category> array;
    private OnItemClickListener listener;

    Category category;

    public CategoryAdapter(Context context, List<Category> array) {
        this.context = context;
        this.array =array;
    }
    public CategoryAdapter(Context context, List<Category> array,OnItemClickListener listener) {
        this.context = context;
        this.array =array;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position,String nameCate);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        category = array.get(position);

        holder.tenSp.setText(category.getName());
        Glide.with(context).load(category.getImage()).into(holder.images);
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView images;
        public TextView tenSp;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            images = (ImageView) itemView.findViewById(R.id.image_product);
            tenSp = (TextView) itemView.findViewById(R.id.tvNameCategory);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Bạn đã chọn category" + tenSp.getText().toString(), Toast.LENGTH_SHORT).show();
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position, tenSp.getText().toString().trim());
                        }
                    }

                }
            });
        }
    }

}
