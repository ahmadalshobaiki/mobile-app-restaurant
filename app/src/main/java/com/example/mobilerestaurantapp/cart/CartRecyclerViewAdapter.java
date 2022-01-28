package com.example.mobilerestaurantapp.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilerestaurantapp.R;
import com.example.mobilerestaurantapp.db.AppDatabase;
import com.example.mobilerestaurantapp.db.Cart;
import com.example.mobilerestaurantapp.db.CartDao;

import java.util.List;

public class CartRecyclerViewAdapter extends
        RecyclerView.Adapter<CartRecyclerViewAdapter.MyViewHolder> {


    private List<Cart> cartList;
    RecyclerView recyclerView;
    Context context;
    Integer userID;
    ImageButton btn_deleteOrder;

    // constructor - to create a Recyclerview Adapter for shopping cart, we need a list of Cart items
    public CartRecyclerViewAdapter(Context context, List<Cart> cartList,
                                   RecyclerView recyclerView, Integer userID){

        this.context = context;
        this.cartList = cartList;
        this.recyclerView = recyclerView;
        this.userID = userID;
    }

    // implement inner class MyViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder{

        // find the components in list_items_cart

        public TextView textView_foodName_cart, textView_foodPrice_cart,
        textView_foodQuantity_cart, textView_totalPrice_cart;


        // implement anonymous class constructor and initialize the components found
        // contains all the components of the row layout that we are going to recycle the data of
        public MyViewHolder(View view){
            super(view);

            textView_foodName_cart = view.findViewById(R.id.txtview_foodname_cart);
            textView_foodPrice_cart = view.findViewById(R.id.txtview_foodprice_cart);
            textView_foodQuantity_cart = view.findViewById(R.id.txtview_foodquantity_cart);
            textView_totalPrice_cart = view.findViewById(R.id.txtview_totalprice_cart);
            btn_deleteOrder = view.findViewById(R.id.btn_delete_jr);


        }
    }

    @NonNull
    @Override
    public CartRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // inflate the layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items_cart, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    // dynamically set the data into the row layout
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Cart cart = cartList.get(position);

        // set the text to food name in cart
        String foodName = cart.getFoodName();
        holder.textView_foodName_cart.setText(foodName);

        // set the text to food price in cart
        int foodPrice = cart.getFoodPrice();
        holder.textView_foodPrice_cart.setText("SR " + Integer.toString(foodPrice));

        // set the text to food quantity in cart
        int foodQuantity = cart.getFoodQuantity();
        holder.textView_foodQuantity_cart.setText("x" + Integer.toString(foodQuantity));

        int totalPrice = cart.getTotalPrice();
        holder.textView_totalPrice_cart.setText("SR " + Integer.toString(totalPrice));



        btn_deleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // remove an order from the cart
                AppDatabase appDatabase = AppDatabase.getDbInstance(context.getApplicationContext());
                CartDao cartDao = appDatabase.cartDao();

                int adapterPosition = holder.getAdapterPosition();

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        // delete the record in the database
                        Cart cart = cartList.get(adapterPosition);
                        cartDao.deleteRecord(cart.getId());
                        cartList.remove(adapterPosition);
                    }
                }).start();

                // update the recycler view
                notifyItemRemoved(adapterPosition);
//                notifyItemRangeChanged(adapterPosition, cartList.size());
                Toast.makeText(context.getApplicationContext(), "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }


}
