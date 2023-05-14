package com.example.ecom_seller.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;


import com.bumptech.glide.Glide;
import com.example.ecom_seller.R;
import com.example.ecom_seller.activity.LoginActivity;
import com.example.ecom_seller.activity.ManagerProductActivity;
import com.example.ecom_seller.activity.OrderAnalysis;
import com.example.ecom_seller.activity.ProductAnalysisActivity;
import com.example.ecom_seller.activity.ProfileActivity;
import com.example.ecom_seller.api.APIService;
import com.example.ecom_seller.model.Order;
import com.example.ecom_seller.model.Photo;
import com.example.ecom_seller.model.Product;
import com.example.ecom_seller.model.StatusOrder;
import com.example.ecom_seller.model.User;
import com.example.ecom_seller.roomDatabase.Database.UserDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    //Sroll
    SwipeRefreshLayout HomeFragment;


    //Hàm trả về view
    ViewPager viewPager;
    View view;
    List<Order> listOrder;
    List<User> listUser;
    List<Product> listProduct;

    TextClock textClock;
    TextView tvName,folower,manager_product,DoanhSo,btnDoanhSo,btnUserTiemNang;
    List<Photo> mListPhoto;

    private Timer mTimer;

    Button  btnLogout;
    TextView btnproductAnalyis,orderAnalyis, ChoXacNhan,DangGiao, DaGiao, DaHuy;
    TextView productSell,productUnSell, productSoldOut;


    ImageView btnProfile;

    TextView role;
    TextView user_number;
    User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        GetData();
        AnhXa();

        LoadData();
        manager_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ManagerProductActivity.class);
                startActivity(intent);
            }
        });
        btnDoanhSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OrderAnalysis.class);
                startActivity(intent);
            }
        });
        btnUserTiemNang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProductAnalysisActivity.class);
                startActivity(intent);
            }
        });
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
                Xacnhan();

            }
        });
        btnproductAnalyis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProductAnalysisActivity.class);
                startActivity(intent);
            }
        });
        orderAnalyis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OrderAnalysis.class);
                startActivity(intent);
            }
        });
        return view;

    }

    private void Xacnhan() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Thông báo");
        alert.setMessage("Xóa đơn hàng sẽ ảnh hưởng đến thống kê. Bạn muốn tiếp tục không ?");
        alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lệnh nút có
                Logout(user);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lệnh nút không
            }
        });
        alert.show();
    }

    private void LoadData() {
        //Load Doanh số
        APIService.apiService.getOrderAll().enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if(response.isSuccessful()){
                    listOrder = response.body();
                    int total = 0;
                    int countChoXacNhan = 0,countDangGiao = 0,countDaGiao = 0,countDaHuy = 0;
                    //Đếm số lượng đơn hàng
                    for (Order order : listOrder){
                        if (order.getStatusOrder().equals(StatusOrder.DAGIAO))
                        {
                            total += order.getPrice();//Tính tổng tiền
                            countDaGiao += 1;
                        } else if (order.getStatusOrder().equals(StatusOrder.CHOXACNHAN)) {
                            countChoXacNhan += 1;
                        } else if (order.getStatusOrder().equals(StatusOrder.DANGGIAO)) {
                            countDangGiao += 1;
                        } else if (order.getStatusOrder().equals(StatusOrder.HUY)) {
                            countDaHuy += 1;
                        }
                    }
                    //Đưa dota lên giao diện
                    DoanhSo.setText(total+"");
                    ChoXacNhan.setText(countChoXacNhan+"");
                    DangGiao.setText(countDangGiao+"");
                    DaGiao.setText(countDaGiao+"");
                    DaHuy.setText(countDaHuy+"");
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });

        //Load sản phẩm
        APIService.apiService.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful())
                {
                    int countProductSell = 0,countProductUnSell = 0, countSoldOut =0;
                    listProduct = response.body();
                    for (Product product : listProduct){
                        if(product.getIsselling()){
                            countProductSell += 1;
                        }
                        else {
                            countProductUnSell += 1;
                        }
                        if(product.getQuantity() == 0){
                            if (product.getIsselling()){
                                countSoldOut += 1;
                            }
                        }
                    }

                    //Đưa dữ liệu lên giao diện
                    productSoldOut.setText(countSoldOut+"");
                    productSell.setText(countProductSell+"");
                    productUnSell.setText(countProductUnSell+"");

                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });



        //Load user count
        APIService.apiService.getUserAll().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    listUser = response.body();
                    user_number.setText(listUser.size()+"");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }

    private void Logout(User user) {
        UserDatabase.getInstance(getContext().getApplicationContext()).usersDao().delete(user);
    }
    private void GetData() {

        user = UserDatabase.getInstance(getContext().getApplicationContext()).usersDao().getAll().get(0);

    }

    private void AnhXa() {

        //SwipeRefreshLayout
        HomeFragment =view.findViewById(R.id.HomeFragment);
        HomeFragment.setOnRefreshListener(this);
        //Button
        btnproductAnalyis = view.findViewById(R.id.productAnalyis);
        orderAnalyis = view.findViewById(R.id.orderAnalyis);
        manager_product = view.findViewById(R.id.manager_product);
        btnDoanhSo = view.findViewById(R.id.ttpolicy);
        btnUserTiemNang = view.findViewById(R.id.tthelp);
        //Thời gian thực
        textClock = view.findViewById(R.id.textClock);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
        String time = dateFormat.format(calendar.getTime());
        textClock.setText(time);

        //Dữ liệu thống kê

        DoanhSo = view.findViewById(R.id.number_revenue);
        user_number = view.findViewById(R.id.user_number);
        ChoXacNhan = view.findViewById(R.id.number_process);
        DangGiao = view.findViewById(R.id.number_shipping);
        DaGiao = view.findViewById(R.id.number_return);
        DaHuy = view.findViewById(R.id.number_review);

        //Product
        productSell = view.findViewById(R.id.number_active);
        productUnSell = view.findViewById(R.id.number_cancel);
        productSoldOut = view.findViewById(R.id.number_soldOut);

        //User
        btnProfile = view.findViewById(R.id.imgProfile);
        tvName =view.findViewById(R.id.tvName);
        folower = view.findViewById(R.id.folower);
        Glide.with(view.getContext()).load(user.getAvatar()).into(btnProfile);
        tvName.setText(user.getFullName().toString());

        //Button Logout
        btnLogout =view.findViewById(R.id.logout);

        //role
        role = view.findViewById(R.id.folower);
        role.setText(user.getRole());


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    public void onRefresh() {
        LoadData();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                HomeFragment.setRefreshing(false);
            }
        }, 2000);
    }
}
