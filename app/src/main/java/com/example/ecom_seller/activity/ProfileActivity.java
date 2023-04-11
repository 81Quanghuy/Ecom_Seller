package com.example.ecom_seller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ecom_seller.R;
import com.example.ecom_seller.model.User;
import com.example.ecom_seller.roomDatabase.Database.UserDatabase;

public class ProfileActivity extends AppCompatActivity {

    ImageView imageProfile;
    TextView full_name, email,password,phone,address;
    Button btnUpdateProfile;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        GetData();
        AnhXa();
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
                GetData();
            }
        });
    }

    private void AnhXa() {
        imageProfile = findViewById(R.id.imgProfile1);
        full_name = findViewById(R.id.tvNameProfile);
        email = findViewById(R.id.tvMail);
        password =findViewById(R.id.tvPassProfile);
        phone = findViewById(R.id.textView17);
        address = findViewById(R.id.textView21);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
        Glide.with(getApplicationContext()).load(user.getAvatar()).into(imageProfile);

        full_name.setText(user.getFullName());
        email.setText(user.getEmail());
        password.setText(user.getPassword());
        phone.setText(user.getPhone());
        address.setText(user.getAddress());

    }
    private void GetData() {


        user = UserDatabase.getInstance(getApplicationContext()).usersDao().getAll().get(0);

    }
}