package com.example.ecom_seller.api;

import com.example.ecom_seller.model.ApiUser;
import com.example.ecom_seller.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {
    //public static final String BASE_URL="http://app.iotstar.vn/shoppingapp/";
    public static final String BASE_URL="http://192.168.1.13//:8080/user/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy MM dd HH:mm:ss").create();
    APIService apiService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(APIService.class);

    @POST("registrationapi.php")
    @FormUrlEncoded
    Call<ApiUser> login(@Query("apicall") String key, @Field("username") String username, @Field("password") String password);

    @POST("get")
    @FormUrlEncoded
    Call<User> loginWithLocal( @Field("username") String username, @Field("password") String password);

}
