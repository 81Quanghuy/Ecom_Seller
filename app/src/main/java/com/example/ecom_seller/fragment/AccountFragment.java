package com.example.ecom_seller.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.ecom_seller.R;
import com.example.ecom_seller.activity.LoginActivity;
import com.example.ecom_seller.activity.MainActivity;
import com.example.ecom_seller.activity.ProfileActivity;
import com.example.ecom_seller.dataLocal.SharedPrefManager;
import com.example.ecom_seller.model.User;
import com.example.ecom_seller.roomDatabase.Database.UserDatabase;


public class AccountFragment extends Fragment {
    View view;

    TextView tvNameAccount,idSeller;
    Button btnLogOut;
    User user;
    ImageView btnProfileAcount;
    ImageView btnSetting;
    //Hàm trả về view
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);
        GetData();
        AnhXa();
        btnProfileAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);

            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Logout(user);
                //SharedPrefManager.getInstance(getContext().getApplicationContext()).logout();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);


            }
        });
        return view;
    }

    private void GetData() {

        user = UserDatabase.getInstance(getContext().getApplicationContext()).usersDao().getAll().get(0);

    }

    private void Logout(User user) {
        UserDatabase.getInstance(getContext().getApplicationContext()).usersDao().delete(user);
    }

    @SuppressLint("SuspiciousIndentation")
    private void AnhXa() {
        //user = UserDatabase.getInstance(getContext().getApplicationContext()).usersDao().getAll().get(0);
        btnLogOut = view.findViewById(R.id.logout);
        btnProfileAcount = view.findViewById(R.id.imgProfile01);
        Glide.with(view.getContext()).load(user.getAvatar()).into(btnProfileAcount);
        tvNameAccount = view.findViewById(R.id.tvNameAccount);
        if(!user.getFullName().isEmpty())
        tvNameAccount.setText(user.getFullName().toString());
        idSeller =view.findViewById(R.id.idSeller);
        idSeller.setText(user.getId().toString().trim());
    }


}
