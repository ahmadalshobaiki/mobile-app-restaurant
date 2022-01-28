package com.example.mobilerestaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mobilerestaurantapp.Orders.Order_page;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {


    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // get the intent extras from the logged in user
        bundle = getIntent().getExtras();

        // initialize bottom navigation view and set a listener
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);


    }

    // bottom navigation view listener function
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch(item.getItemId()){
                        case R.id.nav_home:
                            // display home page
                            selectedFragment = new HomeFragment();
                            selectedFragment.setArguments(bundle);
                            break;
                        case R.id.nav_mainMenu:
                            // display main menu
                            selectedFragment = new MainMenuFragment();
                            selectedFragment.setArguments(bundle);
                            break;
                        case R.id.nav_myOrders:
                            // display orders
                            Intent intent = new Intent(getApplicationContext(), Order_page.class);
                            intent.putExtra("userID", bundle.getInt("userID"));
                            startActivity(intent);
                            return true;
                            //overridePendingTransition(0, 0);

                        case R.id.nav_userProfile:
                            // display user profile
                            // pass the intent extras to userProfile
                            selectedFragment = new UserProfile();
                            selectedFragment.setArguments(bundle);
                            //overridePendingTransition(0, 0);
                            break;
                        case R.id.nav_aboutUs:
                            // display about us
                            selectedFragment = new AboutUsFragment();
                            //overridePendingTransition(0, 0);
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };
}