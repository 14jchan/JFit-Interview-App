package com.example.jfitsampleapplication.RecyclerAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jfitsampleapplication.Activities.BusinessActivity;
import com.example.jfitsampleapplication.Activities.HomeActivity;
import com.example.jfitsampleapplication.Depedencies.DBController;
import com.example.jfitsampleapplication.Objects.Store;
import com.example.jfitsampleapplication.R;

import java.util.LinkedList;
import java.util.List;


/*
@author Jason Chan

Recyclerview to hold all store data as a list on the BusinessActivity.class. Holds the following capabilities:

*Holds and displays as a recyclerview of all stores parsed through BusinessActivity.class
*Allows for direct DB like and dislikes of stores, parses DB for existing likes and dislikes and matches them with the current store list
*Returns the adapter position of the store that was selected(clicked)
 */

public class StoreRecyclerAdapter extends RecyclerView.Adapter<StoreRecyclerAdapter.ViewHolder> {

    private static List<Store> storeList = new LinkedList<>();
    private OnStoreListener onStoreListener;

    public StoreRecyclerAdapter(List<Store> storeList, OnStoreListener onStoreListener){
        this.onStoreListener = onStoreListener;
        this.storeList = storeList;
    }

    public static List<Store> getStoreList() {
        return storeList;
    }

    public static void setStoreList(List<Store> storeList) {
        StoreRecyclerAdapter.storeList = storeList;
    }

    public OnStoreListener getOnStoreListener() {
        return onStoreListener;
    }

    public void setOnStoreListener(OnStoreListener onStoreListener) {
        this.onStoreListener = onStoreListener;
    }

    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView businessNameTextView;
        private ImageButton likeButton;
        private OnStoreListener onStoreListener;
        private DBController dbController;
        private Store store;

        public ViewHolder(final View view, OnStoreListener onStoreListener, Context context){
            super(view);
            businessNameTextView = view.findViewById(R.id.businessNameTextView);
            likeButton = view.findViewById(R.id.businessActivityLikeImageButton);
            this.onStoreListener = onStoreListener;
            dbController = new DBController(context);
            itemView.setOnClickListener(this);

            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleLikeButton();
                }
            });
        }

        public void setStore(Store store) {
            this.store = store;
        }

        @Override
        public void onClick(View view) {
            onStoreListener.onStoreClick(getAdapterPosition());
        }

        private void toggleLikeButton(){
            boolean isLiked = dbController.getLike(store.getStoreID());
            if(!isLiked){
                likeButton.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.like_filled));
                dbController.likeStore(store.getStoreID());
            }else{
                likeButton.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.like_blank));
                dbController.dislikeStore(store.getStoreID());
            }

        }
    }

    public interface OnStoreListener{
        void onStoreClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewcard_store, parent, false);
        return new ViewHolder(itemView, onStoreListener, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String businessName = storeList.get(position).getStoreName();
        holder.setStore(storeList.get(position));
        holder.businessNameTextView.setText(businessName);
        if(holder.dbController.getLike(storeList.get(position).getStoreID())){
            holder.likeButton.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.like_filled));
        }else{
            holder.likeButton.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.like_blank));
        }
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }
}
