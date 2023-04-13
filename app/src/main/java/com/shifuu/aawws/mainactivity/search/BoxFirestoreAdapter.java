package com.shifuu.aawws.mainactivity.search;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.shifuu.aawws.R;
import com.shifuu.aawws.model.Box;
import com.shifuu.aawws.model.Item;
import com.shifuu.aawws.model.OnItemSelectedListener;

import java.util.Random;
import java.util.function.Consumer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class BoxFirestoreAdapter extends FirestoreRecyclerAdapter<Box, BoxFirestoreAdapter.BoxViewHolder> {


    private OnItemSelectedListener listener;
    public BoxFirestoreAdapter(@NonNull FirestoreRecyclerOptions<Box> options, OnItemSelectedListener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull BoxViewHolder holder, int position, @NonNull Box model) {
        holder.box = model;
        holder.bindData();
    }

    @NonNull
    @Override
    public BoxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.box_item, parent, false);



        BoxViewHolder holder = new BoxFirestoreAdapter.BoxViewHolder(view, LayoutInflater.from(parent.getContext()),
                listener);
        return holder;
    }

    public static class BoxViewHolder extends RecyclerView.ViewHolder
    {
        Box box;
        LayoutInflater inflater;
        OnItemSelectedListener listener;

        AppCompatTextView name;
        LinearLayout viewLayout;



        public BoxViewHolder(@NonNull View itemView, LayoutInflater inflater, OnItemSelectedListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.box_name);
            this.listener = listener;
            viewLayout = itemView.findViewById(R.id.box_item_linear_layout);
            this.inflater = inflater;
        }

        public void bindData()
        {
            viewLayout.removeViewsInLayout(0, viewLayout.getChildCount());

            name.setText(box.getName());

            Log.d("BoxFirebaseAdapter", box.toString());

            if(box.getItemRefs() == null)
                return;


            box.getItemRefs().forEach(new Consumer<DocumentReference>() {
                @Override
                public void accept(DocumentReference documentReference) {
                    documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null)
                            {
                                Log.d("BoxFirebaseAdapter", "error retrieving item", error);
                                return;
                            }

                            Item i = new Item();
                            i.setName(value.get("name", String.class));
                            i.setQua(String.valueOf(value.get("qua", Integer.class)));
                            i.setItemId(value.getId());
                            try{
                                i.setBooked(value.get("booked", Integer.class));
                            }catch (NullPointerException e)
                            {
                                i.setBooked(0);
                            }
                            Log.d("BoxFirebaseAdapter", i.toString());

                            View view = inflater.inflate(R.layout.item_item, viewLayout, false);

                            ((AppCompatTextView)view.findViewById(R.id.item_name)).setText(i.getName());
                            ((AppCompatTextView)view.findViewById(R.id.item_qua)).setText("Всего: " + i.getQua());
                            ((AppCompatTextView)view.findViewById(R.id.item_qua_booked_it)).setText("Бронировано: " + String.valueOf(i.getBooked()));

                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Log.d("BoxFirebaseAdapter#bindData#OnClick", i.toString());
                                    listener.onItemSelected(i);
                                }
                            });

                            view.setId(new Random().nextInt());

                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);


                            view.setLayoutParams(lp);

                            viewLayout.addView(view);
                        }
                    });
                }
            });

        }
    }
}