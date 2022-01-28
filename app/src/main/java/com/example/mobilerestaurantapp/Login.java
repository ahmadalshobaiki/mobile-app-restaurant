package com.example.mobilerestaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilerestaurantapp.db.AppDatabase;
import com.example.mobilerestaurantapp.db.FoodDao;
import com.example.mobilerestaurantapp.db.UserDao;
import com.example.mobilerestaurantapp.db.UserEntity;

public class Login extends AppCompatActivity {

    EditText name, password;
    Button Login;
    TextView signup;
    FoodDao foodDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name = (EditText) findViewById(R.id.edttxt_username);
        password = (EditText) findViewById(R.id.edttxt_password);
        Login = (Button) findViewById(R.id.btn_login);
        signup = (TextView) findViewById(R.id.tv_gotosignup);


        // go to sign up page
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = name.getText().toString();
                String passwordtext = password.getText().toString();
                if (username.isEmpty() || passwordtext.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    //perform a Query
                    AppDatabase userDatabase = AppDatabase.getDbInstance(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UserEntity userEntity = userDao.login(username, passwordtext);



                            if (userEntity == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            } else {
                                Integer id = userEntity.id;
                                String username = userEntity.username;
                                String fullname = userEntity.fullname;
                                String email = userEntity.email;
                                String phone = userEntity.phone;
                                String password = userEntity.password;
                                Intent intent = new Intent(Login.this, Home.class);
                                intent.putExtra("userID", id);
                                intent.putExtra("fullname", fullname);
                                intent.putExtra("username", username);
                                intent.putExtra("email", email);
                                intent.putExtra("phone", phone);
                                intent.putExtra("password", password);


                                if(foodDao == null){
                                    foodDao = userDatabase.foodDao();
                                }
                                if(foodDao.getFoodList().size() == 0){
                                    foodDao.addFood(Food.getFoodList());
                                }

                                startActivity(intent);
                                finish();
                            }

                        }
                    }).start();
                }

            }
        });


    }
}