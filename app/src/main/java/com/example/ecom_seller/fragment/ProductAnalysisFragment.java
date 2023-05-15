package com.example.ecom_seller.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ecom_seller.R;
import com.example.ecom_seller.adapter.ManagerProductAdapter;
import com.example.ecom_seller.api.APIService;
import com.example.ecom_seller.model.Product;
import com.example.ecom_seller.model.ReponseThongKeProduct;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAnalysisFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    String status;
    SwipeRefreshLayout productAnalysisFragment;
    List<Product> productList = new ArrayList<>();
    List<ReponseThongKeProduct> reponseThongKeProducts ;
    Button btnPrintProduct;
    View view;
    ArrayList<String> labels = new ArrayList<>();
    ArrayList<String> numberRows = new ArrayList<>();
    BarChart chart;
    ProgressDialog mProgressDialog;
    public ProductAnalysisFragment(String status) {
        // Required empty public constructor
        this.status = status;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_product_analysis, container, false );
        AnhXa();
        btnPrintProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Chức năng đang bảo trì", Toast.LENGTH_SHORT).show();
            }
        });
        LoadData();
        VeBieuDo(status);

        return view;

    }

    private void LoadData() {

        if(status.equals("Month")) {
            //Tạo mảng 12 tháng gần nhất
            for (int i = 1; i <= 12; i++) {
                numberRows.add("Tháng " + i);
            }
        } else if (status.equals("Date")) {
            //Tạo mảng 7 ngày gần nhất
            DateTimeFormatter formatter = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                LocalDate currentDate = LocalDate.now();
                for (int i = 0; i < 7; i++) {
                    String formattedDate = currentDate.minusDays(i).format(formatter);
                    numberRows.add(formattedDate);
                }
                Collections.reverse(numberRows);
            }
            else{
                Toast.makeText(getContext(), "Phiên bản android của bạn không hỗ trợ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void XacNhan(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Thông báo");
        alert.setMessage("Bạn có muốn xóa tất cả không ?");
        alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lệnh nút có

                //DataLocalManager.loggout();
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

    public  void VeBieuDo(String status){

        mProgressDialog.show();
        APIService.apiService.getProductByDate(status).enqueue(new Callback<List<ReponseThongKeProduct>>() {
            @Override
            public void onResponse(Call<List<ReponseThongKeProduct>> call, Response<List<ReponseThongKeProduct>> response) {
                if(response.isSuccessful()){
                    mProgressDialog.dismiss();
                    reponseThongKeProducts = response.body();
                    Log.e("TAG", "onResponse:0"+reponseThongKeProducts.get(0).getCount());
                    Log.e("TAG", "onResponse:1"+reponseThongKeProducts.get(1).getCount());
                    Log.e("TAG", "onResponse:2"+reponseThongKeProducts.get(2).getCount());
                    Log.e("TAG", "onResponse:3"+reponseThongKeProducts.get(3).getCount());
                    Log.e("TAG", "onResponse:4"+reponseThongKeProducts.get(4).getCount());
                    Log.e("TAG", "onResponse:5"+reponseThongKeProducts.get(5).getCount());
                    Log.e("TAG", "onResponse:6"+reponseThongKeProducts.get(6).getCount());
                    // Tạo một danh sách tên  các mục sản phẩm

                    for(int i = 0 ; i < reponseThongKeProducts.size(); i++){
                        if(reponseThongKeProducts.get(i).getProduct() != null){
                            labels.add(reponseThongKeProducts.get(i).getProduct().getName());
                        }
                        else {
                            labels.add("Không có sản phẩm");
                        }

                    }

                    // Thêm các mục sản phẩm vào AxisBottom của biểu đồ
                    XAxis xAxis = chart.getXAxis();
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(numberRows));
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setGranularity(1f);
                    xAxis.setLabelCount(numberRows.size());

                    // Tạo một danh sách các giá trị cho biểu đồ
                    ArrayList<BarEntry> entries = new ArrayList<>();
                    for(int i = 0 ; i < reponseThongKeProducts.size(); i++){
                        if (reponseThongKeProducts.get(i).getProduct() != null){
                            entries.add(new BarEntry(i, reponseThongKeProducts.get(i).getCount()));

                        }
                        else{
                            entries.add(new BarEntry(i, 0));
                        }
                    }

                    // Chú thích cho biểu đồ
                    BarDataSet dataSet = new BarDataSet(entries, "Sản phẩm bán chạy ");
                    dataSet.setColor(Color.BLUE);;
                    BarData lineData = new BarData(dataSet);
                    chart.setData(lineData);
                    chart.invalidate();
                }
            }

            @Override
            public void onFailure(Call<List<ReponseThongKeProduct>> call, Throwable t) {
                mProgressDialog.dismiss();
                Log.e("TAG", "onFailure: "+ t.getMessage() );
            }
        });
        // Bắt sự kiện click trên biểu đồ
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int pos = (int) h.getX(); // Lấy vị trí của cột được click
                String name = labels.get(pos); // Lấy tên của cột đó
                Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show(); // Hiển thị tên của cột đó bằng Toast message
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }
    private void AnhXa() {
        mProgressDialog = new ProgressDialog(getContext() );
        mProgressDialog.setMessage("Vui lòng đợi ...");
        btnPrintProduct = view.findViewById(R.id.btnPrintProduct);
        productAnalysisFragment =view.findViewById(R.id.productAnalyisFragment);
        productAnalysisFragment.setOnRefreshListener(this);
        chart = view.findViewById(R.id.chartProduct);
    }

    @Override
    public void onRefresh() {
        LoadData();
        VeBieuDo(status);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                productAnalysisFragment.setRefreshing(false);
            }
        }, 2000);
    }
}