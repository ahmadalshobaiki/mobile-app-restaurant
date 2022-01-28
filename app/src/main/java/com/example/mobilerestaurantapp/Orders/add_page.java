package com.example.mobilerestaurantapp.Orders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobilerestaurantapp.R;
import com.example.mobilerestaurantapp.db.AppDatabase;

public class add_page extends AppCompatActivity {

    EditText nameED, totalItemsED, totalPriceED, dateED;
    Button btAdd, gotoOrders;
    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_add_page);

        // get bundle data (userID)
        bundle = getIntent().getExtras();

        totalItemsED = findViewById(R.id.edit_text_item);
        totalPriceED = findViewById(R.id.edit_text_price);
        dateED = findViewById(R.id.edit_text_date);
        gotoOrders = findViewById(R.id.btn_gotoorders);
        btAdd = findViewById(R.id.bt_add);

        gotoOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                totalItemsED.setText("");
                totalPriceED.setText("");

                Intent i = new Intent(add_page.this, Order_page.class);
                i.putExtra("userID", bundle.getInt("userID"));
                startActivity(i);

            }
        });

    }



    public void addfun(View view) {

        // add to database

        AppDatabase appDatabase = AppDatabase.getDbInstance(getApplicationContext());
        MainDao mainDao = appDatabase.mainDao();
        orders orders = new orders();

        orders.userID = bundle.getInt("userID");
//        orders.item = totalItemsED.getText().toString();
//        orders.price = totalPriceED.getText().toString();
        orders.date = dateED.getText().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mainDao.insert(orders);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "User id: "+ bundle.getInt("userID") +
                                        "Data added", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();



    }
}