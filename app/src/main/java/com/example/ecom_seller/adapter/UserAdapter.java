package com.example.ecom_seller.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.ecom_seller.activity.EditProfileActivity;
import com.example.ecom_seller.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    Context context;
    List<User> users;


    public void setData(List<User> users){
        this.users = users;
        notifyDataSetChanged();
    }

    public UserAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, null);
        UserAdapter.MyViewHolder myViewHolder = new UserAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyViewHolder holder, int position) {
        User user = users.get(position);
        if(user != null){
            holder.id = user.getId();
            holder.tvUserName.setText(user.getUsername());
            holder.tvPhone.setText(user.getPhone());
            holder.tvStatus.setText(user.getActive() == true ? "Đang Hoạt Động": "Không Hoạt Động");
            Glide.with(context).load(user.getAvatar()).into(holder.img);
        }
    }

    @Override
    public int getItemCount() {
        return users == null ? 0 : users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvUserName, tvPhone, tvStatus;
        private String id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.image_user);
            tvUserName = itemView.findViewById(R.id.tv_name_user);
            tvPhone = itemView.findViewById(R.id.tv_phone_user);
            tvStatus = itemView.findViewById(R.id.tv_status);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("idUser", id);
                    Intent intent = new Intent(context, EditProfileActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }
}
