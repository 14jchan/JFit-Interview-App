package com.example.jfitsampleapplication.Depedencies;

import android.app.Activity;
import android.os.AsyncTask;

import com.example.jfitsampleapplication.Activities.BusinessActivity;
import com.example.jfitsampleapplication.Activities.BusinessDetailsActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/*
@author Jason Chan

Asyncronous task that preforms GET requests given a url endpoint and a bearer token authorization. Holds the following capabilities:

*Multithreaded request for GET API request
*Callback to activity method with API response
 */

public class RESTGetCaller extends AsyncTask<String, String, String> {
    private String URLString = "", result, bearerToken;
    private int returnType;
    private Activity activity;

    public RESTGetCaller(String URL, int returnType, Activity activity, String bearerToken){
        this.returnType = returnType;
        this.activity = activity;
        URLString = URL;
        this.bearerToken = bearerToken;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... uri) {
        try {
            URL url = new URL(URLString);
            System.out.println("GET Calling " + URLString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization","Bearer "+ bearerToken);
            Long startRequestTime = System.currentTimeMillis();
            if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK){
                BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while(System.currentTimeMillis() - startRequestTime < 1250){
                    Thread.sleep(50);
                }
                result = bf.readLine();
                return result;
            } else {
                if(conn.getResponseCode() == 429){
                    Thread.sleep(1500);
                }
                result = "FAILED";
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result = "FAILED";
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(result.equals("FAILED")){
            result = null;
        }
        switch (returnType){
            case 1:
                BusinessActivity businessActivity = (BusinessActivity) activity;
                businessActivity.getAPIResponse(result);
                break;
            case 2:
                BusinessDetailsActivity businessDetailsActivity = (BusinessDetailsActivity) activity;
                businessDetailsActivity.getAPIResponse(result);
            default:
                break;
        }
    }
}