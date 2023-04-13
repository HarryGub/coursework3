package com.shifuu.aawws.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.List;
import java.util.Objects;

public class Box implements ModelEntity{

    private List<DocumentReference> itemRefs;
    private String name;

    @Override
    public String toString() {
        return "Box{" +
                "itemRefs=" + itemRefs +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Box box = (Box) o;
        return Objects.equals(itemRefs, box.itemRefs) && Objects.equals(name, box.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemRefs, name);
    }

    public List<DocumentReference> getItemRefs() {

        return itemRefs;
    }

    public void setItemRefs(List<DocumentReference> itemRefs) {
        this.itemRefs = itemRefs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Box() {
    }

    @Override
    public int getLayer() {
        return LAYER_BOXES;
    }
}
