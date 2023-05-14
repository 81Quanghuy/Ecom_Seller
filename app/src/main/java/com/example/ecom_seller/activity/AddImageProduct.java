package com.example.ecom_seller.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ecom_seller.R;
import com.example.ecom_seller.api.APIService;
import com.example.ecom_seller.fragment.ProductFragment;
import com.example.ecom_seller.model.ImageData;
import com.example.ecom_seller.model.Product;
import com.example.ecom_seller.roomDatabase.Database.ProductDatabase;
import com.example.ecom_seller.util.RealPathUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddImageProduct extends AppCompatActivity {

    Button btnPres,btnTT, btnKT;
    ImageView imagePhoto;
    int MY_REQUEST_CODE = 10;

    List<MultipartBody.Part> mPartList = new ArrayList<>();
    String image;
    Product product;
    private Uri mUri;
    ImageData imageData;
    ProgressDialog mProgressDialog;
    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imagePhoto.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image_product);
        AnhXa();
        btnPres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProductDatabase.getInstance(getApplicationContext()).productDao().deleteAll();
                finish();
            }
        });
        btnTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mUri!= null || mPartList.size()>0){
                    btnKT.setVisibility(View.VISIBLE);
                }
                UpdateData();
            }
        });
        btnKT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddProductComplete();
            }
        });
        imagePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });
    }
    private void XacNhan(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
        alert.setTitle("Thông báo");
        alert.setMessage("Bạn có muốn tiếp tục thêm không");
        alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lệnh nút có
                UpdateData();
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
    private void UpdateData() {
        Log.e("TAG", "product database: "+ProductDatabase.getInstance(getApplicationContext()).productDao().getAll().size() );

        if (mUri != null){
            String strRealPath = RealPathUtil.getRealPath(this, mUri); //lấy đường dẫn thực

            Log.e("TAG", strRealPath);
            File file = new File(strRealPath);

            RequestBody requestBodyAvt = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part partAvatar = MultipartBody.Part.createFormData("image", file.getName(), requestBodyAvt);

            mPartList.add(partAvatar);
            Log.e("TAG", "UpdateData: "+ mPartList.get(0).toString() );
            product = ProductDatabase.getInstance(getApplicationContext()).productDao().getAll().get(0);
            // Nếu chưa có ảnh thì lưu ảnh đầu tiên
            Log.e("TAG", "Product chưa lưu ảnh: "+ product.getListimage());
            if (product.getListimage() == null){
                product.setListimage(APIService.BASE_URL + "images/" + file.getName());
            }
            // Nếu đã có ảnh thì lưu ảnh tiếp theo
            else{
                product.setListimage(product.getListimage() + "," +APIService.BASE_URL + "images/" + file.getName());
                Log.e("TAG", "Product đã lưu ảnh: "+ product.getListimage());
            }
            ProductDatabase.getInstance(getApplicationContext()).productDao().updateProduct(product);
            Toast.makeText(this, "Đã lưu hình ảnh", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Chưa thêm hình ảnh", Toast.LENGTH_SHORT).show();
        }

    }

    private void AddProductComplete() {
        // Đã truyền 1 list ảnh
        Log.e("TAG", "product database: "+ProductDatabase.getInstance(getApplicationContext()).productDao().getAll().size() );

        if(mPartList.size()>0){
            Log.e("TAG", "AddProductComplete: "+ mPartList.size() );
            Log.e("TAG", "AddProductComplete: "+ mPartList.get(mPartList.size()-1) );
            mProgressDialog.show();

            product = ProductDatabase.getInstance(getApplicationContext()).productDao().getAll().get(0);
            Log.e("TAG", "product list image trước khi xử lsy: "+ product.getListimage());
            //Xử lý chuỗi ảnh: lấy 3 ảnh cuối cùng
            String newString= "";
            String[] parts = product.getListimage().split(",");
            if (parts.length > 2) {
                String[] newParts = new String[3];
                newParts[0] = parts[parts.length-3];
                newParts[1] = parts[parts.length-2];
                newParts[2] = parts[parts.length-1];
                newString = String.join(",", newParts);
            }
            else{
                newString = product.getListimage();
            }
            product.setListimage(newString);
            Log.e("TAG", "product list image sau khi xử lsy: "+ product.getListimage());
            //Upload Product
            APIService.apiService.productNotify(product).enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    if(response.isSuccessful()){
                        ProductDatabase.getInstance(getApplicationContext()).productDao().deleteAll();
                        mProgressDialog.dismiss();
                        Toast.makeText(AddImageProduct.this, "Tạo sản phẩm thành công ", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        mProgressDialog.dismiss();
                        Toast.makeText(AddImageProduct.this,"", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    mProgressDialog.dismiss();
                    Toast.makeText(AddImageProduct.this, "Thất bại", Toast.LENGTH_SHORT).show();
                }
            });
            //upload ảnh
            for (MultipartBody.Part part : mPartList) {
                APIService.apiService.uploadImages(part).enqueue(new Callback<ImageData>() {
                    @Override
                    public void onResponse(Call<ImageData> call, Response<ImageData> response) {
                        Toast.makeText(AddImageProduct.this, "Upload Image Success", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<ImageData> call, Throwable t) {
                        Log.e("TAG", "onFailure: "+ t.getMessage() );
                    }
                });
            }
        }
        else{
            mProgressDialog.show();
            //Upload Product
            APIService.apiService.productNotify(product).enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    if(response.isSuccessful()){
                        ProductDatabase.getInstance(getApplicationContext()).productDao().deleteAll();
                        mProgressDialog.dismiss();
                        Toast.makeText(AddImageProduct.this, "Thay đổi thành công ", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        mProgressDialog.dismiss();
                        Toast.makeText(AddImageProduct.this,"", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    mProgressDialog.dismiss();
                    Toast.makeText(AddImageProduct.this, "Thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void AnhXa() {
        btnPres = findViewById(R.id.btn_pre_product);
        btnTT = findViewById(R.id.btn_image_product_cnt);
        btnKT = findViewById(R.id.btn_product_add_finish);
        product = ProductDatabase.getInstance(getApplicationContext()).productDao().getAll().get(0);
        if(product.getListimage()!=null){
            btnKT.setText("Hoàn thành");
            btnKT.setVisibility(View.VISIBLE);
        }
        else{
            btnKT.setVisibility(View.INVISIBLE);
        }

        imagePhoto = findViewById(R.id.imagePhoto);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Vui lòng đợi ...");
    }
    private void onClickRequestPermission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //ko cần cấp phép
            openGallery();
            return;
        }

        //xin cấp phép thành công
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();// mở gallery
        } else {
            //nếu ko tạo cục bộ và xin cấp phép
            String[] permission = {
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

    //Lắng nghe người dùng từ chối hoặc đồng ý
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //nếu ko thoả -> người dùng từ chối
                openGallery();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }
}