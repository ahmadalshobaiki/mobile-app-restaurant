package com.example.mobilerestaurantapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilerestaurantapp.db.AppDatabase;
import com.example.mobilerestaurantapp.db.Cart;
import com.example.mobilerestaurantapp.db.CartDao;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

// Recyclerview Adapter for Main Menu
public class MainMenuRecyclerAdapter extends RecyclerView.Adapter<MainMenuRecyclerAdapter.MyViewHolder> {


    private List<Food> foodList;
    RecyclerView recyclerView;
    Context context;
    ImageButton addToCart;
    Integer userID;


    final View.OnClickListener onClickListener = new MyOnClickListener();


    // constructor - to create a Recyclerview Adapter for Main menu, we need a list of foods
    public MainMenuRecyclerAdapter(Context context, List<Food> foodList,
                                   RecyclerView recyclerView, Integer userID) {
        this.context = context;
        this.foodList = foodList;
        this.recyclerView = recyclerView;
        this.userID = userID;
    }

    // implement inner class MyViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder {

        // find the components in list_items_mainmenu
        public TextView text_foodname;
        public MaterialTextView text_foodprice;
        public NumberPicker picker_spinner_jr;

        // implement anonymous class constructor and initialize the components found
        // contains all the components of the row layout that we are going to recycle the data of
        public MyViewHolder(View view) {
            super(view);
            text_foodname = view.findViewById(R.id.txtview_foodname);
            text_foodprice = view.findViewById(R.id.meowme);
            picker_spinner_jr = view.findViewById(R.id.picker_spinner_jr);
            addToCart = view.findViewById(R.id.btn_add_to_cart);


//            // retrieve value from the number picker
//            picker_spinner_jr.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//                @Override
//                public void onValueChange(NumberPicker numberPicker, int i, int i1) {
//                    int value = picker_spinner_jr.getValue();
//                    Toast.makeText(context.getApplicationContext(), "value: " + value, Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }

    // implement generated functions from abstract super class

    // once the view holder is created, bind the row layout to the view holder
    @NonNull
    @Override
    public MainMenuRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // inflate the layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items_mainmenu, parent, false);
        view.setOnClickListener(onClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.picker_spinner_jr.setMinValue(1);
        myViewHolder.picker_spinner_jr.setMaxValue(10);
        return myViewHolder;
    }

    // dynamically set data into the row layout
    @Override
    public void onBindViewHolder(@NonNull MainMenuRecyclerAdapter.MyViewHolder holder, int position) {
        Food food = foodList.get(position);

        // set the text to food name
        String name = food.getName();
        holder.text_foodname.setText(name);

        // set the text to food price
        int price = food.getPrice();
        holder.text_foodprice.setText("SR " + Integer.toString(price));

        // adds the item to the shopping cart
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AppDatabase appDatabase = AppDatabase.getDbInstance(context.getApplicationContext());
                CartDao cartDao = appDatabase.cartDao();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Cart cart = new Cart();

                        cart.setUserId(userID);
                        cart.setFoodName(name);
                        cart.setFoodPrice(price);
                        cart.setFoodQuantity(holder.picker_spinner_jr.getValue());
                        cart.setTotalPrice(price*holder.picker_spinner_jr.getValue());

                        cartDao.addToCart(cart);

                    }
                }).start();
                Toast.makeText(context.getApplicationContext(), "Added: " + name
                        + " - SR each " + price + " - Quantity: " + holder.picker_spinner_jr.getValue() + " Total: " +
                                (price * holder.picker_spinner_jr.getValue()) + " to cart",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    private class MyOnClickListener implements View.OnClickListener {


        @Override
        public void onClick(View view) {
            int itemPosition = recyclerView.getChildLayoutPosition(view);
            String item = foodList.get(itemPosition).getName();
            Toast.makeText(context, item, Toast.LENGTH_SHORT).show();
        }
    }
}
