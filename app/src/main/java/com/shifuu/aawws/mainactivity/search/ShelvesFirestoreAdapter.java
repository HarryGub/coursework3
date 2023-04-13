package com.shifuu.aawws.mainactivity.search;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.shifuu.aawws.R;
import com.shifuu.aawws.model.OnShelfSelectedListener;
import com.shifuu.aawws.model.Shelf;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class ShelvesFirestoreAdapter extends FirestoreRecyclerAdapter<Shelf, ShelvesFirestoreAdapter.ShelfViewHolder> {


    private OnShelfSelectedListener listener;
    public ShelvesFirestoreAdapter(@NonNull FirestoreRecyclerOptions<Shelf> options, OnShelfSelectedListener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull ShelfViewHolder holder, int position, @NonNull Shelf model) {
        holder.shelf = model;
        holder.bindData();
    }

    @NonNull
    @Override
    public ShelfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShelvesFirestoreAdapter.ShelfViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rack_shelf_item, parent, false), LayoutInflater.from(parent.getContext()),
                listener);
    }

    public static class ShelfViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        Shelf shelf;
        LayoutInflater inflater;
        OnShelfSelectedListener listener;

        AppCompatTextView name;


        public ShelfViewHolder(@NonNull View itemView, LayoutInflater inflater, OnShelfSelectedListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.rack_item_letter);
            itemView.setOnClickListener(this);
            this.listener = listener;
        }

        public void bindData()
        {
            name.setText(shelf.getName());
        }

        @Override
        public void onClick(View view) {
            Log.d("ShelfFirestoreAdapter", shelf.toString());
            listener.onShelfSelected(shelf);
        }
    }
}
