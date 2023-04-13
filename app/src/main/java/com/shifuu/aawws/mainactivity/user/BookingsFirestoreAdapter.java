package com.shifuu.aawws.mainactivity.user;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shifuu.aawws.R;
import com.shifuu.aawws.model.Booking;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class BookingsFirestoreAdapter extends FirestoreRecyclerAdapter<Booking, BookingsFirestoreAdapter.BookingsHolder> {


    public BookingsFirestoreAdapter(@NonNull FirestoreRecyclerOptions<Booking> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BookingsHolder holder, int position, @NonNull Booking model) {
        holder.booking = model;
        holder.bindData();
    }

    @NonNull
    @Override
    public BookingsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookingsHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking_item, parent, false), LayoutInflater.from(parent.getContext()));
    }

    public static class BookingsHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        Booking booking;

        AppCompatTextView name;
        AppCompatTextView quantity;
        LayoutInflater inflater;

        public BookingsHolder(@NonNull View itemView, LayoutInflater inflater) {
            super(itemView);
            this.inflater = inflater;
            name = itemView.findViewById(R.id.bookings_name);
            quantity = itemView.findViewById(R.id.bookings_quantity);
            itemView.setOnClickListener(this);
        }


        public void bindData()
        {
            Log.d("BookigsAdapter", booking.toString());
            name.setText(booking.getName());
            quantity.setText(booking.getQua());
        }

        @Override
        public void onClick(View view) {
            showPopup(view);
        }

        private void showPopup(View view)
        {
            PopupMenu popupMenu = new PopupMenu(this.itemView.getContext(), view);
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.deletion_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {

                    FirebaseFirestore.getInstance()
                            .collection("items")
                            .document(booking.getItem())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful())
                                            {
                                                int booked = task.getResult().get("booked", Integer.class);
                                                booked -= Integer.parseInt(booking.getQua());

                                                FirebaseFirestore.getInstance()
                                                        .collection("items")
                                                        .document(booking.getItem())
                                                        .update("booked", booked);

                                                FirebaseFirestore
                                                        .getInstance()
                                                        .collection("booking")
                                                        .document(booking.getBookId())
                                                        .delete()
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                Toast.makeText(itemView.getContext(), "Бронь отменена", Toast.LENGTH_LONG).show();
                                                            }})
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(itemView.getContext(), "Не удалось отменить бронь", Toast.LENGTH_LONG).show();
                                                            }
                                                        });
                                            }
                                        }
                                    });

                    return true;
                }
            });
            popupMenu.show();
        }
    }
}






