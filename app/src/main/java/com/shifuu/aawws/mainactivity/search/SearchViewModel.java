package com.shifuu.aawws.mainactivity.search;

import android.app.Application;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.shifuu.aawws.model.Item;
import com.shifuu.aawws.model.ModelEntity;
import com.shifuu.aawws.model.Rack;
import com.shifuu.aawws.model.Shelf;

import java.util.List;
import java.util.Stack;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends AndroidViewModel {

    private Rack currentRack;
    private Shelf currentShelf;
    private boolean isSearchAttached;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    private List<Item> items;

    public Rack getCurrentRack() {
        return currentRack;
    }

    public void setCurrentRack(Rack currentRack) {
        this.currentRack = currentRack;
    }

    public Shelf getCurrentShelf() {
        return currentShelf;
    }

    public void setCurrentShelf(Shelf currentShelf) {
        this.currentShelf = currentShelf;
    }

    public SearchViewModel(@NonNull Application application) {
        super(application);


    }

    public boolean isSearchAttached() {
        return isSearchAttached;
    }

    public void setSearchAttached(boolean searchAttached) {
        isSearchAttached = searchAttached;
    }
}