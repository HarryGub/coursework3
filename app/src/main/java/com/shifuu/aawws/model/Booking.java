package com.shifuu.aawws.model;

import java.util.Objects;

public class Booking {
    private String name;
    private String qua;
    private String uId;
    private String item;
    private String bookId;

    @Override
    public String toString() {
        return "Booking{" +
                "name='" + name + '\'' +
                ", qua='" + qua + '\'' +
                ", uId='" + uId + '\'' +
                ", item='" + item + '\'' +
                ", bookId='" + bookId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(name, booking.name) && Objects.equals(qua, booking.qua) && Objects.equals(uId, booking.uId) && Objects.equals(item, booking.item) && Objects.equals(bookId, booking.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, qua, uId, item, bookId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQua() {
        return qua;
    }

    public void setQua(String qua) {
        this.qua = qua;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Booking() {
    }
}
