package com.example.ecom_seller.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.ecom_seller.fragment.AccountFragment;
import com.example.ecom_seller.fragment.HomeFragment;
import com.example.ecom_seller.fragment.LiveFragment;
import com.example.ecom_seller.fragment.NotificationFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return new HomeFragment();
            case 1:
                return new LiveFragment();
            case 2:
                return new NotificationFragment();
            case 3:
                return new AccountFragment();
        }
        return null;
    }

    //trả về số lượng của task
    @Override
    public int getCount() {
        return 4;
    }
}
