package com.example.ecom_seller.activity;

import static com.example.ecom_seller.dataLocal.DataLocalManager.getFirstInstall;
import static com.example.ecom_seller.dataLocal.DataLocalManager.setFirstInstall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecom_seller.R;
import com.example.ecom_seller.dataLocal.DataLocalManager;
import com.example.ecom_seller.dataLocal.SharedPrefManager;
import com.example.ecom_seller.api.APIService;
import com.example.ecom_seller.model.User;
import com.example.ecom_seller.roomDatabase.Database.UserDatabase;
import com.example.ecom_seller.util.PasswordEncoder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername,edtPassword;
    Button login;
    User user ;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnhXa();
        if(user!=null){
            if(isCheckExist(user)){
                finish();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetUser();
            }
        });
    }
    private void FirstInstall() {
        if(!DataLocalManager.getFirstInstall()){
            Toast.makeText(this, "First Install App", Toast.LENGTH_LONG).show();
            DataLocalManager.setFirstInstall(true);
        }
    }
    private boolean isCheckExist(@NonNull User user){
        List<User> list = UserDatabase.getInstance(this).usersDao().checkUser(user.getUsername());
        return list != null && !list.isEmpty();
    }

    private void GetUser() {
        String username = edtUsername.getText().toString().trim();
        String password=  PasswordEncoder.encode(edtPassword.getText().toString().trim());
        //String password = edtPassword.getText().toString().trim();
        if(TextUtils.isEmpty(username)){
            edtUsername.setError("Please enter your username");
            edtUsername.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(password)){
            edtPassword.setError("Please enter password");
            edtPassword.requestFocus();
            return;
        }
        APIService.apiService.loginWithLocal(username,password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                user = response.body();
                if(user != null){

                    AddUser(user);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Sai tên đăng nhập hoặc mật khẩu",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("TAG",t.getMessage()+"");
                Toast.makeText(getApplicationContext(),"Sai tên đăng nhập hoặc mật khẩu",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AddUser(User user ) {
            UserDatabase.getInstance(this).usersDao().insertUser(user);
    }


    private void AnhXa() {
        List<User> userList =UserDatabase.getInstance(getApplicationContext()).usersDao().getAll();
        if(!userList.isEmpty() && userList.size()>0){
            user = UserDatabase.getInstance(getApplicationContext()).usersDao().getAll().get(0);
        }
        edtUsername = findViewById(R.id.username);
        edtPassword = findViewById(R.id.password);
        login = findViewById(R.id.loginBtn);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
    }

}