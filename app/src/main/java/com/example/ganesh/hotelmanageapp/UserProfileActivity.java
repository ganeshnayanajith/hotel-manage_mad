package com.example.ganesh.hotelmanageapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UserProfileActivity extends AppCompatActivity {




    private TextView txtDetails;
    private EditText inputName,inputMobile,inputNic,inputAddress;
    private Button btnSave;

    private ProgressDialog mStatusProgress;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getSupportActionBar().setTitle("User Profile");



        txtDetails = (TextView) findViewById(R.id.txt_user);
        inputName = (EditText) findViewById(R.id.name);
        inputMobile = (EditText) findViewById(R.id.mobileNo);
        inputNic = (EditText) findViewById(R.id.nic);
        inputAddress = (EditText)findViewById(R.id.address);
        btnSave = (Button) findViewById(R.id.btn_save);


        mAuth = FirebaseAuth.getInstance();

        mCurrentUser = mAuth.getCurrentUser();
        String current_uid = mCurrentUser.getUid();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("users").child(current_uid);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStatusProgress=new ProgressDialog(UserProfileActivity.this);
                mStatusProgress.setTitle("Changing Status");
                mStatusProgress.setMessage("Please Wait,Status is Changing...");
                mStatusProgress.show();

                String name = inputName.getText().toString();
                String mobile = inputMobile.getText().toString();
                String nic = inputNic.getText().toString();
                String address = inputAddress.getText().toString();


                mDatabase.child("name").setValue(name);
                mDatabase.child("mobile").setValue(mobile);
                mDatabase.child("nic").setValue(nic);
                mDatabase.child("address").setValue(address);

                mStatusProgress.dismiss();

                Toast.makeText(UserProfileActivity.this, "Profile saved", Toast.LENGTH_SHORT).show();


                Intent save=new Intent(UserProfileActivity.this,MainActivity.class);
                startActivity(save);



            }
        });


    }
}
