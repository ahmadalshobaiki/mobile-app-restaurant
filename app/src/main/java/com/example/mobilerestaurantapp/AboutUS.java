package com.example.mobilerestaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mobilerestaurantapp.R;

public class AboutUS extends AppCompatActivity {

    Button firstFragment, secondFragment, fourthFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        firstFragment = findViewById(R.id.fragment1btn);
        secondFragment = findViewById(R.id.fragment2btn);
        fourthFragment = findViewById(R.id.fragment4btn);



        replaceFragment(new Fragment3());



        firstFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                replaceFragment(new Fragment1());
            }
        });

        secondFragment.setOnClickListener(view -> replaceFragment(new Fragment2()));

        fourthFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new Fragment4());
            }
        });
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

    }
}