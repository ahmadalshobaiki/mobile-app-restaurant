package com.example.mobilerestaurantapp.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mobilerestaurantapp.Food;
import com.example.mobilerestaurantapp.Orders.MainDao;
import com.example.mobilerestaurantapp.Orders.*;

@Database(entities = {UserEntity.class, orders.class, Bill.class, Food.class, Cart.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String dbname = "AppDB";
    private static AppDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract MainDao mainDao();
    public abstract FoodDao foodDao();
    public abstract CartDao cartDao();

    public static synchronized AppDatabase getDbInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, dbname).fallbackToDestructiveMigration().build();

        }
        return INSTANCE;
    }

}
