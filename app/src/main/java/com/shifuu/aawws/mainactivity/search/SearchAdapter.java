package com.shifuu.aawws.mainactivity.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.shifuu.aawws.R;
import com.shifuu.aawws.model.Item;
import com.shifuu.aawws.model.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class SearchAdapter extends ListAdapter<Item, SearchAdapter.ItemViewHolder> {

    public static final DiffUtil.ItemCallback<Item> DIFF_CALLBACK = new DiffUtil.ItemCallback<Item>()
    {

        @Override
        public boolean areItemsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.equals(newItem);
        }
    };

    private List<Item> list;
    private OnItemSelectedListener listener;


    protected SearchAdapter(List<Item> list, OnItemSelectedListener onItemSelectedListener) {
        super(DIFF_CALLBACK);
        this.list = list;
        this.listener = onItemSelectedListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item_search, parent, false);
        return new SearchAdapter.ItemViewHolder(rootView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.item = list.get(position);
        holder.bindData();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void submitList(@Nullable List<Item> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder
    {

        Item item;
        AppCompatTextView name;
        AppCompatTextView qua;
        AppCompatTextView box;
        AppCompatTextView booked;

        public ItemViewHolder(@NonNull View itemView, OnItemSelectedListener listener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemSelected(item);
                }
            });
            name = (AppCompatTextView) itemView.findViewById(R.id.item_name);
            qua = (AppCompatTextView) itemView.findViewById(R.id.item_qua);
            box = (AppCompatTextView) itemView.findViewById(R.id.item_box);
            booked = (AppCompatTextView) itemView.findViewById(R.id.item_qua_booked);

        }

        void bindData()
        {
            name.setText(item.getName());
            qua.setText("Всего: " + item.getQua());
            box.setText(item.getBox());
            booked.setText("Бронировано: " + String.valueOf(item.getBooked()));
        }
    }
}
