package com.example.ecom_seller.dataLocal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.ecom_seller.activity.LoginActivity;
import com.example.ecom_seller.model.User;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_ID = "keyid";
    private static final String KEY_IMAGES = "keyimages";
    private static final String KEY_FIRSTNAME = "keyfirstname";
    private static final String KEY_LASTNAME = "keylastname";
    private static final String KEY_ADDRESS = "keyaddress";
    private static final String KEY_PHONE ="keyphone" ;
    private static final String KEY_STORE_ID = "keystoreid";

    private static SharedPrefManager mInstance;

    private static Context ctx;

    public SharedPrefManager(Context context) {
        ctx = context;
    }
    public static synchronized SharedPrefManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }
    public void userLogin(User user){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getUsername());
//        editor.putString(KEY_FIRSTNAME, user.getFirstName());
//        editor.putString(KEY_LASTNAME, user.getLastName());
//        editor.putString(KEY_EMAIL, user.getEmail());
//        editor.putString(KEY_ADDRESS, user.getAddress());
//        editor.putString(KEY_IMAGES, user.getAvatar());
//        editor.putString(KEY_PHONE, user.getPhone());
       // editor.putString(KEY_STORE_ID, user.getStores().getId());

        editor.apply();
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    public User getUser(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_ID, null),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_ADDRESS, null),
                sharedPreferences.getString(KEY_PHONE,null),
                sharedPreferences.getString(KEY_FIRSTNAME,null),
                sharedPreferences.getString(KEY_LASTNAME,null),
                sharedPreferences.getString(KEY_STORE_ID,null),

                sharedPreferences.getString(KEY_IMAGES, null)
        );
    }
    public void logout(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        ctx.startActivity(new Intent(ctx, LoginActivity.class));
    }

}
