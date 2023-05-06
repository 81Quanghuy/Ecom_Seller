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
                finish();
            }
        });
        btnTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //XacNhan();
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
        if (mUri != null){
            String strRealPath = RealPathUtil.getRealPath(this, mUri); //lấy đường dẫn thực

            Log.e("TAG", strRealPath);
            File file = new File(strRealPath);

            RequestBody requestBodyAvt = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part partAvatar = MultipartBody.Part.createFormData("image", file.getName(), requestBodyAvt);

            mPartList.add(partAvatar);
            product = ProductDatabase.getInstance(getApplicationContext()).productDao().getAll().get(0);
            product.setListimage(product.getListimage() + "," +APIService.BASE_URL + "images/" + file.getName());
            ProductDatabase.getInstance(getApplicationContext()).productDao().updateProduct(product);
            Toast.makeText(this, "Đã lưu hình ảnh", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Chưa thêm hình ảnh", Toast.LENGTH_SHORT).show();
        }

    }

    public MultipartBody.Part AddMutipart(){
        String strRealPath = RealPathUtil.getRealPath(this, mUri); //lấy đường dẫn thực

        Log.e("TAG", strRealPath);
        File file = new File(strRealPath);

        RequestBody requestBodyAvt = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part partAvatar = MultipartBody.Part.createFormData("image", file.getName(), requestBodyAvt);
        return  partAvatar;

    }
    private void AddProductComplete() {
        // Đã truyền 1 list ảnh
        if(mPartList!= null){
            for (MultipartBody.Part part : mPartList) {
                APIService.apiService.uploadImages(part).enqueue(new Callback<ImageData>() {
                    @Override
                    public void onResponse(Call<ImageData> call, Response<ImageData> response) {
                        Toast.makeText(AddImageProduct.this, "Upload Image Success", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<ImageData> call, Throwable t) {
                        Toast.makeText(AddImageProduct.this, "Upload Image Fail", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }
        else{
            if(mUri!= null){
                APIService.apiService.uploadImages(AddMutipart()).enqueue(new Callback<ImageData>() {
                    @Override
                    public void onResponse(Call<ImageData> call, Response<ImageData> response) {
                        Toast.makeText(AddImageProduct.this, "Upload Image Success", Toast.LENGTH_SHORT).show();
                        if(product.getListimage()!= null){
                            product.setListimage(product.getListimage() + "," +APIService.BASE_URL + "images/" + response.body().getName());

                        }
                        else{
                            product.setListimage(APIService.BASE_URL + "images/" + response.body().getName());
                        }
                        ProductDatabase.getInstance(getApplicationContext()).productDao().updateProduct(product);
                        }

                    @Override
                    public void onFailure(Call<ImageData> call, Throwable t) {
                        Toast.makeText(AddImageProduct.this, "Upload Image Fail", Toast.LENGTH_SHORT).show();

                    }
                });
            }
            else{
                    Toast.makeText(this, "Chưa thêm hình ảnh", Toast.LENGTH_SHORT).show();
            }
        }

        // Đã truyền 1 list ảnh or 1 ảnh
        if(mPartList!= null || mUri!= null){
            mProgressDialog.show();
            product = ProductDatabase.getInstance(getApplicationContext()).productDao().getAll().get(0);
            String newString= "";
            String[] parts = product.getListimage().split(",");
            if (parts.length > 2) {
                String[] newParts = new String[3];
                newParts[0] = parts[parts.length-3];
                newParts[1] = parts[parts.length-2];
                newParts[2] = parts[parts.length-1];
                newString = String.join(",", newParts);
            }
            product.setListimage(newString);
            APIService.apiService.productNotify(product).enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    if(response.isSuccessful()){
                        mProgressDialog.dismiss();
                        Toast.makeText(AddImageProduct.this, "Oke ", Toast.LENGTH_SHORT).show();
                        finish();
//                    Intent intent = new Intent(AddImageProduct.this, ProductFragment.class);
//
//                    startActivity(intent);
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