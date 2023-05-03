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
import com.example.ecom_seller.model.User;
import com.example.ecom_seller.util.RealPathUtil;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {
    Category cate;
    String id;
    MultipartBody.Part partAvatar;
    EditText nameCate;
    TextView imageUserText;
    ImageView imgUpdateCateimage, imageShowCate, btnBackAccount;
    Button btnAddCate,btnDeleteCate,btnMotifyCate;
    ImageData imageData;
    int MY_REQUEST_CODE = 10;

    private Uri mUri;

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
                            imageShowCate.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        AnhXa();
        imageShowCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });
        btnBackAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnAddCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUri != null) {
                    AddCate();
                }

            }
        });
        btnMotifyCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUri != null) {
                    AddImageAndMotifyCategory();
                }
                else{
                    MotifyCategory();
                }

            }
        });
        btnDeleteCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteCate();
            }
        });
    }

    private void AddImageAndMotifyCategory() {
        mProgressDialog.show();
        String strRealPath = RealPathUtil.getRealPath(this, mUri); //lấy đường dẫn thực

        Log.e("TAG", strRealPath);
        File file = new File(strRealPath);

        RequestBody requestBodyAvt = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        partAvatar = MultipartBody.Part.createFormData("image", file.getName(), requestBodyAvt);

        if (CheckCate()) {
            APIService.apiService.uploadImages(partAvatar).enqueue(new Callback<ImageData>() {
                @Override
                public void onResponse(Call<ImageData> call, Response<ImageData> response) {

                    imageData = response.body();
                    imageUserText.setText(APIService.BASE_URL + "images/" + imageData.getName());
                    MotifyCategory();
                }

                @Override
                public void onFailure(Call<ImageData> call, Throwable t) {
                    Log.d("TAG", t.getMessage());
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Thất Bại", Toast.LENGTH_LONG).show();

                }
            });
        }
    }



    private void DeleteCate() {
        mProgressDialog.show();
        final Category[] entity = new Category[1];
        final String[] message = {null};
        Log.e("TAG","IdDELETE" +id);
       APIService.apiService.removeCate(id).enqueue(new Callback<Category>() {
           @Override
           public void onResponse(Call<Category> call, Response<Category> response) {
              if(response.isSuccessful()){
                  entity[0] = response.body();
                  if(entity[0].getName().equals("Category đang có sản phẩm")){
                      Toast.makeText(CategoryActivity.this, entity[0].getName(), Toast.LENGTH_SHORT).show();
                  }
                  else{
                      Toast.makeText(CategoryActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    finish();
                  }
                  mProgressDialog.dismiss();
              }
           }

           @Override
           public void onFailure(Call<Category> call, Throwable t) {
               mProgressDialog.dismiss();
           }
       });
    }

    private void AnhXa() {

        btnBackAccount = findViewById(R.id.btnBackCate);
        imgUpdateCateimage = findViewById(R.id.imgUpdateCateimage);
        imageShowCate = findViewById(R.id.imageShowCate);
        nameCate = findViewById(R.id.namecate);
        btnAddCate = findViewById(R.id.btn_add_cate);
        btnDeleteCate = findViewById(R.id.btn_delete_category);
        btnMotifyCate = findViewById(R.id.btn_motifycategory);
        imageUserText = findViewById(R.id.imageCateUserText);
        imageUserText.setVisibility(View.INVISIBLE);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Vui lòng đợi ...");

        UploadData();
    }

    private void UploadData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            id  = bundle.getString("CateId");
            Log.e("TAG","idCate "+id);
            APIService.apiService.getByid(id).enqueue(new Callback<Category>() {
                @Override
                public void onResponse(Call<Category> call, Response<Category> response) {
                    if(response.isSuccessful()){
                        cate = response.body();
                        Log.e("TAG","Catename "+cate.getName());


                        nameCate.setText(cate.getName());

                        Glide.with(getApplicationContext()).load(cate.getImage()).into(imageShowCate);
                        imageUserText.setText(cate.getImage());
                        btnAddCate.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<Category> call, Throwable t) {

                }
            });


        }
        else{

            btnDeleteCate.setVisibility(View.INVISIBLE);
            btnMotifyCate.setVisibility(View.INVISIBLE);
        }
    }

    private void callAPIUploadImage(MultipartBody.Part partAvatar) {
        Log.e("TAG","partAvatar"+ partAvatar);
            APIService.apiService.uploadImages(partAvatar).enqueue(new Callback<ImageData>() {
                @Override
                public void onResponse(Call<ImageData> call, Response<ImageData> response) {
                    mProgressDialog.dismiss();
                    finish();

                }

                @Override
                public void onFailure(Call<ImageData> call, Throwable t) {
                    Log.d("TAG", t.getMessage());
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Lỗi khi up ảnh", Toast.LENGTH_LONG).show();

                }
            });
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

    private void AddCate() {

        if(CheckCate()){

            String strRealPath = RealPathUtil.getRealPath(this, mUri); //lấy đường dẫn thực

            Log.e("TAG", strRealPath);
            File file = new File(strRealPath);

            RequestBody requestBodyAvt = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            partAvatar = MultipartBody.Part.createFormData("image", file.getName(), requestBodyAvt);
            imageUserText.setText(APIService.BASE_URL + "images/" + file.getName());
            Log.e("TAG","imageText"+ imageUserText.getText());
            Log.e("TAG","imageTextPart"+ partAvatar.toString());
            cate = UploadCate();
            if (cate != null) {

                mProgressDialog.show();
                Log.e("TAG","Catename: "+cate.getName());
                APIService.apiService.addCate(cate).enqueue(new Callback<Category>() {
                    @Override
                    public void onResponse(Call<Category> call, Response<Category> response) {
                        if(response.isSuccessful()){
                            callAPIUploadImage(partAvatar);
                            Toast.makeText(getApplicationContext(), "thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Category> call, Throwable t) {

                        mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "tên mặt hàng đã tồn tại", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }

    }

    private Boolean CheckCate() {
        String nameCategory = nameCate.getText().toString().trim();
        if (TextUtils.isEmpty(nameCategory)) {
            nameCate.setError("Please enter name category");
            nameCate.requestFocus();
            return false;
        }
        return true;
    }

    private Category UploadCate() {
        if(cate == null){
            cate = new Category();
        }
        else{
            Log.e("TAG","id"+ id);
        }

        String nameCategory = nameCate.getText().toString().trim();
        if (CheckCate()) {
            Log.e("TAG","ImageCate"+ imageUserText.getText().toString().trim());
            if (imageUserText.getText().toString().trim() != "") {
                cate.setImage(imageUserText.getText().toString().trim());
            }
            Log.e("TAG","nameCate"+ nameCategory);
            cate.setName(nameCategory);
            cate.setUpdateat(null);
            cate.setCreateat(null);
            cate.setIsdeleted(false);

        }
        return cate;
    }
    private void MotifyCategory() {
        if(CheckCate()){
            cate = UploadCate();
            if (cate != null) {
                mProgressDialog.show();
                Log.e("TAG",cate.getId()+"\n"+cate.getName()+"\n"+cate.getImage()+"\n"+cate.getCreateat()+"\n"+cate.getUpdateat()+"\n"+cate.getIsdeleted());
                APIService.apiService.UpdateCate(cate).enqueue(new Callback<Category>() {
                    @Override
                    public void onResponse(Call<Category> call, Response<Category> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(CategoryActivity.this, "Thay đổi thành công", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                            finish();
                        }
                        else{
                            response.message();
                            mProgressDialog.dismiss();
                            Toast.makeText(CategoryActivity.this, response.message(), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Category> call, Throwable t) {
                        Toast.makeText(CategoryActivity.this, "Thay đổi thất bại", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
            else{
                Log.e("TAG","Ko Vào được If");
            }
        }
    }
}