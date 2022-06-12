package com.example.jfitsampleapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jfitsampleapplication.Depedencies.JSONReader;
import com.example.jfitsampleapplication.Depedencies.Properties;
import com.example.jfitsampleapplication.Depedencies.RESTGetCaller;
import com.example.jfitsampleapplication.Dialogues.LoadingDialogue;
import com.example.jfitsampleapplication.Objects.Store;
import com.example.jfitsampleapplication.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class BusinessDetailsActivity extends AppCompatActivity {

    private TextView businessNameTextView, businessRatingTextView, businessAddressTextView, businessCategoriesTextView, businessQuote1TextView, businessQuote2TextView,businessQuote3TextView;
    private ImageButton likeButton;
    private ImageView businessLogoImage;
    private LoadingDialogue loadingDialogue;
    private Button backButton;
    private Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_details);

        Intent i = getIntent();
        try {
            store = BusinessActivity.storeList.get(i.getIntExtra("StoreListPosition", 0));
        }catch (Exception e){
            Intent intent = new Intent(BusinessDetailsActivity.this, HomeActivity.class);
            intent.putExtra("ToastMessage", "Malformed Store List.");
            startActivity(intent);
        }

        businessNameTextView = findViewById(R.id.businessActivityAddressTextView);
        businessRatingTextView = findViewById(R.id.businessActivityRatingTextView);
        businessAddressTextView = findViewById(R.id.businessActivityAddressTextView);
        businessCategoriesTextView = findViewById(R.id.businessActivityCategoriesTextView);
        businessQuote1TextView = findViewById(R.id.businessActivityTestamonial1TextView);
        businessQuote2TextView = findViewById(R.id.businessActivityTestamonial2TextView);
        businessQuote3TextView = findViewById(R.id.businessActivityTestamonial3TextView);
        likeButton = findViewById(R.id.businessActivityLikeImageButton);
        businessLogoImage = findViewById(R.id.businessActivityLogoImageView);
        backButton = findViewById(R.id.businessActivityBackButton);
        loadingDialogue = new LoadingDialogue(BusinessDetailsActivity.this);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BusinessDetailsActivity.this, BusinessActivity.class);
                intent.putExtra("refreshPage", false);
                startActivity(intent);
            }
        });

        setActivityData();
    }

    private void setActivityData(){
        businessNameTextView.setText(store.getStoreName());
        businessRatingTextView.setText(Double.toString(store.getRating()));
        businessAddressTextView.setText(store.getAddress());
        String totalCatagoriesString = "";
        for(String category : store.getCategories()){
            totalCatagoriesString = totalCatagoriesString + category + "\n";
        }
        totalCatagoriesString = totalCatagoriesString.substring(0,totalCatagoriesString.length()-2);
        businessCategoriesTextView.setText(totalCatagoriesString);

        getAPIData();
    }

    public void getAPIData(){
        loadingDialogue.startLoadingDialogue();
        RESTGetCaller newRequest = new RESTGetCaller("https://api.yelp.com/v3/businesses/" + store.getStoreID() +"/reviews", 2, BusinessDetailsActivity.this, Properties.YELP_API_TOKEN);
    }

    public void getAPIResponse(String response){
        loadingDialogue.dismissDialog();
        if(response == null){
            Intent intent = new Intent(BusinessDetailsActivity.this, HomeActivity.class);
            intent.putExtra("ToastMessage", "Internet Connection Unavailable.");
            startActivity(intent);
        }else {
            try {
                store.setReviews(JSONReader.getReviewsFromJSON(response));
            } catch (Exception e) {
                Intent intent = new Intent(BusinessDetailsActivity.this, HomeActivity.class);
                intent.putExtra("ToastMessage", "Malformed Server Response.");
                startActivity(intent);
            }
        }
    }
}