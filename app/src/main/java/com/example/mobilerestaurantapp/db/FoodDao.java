package com.example.mobilerestaurantapp.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mobilerestaurantapp.Food;

import java.util.List;

@Dao
public interface FoodDao {


    @Insert
    Void addFood(List<Food> foodList);

    @Query("SELECT * FROM food")
    List<Food> getFoodList();


}
