package com.example.ecom_seller.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.ecom_seller.fragment.ManagerProductFragment;
import com.example.ecom_seller.fragment.OrderDetailFragment;
import com.example.ecom_seller.fragment.ProductFragment;
import com.example.ecom_seller.model.StatusOrder;

public class ProductListAdapter extends FragmentStateAdapter {

    public ProductListAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
                case 1:
                    return new ManagerProductFragment("Tạm ngưng");
                case 2:
                    return new ManagerProductFragment("Hết hàng");
                case 0:
                default:
                return new ManagerProductFragment("Đang kinh doanh");
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
