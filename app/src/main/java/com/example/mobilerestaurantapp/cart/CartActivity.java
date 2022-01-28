package com.example.mobilerestaurantapp.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilerestaurantapp.Orders.MainDao;
import com.example.mobilerestaurantapp.Orders.Bill;
import com.example.mobilerestaurantapp.Orders.orders;
import com.example.mobilerestaurantapp.R;
import com.example.mobilerestaurantapp.db.AppDatabase;
import com.example.mobilerestaurantapp.db.Cart;
import com.example.mobilerestaurantapp.db.CartDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    View view;
    private List<Cart> cartList;
    private RecyclerView recyclerView;
    CartRecyclerViewAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    AppDatabase appDatabase;
    Bundle bundle;
    Button button_payment;
    TextView textView_grandTotal, textView_totalItems;

    EditText edtTxtCreditCardNo, edtTxtAddress, edtTxtFirstName, edtTxtLastname, edtTxtCVV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recyclerView_cart);
        button_payment = findViewById(R.id.btn_confirm_payment);
        edtTxtCreditCardNo = findViewById(R.id.edttxt_payment_number);
        edtTxtAddress = findViewById(R.id.edttxt_paymentAddress);
        edtTxtFirstName = findViewById(R.id.edttxt_firstName);
        edtTxtLastname = findViewById(R.id.edttxt_lastName);
        edtTxtCVV = findViewById(R.id.edttxt_cvv);






        textView_grandTotal = findViewById(R.id.txtview_grandTotal_cart);
        textView_totalItems = findViewById(R.id.txtview_totalItems_cart);

        // get the intent extras from the logged in user
        bundle = getIntent().getExtras();

        cartList = new ArrayList<>();

        // initialize an adapter and pass the userID
        setAdapter(bundle.getInt("userID"));


    }

    private void setAdapter(Integer userID){
        new Thread(new Runnable() {
            @Override
            public void run() {
                appDatabase = AppDatabase.getDbInstance(getApplicationContext());
                CartDao cartDao = appDatabase.cartDao();

                // display cart items in shopping cart
                cartList = cartDao.getCartList();

                int grandTotal = 0;
                int totalItems = 0;
                for (int i = 0; i < cartList.size(); i++){

                    grandTotal = grandTotal + cartList.get(i).getTotalPrice();
                    totalItems = totalItems + cartList.get(i).getFoodQuantity();
                }

                textView_grandTotal.setText("Grand Total: " + String.valueOf(grandTotal) + " SR ");
                textView_totalItems.setText("Total Items: " + String.valueOf(totalItems) + " ");

                confirmPayment(grandTotal, totalItems);


                runOnUiThread(()->{
                    layoutManager = new LinearLayoutManager(getApplicationContext());
                    adapter = new CartRecyclerViewAdapter(getApplicationContext(), cartList, recyclerView, userID);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
                });
            }
        }).start();
    }


    private void confirmPayment(int grandTotal, int totalItems){
        button_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(recyclerView.getAdapter().getItemCount() == 0){
                    Toast.makeText(getApplicationContext(), "Place an order first!", Toast.LENGTH_SHORT).show();
                }else if(edtTxtCreditCardNo.getText().toString().isEmpty()
                        || edtTxtAddress.getText().toString().isEmpty()
                        || edtTxtFirstName.getText().toString().isEmpty()
                        || edtTxtLastname.getText().toString().isEmpty()
                        || edtTxtCVV.getText().toString().isEmpty()){

                    Toast.makeText(getApplicationContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show();

                } else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            appDatabase = AppDatabase.getDbInstance(getApplicationContext());
                            CartDao cartDao = appDatabase.cartDao();
                            MainDao orderDao = appDatabase.mainDao();

                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm aaa");
                            String date = dateFormat.format(calendar.getTime());

                            // insert into orders table before delete
                            orders order = new orders();
                            order.userID = bundle.getInt("userID");
                            order.item = totalItems;
                            order.price = grandTotal;
                            order.date = date;
                            orderDao.insert(order);

                            // insert into bill table before delete
                            StringBuilder stringBuilder = new StringBuilder();

                            Bill bill = new Bill();
                            bill.userID = bundle.getInt("userID");


                            // get all the orders and append to string builder
                            for(int i = 0; i < cartList.size(); i++){

                                String foodName = cartList.get(i).getFoodName();
                                String foodPrice = cartList.get(i).getFoodPrice().toString();
                                String foodQuantity = cartList.get(i).getFoodQuantity().toString();
                                String foodTotal =  Integer.toString(cartList.get(i).getFoodPrice() *
                                        cartList.get(i).getFoodQuantity());


                                stringBuilder.append(foodName + " SR " +  foodPrice + " x"
                                        +foodQuantity + " SR" + foodTotal + ", ");
                            }

                            bill.items = stringBuilder.toString();
                            bill.total = grandTotal;


                            orderDao.insertbill(bill);



                            // delete user shopping cart
                            cartDao.deleteAll(bundle.getInt("userID"));


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Your order has been placed, thank you :)", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        }
                    }).start();
                }
            }
        });
    }
}