package com.example.jfitsampleapplication.Objects;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/*
@author Jason Chan

Custom object to hold store data of any given store. Holds the following capabilities:

*Holds the YELP store ID of a target store, store name, address, and logo image. Image may be also stored as a DRAWABLE given processing with the ImageURLParser.class
*Holds List of categories that fit within the store
*Holds List of Review.class data types of reviews given to the store (max. 3)
*Holds the overall ratings of the store
*Allows for the getting and setting of all above variables

 */

public class Store {

    private String storeID,storeName, address, imageURL;
    private List<String> categories;
    private List<Review> reviews;
    private double rating;
    private Drawable storeLogo;

    public Store(){};

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = formatAddress(address);
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Drawable getStoreLogo() {
        return storeLogo;
    }

    public void setStoreLogo(Drawable storeLogo) {
        this.storeLogo = storeLogo;
    }

    private String formatAddress(String address){
        String formatted = "";
        while(!address.isEmpty()){
            formatted = formatted + address.substring(0,Math.min(address.length(), 20)) + "\n";
            address = address.substring(Math.min(20, address.length()));
        }
        return formatted;
    }


}
