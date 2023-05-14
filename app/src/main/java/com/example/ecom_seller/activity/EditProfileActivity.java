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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecom_seller.R;
import com.example.ecom_seller.api.APIService;
import com.example.ecom_seller.model.ImageData;
import com.example.ecom_seller.model.User;
import com.example.ecom_seller.roomDatabase.Database.UserDatabase;
import com.example.ecom_seller.util.PasswordEncoder;
import com.example.ecom_seller.util.RealPathUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    ImageView btnBackAccount;
    String id;
    Boolean isActive;
    EditText email,name, phone,address,password;

    AppCompatButton btnCapNhat,btnDelete,btnUnActive;
    TextView image;
    ImageView imgProfle;

    ImageData imageData;
    int MY_REQUEST_CODE = 10;

    private Uri mUri;
    User user;
    String pathImages;

    ProgressDialog mProgressDialog;

    Call<String> response;

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data == null){
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imgProfle.setImageBitmap(bitmap);
                        }catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        AnhXa();
        btnBackAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mUri != null){
                    callAPIUploadImage();
                }
                else{
                    callAPIUploadUser(user);
                }

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mProgressDialog.show();
//                DeleteAccount(user);
                Toast.makeText(getApplicationContext(), "Chức năng đang phát triển", Toast.LENGTH_LONG).show();
            }
        });
        btnUnActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressDialog.show();
                UnActiveAccount(user);
            }
        });
        imgProfle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });
    }

    private void UnActiveAccount(User user){
        APIService.apiService.ToggleActiveUserById(user.getId()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    mProgressDialog.dismiss();
                    finish();
                    Toast.makeText(getApplicationContext(), "Thành Công", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mProgressDialog.dismiss();
                finish();
                Toast.makeText(getApplicationContext(), "Thành Công", Toast.LENGTH_LONG).show();

            }
        });
    }
    private void DeleteAccount(User user) {
        APIService.apiService.DeleteUserById(user.getId()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    mProgressDialog.dismiss();
                    finish();
                    Toast.makeText(getApplicationContext(), "Thành Công", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(getApplicationContext(), " Thành Công", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void UploadUser(User user){
        Log.e("TestUPload",image.getText().toString().trim());
        if(image.getText().toString().trim()!= ""){
            user.setAvatar(image.getText().toString());
        }

        user.setFullName(name.getText().toString());
        if(password.getText().toString().trim() != ""){
            user.setPassword(PasswordEncoder.encode(password.getText().toString().trim()));
        }
        else {
            user.setPassword(user.getPassword());
        }
        user.setPhone(phone.getText().toString());
        user.setAddress(address.getText().toString());
        user.setEmail(email.getText().toString());
        UserDatabase.getInstance(getApplicationContext()).usersDao().updateUser(user);
    }

    private void AnhXa() {
        btnCapNhat = findViewById(R.id.btn_update_profile);
        //init progess
        mProgressDialog = new ProgressDialog(this );
        mProgressDialog.setMessage("Vui lòng đợi ...");
        btnBackAccount = findViewById(R.id.btnBackAccount1);
        btnUnActive =findViewById(R.id.btn_unActive_account);
        imgProfle = findViewById(R.id.profilepic);

        name = findViewById(R.id.fullname);
        email = findViewById(R.id.addEmailUser);
        phone = findViewById(R.id.phoneUser);
        address = findViewById(R.id.addressUser);
        password =findViewById(R.id.passwordUser);
        image =findViewById(R.id.imageUserText);
        image.setVisibility(View.INVISIBLE);
        btnDelete = findViewById(R.id.btn_delete_account);

        UploadData();
    }
private void UploadData(){

    Intent intent = getIntent();
    Bundle bundle = intent.getExtras();
    if (bundle != null) {
        id = bundle.getString("idUser");
        getUserById(id);
    }
    else{
        btnDelete.setVisibility(View.INVISIBLE);
        btnUnActive.setVisibility(View.INVISIBLE);
        user = UserDatabase.getInstance(getApplicationContext()).usersDao().getAll().get(0);
        Glide.with(getApplicationContext()).load(user.getAvatar()).into(imgProfle);
        image.setText(user.getAvatar());
        name.setText(user.getFullName());
        email.setText(user.getEmail());
        phone.setText(user.getPhone());
        address.setText(user.getAddress());
        //password.setText(user.getPassword());

    }



}

    private void getUserById(String id) {
        APIService.apiService.getUserById(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()){
                    user = response.body();
                    Glide.with(getApplicationContext()).load(user.getAvatar()).into(imgProfle);
                    image.setText(user.getAvatar());
                    name.setText(user.getFullName());
                    email.setText(user.getEmail());
                    phone.setText(user.getPhone());
                    address.setText(user.getAddress());
                    //password.setText("");
                    isActive = user.getActive();
                    if(isActive){
                        btnUnActive.setText("Tắt hoạt động");
                        btnDelete.setVisibility(View.INVISIBLE);
                    }
                    else{
                        btnUnActive.setText("Bật hoạt động");
                        btnCapNhat.setVisibility(View.INVISIBLE);
                    }
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    private void callAPIUploadImage() {
        mProgressDialog.show();

        String strRealPath = RealPathUtil.getRealPath(this, mUri); //lấy đường dẫn thực

        Log.e("TAG", strRealPath);
        File file = new File(strRealPath);

        RequestBody requestBodyAvt = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part partAvatar = MultipartBody.Part.createFormData("image", file.getName(),requestBodyAvt);
        APIService.apiService.uploadImages(partAvatar).enqueue(new Callback<ImageData>() {
            @Override
            public void onResponse(Call<ImageData> call, Response<ImageData> response) {

                imageData = response.body();
                image.setText(APIService.BASE_URL+"images/"+ imageData.getName());
                callAPIUploadUser(user);
            }

            @Override
            public void onFailure(Call<ImageData> call, Throwable t) {
                Log.d("TAG", t.getMessage());
                mProgressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Thất Bại", Toast.LENGTH_LONG).show();
                finish();

            }
        });

    }

    private void onClickRequestPermission() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            //ko cần cấp phép
            openGallery();
            return;
        }

        //xin cấp phép thành công
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            openGallery();// mở gallery
        }else{
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
        if(requestCode == MY_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
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
    private void callAPIUploadUser(User user) {

        UploadUser(user);
        APIService.apiService.updateUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                mProgressDialog.dismiss();
                UploadData();
                Toast.makeText(getApplicationContext(), "Thành Công", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                mProgressDialog.dismiss();
                Log.d("TAG", t.getMessage());
                Toast.makeText(getApplicationContext(), "Thất Bại", Toast.LENGTH_LONG).show();
            }
        });
    }

}