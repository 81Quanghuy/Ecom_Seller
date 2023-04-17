package com.example.ecom_seller.roomDatabase.Database;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
//    @TypeConverter
//    public static Store fromString(String value) {
//        return value == null ? null : new Store(value);
//    }
//
//    @TypeConverter
//    public static String storeToString(Store store) {
//        return store == null ? null : String.valueOf(store.getId());
//    }
}