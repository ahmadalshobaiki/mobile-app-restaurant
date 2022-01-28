package com.example.mobilerestaurantapp.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {

     @Insert
     Void registerUser(UserEntity userEntity);

     @Query("SELECT * from users where username =(:username) and password = (:password)")
     UserEntity login(String username,String password);

     @Query("SELECT * from users where id =(:id) ")
     UserEntity getUser(int id);

     @Update
     void updateUser(UserEntity userEntity);




}
