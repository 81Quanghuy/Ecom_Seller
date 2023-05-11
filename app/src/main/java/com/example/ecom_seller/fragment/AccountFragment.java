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

import com.example.ecom_seller.R;
import com.example.ecom_seller.activity.AddUserActivity;
import com.example.ecom_seller.adapter.UserAdapter;
import com.example.ecom_seller.api.APIService;
import com.example.ecom_seller.model.Product;
import com.example.ecom_seller.model.User;
import com.example.ecom_seller.util.LinePagerIndicatorDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AccountFragment extends Fragment {

    List<User> userList;
    UserAdapter userAdapter;
    RecyclerView rcUserActive,rcUserNoActive;
    TextView btnaddUser;
    androidx.appcompat.widget.SearchView searchView;
    View view;
    //Hàm trả về view
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);

        AnhXa();
        getUsers();
        btnaddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddUserActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void getUsers() {
        //get User Active
        APIService.apiService.getUsers(true).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    userList = response.body();
                    userAdapter = new UserAdapter(getContext(), userList);
                    rcUserActive.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    rcUserActive.setLayoutManager(layoutManager);
                    rcUserActive.setAdapter(userAdapter);
                    rcUserActive.addItemDecoration(new LinePagerIndicatorDecoration());
                    searchView =view.findViewById(R.id.searchViewUser);
                    searchView.clearFocus();
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            List<User> list = new ArrayList<>();
                            for (User user: userList){
                                if(user.getFullName().toLowerCase().contains(newText.toLowerCase())){
                                    list.add(user);
                                }
                            }
                            if(list.isEmpty()){
                                Toast.makeText(getContext(), "Không có", Toast.LENGTH_SHORT).show();
                            }
                            else{
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

        //getUser No Active
        APIService.apiService.getUsers(false).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()) {
                    userList = response.body();
                    userAdapter = new UserAdapter(getContext(), userList);
                    rcUserNoActive.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    rcUserNoActive.setLayoutManager(layoutManager);
                    rcUserNoActive.setAdapter(userAdapter);
                    userAdapter.notifyDataSetChanged();
                }
            }


            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
                Toast.makeText(getContext().getApplicationContext(), "Thất bại", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void AnhXa() {
        btnaddUser= view.findViewById(R.id.btnaddUser);
        rcUserActive = view.findViewById(R.id.rc_user);

        rcUserNoActive = view.findViewById(R.id.rc_userNoActice);
    }


}
