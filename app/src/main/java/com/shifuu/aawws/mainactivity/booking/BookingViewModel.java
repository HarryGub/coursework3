package com.shifuu.aawws.mainactivity.booking;

import android.app.Application;

import com.shifuu.aawws.model.Item;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

public class BookingViewModel extends AndroidViewModel {

    private Item item;
    private String quaToBook;

    public BookingViewModel(@NonNull Application application) {
        super(application);
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public String getQuaToBook() {
        return quaToBook;
    }

    public void setQuaToBook(String quaToBook) {
        this.quaToBook = quaToBook;
    }
}