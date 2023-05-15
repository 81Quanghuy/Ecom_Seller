//package com.example.ecom_seller.fragment;
//
//import android.app.DatePickerDialog;
//import android.app.ProgressDialog;
//import android.content.DialogInterface;
//import android.graphics.Color;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AlertDialog;
//import androidx.fragment.app.Fragment;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//
//import android.os.Handler;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.ecom_seller.R;
//import com.example.ecom_seller.api.APIService;
//import com.example.ecom_seller.model.Order;
//import com.example.ecom_seller.model.Product;
//import com.example.ecom_seller.model.ReponseThongKeProduct;
//import com.example.ecom_seller.model.StatusOrder;
//import com.github.mikephil.charting.charts.BarChart;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.data.BarData;
//import com.github.mikephil.charting.data.BarDataSet;
//import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
//import com.github.mikephil.charting.highlight.Highlight;
//import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collections;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class DoanhThuFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
//    String status;
//    SwipeRefreshLayout DTrefreshLayout;
//    List<Order> listOrders;
//    List<Order> templ;
//    Button btnPrintProduct,btnFilterDT;
//    View view;
//    ArrayList<String> labels = new ArrayList<>();
//    ArrayList<String> numberRows = new ArrayList<>();
//    BarChart chart;
//    ProgressDialog mProgressDialog;
//    TextView timeAnalysis;
//    public DoanhThuFragment(String status) {
//        // Required empty public constructor
//        this.status = status;
//    }
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        view = inflater.inflate(R.layout.fragment_doanh_thu, container, false );
//        AnhXa();
//        LoadData();
//        btnPrintProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "Chức năng đang bảo trì", Toast.LENGTH_SHORT).show();
//            }
//        });
//        btnFilterDT.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(!timeAnalysis.getText().toString().equals("Lọc ngày tại đây")){
//                    LoadData(status,timeAnalysis.getText().toString());
//                }
//            }
//        });
//        btnPrintProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //exportToPdf(getContext(),pieChart);
//                Toast.makeText(getContext(), "Chức năng đang bảo trì", Toast.LENGTH_SHORT).show();
//            }
//        });
//        timeAnalysis.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Calendar c = Calendar.getInstance();
//                int mYear = c.get(Calendar.YEAR);
//                int mMonth = c.get(Calendar.MONTH);
//                int mDay = c.get(Calendar.DAY_OF_MONTH);
//
//                // Tạo DatePickerDialog và hiển thị
//                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
//                        new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year,
//                                                  int monthOfYear, int dayOfMonth) {
//                                // Lấy giá trị ngày được chọn bởi người dùng
//                                String selectedDate = String.format("%02d/%02d/%d", dayOfMonth, monthOfYear + 1, year);
//
//                                // Hiển thị giá trị ngày được chọn lên TextView
//                                timeAnalysis.setText(selectedDate);
//                            }
//                        }, mYear, mMonth, mDay);
//                datePickerDialog.show();
//            }
//        });
//        return view;
//
//    }
//    private void LoadData() {
//
//        if(status.equals("Month")) {
//            //Tạo mảng 12 tháng gần nhất
//            for (int i = 1; i <= 12; i++) {
//                numberRows.add("Tháng " + i);
//            }
//        } else if (status.equals("Date")) {
//            //Tạo mảng 7 ngày gần nhất
//            DateTimeFormatter formatter = null;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//                LocalDate currentDate = LocalDate.now();
//                for (int i = 0; i < 7; i++) {
//                    String formattedDate = currentDate.minusDays(i).format(formatter);
//                    numberRows.add(formattedDate);
//                }
//                Collections.reverse(numberRows);
//            }
//            else{
//                Toast.makeText(getContext(), "Phiên bản android của bạn không hỗ trợ", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//    private void LoadData(String status, String toString) {
//        mProgressDialog.show();
//        APIService.apiService.getOrderAnalysis(status,toString).enqueue(new Callback<List<Order>>() {
//            @Override
//            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
//                if(response.isSuccessful()){
//                    templ = response.body();
//
//                    if(templ.size() == 0){
//                        Toast.makeText(getContext(), "Không có đơn hàng nào", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        for (Order order: templ) {
//                            if(order.getStatusOrder().equals(StatusOrder.DAGIAO)){
//                                listOrders.add(order);
//                            }
//                        }
//                        VeBieuDo(listOrders);
//                    }
//                }
//                else {
//                    Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Order>> call, Throwable t) {
//                Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//    public  void VeBieuDo(List<Order> listOrders){
//
//        mProgressDialog.show();
//
//        for(int i = 0 ; i < reponseThongKeProducts.size(); i++){
//            if(reponseThongKeProducts.get(i).getProduct() != null){
//                labels.add(reponseThongKeProducts.get(i).getProduct().getName());
//            }
//            else {
//                labels.add("Không có sản phẩm");
//            }
//
//        }
//
//                    // Thêm các mục sản phẩm vào AxisBottom của biểu đồ
//                    XAxis xAxis = chart.getXAxis();
//                    xAxis.setValueFormatter(new IndexAxisValueFormatter(numberRows));
//                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//                    xAxis.setGranularity(1f);
//                    xAxis.setLabelCount(numberRows.size());
//
//                    // Tạo một danh sách các giá trị cho biểu đồ
//                    ArrayList<BarEntry> entries = new ArrayList<>();
//                    for(int i = 0 ; i < reponseThongKeProducts.size(); i++){
//                        if (reponseThongKeProducts.get(i).getProduct() != null){
//                            entries.add(new BarEntry(i, reponseThongKeProducts.get(i).getCount()));
//
//                        }
//                        else{
//                            entries.add(new BarEntry(i, 0));
//                        }
//                    }
//
//                    // Chú thích cho biểu đồ
//                    BarDataSet dataSet = new BarDataSet(entries, "Sản phẩm bán chạy ");
//                    dataSet.setColor(Color.BLUE);;
//                    BarData lineData = new BarData(dataSet);
//                    chart.setData(lineData);
//                    chart.invalidate();
//
//        // Bắt sự kiện click trên biểu đồ
//        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//            @Override
//            public void onValueSelected(Entry e, Highlight h) {
//                int pos = (int) h.getX(); // Lấy vị trí của cột được click
//                String name = labels.get(pos); // Lấy tên của cột đó
//                Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show(); // Hiển thị tên của cột đó bằng Toast message
//            }
//
//            @Override
//            public void onNothingSelected() {
//
//            }
//        });
//
//    }
//    private void AnhXa() {
//        timeAnalysis = view.findViewById(R.id.timeAnalysisDT);
//        mProgressDialog = new ProgressDialog(getContext() );
//        mProgressDialog.setMessage("Vui lòng đợi ...");
//        btnPrintProduct = view.findViewById(R.id.btnPrintDT);
//        DTrefreshLayout =view.findViewById(R.id.DTrefreshLayout);
//        DTrefreshLayout.setOnRefreshListener(this);
//        chart = view.findViewById(R.id.chartDT);
//        btnFilterDT = view.findViewById(R.id.btnFilterDT);
//    }
//
//    @Override
//    public void onRefresh() {
//        LoadData();
//        VeBieuDo(listOrders);
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                DTrefreshLayout.setRefreshing(false);
//            }
//        }, 2000);
//    }
//
//}