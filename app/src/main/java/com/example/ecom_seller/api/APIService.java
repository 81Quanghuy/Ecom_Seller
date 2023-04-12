package com.example.ecom_seller.api;

import androidx.room.Delete;

import com.example.ecom_seller.model.ImageData;
import com.example.ecom_seller.model.Order;
import com.example.ecom_seller.model.OrderItem;
import com.example.ecom_seller.model.Product;
import com.example.ecom_seller.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIService {
    //public static final String BASE_URL="http://app.iotstar.vn/shoppingapp/";
    public static final String BASE_URL="https://ecomserver.up.railway.app/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy MM dd HH:mm:ss").create();
    APIService apiService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(APIService.class);

    @POST("user/get")
    @FormUrlEncoded
    Call<User> loginWithLocal( @Field("username") String username, @Field("password") String password);

    @POST("user")
    @FormUrlEncoded
    Call<User> signUp( @Field("username") String username, @Field("password") String password);
    @POST("products/delete")
    @FormUrlEncoded
    Call<String> deleteProduct(@Field("id") String id);

    @POST("images")
    @Multipart
    Call<ImageData> uploadImages(@Part MultipartBody.Part image);

    @POST("user/updateUser")
    Call<User> updateUser(@Body User user);

    @GET("orderItem/getHuy")
    Call<List<OrderItem>> getOrderHuy();

    @GET("user/list")
    Call<List<User>> getUserAll();


}
