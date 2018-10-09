package com.example.ganesh.hotelmanageapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ganesh.hotelmanageapp.Admin.AddFood;
import com.example.ganesh.hotelmanageapp.Admin.AddLiquor;
import com.example.ganesh.hotelmanageapp.Auth.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminActivity extends AppCompatActivity {

    Button liquorManagement;
    Button FoodManagement;
    Button RoomManagement;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getSupportActionBar().setTitle("Admin Profile");

        mAuth = FirebaseAuth.getInstance();
        liquorManagement = findViewById(R.id.btn_liquor_management);
        FoodManagement = findViewById(R.id.btn_food_management);
        RoomManagement = findViewById(R.id.btn_room_management);

        liquorManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AddLiquor.class));
            }
        });

        FoodManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AddFood.class));
            }
        });

    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);

        if(currentUser==null){
            sendToWelcome();
        }

    }

    private void sendToWelcome(){
        Intent startIntent=new Intent(AdminActivity.this,LoginActivity.class);
        startActivity(startIntent);
        finish();
    }



}
