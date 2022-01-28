package com.example.mobilerestaurantapp.Orders;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilerestaurantapp.R;
import com.example.mobilerestaurantapp.db.AppDatabase;


import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    // create variables
    private List<orders> dataList;
    private Activity activity;
    private AppDatabase database;
    Bill bill;

    // create constructor

    public MainAdapter(Activity activity, List<orders> dataList){
        this.activity = activity;
        this.dataList = dataList;
//        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // create view

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main, parent ,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //create main data
        orders data = dataList.get(position);
        // create database
        database  = AppDatabase.getDbInstance(activity);
        // set text as text views
        int idx = holder.getAdapterPosition() + 1;
        holder.idTV.setText(String.valueOf(idx));
        holder.itemTV.setText("Total items: " + data.getItem());
        holder.priceTV.setText("Total price: " + data.getPrice());
        holder.dateTV.setText("Date: " + data.getDate());

        holder.btDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int adapterPosition = holder.getAdapterPosition();

                // delete
               new Thread(new Runnable() {
                   @Override
                   public void run() {

                       orders d = dataList.get(adapterPosition);
                       database.mainDao().deleteOrder(d.getId());
                       dataList.remove(adapterPosition);
                   }
               }).start();


                // notify when deleted
                notifyItemRemoved(adapterPosition);
                notifyItemRangeChanged(adapterPosition,dataList.size());
                Toast.makeText(activity.getApplicationContext(), "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get the order ID and use it to query the bill table for the order details using
                // order id
                // ( WHERE order id = bill id )
                int adapterPosition = holder.getAdapterPosition();

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        orders order = dataList.get(adapterPosition);
                        int orderID = order.getId();

                        bill = new Bill();

                        bill = database.mainDao().getBillDetails(orderID);

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("hi jr", "run: " + activity);
                                AlertDialog alertDialog = new AlertDialog.Builder(view.getContext())
                                        .setTitle("Bill Details")
                                        .setMessage("-----------------------\n" +
                                                bill.items.replace(", ", "\n")
                                                + "-----------------------\n" + " SR " + bill.total)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        }).create();

                                alertDialog.show();
                            }
                        });

                    }
                }).start();

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        // create variable
        TextView  itemTV , priceTV ,idTV, dateTV;
        ImageView btDelete, btPreview;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idTV = itemView.findViewById(R.id.id_tv);
            itemTV = itemView.findViewById(R.id.tv_totalItems);
            priceTV = itemView.findViewById(R.id.tv_totalPrice);
            dateTV = itemView.findViewById(R.id.tv_date);
            btDelete  = itemView.findViewById(R.id.bt_delete);
            btPreview = itemView.findViewById(R.id.bt_preview);

        }
    }
}
