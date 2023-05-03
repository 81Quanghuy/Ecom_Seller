package com.example.ecom_seller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecom_seller.R;
import com.example.ecom_seller.adapter.PhotoAdapter;
import com.example.ecom_seller.adapter.ReviewAdapter;
import com.example.ecom_seller.api.APIService;
import com.example.ecom_seller.model.Photo;
import com.example.ecom_seller.model.Product;
import com.example.ecom_seller.model.Review;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {
    ViewPager2 mViewPager2;
    ImageView btnBack,imgProduct;
    String id;
    CircleIndicator3 mCircleIndicator3;
    Product product;
    AppCompatButton Ngungkinhdoanh, ThayDoiThongTin;
    TextView tvPrice, tvDesciption, tvGiaChuaGiam, tvNameSp, tvHangSP;
    RecyclerView rcReview;
    List<Review> mListReview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        AnhXa();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Ngungkinhdoanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ngungkinhdoanh();
            }
        });
        ThayDoiThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThayDoiThongTin();
            }
        });
    }

    private void ThayDoiThongTin() {

        Bundle bundle = new Bundle();
        bundle.putString("ProductId", id);
        Intent intent = new Intent(ProductDetailActivity.this, AddProductActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void Ngungkinhdoanh() {

        APIService.apiService.ToggeActive(id,false).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    Toast.makeText(ProductDetailActivity.this, "Đã ngừng kinh doanh sản phẩm", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

                Log.e("TAG", t.getMessage());
                Toast.makeText(ProductDetailActivity.this, "Sản phẩm đang được bán", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AnhXa() {
        rcReview = findViewById(R.id.rc_item_review);
        btnBack = findViewById(R.id.btn_back_to_prodetail);
        tvPrice = findViewById(R.id.tvGiaDaGiam);
        tvGiaChuaGiam = findViewById(R.id.tv_Gia_Da_Giam);
        tvGiaChuaGiam.setPaintFlags(tvGiaChuaGiam.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tvDesciption = findViewById(R.id.tvmota);
        tvNameSp = findViewById(R.id.tvnamedetail);
        tvHangSP = findViewById(R.id.tvHang_SP_detail);
        mViewPager2 = findViewById(R.id.view_page_2);
        mCircleIndicator3 = findViewById(R.id.indicator_3);
        Ngungkinhdoanh = findViewById(R.id.btnunactivePro);
        ThayDoiThongTin = findViewById(R.id.btnchangePro);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            id = bundle.getString("ProductId");
            getProductById(id);
        }
        if(product != null){
            Glide.with(getApplicationContext())
                    .load(product.getListimage())
                    .into(imgProduct);
            tvGiaChuaGiam.setText(product.getPrice().toString());
            tvPrice.setText(product.getPromotionaprice().toString());
            tvDesciption.setText(product.getDesciption());
            tvNameSp.setText(product.getName());
            Log.d("TAG", product.getName());
        }


    }
    private void GetReviews(Product p) {
        APIService.apiService.getReviewByProduct(p).enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                mListReview = response.body();
                ReviewAdapter reviewAdapter = new ReviewAdapter(ProductDetailActivity.this, mListReview);
                rcReview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rcReview.setLayoutManager(layoutManager);
                rcReview.setAdapter(reviewAdapter);
                reviewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi"+ t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void getProductById(String id) {
        Log.e("TAG","IDproduct"+id);
        APIService.apiService.getProductById(id).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                product = response.body();
                tvGiaChuaGiam.setText(String.format( "%,.0f",product.getPrice())+"đ");
                tvPrice.setText(String.format( "%,.0f",product.getPromotionaprice())+"đ");
                tvDesciption.setText(product.getDesciption());
                tvNameSp.setText(product.getName());

                List<Photo> photoList = new ArrayList<>();

                photoList = product.getListPhoto();

                PhotoAdapter photoAdapter = new PhotoAdapter(ProductDetailActivity.this, photoList);
                mViewPager2.setAdapter(photoAdapter);
                mCircleIndicator3.setViewPager(mViewPager2);
                GetReviews(product);
            }
            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });
    }
}