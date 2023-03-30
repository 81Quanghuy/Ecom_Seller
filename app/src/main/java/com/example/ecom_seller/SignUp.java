package com.example.ecom_seller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {

    TextView sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        AnhXa();
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void AnhXa() {
        sign_in = findViewById(R.id.sign_in_change);
    }
}