package com.shifuu.aawws.mainactivity.search;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.shifuu.aawws.R;
import com.shifuu.aawws.model.OnRackSelectedListener;
import com.shifuu.aawws.model.Rack;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class RacksFirestoreAdapter extends FirestoreRecyclerAdapter<Rack, RacksFirestoreAdapter.RackViewHolder> {


    private OnRackSelectedListener listener;
    public RacksFirestoreAdapter(@NonNull FirestoreRecyclerOptions<Rack> options, OnRackSelectedListener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull RackViewHolder holder, int position, @NonNull Rack model) {
        holder.rack = model;
        holder.bindData();
    }

    @NonNull
    @Override
    public RackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RacksFirestoreAdapter.RackViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rack_shelf_item, parent, false), LayoutInflater.from(parent.getContext()),
                listener);
    }

    public static class RackViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        Rack rack;
        LayoutInflater inflater;
        OnRackSelectedListener listener;

        AppCompatTextView name;


        public RackViewHolder(@NonNull View itemView, LayoutInflater inflater, OnRackSelectedListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.rack_item_letter);
            itemView.setOnClickListener(this);
            this.listener = listener;
        }

        public void bindData()
        {
            name.setText(rack.getName());
        }

        @Override
        public void onClick(View view) {
            Log.d("RacksFirestoreAdapter", rack.toString());
            listener.onRackSelected(rack);
        }
    }
}
