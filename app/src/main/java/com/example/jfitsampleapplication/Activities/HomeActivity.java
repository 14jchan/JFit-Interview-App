package com.example.jfitsampleapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jfitsampleapplication.R;

/*
@author Jason Chan

Tertiary Activity that holds the store lists and displays it in a recycler view. Holds the following requirements:

*Display the business details.
*Each business will show the name, rating, address information, and category information.
*Displays a thumbnail image of the restaurant.
*Implements a like feature allowing the user to like a business (stores business ID in DB)
*Display up to three review excerpts related to the business.
*Provide a back button to return to the previous screen.
 */
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