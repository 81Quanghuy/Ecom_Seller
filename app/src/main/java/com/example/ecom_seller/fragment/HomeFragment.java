package com.example.ecom_seller.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
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

import java.text.DecimalFormat;
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

    ConstraintLayout layoutDoanhThu;
    TextClock textClock;
    TextView tvName,folower,manager_product,DoanhSo,btnUserTiemNang;
    TextView doanhthungay,doanhthuthang,doanhthunam;
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
        layoutDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OrderAnalysis.class);
                startActivity(intent);
            }
        });
        manager_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ManagerProductActivity.class);
                startActivity(intent);
            }
        });
        btnUserTiemNang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), ProductAnalysisActivity.class);
//                startActivity(intent);
                Toast.makeText(getActivity(), "Chức năng đang phát triển", Toast.LENGTH_SHORT).show();
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
        alert.setMessage(" Bạn muốn đăng xuất ?");
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
                    Double dtNgay =0.0, dtThang=0.0, dtNam=0.0;
                    //Đếm số lượng đơn hàng
                    for (Order order : listOrder){
                        if (order.getStatusOrder().equals(StatusOrder.DAGIAO))
                        {
                            //Tính doanh thu theo ngày
                            if (order.getUpdateat().equals(new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()))){
                                dtNgay += order.getPrice();
                            }

                            //Tính doanh thu theo tháng
                            if (order.getUpdateat().substring(3,5).equals(new SimpleDateFormat("MM").format(Calendar.getInstance().getTime()))){
                                dtThang += order.getPrice();
                            }

                            //Tính doanh thu theo năm
                            if (order.getUpdateat().substring(6,10).equals(new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime()))){
                                dtNam += order.getPrice();
                            }

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
                    DecimalFormat formatter = new DecimalFormat("###,###,###");
                    String doanhso = formatter.format(total) + " VNĐ";
                    doanhthungay.setText(dtNgay+"");
                    doanhthuthang.setText(dtThang+"");
                    doanhthunam.setText(dtNam+"");
                    DoanhSo.setText(doanhso);
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

        layoutDoanhThu = view.findViewById(R.id.layoutDoanhThu);
        //Button
        btnproductAnalyis = view.findViewById(R.id.productAnalyis);
        orderAnalyis = view.findViewById(R.id.orderAnalyis);
        manager_product = view.findViewById(R.id.manager_product);
        btnUserTiemNang = view.findViewById(R.id.tthelp);
        //Thời gian thực
        textClock = view.findViewById(R.id.textClock);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
        String time = dateFormat.format(calendar.getTime());
        textClock.setText(time);

        //Dữ liệu thống kê

        doanhthungay = view.findViewById(R.id.doanhthungay);
        doanhthuthang = view.findViewById(R.id.doanhthuthang);
        doanhthunam = view.findViewById(R.id.doanhthunam);
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
