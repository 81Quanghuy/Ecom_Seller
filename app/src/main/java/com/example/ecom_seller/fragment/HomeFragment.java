package com.example.ecom_seller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import com.bumptech.glide.Glide;
import com.example.ecom_seller.R;
import com.example.ecom_seller.activity.GraphViewProduct;
import com.example.ecom_seller.activity.LoginActivity;
import com.example.ecom_seller.activity.ProfileActivity;
import com.example.ecom_seller.model.Photo;
import com.example.ecom_seller.model.User;
import com.example.ecom_seller.roomDatabase.Database.UserDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {

    //Hàm trả về view
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    View view;

    TextClock textClock;
    TextView tvName,folower;
    List<Photo> mListPhoto;

    private Timer mTimer;

    Button btnSearch, btnLogout;
    TextView btnproductAnalyis,orderAnalyis;
    ImageView btnProfile;

    User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        GetData();
        AnhXa();

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout(user);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        btnproductAnalyis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GraphViewProduct.class);
                startActivity(intent);
            }
        });
        orderAnalyis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GraphViewProduct.class);
                startActivity(intent);
            }
        });
        return view;

    }
    private void Logout(User user) {
        UserDatabase.getInstance(getContext().getApplicationContext()).usersDao().delete(user);
    }
    private void GetData() {

        user = UserDatabase.getInstance(getContext().getApplicationContext()).usersDao().getAll().get(0);
    }

    private void AnhXa() {
        btnproductAnalyis = view.findViewById(R.id.productAnalyis);
        orderAnalyis = view.findViewById(R.id.orderAnalyis);
        textClock = view.findViewById(R.id.textClock);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
        String time = dateFormat.format(calendar.getTime());
        textClock.setText(time);


        btnProfile = view.findViewById(R.id.imgProfile);
        tvName =view.findViewById(R.id.tvName);
        folower = view.findViewById(R.id.folower);
        Glide.with(view.getContext()).load(user.getAvatar()).into(btnProfile);
        tvName.setText(user.getFullName().toString());
        folower.setText("Followers ");
        btnLogout =view.findViewById(R.id.logout);
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
