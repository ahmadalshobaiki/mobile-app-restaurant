package com.example.mobilerestaurantapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.mobilerestaurantapp.cart.CartActivity;
import com.example.mobilerestaurantapp.db.AppDatabase;
import com.example.mobilerestaurantapp.db.FoodDao;

import java.util.ArrayList;
import java.util.List;

public class MainMenuFragment extends Fragment {

    View view;
    private List<Food> foodlist;
    private RecyclerView recyclerView;
    MainMenuRecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    AppDatabase appDatabase;
    ImageButton goToCart;
    Bundle bundle;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainMenuFragment newInstance(String param1, String param2) {
        MainMenuFragment fragment = new MainMenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_MainMenu);
        goToCart = view.findViewById(R.id.btn_go_to_cart);

        // get bundle data from the activity (Home)
        bundle = getArguments();

        foodlist = new ArrayList<>();

        // initialize an adapter and pass the userID
        setAdapter(bundle.getInt("userID"));

        // go to cart button
        goToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CartActivity.class);
                intent.putExtra("userID", bundle.getInt("userID"));
                startActivity(intent);

            }
        });


        return view;
    }

    // bind the adapter to the recycler view
    private void setAdapter(Integer userID) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                appDatabase = AppDatabase.getDbInstance(getActivity().getApplicationContext());
                FoodDao foodDao = appDatabase.foodDao();

                // displays food items in main menu
                foodlist = foodDao.getFoodList();


                getActivity().runOnUiThread(() -> {
                    layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    adapter = new MainMenuRecyclerAdapter(getActivity().getApplicationContext(), foodlist, recyclerView, userID);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
                });
            }
        }).start();


//        MainMenuRecyclerAdapter adapter = new MainMenuRecyclerAdapter(getActivity().getApplicationContext(), foodlist, recyclerView);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//
//
//        recyclerView.setAdapter(adapter);
    }


}