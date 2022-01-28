package com.example.mobilerestaurantapp.Orders;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilerestaurantapp.R;
import com.example.mobilerestaurantapp.db.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class bill_page extends AppCompatActivity {

    TextView total_tv , items_tv;
    List<Bill> dataList = new ArrayList<>();
    AppDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_page);
        getSupportActionBar().hide();
        total_tv = findViewById(R.id.total_tv_bill);
        items_tv = findViewById(R.id.items_tv_bill);
        database = AppDatabase.getDbInstance(this);
        new Thread(new Runnable() {
            @Override
            public void run() {

                dataList = database.mainDao().getALLbill();

                Bill bill = new Bill();

                bill.setItems("pizza burger salad coca cola");
                bill.setTotal(50);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        total_tv.setText(String.valueOf(bill.getTotal()));
                        items_tv.setText(bill.getItems());
                    }
                });
            }
        }).start();



    }

}