package com.example.ecom_seller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.ecom_seller.R;
import com.example.ecom_seller.adapter.OrderAnalysisAdapter;
import com.example.ecom_seller.adapter.ProductAnalysisAdapter;
import com.example.ecom_seller.api.APIService;
import com.example.ecom_seller.model.Order;
import com.example.ecom_seller.model.Product;
import com.example.ecom_seller.model.StatusOrder;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderAnalysis extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    OrderAnalysisAdapter orderAnalysisAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_analysis);
        tabLayout = findViewById(R.id.tab_orderAnlysis);
        viewPager2 = findViewById(R.id.viewPager2_alOrder);
        orderAnalysisAdapter = new OrderAnalysisAdapter(this);
        viewPager2.setAdapter(orderAnalysisAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Theo ngày");
                    break;
                case 1:
                    tab.setText("Theo tháng");
                    break;
                case 2:
                    tab.setText("Theo năm");
                    break;
            }
        }).attach();
    }

}

//    List<Product> productList;
//    List<Order> orders;//
//
//    BarChart chart;
//    PieChart pieChart;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_order_analysis);
//        AnhXa();
//        loadData();
//
//
//        // Tạo một danh sách các mục sản phẩm
//        ArrayList<String> labels = new ArrayList<>();
//        labels.add("Mục 1");
//        labels.add("Mục 2");
//        labels.add("Mục 3");
//
//
//        // Thêm các mục sản phẩm vào AxisBottom của biểu đồ
//        XAxis xAxis = chart.getXAxis();
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setGranularity(1f);
//        xAxis.setLabelCount(labels.size());
//
//        ArrayList<BarEntry> entries = new ArrayList<>();
//
//        entries.add(new BarEntry(0, 10));
//        entries.add(new BarEntry(1, 20));
//        entries.add(new BarEntry(2, 15));
//        entries.add(new BarEntry(3, 30));
//        entries.add(new BarEntry(4, 25));
//
//        BarDataSet dataSet = new BarDataSet(entries, "Sản phẩm đã bán");
//        dataSet.setColor(Color.BLUE);;
//        BarData lineData = new BarData(dataSet);
//        chart.setData(lineData);
//        chart.invalidate();
//
//
//        //Biểu đồ tròn
//        // Tạo dữ liệu
//
//    }
//
//    private void AnhXa() {
//        chart  = findViewById(R.id.chart);
//        pieChart = findViewById(R.id.pie_chart);
//        pieChart.getDescription().setEnabled(false);
//        pieChart.setDrawHoleEnabled(false);
//    }
//
//    private void loadData() {
//        //Call api Order to get list order
//        APIService.apiService.getOrderAll().enqueue(new Callback<List<Order>>() {
//            @Override
//            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
//                orders = response.body();
//                float total = orders.size();
//                float countChoXacNhan = 0,countDangGiao = 0,countDaGiao = 0,countDaHuy = 0, countTuChoi = 0;
//                for (   Order order : orders){
//                    if (order.getStatusOrder().equals(StatusOrder.DANGGIAO)){
//                        countDangGiao++;
//                    } else if (order.getStatusOrder().equals(StatusOrder.CHOXACNHAN)){
//                        countChoXacNhan++;
//                    } else if (order.getStatusOrder().equals(StatusOrder.DAGIAO)){
//                        countDaGiao++;
//                    } else if (order.getStatusOrder().equals(StatusOrder.HUY)){
//                        countDaHuy++;
//                    } else if (order.getStatusOrder().equals(StatusOrder.TUCHOI)){
//                        countTuChoi++;
//                    }
//                    }
//                Log.e("TAG", "VeBieuDoTronAPI: "+total+countChoXacNhan+" "+countDaGiao+" "+countDaHuy+" "+countTuChoi+" "+countDangGiao );
//                VeBieuDoTron((countChoXacNhan/total)*100,(countDaGiao/total)*100,(countDaHuy/total)*100,(countTuChoi/total)*100,(countDangGiao/total)*100);
//                }
//            @Override
//            public void onFailure(Call<List<Order>> call, Throwable t) {
//
//                Log.e("TAG", "onFailure vẽ biểu đồ: "+t.getMessage() );
//            }
//        });
//    }
//
//    private void VeBieuDoTron(float choxacnhan, float dagiao, float bihuy, float tuchoi, float danggiao) {
//        Log.e("TAG", "VeBieuDoTron: "+choxacnhan+" "+dagiao+" "+bihuy+" "+tuchoi+" "+danggiao );
//        List<PieEntry> entries1 = new ArrayList<>();
//        if (choxacnhan != 0 ){
//            entries1.add(new PieEntry(choxacnhan, "Chờ xác nhận"));
//        }
//        if (dagiao != 0 ){
//            entries1.add(new PieEntry(dagiao, "Đã giao"));
//        }
//        if (bihuy != 0 ){
//            entries1.add(new PieEntry(bihuy, "Bị Hủy"));
//        }
//        if (tuchoi != 0 ){
//            entries1.add(new PieEntry(tuchoi, "Từ chối"));
//        }
//        if (danggiao != 0 ){
//            entries1.add(new PieEntry(danggiao, "Đang giao"));
//        }
//
//        // Cấu hình biểu đồ
//        PieDataSet dataSet1 = new PieDataSet(entries1, "Biểu đồ tỉ lệ đơn hàng theo trạng thái");
//        dataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
//        PieData data = new PieData(dataSet1);
//        data.setDrawValues(true);
//        data.setValueTextSize(12f);
//        data.setValueTextColor(Color.WHITE);
//        pieChart.setData(data);
//        pieChart.animateY(1000);
//    }
//}