package com.example.jfitsampleapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.jfitsampleapplication.Depedencies.Properties;
import com.example.jfitsampleapplication.Depedencies.RESTGetCaller;
import com.example.jfitsampleapplication.Dialogues.LoadingDialogue;
import com.example.jfitsampleapplication.R;

public class BusinessActivity extends AppCompatActivity {

    private static String location, APIResponse;
    private LoadingDialogue loadingDialogue;

    //initialization and verifies that a location has been passed through, then starts API Call
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        Intent i = getIntent();
        location = i.getStringExtra("TargetCity");
        if(location == null || location.isEmpty()){
            i = new Intent(this, HomeActivity.class);
            startActivity(i);
        }
        loadingDialogue = new LoadingDialogue(BusinessActivity.this);
        startAPICall(location);
    }

    //locks screen with loading dialogue + calls API
    private void startAPICall(String location){
        loadingDialogue.startLoadingDialogue();
        RESTGetCaller newRequest = new RESTGetCaller("https://api.yelp.com/v3/businesses/search?limit=10&location=" + location, 1, BusinessActivity.this, Properties.YELP_API_TOKEN);
        newRequest.execute();
    }

    //recieves the api response from multithread and dismisses the loading dialogue
    public void APIInterception(String response){
        APIResponse = response;
        loadingDialogue.dismissDialog();
        if(response == null){
            Log.d("API Response:" , "its null");
        }else{
            Log.d("API Response:" , APIResponse);
        }
    }


}