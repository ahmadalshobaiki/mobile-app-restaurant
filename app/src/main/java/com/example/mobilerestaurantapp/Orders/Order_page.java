package com.example.mobilerestaurantapp.Orders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.example.mobilerestaurantapp.R;
import com.example.mobilerestaurantapp.db.AppDatabase;


import java.util.ArrayList;
import java.util.List;

public class Order_page extends AppCompatActivity {



    RecyclerView recyclerView;

    List<orders> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    AppDatabase database;
    MainAdapter adapter;
    Button gotoAdd;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);
        getSupportActionBar().hide();


        // get bundle data (userID)
        bundle = getIntent().getExtras();

        // get components
        recyclerView = findViewById(R.id.recycler_view);


        // run on thread
         new Thread(new Runnable() {
             @Override
             public void run() {

                 // initialize database
                 database = AppDatabase.getDbInstance(getApplicationContext());
                 // get the users order by userID
                 dataList = database.mainDao().getALL(bundle.getInt("userID"));

                 // run on ui thread
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         //initialize layout
                         linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                         //initialize adapter
                         adapter = new MainAdapter(Order_page.this, dataList);

                         // set layout
                         recyclerView.setLayoutManager(linearLayoutManager);
                         // set adapter giving us null object reference
                         recyclerView.setAdapter(adapter);
                     }
                 });
                 // run on thread
                 // dataList.clear();
                 // dataList.addAll(database.mainDao().getALL());
                 // recyclerView.getRecycledViewPool().clear();
                 // adapter.notifyDataSetChanged();
             }
         }).start();
    }

}
