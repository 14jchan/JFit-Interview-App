package com.example.jfitsampleapplication.Dialogues;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.jfitsampleapplication.R;

//loading dialogue for all api queries process times

public class LoadingDialogue {

    private Activity activity;
    private AlertDialog dialog;

    public LoadingDialogue(Activity myActivity){
        activity = myActivity;
    }

    public void startLoadingDialogue(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialogue,null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();


    }

    public void dismissDialog(){
        dialog.dismiss();
    }

}

