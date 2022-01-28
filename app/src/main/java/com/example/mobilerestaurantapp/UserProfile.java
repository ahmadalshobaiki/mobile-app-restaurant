package com.example.mobilerestaurantapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilerestaurantapp.db.AppDatabase;
import com.example.mobilerestaurantapp.db.UserDao;
import com.example.mobilerestaurantapp.db.UserEntity;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfile extends Fragment {

    View view;
    TextView tv_fullname, tv_username;
    EditText edttxt_fullname, edttxt_username, edttxt_email, edttxt_phone, edttxt_password, edttxt_comfirmPassword;
    Button updateButton;
    Integer userID;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfile newInstance(String param1, String param2) {
        UserProfile fragment = new UserProfile();
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
        view = inflater.inflate(R.layout.activity_user_profile, container, false);

        // get the components
        tv_fullname = view.findViewById(R.id.tv_fullname);
        tv_username = view.findViewById(R.id.tv_username);
        edttxt_fullname = view.findViewById(R.id.edtxt_fullname);
        edttxt_username = view.findViewById(R.id.edtxt_username);
        edttxt_password = view.findViewById(R.id.edtxt_password);
        edttxt_comfirmPassword = view.findViewById(R.id.edtxt_confirmPassword);
        edttxt_email = view.findViewById(R.id.edtxt_email);
        edttxt_phone = view.findViewById(R.id.edtxt_phone);
        updateButton = view.findViewById(R.id.btn_update);



        // retrieve the data from Home Activity
        Bundle bundle = getArguments();

        if(bundle != null){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    userID = bundle.getInt("userID");

                    AppDatabase appDatabase = AppDatabase.getDbInstance(getActivity().getApplicationContext());
                    UserDao userDao = appDatabase.userDao();

                    UserEntity getUserData = new UserEntity();

                    getUserData = userDao.getUser(userID);
                    String fullName = getUserData.fullname;
                    String userName = getUserData.username;
                    String password = getUserData.password;
                    String email = getUserData.email;
                    String phone = getUserData.phone;


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_fullname.setText(fullName);
                            tv_username.setText(userName);
                            edttxt_fullname.setText(fullName);
                            edttxt_username.setText(userName);
                            edttxt_password.setText(password);
                            edttxt_comfirmPassword.setText(password);
                            edttxt_email.setText(email);
                            edttxt_phone.setText(phone);
                        }
                    });
                }
            }).start();
        }


        // set update button onclick listener
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // update query
                String newFullname = edttxt_fullname.getText().toString();
                String newUsername = edttxt_username.getText().toString();
                String newPassword = edttxt_password.getText().toString();
                String confirmPassword = edttxt_comfirmPassword.getText().toString();
                String newEmail = edttxt_email.getText().toString();
                String newPhone = edttxt_phone.getText().toString();

                /*


               } else if (phone.getText().toString().length()!=10 || !phone.getText().toString().startsWith("05")){
                    Toast.makeText(getApplicationContext(), "Please enter phone number starts with 05", Toast.LENGTH_SHORT).show();
                 */
                Pattern specialCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
                Pattern lowerCasePatten = Pattern.compile("[a-z ]");
                Pattern digitCasePatten = Pattern.compile("[0-9 ]");

                // validate then update
                if(newFullname.isEmpty() || newUsername.isEmpty() || newPassword.isEmpty() ||
                        confirmPassword.isEmpty() || newEmail.isEmpty() || newPhone.isEmpty()){

                    Toast.makeText(getActivity().getApplicationContext(), "One or more fields are empty!", Toast.LENGTH_LONG).show();
                }else if(!newFullname.contains(" ")){
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter your full name!", Toast.LENGTH_SHORT).show();
                }else if(newUsername.length()<5 ){
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter valid username with at least 5 characters!", Toast.LENGTH_SHORT).show();
                }else if(newEmail.length()<5 || !newEmail.contains("@") || !newEmail.contains(".")){
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter valid email!", Toast.LENGTH_SHORT).show();
                }else if(newPassword.length() < 8){
                    Toast.makeText(getActivity().getApplicationContext(), "Password should be more than 8 characters!", Toast.LENGTH_SHORT).show();
                }else if(!specialCharPatten.matcher(newPassword).find()){
                    Toast.makeText(getActivity().getApplicationContext(), "Password must have at least one special character!", Toast.LENGTH_SHORT).show();
                }else if(!UpperCasePatten.matcher(newPassword).find()){
                    Toast.makeText(getActivity().getApplicationContext(), "Password must have at least one uppercase character!", Toast.LENGTH_SHORT).show();
                }else if(!lowerCasePatten.matcher(newPassword).find()){
                    Toast.makeText(getActivity().getApplicationContext(), "Password must have at least one lowercase character!", Toast.LENGTH_SHORT).show();
                }else if(!digitCasePatten.matcher(newPassword).find()){
                    Toast.makeText(getActivity().getApplicationContext(), "Password must have at least one digit character!", Toast.LENGTH_SHORT).show();
                }else if(!newPassword.equals(confirmPassword)){
                    Toast.makeText(getActivity().getApplicationContext(), "Passwords does not match!", Toast.LENGTH_LONG)
                            .show();
                }else if(newPhone.length()!=10 || !newPhone.startsWith("05")){
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter phone number starts with 05!", Toast.LENGTH_SHORT).show();
                }else{
                    // update
                    UserEntity userEntity = new UserEntity();
                    userEntity.id = userID;
                    userEntity.fullname = newFullname;
                    userEntity.username = newUsername;
                    userEntity.password = newPassword;
                    userEntity.email = newEmail;
                    userEntity.phone = newPhone;


                    AppDatabase appDatabase = AppDatabase.getDbInstance(getActivity().getApplicationContext());
                    UserDao userDao = appDatabase.userDao();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            userDao.updateUser(userEntity);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity().getApplicationContext(), "Your profile has been updated!", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            });
                        }
                    }).start();
                }
            }
        });

        return view;
    }
}