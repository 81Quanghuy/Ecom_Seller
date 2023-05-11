package com.example.ecom_seller.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecom_seller.R;
import com.example.ecom_seller.adapter.CategoryAdapter;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    ArrayList<String> listCateName = new ArrayList<String>();
    Spinner mySpinner;
    Category entity;
    List<Category> categories;
    Product product;
    String id,category;
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
                ProductDatabase.getInstance(AddProductActivity.this).productDao().insertProduct(SaveData());
                Product product1 = ProductDatabase.getInstance(AddProductActivity.this).productDao().getAll().get(0);
                Log.e("TAG","product Data: "+ ProductDatabase.getInstance(AddProductActivity.this).productDao().getAll().size());
                Log.e("TAG","product name: "+ product1.getName());
                Log.e("TAG","product des: "+ product1.getDesciption());
                Log.e("TAG","product price: "+ product1.getPrice());
                Log.e("TAG","product pricesale: "+ product1.getPromotionaprice());
                Log.e("TAG","product quality: "+ product1.getQuantity());
                Log.e("TAG","category" + product1.getCategory().getName());
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
        String name = nameProduct.getText().toString().trim();
        String priceNoSale = priceNoSaleProduct.getText().toString().trim();
        String priceSale = PriceSaleProduct.getText().toString().trim();
        String des = DesProduct.getText().toString().trim();
        String quantity = QuantityProduct.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            nameProduct.setError("Vui lòng nhập tên sản phẩm");
            nameProduct.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(priceNoSale)) {
            priceNoSaleProduct.setError("Vui lòng nhập tên giá nhập hàng");
            priceNoSaleProduct.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(priceSale)) {
            PriceSaleProduct.setError("Vui lòng nhập giá bán hàng");
            PriceSaleProduct.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(des)) {
            DesProduct.setError("Vui lòng nhập mô tả sản phẩm");
            DesProduct.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(quantity)) {
            QuantityProduct.setError("Vui lòng nhập số lượng nhập hàng");
            QuantityProduct.requestFocus();
            return false;
        }
        return true;
    }
    private Product SaveData() {

        if(CheckData())
        {
            if(product == null){
                product = new Product();
                product.setId(UUID.randomUUID().toString().split("-")[0]);
                id = product.getId();
            }
            else
            {
                Log.e("TAG","id: "+ id);

                product.setName(nameProduct.getText().toString().trim());
                product.setDesciption(DesProduct.getText().toString().trim());
                product.setPrice(Double.parseDouble(priceNoSaleProduct.getText().toString().trim()));
                product.setPromotionaprice(Double.parseDouble(PriceSaleProduct.getText().toString().trim()));
                product.setQuantity(Integer.parseInt(QuantityProduct.getText().toString().trim()));
                Log.e("TAG", (String) mySpinner.getSelectedItem());
                for (Category cate:categories ) {
                    if(cate.getName().equals((String)mySpinner.getSelectedItem())){
                        entity =cate;
                        product.setCategory(entity);
                        break;
                    }
                }

            }
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
        mySpinner = findViewById(R.id.spiner_cate);
        SpinerCategory();
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
                        category = product.getCategory().getName();
                        int index = listCateName.indexOf(category);
                        mySpinner.setSelection(index);
                    }
                }
                @Override
                public void onFailure(Call<Product> call, Throwable t) {

                }
            });
        }

    }
    private void SpinerCategory() {
        APIService.apiService.getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    categories = response.body();

                    for (Category cate: categories) {
                        listCateName.add(cate.getName());
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(AddProductActivity.this, android.R.layout.simple_spinner_item,listCateName);
                    mySpinner.setAdapter(arrayAdapter);
                }

            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
            }
        });

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category =(String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                category =(String) adapterView.getItemAtPosition(0);
                Toast.makeText(AddProductActivity.this, category, Toast.LENGTH_SHORT).show();
            }
        });
    }
}