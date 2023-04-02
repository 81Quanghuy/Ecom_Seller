package com.example.ecom_seller.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecom_seller.R;


public class SearchActivity extends AppCompatActivity {
    AutoCompleteTextView autoCompleteTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        AnhXa();

        String[] country = getResources().getStringArray(R.array.country);//sau này thay bằng tên sp import vào
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, country);
        autoCompleteTextView.setAdapter(arrayAdapter);
    }

    private void AnhXa() {
        autoCompleteTextView = findViewById(R.id.autoCompleteTv);
    }
}