package com.example.ecom_seller.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecom_seller.R;
import com.example.ecom_seller.api.APIService;
import com.example.ecom_seller.model.Category;
import com.example.ecom_seller.model.ImageData;
import com.example.ecom_seller.model.Product;
import com.example.ecom_seller.model.User;
import com.example.ecom_seller.roomDatabase.Database.ProductDatabase;
import com.example.ecom_seller.roomDatabase.Database.UserDatabase;
import com.example.ecom_seller.util.RealPathUtil;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {
    ImageView btnBackAccountProduct;
    EditText nameProduct,priceNoSaleProduct,PriceSaleProduct,DesProduct,QuantityProduct;
    Button btn_huy_product, btn_product_cnt;

    Product product;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        AnhXa();

        btn_huy_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DeleteDataLocal();
                finish();
            }
        });
        btn_product_cnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveData();
                Product product1 = ProductDatabase.getInstance(AddProductActivity.this).productDao().getAll().get(0);
                Log.e("TAG","product name: "+ product1.getName());
                Log.e("TAG","product des: "+ product1.getDesciption());
                Log.e("TAG","product price: "+ product1.getPrice());
                Log.e("TAG","product pricesale: "+ product1.getPromotionaprice());
                Log.e("TAG","product quality: "+ product1.getQuantity());

                Intent intent = new Intent(AddProductActivity.this, AddImageProduct.class);
                startActivity(intent);
            }
        });
        btnBackAccountProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteDataLocal();
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        DeleteDataLocal();
        finish();
    }
    private void DeleteDataLocal() {
        ProductDatabase.getInstance(AddProductActivity.this).productDao().deleteAll();
    }

    private boolean CheckData() {
        return true;
    }
    private Product SaveData() {

        if(CheckData())
        {
            if(product == null){
                product = new Product();
            }
            else
            {
                Log.e("TAG","id"+ id);

                product.setName(nameProduct.getText().toString().trim());
                product.setDesciption(DesProduct.getText().toString().trim());
                product.setPrice(Double.parseDouble(priceNoSaleProduct.getText().toString().trim()));
                product.setPromotionaprice(Double.parseDouble(PriceSaleProduct.getText().toString().trim()));
                product.setQuantity(Integer.parseInt(QuantityProduct.getText().toString().trim()));

            }
        }
        if(ProductDatabase.getInstance(AddProductActivity.this).productDao().getAll().size()==0){

            ProductDatabase.getInstance(AddProductActivity.this).productDao().insertProduct(product);
        }
        else{
            ProductDatabase.getInstance(AddProductActivity.this).productDao().updateProduct(product);
        }

        return product;
    }
    private void AnhXa() {
        btnBackAccountProduct = findViewById(R.id.btnBackAccountProduct);
        nameProduct = findViewById(R.id.nameProduct);
        priceNoSaleProduct = findViewById(R.id.priceNoSaleProduct);
        PriceSaleProduct = findViewById(R.id.PriceSaleProduct);
        QuantityProduct = findViewById(R.id.QuantityProduct);
        DesProduct = findViewById(R.id.DesProduct);
        btn_huy_product = findViewById(R.id.btn_huy_product);
        btn_product_cnt = findViewById(R.id.btn_product_cnt);
        UploadData();
    }


    private void UploadData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            id  = bundle.getString("ProductId");
            Log.e("TAG","ProductId "+id);
            APIService.apiService.ProductGetData(id).enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    if (response.isSuccessful()){
                        product=  response.body();
                        // lưu product vào cục bộ
                        ProductDatabase.getInstance(AddProductActivity.this).productDao().insertProduct(product);
                        Log.e("TAG", String.valueOf(ProductDatabase.getInstance(AddProductActivity.this).productDao().getAll().size()));
                        nameProduct.setText(product.getName());
                        priceNoSaleProduct.setText(String.valueOf(product.getPrice().toString()));
                        PriceSaleProduct.setText(String.valueOf(product.getPromotionaprice().toString()));
                        QuantityProduct.setText(String.valueOf(product.getQuantity()));
                        DesProduct.setText(product.getDesciption());
                    }
                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {

                }
            });
    }
}
}