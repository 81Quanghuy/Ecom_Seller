package com.example.ecom_seller.roomDatabase.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.ProvidedTypeConverter;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.ecom_seller.model.Product;
import com.example.ecom_seller.roomDatabase.DAO.ProductDao;

import retrofit2.Callback;

@Database(entities = {Product.class},version = 1)
@TypeConverters(CategoryConverter.class)
public abstract class ProductDatabase extends RoomDatabase {
    private static final String DATABASE_NAME="product.db";
    private static ProductDatabase instance;
    public static synchronized ProductDatabase getInstance(Context context){
        if(instance ==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),ProductDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public  abstract ProductDao productDao();
}
