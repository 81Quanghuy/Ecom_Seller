package com.example.ecom_seller.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ecom_seller.R;
import com.example.ecom_seller.adapter.OrderAdapter;
import com.example.ecom_seller.adapter.UserAdapter;
import com.example.ecom_seller.api.APIService;
import com.example.ecom_seller.model.Order;
import com.example.ecom_seller.model.StatusOrder;
import com.example.ecom_seller.model.User;
import com.example.ecom_seller.util.LinePagerIndicatorDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    Boolean active;
    SwipeRefreshLayout userListFragment;
    UserAdapter userAdapter;
    RecyclerView rcUser;
    List<User> listUsers = new ArrayList<>();
    View view;
    SearchView searchView;
    public UserListFragment(Boolean active) {
        // Required empty public constructor
        this.active = active;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_list, container, false);
        AnhXa();
        return view;
    }

    private void AnhXa() {
        rcUser = view.findViewById(R.id.rc_userList);
        userListFragment = view.findViewById(R.id.UserListFragment);
        userListFragment.setOnRefreshListener(this);
        getListUsers(active);
    }

    private void getListUsers(Boolean active) {
        APIService.apiService.getUsers(active).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    listUsers = response.body();
                    userAdapter = new UserAdapter(getContext(), listUsers);
                    rcUser.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    rcUser.setLayoutManager(layoutManager);
                    rcUser.setAdapter(userAdapter);
                    rcUser.addItemDecoration(new LinePagerIndicatorDecoration());
                    searchView = view.findViewById(R.id.searchViewUser);
                    searchView.clearFocus();
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            List<User> list = new ArrayList<>();
                            for (User user : listUsers) {
                                if (user.getFullName().toLowerCase().contains(newText.toLowerCase())) {
                                    list.add(user);
                                }
                            }
                            if (list.isEmpty()) {
                                Toast.makeText(getContext(), "Không có", Toast.LENGTH_SHORT).show();
                            } else {
                                userAdapter.setListenterList(list);
                            }
                            return false;
                        }
                    });
                }
            }


            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
                Toast.makeText(getContext().getApplicationContext(), "Thất bại", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        getListUsers(active);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                userListFragment.setRefreshing(false);
            }
        }, 2000);
    }
}