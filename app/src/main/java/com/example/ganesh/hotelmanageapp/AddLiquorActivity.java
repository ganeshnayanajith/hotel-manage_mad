package com.example.ganesh.hotelmanageapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddLiquorActivity extends AppCompatActivity {

    private ImageView mLiquorImage;
    private EditText mName;
    private EditText msize;
    private EditText mPrice;
    private Button msubmit;
    private Button mCancel;

    private static final int IMAGE_PICK=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_liquor);

        mLiquorImage=(ImageView)findViewById(R.id.uploadImageView);
        mName=(EditText)findViewById(R.id.name);
        msize=(EditText)findViewById(R.id.size);
        mPrice=(EditText)findViewById(R.id.price);
        msubmit=(Button)findViewById(R.id.btn_submit);

        msubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageIntent=new Intent();
                imageIntent.setType("image/*");
                imageIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(imageIntent,"SELECT IMAGE"),IMAGE_PICK);
            }
        });




    }
}
