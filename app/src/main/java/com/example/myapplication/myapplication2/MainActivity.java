package com.example.myapplication.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = findViewById(R.id.image);
        // getting the img btn
        Button getImage = findViewById(R.id.getImgBtn);
        getImage.setOnClickListener(new GetImageOnClickListener(imageView));
    }

    // get the list activity
    public void seeList(View view) {
        // new activity
        Intent list = new Intent(getApplicationContext(), ListActivity.class);
        startActivity(list);
    }
}