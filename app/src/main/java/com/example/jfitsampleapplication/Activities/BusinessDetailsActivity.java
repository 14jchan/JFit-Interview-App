package com.example.jfitsampleapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jfitsampleapplication.Depedencies.DBController;
import com.example.jfitsampleapplication.Depedencies.ImageURLParser;
import com.example.jfitsampleapplication.Depedencies.JSONReader;
import com.example.jfitsampleapplication.Depedencies.Properties;
import com.example.jfitsampleapplication.Depedencies.RESTGetCaller;
import com.example.jfitsampleapplication.Dialogues.LoadingDialogue;
import com.example.jfitsampleapplication.Objects.Review;
import com.example.jfitsampleapplication.Objects.Store;
import com.example.jfitsampleapplication.R;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BusinessDetailsActivity extends AppCompatActivity {

    private TextView businessNameTextView, businessRatingTextView, businessAddressTextView, businessCategoriesTextView, businessQuoteTextView;
    private ImageButton likeButton;
    private ImageView businessLogoImage;
    private LoadingDialogue loadingDialogue;
    private Button backButton;
    private Store store;
    private DBController dbController;

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

        businessNameTextView = findViewById(R.id.businessActivityNameTextView);
        businessRatingTextView = findViewById(R.id.businessActivityRatingTextView);
        businessAddressTextView = findViewById(R.id.businessActivityAddressTextView);
        businessCategoriesTextView = findViewById(R.id.businessActivityCategoriesTextView);
        businessQuoteTextView = findViewById(R.id.businessActivityTestamonialsTextView);
        likeButton = findViewById(R.id.businessActivityLikeImageButton);
        businessLogoImage = findViewById(R.id.businessActivityLogoImageView);
        backButton = findViewById(R.id.businessActivityBackButton);
        loadingDialogue = new LoadingDialogue(BusinessDetailsActivity.this);
        dbController = new DBController(this);
        setLikeButtonImage();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToBusinessActivity();
            }
        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLikeButton();
            }
        });
        setActivityData();
    }

    private void setLikeButtonImage(){
        boolean isLiked = dbController.getLike(store.getStoreID());
        if(!isLiked){
            likeButton.setImageDrawable(getResources().getDrawable(R.drawable.like_blank));
        }else{
            likeButton.setImageDrawable(getResources().getDrawable(R.drawable.like_filled));
        }
    }

    private void toggleLikeButton(){
        boolean isLiked = dbController.getLike(store.getStoreID());
        if(!isLiked){
            likeButton.setImageDrawable(getResources().getDrawable(R.drawable.like_filled));
            dbController.likeStore(store.getStoreID());
        }else{
            likeButton.setImageDrawable(getResources().getDrawable(R.drawable.like_blank));
            dbController.dislikeStore(store.getStoreID());
        }

    }

    private void backToBusinessActivity(){
        Intent intent = new Intent(BusinessDetailsActivity.this, BusinessActivity.class);
        intent.putExtra("refreshPage", false);
        startActivity(intent);
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
        newRequest.execute();
    }

    public void getAPIResponse(String response){
        if(response == null){
            loadingDialogue.dismissDialog();
            Intent intent = new Intent(BusinessDetailsActivity.this, HomeActivity.class);
            intent.putExtra("ToastMessage", "Internet Connection Unavailable.");
            startActivity(intent);
        }else {
            try {
                List<Review> listOfReviews = JSONReader.getReviewsFromJSON(response);
                store.setReviews(listOfReviews);
                setStoreQuotes();
                ImageURLParser imageParseRequest = new ImageURLParser(BusinessDetailsActivity.this, store.getImageURL(), 1);
                imageParseRequest.execute();
            } catch (Exception e) {
                loadingDialogue.dismissDialog();
                Intent intent = new Intent(BusinessDetailsActivity.this, HomeActivity.class);
                intent.putExtra("ToastMessage", "Malformed Server Response.");
                startActivity(intent);
            }
        }
    }

    public void setStoreLogo(Drawable drawable){
        loadingDialogue.dismissDialog();
        if(drawable != null){
            store.setStoreLogo(drawable);
            businessLogoImage.setImageDrawable(drawable);
        }
    }

    public void setStoreQuotes(){
        String reviewText = "";
        for(Review review : store.getReviews()){
            if(review.getReview().length() > 50){
                reviewText = reviewText + review.getReview().substring(0,53) + "\n" + review.getReview().substring(54, Math.min(100, review.getReview().length()))  + "...\n-" + review.getReviewer() + " " + review.getDate() + " (" + review.getRating() + "/5.0)" + "\n\n";
            }else {
                reviewText = reviewText + review.getReview() + "...\n-" + review.getReviewer() + " " + review.getDate() + " (" + review.getRating() + "/5.0)" + "\n\n";
            }
        }
        businessQuoteTextView.setText(reviewText);
    }
}