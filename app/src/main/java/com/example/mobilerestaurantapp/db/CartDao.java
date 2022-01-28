package com.example.mobilerestaurantapp.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CartDao {


    @Query("SELECT * FROM cart")
    List<Cart> getCartList();

    @Insert
    Void addToCart(Cart cartValues);

    // delete *ALL* items of a user in the cart
    @Query("DELETE FROM cart WHERE userID = :userID")
    void deleteAll(Integer userID);

    // delete *AN* item of a user in the cart
    @Query("DELETE FROM cart WHERE id = :itemID")
    void deleteRecord(Integer itemID);


}
