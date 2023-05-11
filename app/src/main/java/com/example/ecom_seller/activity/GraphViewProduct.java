package com.example.ecom_seller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ecom_seller.R;
import com.example.ecom_seller.api.APIService;
import com.example.ecom_seller.model.Order;
import com.example.ecom_seller.model.Product;
import com.example.ecom_seller.model.StatusOrder;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GraphViewProduct extends AppCompatActivity {

    List<Product> productList;
    List<Order> orders;//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view_product);
        BarChart chart  = findViewById(R.id.chart);
        PieChart pieChart = findViewById(R.id.pie_chart);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(false);
        // Tạo một danh sách các mục sản phẩm
        ArrayList<String> labels = new ArrayList<>();
        labels.add("Mục 1");
        labels.add("Mục 2");
        labels.add("Mục 3");

// Thêm các mục sản phẩm vào AxisBottom của biểu đồ
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.size());

        ArrayList<BarEntry> entries = new ArrayList<>();

        entries.add(new BarEntry(0, 10));
        entries.add(new BarEntry(1, 20));
        entries.add(new BarEntry(2, 15));
        entries.add(new BarEntry(3, 30));
        entries.add(new BarEntry(4, 25));

        BarDataSet dataSet = new BarDataSet(entries, "Sản phẩm đã bán");
        dataSet.setColor(Color.BLUE);;
        BarData lineData = new BarData(dataSet);
        chart.setData(lineData);
        chart.invalidate();


        // Tạo dữ liệu
        List<PieEntry> entries1 = new ArrayList<>();
        entries1.add(new PieEntry(50f, "Đã giao"));
        entries1.add(new PieEntry(30f, "Được giao"));
        entries1.add(new PieEntry(20f, "Đã hủy"));

// Cấu hình biểu đồ
        PieDataSet dataSet1 = new PieDataSet(entries1, "Biểu đồ đánh giá sản phẩm");
        dataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet1);
        data.setDrawValues(true);
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);
        pieChart.animateY(1000);
    }
}