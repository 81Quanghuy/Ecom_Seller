package com.example.ecom_seller.dataLocal;

import android.app.Application;

//Khởi tạo phạm vi sử dụng cho project
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(getApplicationContext()); //Khởi tạo datalocal manager cho toàn app
    }
}
