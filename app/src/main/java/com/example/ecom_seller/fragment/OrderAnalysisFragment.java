package com.example.ecom_seller.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecom_seller.R;
import com.example.ecom_seller.api.APIService;
import com.example.ecom_seller.model.Order;
import com.example.ecom_seller.model.StatusOrder;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAnalysisFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    String status;
    Button btnFilterOrder,btnPrintOrder;
    SwipeRefreshLayout orderAnalysisFragment;
    ProgressDialog mProgressDialog;
    TextView timeAnalysis;
    PieChart pieChart;
    List<Order> orders;
    View view;
    public OrderAnalysisFragment(String status) {
        // Required empty public constructor
        this.status = status;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_order_analysis, container, false );
        AnhXa();
        loadData();
        btnFilterOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timeAnalysis.getText().toString().equals("Lọc ngày tại đây")){
                    loadData();
                }else{
                    loadData(status,timeAnalysis.getText().toString());
                }
            }
        });

        btnPrintOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportToPdf(getContext(),pieChart);
            }
        });
        timeAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                // Tạo DatePickerDialog và hiển thị
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Lấy giá trị ngày được chọn bởi người dùng
                                String selectedDate = String.format("%02d/%02d/%d", dayOfMonth, monthOfYear + 1, year);

                                // Hiển thị giá trị ngày được chọn lên TextView
                                timeAnalysis.setText(selectedDate);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        return view;

    }

    public void exportToPdf(final Context context, final PieChart pieChart) {
        // Show an AlertDialog to ask the user for the file name and location
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Save PDF");
        builder.setMessage("Enter a file name and location");

        final EditText input = new EditText(context);
        builder.setView(input);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String fileName = input.getText().toString().trim();

                if (fileName.isEmpty()) {
                    Toast.makeText(context, "Please enter a file name", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a new File object with the specified file name and location






                File file = new File(Environment.getExternalStorageDirectory(), fileName + ".pdf");

                try {
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream(file));
                    document.open();

                    // Add pie chart to PDF
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    pieChart.getChartBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
                    Image pieChartImage = Image.getInstance(stream.toByteArray());
                    document.add(pieChartImage);

//                    // Add bar chart to PDF
//                    ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
//                    barChart.getChartBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream2);
//                    Image barChartImage = Image.getInstance(stream2.toByteArray());
//                    document.add(barChartImage);

                    document.close();
                    Toast.makeText(context, "Exported to PDF!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("TAG", "onClick: "+e.getMessage() );
                    Toast.makeText(context, "Export failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        } );
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }




    private void loadData(String status, String date) {
        mProgressDialog.show();
        Log.e("TAG", "status: "+status );
        Log.e("TAG", "date: "+date );
        APIService.apiService.getOrderAnalysis(status,date).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                orders = response.body();
                mProgressDialog.dismiss();
                float total = orders.size();
                float countChoXacNhan = 0,countDangGiao = 0,countDaGiao = 0,countDaHuy = 0, countTuChoi = 0;
                for (   Order order : orders){
                    if (order.getStatusOrder().equals(StatusOrder.DANGGIAO)){
                        countDangGiao++;
                    } else if (order.getStatusOrder().equals(StatusOrder.CHOXACNHAN)){
                        countChoXacNhan++;
                    } else if (order.getStatusOrder().equals(StatusOrder.DAGIAO)){
                        countDaGiao++;
                    } else if (order.getStatusOrder().equals(StatusOrder.HUY)){
                        countDaHuy++;
                    } else if (order.getStatusOrder().equals(StatusOrder.TUCHOI)){
                        countTuChoi++;
                    }
                }
                Log.e("TAG", "VeBieuDoTronAPI: "+total+countChoXacNhan+" "+countDaGiao+" "+countDaHuy+" "+countTuChoi+" "+countDangGiao );
                VeBieuDoTron((countChoXacNhan/total)*100,(countDaGiao/total)*100,(countDaHuy/total)*100,(countTuChoi/total)*100,(countDangGiao/total)*100);
            }
            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

                mProgressDialog.dismiss();
                Log.e("TAG", "onFailure vẽ biểu đồ: "+t.getMessage() );
            }
        });
    }

    private void AnhXa() {
        timeAnalysis = view.findViewById(R.id.timeAnalysis);
        mProgressDialog = new ProgressDialog(getContext() );
        mProgressDialog.setMessage("Vui lòng đợi ...");
        orderAnalysisFragment = view.findViewById(R.id.orderAnalyisFragment);
        orderAnalysisFragment.setOnRefreshListener(this);
        pieChart = view.findViewById(R.id.pie_chart);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(false);
        btnFilterOrder = view.findViewById(R.id.btnFilterOrder);
        btnPrintOrder = view.findViewById(R.id.btnPrintOrder);
    }

    private void loadData() {
        mProgressDialog.show();
        //Call api Order to get list order
        APIService.apiService.getOrderAll().enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                orders = response.body();
                mProgressDialog.dismiss();
                float total = orders.size();
                float countChoXacNhan = 0,countDangGiao = 0,countDaGiao = 0,countDaHuy = 0, countTuChoi = 0;
                for (   Order order : orders){
                    if (order.getStatusOrder().equals(StatusOrder.DANGGIAO)){
                        countDangGiao++;
                    } else if (order.getStatusOrder().equals(StatusOrder.CHOXACNHAN)){
                        countChoXacNhan++;
                    } else if (order.getStatusOrder().equals(StatusOrder.DAGIAO)){
                        countDaGiao++;
                    } else if (order.getStatusOrder().equals(StatusOrder.HUY)){
                        countDaHuy++;
                    } else if (order.getStatusOrder().equals(StatusOrder.TUCHOI)){
                        countTuChoi++;
                    }
                    }
                Log.e("TAG", "VeBieuDoTronAPI: "+total+countChoXacNhan+" "+countDaGiao+" "+countDaHuy+" "+countTuChoi+" "+countDangGiao );
                VeBieuDoTron((countChoXacNhan/total)*100,(countDaGiao/total)*100,(countDaHuy/total)*100,(countTuChoi/total)*100,(countDangGiao/total)*100);
                }
            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                mProgressDialog.dismiss();
                Log.e("TAG", "onFailure vẽ biểu đồ: "+t.getMessage() );
            }
        });
    }

    private void VeBieuDoTron(float choxacnhan, float dagiao, float bihuy, float tuchoi, float danggiao) {
        Log.e("TAG", "VeBieuDoTron: "+choxacnhan+" "+dagiao+" "+bihuy+" "+tuchoi+" "+danggiao );
        List<PieEntry> entries1 = new ArrayList<>();
        if (choxacnhan != 0 ){
            entries1.add(new PieEntry(choxacnhan, "Chờ xác nhận"));
        }
        if (dagiao != 0 ){
            entries1.add(new PieEntry(dagiao, "Đã giao"));
        }
        if (bihuy != 0 ){
            entries1.add(new PieEntry(bihuy, "Bị Hủy"));
        }
        if (tuchoi != 0 ){
            entries1.add(new PieEntry(tuchoi, "Từ chối"));
        }
        if (danggiao != 0 ){
            entries1.add(new PieEntry(danggiao, "Đang giao"));
        }

        // Cấu hình biểu đồ
        PieDataSet dataSet1 = new PieDataSet(entries1, "Biểu đồ tỉ lệ đơn hàng theo trạng thái");
        dataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet1);
        data.setDrawValues(true);
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);
        pieChart.animateY(1000);
    }

    @Override
    public void onRefresh() {
        loadData();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                orderAnalysisFragment.setRefreshing(false);
            }
        }, 2000);
    }
}