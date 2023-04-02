package com.example.ecom_seller.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.ecom_seller.activity.FollowedFragment;
import com.example.ecom_seller.activity.ViewFragment;

public class MallViewPagerAdapter extends FragmentStatePagerAdapter {

    public MallViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return new ViewFragment();
            case 1:
                return new FollowedFragment();
            default:
                return new ViewFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Xem Dạo";
                break;
            case 1:
                title = "Đã Theo Dõi";
                break;
        }
        return title;
    }
}
