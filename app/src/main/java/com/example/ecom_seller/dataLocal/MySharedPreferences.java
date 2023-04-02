package com.example.ecom_seller.dataLocal;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class MySharedPreferences {
    private static String MY_SHARED_PREFERENCES = "MY_SHARED_PREFERENCES";
    private Context mContext;

    public MySharedPreferences(Context context) {
        this.mContext = context;
    }

    //put dữ liệu vào sharedpreferences
    public void putBooleanValue(String key, boolean vaule){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, vaule);
        editor.apply();
    }

    //get dữ liệu vào sharedpreferecens
    public boolean getBooleanVaule(String key){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES,
                Context.MODE_PRIVATE);

        return sharedPreferences.getBoolean(key, false);
    }

    public void putStringValue(String key, String vaule){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, vaule);
        editor.apply();
    }

    public String getStringVaule(String key){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES,
                Context.MODE_PRIVATE);

        return sharedPreferences.getString(key, "");
    }

    public void putStringSetValue(String key, Set<String> vaule){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, vaule);
        editor.apply();
    }

    public Set<String> getStringSetVaule(String key){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
        Set<String> valueDefault = new HashSet<>();
        return sharedPreferences.getStringSet(key, valueDefault);
    }
}
