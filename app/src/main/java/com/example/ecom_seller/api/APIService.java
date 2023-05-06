package com.example.ecom_seller.api;

import androidx.room.Delete;

import com.example.ecom_seller.model.Category;
import com.example.ecom_seller.model.ImageData;
import com.example.ecom_seller.model.Order;
import com.example.ecom_seller.model.OrderItem;
import com.example.ecom_seller.model.Product;
import com.example.ecom_seller.model.Review;
import com.example.ecom_seller.model.StatusOrder;
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
    public static final String BASE_URL="https://ecomserver1.up.railway.app/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy MM dd HH:mm:ss").create();
    APIService apiService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(APIService.class);
    @POST("reviews/product")
    Call<List<Review>> getReviewByProduct(@Body Product product);
    @POST("user/get")
    @FormUrlEncoded
    Call<User> loginWithLocal( @Field("username") String username, @Field("password") String password);
    @GET("products/list")
    Call<List<Product>> getProducts();
    @POST("products/notify")
    Call<Product> productNotify(@Body Product product);
    @POST("products/getId")
    @FormUrlEncoded
    Call<Product> ProductGetData(@Field("id") String id);
    @GET("categories/list")
    Call<List<Category>> getCategories();

    @POST("products/categoryName")
    @FormUrlEncoded
    Call<List<Product>> ListProductByCate(@Field("name") String name);
    @POST("categories/add")
    Call<Category> addCate(@Body Category category);

    @POST("categories/getById")
    @FormUrlEncoded
    Call<Category> getByid(@Field("id")String id);
    @POST("categories/update")
    Call<Category> UpdateCate(@Body Category category);

    @POST("categories/delete")
    @FormUrlEncoded
    Call<Category> removeCate(@Field("id") String id);
    @POST("user/getUserById")
    @FormUrlEncoded
    Call<User> getUserById(@Field("id")String id);

    @POST("user/delete")
    @FormUrlEncoded
    Call<String> DeleteUserById(@Field("id")String id);

    @POST("user/active-run")
    @FormUrlEncoded
    Call<String> ToggleActiveUserById(@Field("id")String id);

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

    @POST("order/updateStatusAll")
    @FormUrlEncoded
    Call<List<Order>> updateStatusAll(@Field("status") StatusOrder Status,@Field("statusChange") StatusOrder statusChange);
    @POST("order/getList")
    @FormUrlEncoded
    Call<List<Order>> getOrderList(@Field("status") StatusOrder Status);

    @GET("user/list")
    Call<List<User>> getUserAll();

    @POST("user/add")
    Call<User> addUser(@Body User user);

    @POST("user/active")
    @FormUrlEncoded
    Call<List<User>> getUsers(@Field("isActive") Boolean isActive);

    @POST("orderItem/get")
    @FormUrlEncoded
    Call<List<OrderItem>> getOrderItemList(@Field("id")String id);

    @POST("/order/changeStatus")
    @FormUrlEncoded
    Call<Order> changeStatuss(@Field("id")String id,@Field("status") StatusOrder status);

    @POST("order/delete")
    Call<String> deleteOrder(@Body Order order);

    @POST("products/getId")
    @FormUrlEncoded
    Call<Product> getProductById(@Field("id") String id);

    @POST("products/active")
    @FormUrlEncoded
    Call<Product> ToggeActive(@Field("id") String id,@Field("isselling") Boolean isselling);
}
