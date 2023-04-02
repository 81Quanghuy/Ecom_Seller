package com.example.ecom_seller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.ecom_seller.R;
import com.example.ecom_seller.adapter.MallViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MallFragment extends Fragment {

    //Hàm trả về view
    View view;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mall, container, false);
        AnhXa();
        return view;
    }

    private void AnhXa() {
        mTabLayout = view.findViewById(R.id.tabLayoutMall);
        mViewPager = view.findViewById(R.id.view_pager_mall);
        MallViewPagerAdapter viewPagerAdapter = new MallViewPagerAdapter(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

}
