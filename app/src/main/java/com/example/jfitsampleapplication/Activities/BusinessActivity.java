package com.example.jfitsampleapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.jfitsampleapplication.Depedencies.JSONReader;
import com.example.jfitsampleapplication.Depedencies.RESTGetCaller;
import com.example.jfitsampleapplication.Dialogues.LoadingDialogue;
import com.example.jfitsampleapplication.Objects.Store;
import com.example.jfitsampleapplication.R;
import com.example.jfitsampleapplication.RecyclerAdapters.StoreRecyclerAdapter;

import java.util.LinkedList;
import java.util.List;

/*
@author Jason Chan

Main Class for the first business activity. Holds the following requirements:

*Show a list of five cities of your choosing. -> Los Altos, Mountain View, Sunnyvale, Saratoga, Cupertino
*Clicking on one of these cities will load the second screen -> BusinessActivity
 */
public class BusinessActivity extends AppCompatActivity implements StoreRecyclerAdapter.OnStoreListener {

    private RecyclerView storeRecyclerView;
    private static String location, APIResponse;
    private Button backButton;
    private LoadingDialogue loadingDialogue;
    private boolean refreshPage;
    public static List<Store> storeList;

    //initialization and verifies that a location has been passed through, then starts API Call
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        backButton = findViewById(R.id.backButton);
        storeRecyclerView = findViewById(R.id.storeRecyclerView);
        if(storeList == null){
            storeList = new LinkedList<>();
        }

        Intent i = getIntent();
        refreshPage = i.getBooleanExtra("refreshPage", true);
        location = i.getStringExtra("TargetCity");
        if((location == null || location.isEmpty()) && refreshPage){
            i = new Intent(this, HomeActivity.class);
            startActivity(i);
        }
        loadingDialogue = new LoadingDialogue(BusinessActivity.this);
        if(refreshPage) {
            i.putExtra("refreshPage", true);
            startAPICall(location);
        }else{
            Log.d("storeListSize" , Integer.toString(storeList.size()));
            setRecyclerAdapter();
        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButtonClicked();
            }
        });
    }

    private void backButtonClicked(){
        Intent intent = new Intent(BusinessActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    //locks screen with loading dialogue + calls API
    private void startAPICall(String location){
        loadingDialogue.startLoadingDialogue();
        RESTGetCaller newRequest = new RESTGetCaller("https://api.yelp.com/v3/businesses/search?limit=10&location=" + location, 1, BusinessActivity.this, getString(R.string.yelp_api_token));
        newRequest.execute();
    }

    //recieves the api response from multithread and dismisses the loading dialogue, stores store data in static class var.
    public void getAPIResponse(String response){
        APIResponse = response;
        loadingDialogue.dismissDialog();
        if(response == null){
            Intent intent = new Intent(BusinessActivity.this, HomeActivity.class);
            intent.putExtra("ToastMessage", "Internet Connection Unavailable.");
            startActivity(intent);
        }else{
            try {
                storeList = JSONReader.JSONtoStoreList(APIResponse);
                setRecyclerAdapter();
            }catch (Exception e){
                Intent intent = new Intent(BusinessActivity.this, HomeActivity.class);
                intent.putExtra("ToastMessage", "Malformed Server Response.");
                startActivity(intent);
            }
        }
    }

    private void setRecyclerAdapter(){
        StoreRecyclerAdapter storeRecyclerAdapter = new StoreRecyclerAdapter(storeList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        storeRecyclerView.setLayoutManager(layoutManager);
        storeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        storeRecyclerView.setAdapter(storeRecyclerAdapter);
    }


    @Override
    public void onStoreClick(int position) {
        Intent intent = new Intent(BusinessActivity.this, BusinessDetailsActivity.class);
        intent.putExtra("StoreListPosition", position);
        startActivity(intent);
    }
}