package com.example.ecom_seller.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.ecom_seller.fragment.OrderAnalysisFragment;
import com.example.ecom_seller.fragment.ProductAnalysisFragment;

public class OrderAnalysisAdapter extends FragmentStateAdapter {
    public OrderAnalysisAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new OrderAnalysisFragment("month");

            case 2:
                return new OrderAnalysisFragment("year");
            case 0:
            default:
                return new OrderAnalysisFragment("date");

        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
