package com.example.jfitsampleapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jfitsampleapplication.R;

public class HomeActivity extends AppCompatActivity {

    Button locationOneButton, locationTwoButton, locationThreeButton, locationFourButton, locationFiveButton;


    //initialization of buttons + creation of onclick functions to pass locations of city requested
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent i = getIntent();
        if(i.getStringExtra("ToastMessage") != null){
            Toast.makeText(HomeActivity.this, i.getStringExtra("ToastMessage"),
                    Toast.LENGTH_LONG).show();
            String update = null;
            i.putExtra("ToastMessage", update);
        }

        locationOneButton = findViewById(R.id.cityOneBtn);
        locationTwoButton  = findViewById(R.id.cityTwoBtn);
        locationThreeButton = findViewById(R.id.cityThreeBtn);
        locationFourButton = findViewById(R.id.cityFourBtn);
        locationFiveButton = findViewById(R.id.cityFiveBtn);

        locationOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, BusinessActivity.class);
                i.putExtra("TargetCity", "Los Altos, CA");
                startActivity(i);
            }
        });

        locationTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, BusinessActivity.class);
                i.putExtra("TargetCity", "Mountain View, CA");
                startActivity(i);
            }
        });

        locationThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, BusinessActivity.class);
                i.putExtra("TargetCity", "Sunnyvale, CA");
                startActivity(i);
            }
        });

        locationFourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, BusinessActivity.class);
                i.putExtra("TargetCity", "Saratoga, CA");
                startActivity(i);
            }
        });

        locationFiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, BusinessActivity.class);
                i.putExtra("TargetCity", "Cupertino, CA");
                startActivity(i);
            }
        });
    }


}