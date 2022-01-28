package com.example.mobilerestaurantapp.Orders;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MainDao {

    // insert orders
    @Insert(onConflict = REPLACE)
    void insert(orders mainData);

    // insert bills
    @Insert(onConflict = REPLACE)
    void insertbill(Bill billTable);

    // delete orders
    @Delete
    void delete(orders mainData);

    // delete a specific order
    @Query("DELETE FROM orders WHERE id = :id")
    void deleteOrder(int id);

    // delete bills
    @Delete
    void deletebill(Bill billTable);

    // wipe all orders
    @Delete
    void reset(List<orders> mainData);

    @Delete
    void resetbill(List<Bill> Bill);

    //update
    @Query("UPDATE Bill SET ITEMS = :item ,TOTAL = :total  WHERE ID = :ID")
    void update(int ID, String item, int total);

    //get all data
    @Query("SELECT * FROM orders WHERE USERID = :userID ORDER BY DATE DESC")
    List<orders> getALL(int userID);

    //get all data
    @Query("SELECT * FROM Bill")
    List<Bill> getALLbill();

    // get one bill data of a user
    @Query("SELECT * FROM Bill WHERE id = :orderID")
    Bill getBillDetails(int orderID);

}
