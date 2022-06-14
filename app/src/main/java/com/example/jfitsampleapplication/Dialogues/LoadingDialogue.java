package com.example.jfitsampleapplication.Dialogues;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.jfitsampleapplication.R;

/*
@author Jason Chan

Implementable loading dialogue box for any activity. Holds the following capabilities:

*Ability to show and hide all loading dialogues
*Ability to lock screen while loading dialogue is displayed
 */

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

