package com.example.jfitsampleapplication.RecyclerAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jfitsampleapplication.Objects.Store;
import com.example.jfitsampleapplication.R;

import java.util.LinkedList;
import java.util.List;

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
        private OnStoreListener onStoreListener;

        public ViewHolder(final View view, OnStoreListener onStoreListener){
            super(view);
            businessNameTextView = view.findViewById(R.id.businessNameTextView);
            this.onStoreListener = onStoreListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onStoreListener.onStoreClick(getAdapterPosition());
        }
    }

    public interface OnStoreListener{
        void onStoreClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewcard_store, parent, false);
        return new ViewHolder(itemView, onStoreListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String businessName = storeList.get(position).getStoreName();
        holder.businessNameTextView.setText(businessName);
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }
}
