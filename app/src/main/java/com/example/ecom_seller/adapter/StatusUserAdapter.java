package com.example.ecom_seller.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.ecom_seller.fragment.UserListFragment;

public class StatusUserAdapter extends FragmentStateAdapter {

    public StatusUserAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new UserListFragment(false);
            case 0:
            default:
                return new UserListFragment(true);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
