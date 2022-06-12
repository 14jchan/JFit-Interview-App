package com.example.jfitsampleapplication.Objects;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

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
