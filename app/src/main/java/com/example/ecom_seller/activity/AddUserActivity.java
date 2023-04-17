package com.example.ecom_seller.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecom_seller.R;
import com.example.ecom_seller.api.APIService;
import com.example.ecom_seller.model.ImageData;
import com.example.ecom_seller.model.User;
import com.example.ecom_seller.roomDatabase.Database.UserDatabase;
import com.example.ecom_seller.util.RealPathUtil;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserActivity extends AppCompatActivity {
    String id;
    EditText fullname, username, password, address, phone, email;
    TextView imageUserText;
    ImageView changeprofilepic, profilepic, btnBackAccount;
    Button addUser;
    ImageData imageData;
    int MY_REQUEST_CODE = 10;

    private Uri mUri;
    User user;
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
                            profilepic.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        AnhXa();


        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUri != null) {
                    callAPIUploadImage();
                }
                else{
                    GetSignUp();
                }
            }
        });
        changeprofilepic.setOnClickListener(new View.OnClickListener() {
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
    }

    private void AnhXa() {
        btnBackAccount = findViewById(R.id.btnBackAccount);
        changeprofilepic = findViewById(R.id.changeprofilepic);
        profilepic = findViewById(R.id.profilepic);
        fullname = findViewById(R.id.fullname);
        username = findViewById(R.id.usernameUser);
        password = findViewById(R.id.passwordUser);
        address = findViewById(R.id.addressUser);
        phone = findViewById(R.id.phoneUser);
        addUser = findViewById(R.id.btn_add_profile);
        imageUserText = findViewById(R.id.imageUserText);
        imageUserText.setVisibility(View.INVISIBLE);
        email = findViewById(R.id.addEmailUser);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Vui lòng đợi ...");
    }


    private void callAPIUploadImage() {

        String strRealPath = RealPathUtil.getRealPath(this, mUri); //lấy đường dẫn thực

        Log.e("TAG", strRealPath);
        File file = new File(strRealPath);

        RequestBody requestBodyAvt = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part partAvatar = MultipartBody.Part.createFormData("image", file.getName(), requestBodyAvt);

        if (CheckUser()) {
            APIService.apiService.uploadImages(partAvatar).enqueue(new Callback<ImageData>() {
                @Override
                public void onResponse(Call<ImageData> call, Response<ImageData> response) {

                    imageData = response.body();
                    imageUserText.setText(APIService.BASE_URL + "images/" + imageData.getName());
                    GetSignUp();
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


    private void GetSignUp() {

        if(CheckUser()){
            user = UploadUser();
            if (user != null) {

                APIService.apiService.addUser(user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        user = response.body();
                        if (user != null) {
                            Toast.makeText(getApplicationContext(), "thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Có lỗi ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

    }

    private Boolean CheckUser() {
        String username12 = username.getText().toString().trim();
        String fullname12 = fullname.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String phone12 = phone.getText().toString().trim();
        String address12 = address.getText().toString().trim();
        String email12 = email.getText().toString().trim();
        if (TextUtils.isEmpty(fullname12)) {
            fullname.setError("Please enter fullname");
            fullname.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(username12)) {
            username.setError("Please enter your username");
            username.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(pass)) {
            password.setError("Please enter password");
            password.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(email12)) {
            email.setError("Please enter your email");
            email.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(phone12)) {
            phone.setError("Please enter phone");
            phone.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(address12)) {
            address.setError("Please enter address");
            address.requestFocus();
            return false;
        }
        return true;
    }

    private User UploadUser() {
        User user = new User();
        String username12 = username.getText().toString().trim();
        String fullname12 = fullname.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String phone12 = phone.getText().toString().trim();
        String address12 = address.getText().toString().trim();
        String email12 = email.getText().toString().trim();
        if (CheckUser()) {
            Log.e("ImageTextView", imageUserText.getText().toString().trim());
            if (imageUserText.getText().toString().trim() != "") {
                user.setAvatar(imageUserText.getText().toString().trim());
            }
            user.setFullName(fullname12);
            user.setEmail(email12);
            user.setUsername(username12);
            user.setPassword(pass);
            user.setPhone(phone12);
            user.setAddress(address12);
        }
        return user;
    }
}