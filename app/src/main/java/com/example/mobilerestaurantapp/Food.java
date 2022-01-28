package com.example.mobilerestaurantapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "food")
public class Food {

    public static List<Food> foodList;

    public static List<Food> getFoodList() {

        if(foodList == null){
            foodList = new ArrayList<>();
        }

        foodList.add(new Food("Chicken", 30));
        foodList.add(new Food("Falafel", 15));
        foodList.add(new Food("Fries", 20));
        foodList.add(new Food("Shawarma", 25));
        foodList.add(new Food("Beef ", 35));
        foodList.add(new Food("Chicken Burger", 40));
        foodList.add(new Food("Beef Boorgir", 45));
        foodList.add(new Food("Pizza ", 50));
        foodList.add(new Food("Soup", 10));
        foodList.add(new Food("Beans", 5));
        foodList.add(new Food("Malukiya", 20));
        foodList.add(new Food("Salad", 20));
        return foodList;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;


    public void setId(int id) {
        this.id = id;
    }

    @ColumnInfo
    private String name;

    @ColumnInfo
    private int price;


    public int getId() {
        return id;
    }

    public Food(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
