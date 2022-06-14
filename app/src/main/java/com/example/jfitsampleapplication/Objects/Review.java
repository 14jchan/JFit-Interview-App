package com.example.jfitsampleapplication.Objects;

/*
@author Jason Chan

Custom object to hold review data of a store. Holds the following capabilities:

*Holds the review text, reviewer name, date of review, as well as the rating given to the review
*Allows for the getting and setting of all above variables
 */

public class Review {
    private String review, reviewer, date;
    private Double rating;

    public Review(){}


    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
