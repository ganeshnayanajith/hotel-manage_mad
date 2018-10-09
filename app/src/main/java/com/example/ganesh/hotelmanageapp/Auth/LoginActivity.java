package com.example.ganesh.hotelmanageapp.Auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ganesh.hotelmanageapp.AdminActivity;
import com.example.ganesh.hotelmanageapp.MainActivity;
import com.example.ganesh.hotelmanageapp.R;
import com.example.ganesh.hotelmanageapp.ResetPasswordActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText mInputEmail, mInputPassword;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgressBar;
    private Button mBtnSignup, mBtnLogin, mBtnReset;

    String userType="user";

    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mInputEmail = (EditText) findViewById(R.id.email);
        mInputPassword = (EditText) findViewById(R.id.password);
        mBtnSignup = (Button) findViewById(R.id.btn_signup);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnReset = (Button) findViewById(R.id.btn_reset_password);




        mAuth = FirebaseAuth.getInstance();
        mUserDatabase= FirebaseDatabase.getInstance().getReference().child("users");

        mProgressBar = new ProgressDialog(this);




        mBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainIntent=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(mainIntent);
            }
        });



        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=mInputEmail.getText().toString();
                String password=mInputPassword.getText().toString();


                if(password.equals("adminadmin")){
                    userType="admin";
                }

                if( TextUtils.isEmpty(email) ){
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }else if( TextUtils.isEmpty(password) ){
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }else{

                    mProgressBar.setTitle("Account Login");
                    mProgressBar.setMessage("Please wait, Login To Your Account...");
                    mProgressBar.setCanceledOnTouchOutside(false);
                    mProgressBar.show();


                    loginUser(email,password,userType);
                }



            }
        });
        mBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,ResetPasswordActivity.class);
                startActivity(i);
            }
        });






    }






    private void loginUser(String email, String password, final String user) {

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    mProgressBar.dismiss();

                        if(user.equals("user")){
                            Intent userIntent=new Intent(LoginActivity.this,MainActivity.class);
                            userIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(userIntent);
                            finish();
                        }else{
                            Intent userIntent=new Intent(LoginActivity.this,AdminActivity.class);
                            userIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(userIntent);
                            finish();

                        }




                }else{
                    mProgressBar.hide();
                    Toast.makeText(LoginActivity.this,"Please Check Your Network Connection",Toast.LENGTH_LONG).show();
                }
            }
        });
    }





}
