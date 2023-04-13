package com.shifuu.aawws.model;

import java.io.Serializable;
import java.util.Objects;

public class Item implements Serializable {

    private String name;
    private String qua;
    private String box;
    private String itemId;
    private int booked;

    public int getBooked() {
        return booked;
    }

    public void setBooked(int booked) {
        this.booked = booked;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", qua='" + qua + '\'' +
                ", box='" + box + '\'' +
                ", itemId='" + itemId + '\'' +
                ", booked=" + booked +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return booked == item.booked && Objects.equals(name, item.name) && Objects.equals(qua, item.qua) && Objects.equals(box, item.box) && Objects.equals(itemId, item.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, qua, box, itemId, booked);
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

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Item() {
    }
}
