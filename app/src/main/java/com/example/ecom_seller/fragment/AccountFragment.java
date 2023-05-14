package com.example.ecom_seller.fragment;

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
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ecom_seller.R;
import com.example.ecom_seller.activity.AddUserActivity;
import com.example.ecom_seller.adapter.StatusOrderAdapter;
import com.example.ecom_seller.adapter.StatusUserAdapter;
import com.example.ecom_seller.adapter.UserAdapter;
import com.example.ecom_seller.api.APIService;
import com.example.ecom_seller.model.Product;
import com.example.ecom_seller.model.User;
import com.example.ecom_seller.util.LinePagerIndicatorDecoration;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AccountFragment extends Fragment {
    StatusUserAdapter userAdapter;
    TextView btnaddUser;

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    View view;
    //Hàm trả về view
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);

        AnhXa();
        btnaddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddUserActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void AnhXa() {
        tabLayout = view.findViewById(R.id.tab_layout_user);
        viewPager2 = view.findViewById(R.id.viewPager2_user);
        userAdapter = new StatusUserAdapter(getActivity());
        btnaddUser = view.findViewById(R.id.btnaddUser);
        viewPager2.setAdapter(userAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Đang hoạt động");
                    break;
                case 1:
                    tab.setText("Tạm ngưng");
                    break;
            }
        }).attach();
    }


}
