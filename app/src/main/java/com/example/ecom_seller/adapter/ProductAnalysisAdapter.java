package com.example.ecom_seller.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.ecom_seller.fragment.ManagerProductFragment;
import com.example.ecom_seller.fragment.ProductAnalysisFragment;

public class ProductAnalysisAdapter extends FragmentStateAdapter {
    public ProductAnalysisAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new ProductAnalysisFragment("Month");
            case 0:
            default:
                return new ProductAnalysisFragment("Date");
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
