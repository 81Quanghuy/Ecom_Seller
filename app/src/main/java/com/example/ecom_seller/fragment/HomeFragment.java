package com.example.ecom_seller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import com.example.ecom_seller.R;
import com.example.ecom_seller.activity.SearchActivity;
import com.example.ecom_seller.adapter.PhotoAdapter;
import com.example.ecom_seller.model.Photo;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {

    //Hàm trả về view
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    PhotoAdapter photoAdapter;
    View view;

    List<Photo> mListPhoto;

    private Timer mTimer;

    Button btnSearch;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        AnhXa();
        return view;
    }

    private void AnhXa() {

        viewPager = view.findViewById(R.id.viewPager_Home);
        circleIndicator =view.findViewById(R.id.indicator);
        mListPhoto = getListPhoto();
        photoAdapter = new PhotoAdapter(getContext(), mListPhoto);
        viewPager.setAdapter(photoAdapter);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        btnSearch = view.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });

        autoSlideImage();
    }

    private List<Photo> getListPhoto() {
        List<Photo> photos = new ArrayList<>();
        photos.add(new Photo(R.drawable.hinh1));
        photos.add(new Photo(R.drawable.hinh2));
        photos.add(new Photo(R.drawable.hinh3));
        photos.add(new Photo(R.drawable.hinh4));
        return photos;
    }

    private void autoSlideImage(){

        if (mListPhoto == null || mListPhoto.isEmpty() ||viewPager == null) {
            return;
        }
        //Init Timer
        if(mTimer == null){
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int totalItem = mListPhoto.size() - 1;
                        if(currentItem < totalItem){
                            currentItem++;
                            viewPager.setCurrentItem(currentItem);
                        }
                        else {
                            viewPager.setCurrentItem(0);
                        }

                    }

                });
            }
        }, 500, 3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }
    }
}
