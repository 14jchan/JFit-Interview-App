package com.example.jfitsampleapplication.Depedencies;

import android.util.Log;

import com.example.jfitsampleapplication.Objects.Review;
import com.example.jfitsampleapplication.Objects.Store;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/*
@author Jason Chan

Parses and returns relevant data in custom store/review formats. Holds the following capabilities:

*Conversion of YELP JSON data to a store list
*Conversion of YELP JSON data to reviews of a specific store
 */

public class JSONReader {

    public static List<Store> JSONtoStoreList(String JSON) throws Exception{
        List<Store> storeList = new LinkedList<>();


        JSONObject overallJsonObject = new JSONObject(JSON);
        JSONArray storeListArray = overallJsonObject.getJSONArray("businesses");
        for(int i = 0; i < storeListArray.length(); i++){
            JSONObject storeObject = storeListArray.getJSONObject(i);
            Store store = new Store();
            store.setStoreID(storeObject.getString("id"));
            store.setStoreName(storeObject.getString("name"));
            store.setImageURL(storeObject.getString("image_url"));
            store.setRating(storeObject.getDouble("rating"));

            JSONObject locationObject = storeObject.getJSONObject("location");
            String address1 = locationObject.getString("address1");
            String address2 = locationObject.getString("address2");
            String addressCity = locationObject.getString("city");
            String addressState = locationObject.getString("state");
            String addressZip = locationObject.getString("zip_code");
            String totalAddress;
            if(address2.isEmpty() || address2 == null){
                totalAddress = address1 + " " + addressCity + ", " + addressState + " " + addressZip;
            }else{
                totalAddress = address1 + " " + address2 + " " + addressCity + ", " + addressState + " " + addressZip;
            }
            store.setAddress(totalAddress.replace(" null ", " "));

            JSONArray categoryArray = storeObject.getJSONArray("categories");
            List<String> categoryList = new LinkedList<>();
            for(int k = 0; k < categoryArray.length(); k++){
                JSONObject categoryObject = categoryArray.getJSONObject(k);
                categoryList.add(categoryObject.getString("title"));
            }
            store.setCategories(categoryList);

            storeList.add(store);
        }

        return storeList;
    }


    public static List<Review> getReviewsFromJSON(String JSON) throws Exception{
        List<Review> reviews = new LinkedList<>();
        JSONObject overallJsonObject = new JSONObject(JSON);
        JSONArray reviewsArrays = overallJsonObject.getJSONArray("reviews");
        for(int i = 0; i < Math.min(reviewsArrays.length(), 3); i++){
            JSONObject reviewJSON = reviewsArrays.getJSONObject(i);
            Review review = new Review();
            review.setReview(reviewJSON.getString("text").replace("\n"," ").replace("\t"," "));
            review.setDate(reviewJSON.getString("time_created"));
            review.setRating(reviewJSON.getDouble("rating"));

            JSONObject reviewUser = reviewJSON.getJSONObject("user");
            review.setReviewer(reviewUser.getString("name"));
            reviews.add(review);
        }


        return reviews;
    }
}
