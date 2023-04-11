package com.example.ecom_seller.roomDatabase.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ecom_seller.model.User;

import java.util.List;

@Dao
public interface UsersDao {
    @Query("Select * from user")
    List<User> getAll();
    @Query("Select * from user where id in (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE id = :userId")
    User getUserById(int userId);
    @Insert
    void insertAll(User... users);
    @Insert
    void insertUser(User user);
    @Update
    void update(User... users);

    @Delete
    void delete(User user);

    @Query("Select * from user where username= :username")
    List<User> checkUser(String username);
    @Update
    void updateUser(User user);
}
