package com.example.ecom_seller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ecom_seller.R;
import com.example.ecom_seller.activity.SettingActivity;


public class AccountFragment extends Fragment {
    View view;

    ImageView btnSetting;
    //Hàm trả về view
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);
        AnhXa();
        return view;
    }

    private void AnhXa() {

        btnSetting = view.findViewById(R.id.btn_setting_profile);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });
    }


}
