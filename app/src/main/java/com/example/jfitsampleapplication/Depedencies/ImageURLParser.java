package com.example.jfitsampleapplication.Depedencies;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.example.jfitsampleapplication.Activities.BusinessDetailsActivity;

import java.io.InputStream;
import java.net.URL;

public class ImageURLParser extends AsyncTask<Drawable, Drawable, Drawable> {
    private Activity activity;
    private String imageURL;
    private int returnType;


    public ImageURLParser(Activity activity, String imageURL, int returnType) {
        this.activity = activity;
        this.imageURL = imageURL;
        this.returnType = returnType;
    }

    @Override
    protected Drawable doInBackground(Drawable... drawables) {
        try {
            InputStream iStream = null;
            iStream = (InputStream) new URL(imageURL).getContent();
            Drawable drawable = Drawable.createFromStream(iStream, "src name");
            return drawable;
        }catch(Exception e){
            return null;
        }
    }

    @Override
    protected void onPostExecute(Drawable drawable) {
        super.onPostExecute(drawable);
        switch (returnType) {
            case 1:
                BusinessDetailsActivity businessDetailsActivity = (BusinessDetailsActivity) activity;
                businessDetailsActivity.setStoreLogo(drawable);
                break;
            default:
                break;
        }
    }
}
