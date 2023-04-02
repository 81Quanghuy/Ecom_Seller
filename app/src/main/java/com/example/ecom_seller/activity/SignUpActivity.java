package com.example.ecom_seller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecom_seller.R;
import com.example.ecom_seller.api.APIService;
import com.example.ecom_seller.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    TextView sign_in;

    User user;
    EditText editUsername,editPass,editPassAgain;
    Button signInBtn;
    User use;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        AnhXa();

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetSignUp();
            }
        });
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void GetSignUp() {
        String username = editUsername.getText().toString().trim();
        String password=  editPass.getText().toString().trim();
        String rePassword=  editPassAgain.getText().toString().trim();
        if(TextUtils.isEmpty(username)){
            editUsername.setError("Please enter your username");
            editUsername.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(password)){
            editPass.setError("Please enter password");
            editPass.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(rePassword)){
            editPassAgain.setError("Please enter password");
            editPassAgain.requestFocus();
            return;
        }
        if(!password.equals(rePassword)){
            Toast.makeText(getApplicationContext(),"Nhập khẩu không khớp",Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            APIService.apiService.signUp(username,password).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    user = response.body();
                    if(user != null){
                        Toast.makeText(getApplicationContext(),"Chúc mừng bạn đã đăng ký thành công",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"tên đăng nhập đã tồn tại",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"tên đăng nhập đã tồn tại",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void AnhXa() {
        sign_in = findViewById(R.id.sign_in_change);
        signInBtn = findViewById(R.id.register_btn);
        editUsername = findViewById(R.id.username01);
        editPass = findViewById(R.id.password01);
        editPassAgain = findViewById(R.id.repassword);
        progressBar  = findViewById(R.id.progressBarSignUp);
        progressBar.setVisibility(View.INVISIBLE);
    }
}