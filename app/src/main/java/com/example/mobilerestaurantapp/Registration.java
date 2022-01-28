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
import com.example.mobilerestaurantapp.db.UserDao;
import com.example.mobilerestaurantapp.db.UserEntity;

import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    EditText Fullname,password,name,repassword, email, phone;
    Button register;
    TextView Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Fullname = (EditText) findViewById(R.id.edttxt_fullname);
        password = (EditText) findViewById(R.id.edttxt_password);
        repassword = (EditText) findViewById(R.id.edttxt_confirmpass);
        name = (EditText) findViewById(R.id.edttxt_username);
        register = (Button) findViewById(R.id.btn_register);
        Login =(TextView) findViewById(R.id.tv_gotologin);
        email = (EditText) findViewById(R.id.edttxt_email);
        phone = (EditText) findViewById(R.id.edttxt_phone);
        String pass= password.getText().toString();
        String repass= repassword.getText().toString();

        // go to login page
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registration.this,Login.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating user entity;



                Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
                Pattern lowerCasePatten = Pattern.compile("[a-z ]");
                Pattern digitCasePatten = Pattern.compile("[0-9 ]");

                // UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                //  UserDao userDao = userDatabase.userDao();

                //   String username = name.getText().toString();

                //    UserEntity userEntity1 = userDao.CheckUsernamePassword(username);

                if (name.getText().toString().isEmpty() || password.getText().toString().isEmpty() || Fullname.getText().toString().isEmpty()
                        || email.getText().toString().isEmpty() || phone.getText().toString().isEmpty() || repassword.getText().toString().isEmpty() ){
                    Toast.makeText(getApplicationContext(), "Please fill all fields !!", Toast.LENGTH_SHORT).show();
                }else if (name.getText().toString().length()<5){
                    Toast.makeText(getApplicationContext(), "Please enter valid username with at least 5 characters !!", Toast.LENGTH_SHORT).show();
                }else if (!Fullname.getText().toString().contains(" ")){
                    Toast.makeText(getApplicationContext(), "Please enter your full name !!", Toast.LENGTH_SHORT).show();
                }else if (email.getText().length()<5 || !email.getText().toString().contains("@") || !email.getText().toString().contains(".")){
                    Toast.makeText(getApplicationContext(), "Please enter valid email !!", Toast.LENGTH_SHORT).show();
                }else if(password.getText().toString().length()<8){
                    Toast.makeText(getApplicationContext(), "the password should be more than 8 characters !!", Toast.LENGTH_SHORT).show();
                }else if(!specailCharPatten.matcher(password.getText().toString()).find()){
                    Toast.makeText(getApplicationContext(), "Password must have at least one special character !!", Toast.LENGTH_SHORT).show();
                }else if(!UpperCasePatten.matcher(password.getText().toString()).find()){
                    Toast.makeText(getApplicationContext(), "Password must have at least one uppercase character !!", Toast.LENGTH_SHORT).show();
                }else if(!lowerCasePatten.matcher(password.getText().toString()).find()){
                    Toast.makeText(getApplicationContext(), "Password must have at least one lowercase character !!", Toast.LENGTH_SHORT).show();
                }else if(!digitCasePatten.matcher(password.getText().toString()).find()) {
                    Toast.makeText(getApplicationContext(), "Password must have at least one digit character !!", Toast.LENGTH_SHORT).show();
                }else if (!pass.equals(repass) ){
                    Toast.makeText(getApplicationContext(), "Password Not matching !!", Toast.LENGTH_SHORT).show();
                    //      }else if(userEntity1 != null){
                    //          Toast.makeText(getApplicationContext(), "the username is already in use!!! try another username", Toast.LENGTH_SHORT).show();
                    //    }else if(userEntity.getPassword().length()<8){
                    //      Toast.makeText(getApplicationContext(), "the password should be more than 8 characters", Toast.LENGTH_SHORT).show();
                } else if (phone.getText().toString().length()!=10 || !phone.getText().toString().startsWith("05")){
                    Toast.makeText(getApplicationContext(), "Please enter phone number starts with 05 !!", Toast.LENGTH_SHORT).show();
                }else{
                    UserEntity userEntity = new UserEntity();
                    userEntity.setFullname(Fullname.getText().toString());
                    userEntity.setPassword(password.getText().toString());
                    userEntity.setName(name.getText().toString());
                    userEntity.email = email.getText().toString();
                    userEntity.phone = phone.getText().toString();

                  //  do insert operation
                    AppDatabase userDatabase = AppDatabase.getDbInstance(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            //user register
                            userDao.registerUser(userEntity);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Your account has been registered successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                }
                            });
                        }
                    }).start();

                }

            }
        });
    }



}