package com.example.ganesh.hotelmanageapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RoomBookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_booking);
        getSupportActionBar().setTitle("Room Booking");
    }
}
