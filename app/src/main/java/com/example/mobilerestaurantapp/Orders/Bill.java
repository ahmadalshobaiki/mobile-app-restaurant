package com.example.mobilerestaurantapp.Orders;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Bill implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "userID")
    public Integer userID;

    @ColumnInfo(name = "orderID")
    public Integer orderID;

    @ColumnInfo(name = "ITEMS")
    public String items;

    @ColumnInfo(name = "TOTAL")
    public int total;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
