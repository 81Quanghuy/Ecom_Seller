package com.example.ecom_seller.roomDatabase.Database;
import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import com.example.ecom_seller.model.Category;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Date;
public class CategoryConverter {
    @TypeConverter
    public static Category fromString(String value) {
        Type type = new TypeToken<Category>(){}.getType();
        return new Gson().fromJson(value, type);
    }

    @TypeConverter
    public static String fromCategory(Category category) {
        Gson gson = new Gson();
        String json = gson.toJson(category);
        return json;
    }

//    @TypeConverter
//
//    public String fromCategory(Category category) {
//        return category.getId();
//    }
//
//    @TypeConverter
//    public Category toCategory(String id) {
//        Category category = new Category();
//        category.setId(id);
//        return category;
//    }
}
