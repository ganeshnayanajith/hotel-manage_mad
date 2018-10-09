package com.example.ganesh.hotelmanageapp.UserHandle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ganesh.hotelmanageapp.MainActivity;
import com.example.ganesh.hotelmanageapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {


    private TextView txtDetails;
    private EditText inputName, inputMobile, inputNic, inputAddress;
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
        inputAddress = (EditText) findViewById(R.id.address);
        btnSave = (Button) findViewById(R.id.btn_save);


        mAuth = FirebaseAuth.getInstance();

        mCurrentUser = mAuth.getCurrentUser();
        String current_uid = mCurrentUser.getUid();

        if (mCurrentUser != null) {
            mDatabase = FirebaseDatabase.getInstance().getReference("users/" + current_uid);
            Log.i("gihan", "Current user : " + current_uid);
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.i("gihan", "children : " + dataSnapshot.getChildren());
                    Log.i("gihan", "value : " + dataSnapshot.getValue());
                    User user = dataSnapshot.getValue(User.class);
                    inputName.setText(user.getName());
                    inputNic.setText(user.getNic());
                    inputAddress.setText(user.getAddress());
                    inputMobile.setText(user.getMobile());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(current_uid);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStatusProgress = new ProgressDialog(UserProfileActivity.this);
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


                Intent save = new Intent(UserProfileActivity.this, MainActivity.class);
                startActivity(save);


            }
        });


    }
}
