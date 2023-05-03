package com.example.ecom_seller.roomDatabase.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ecom_seller.model.Product;
import com.example.ecom_seller.model.User;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("Select * from product")
    List<Product> getAll();
    @Query("Select * from product where id in (:productIds)")
    List<User> loadAllByIds(String[] productIds);

    @Query("SELECT * FROM product WHERE id = :productId")
    Product getProductById(String productId);
    @Insert
    void insertAll(Product... products);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProduct(Product product);
    @Update
    void update(Product... products);
    @Query("DELETE FROM product")
    void deleteAll();
    @Delete
    void delete(Product product);

    @Query("Select * from product where name= :name")
    List<Product> checkUser(String name);
    @Update
    void updateProduct(Product product);
}
