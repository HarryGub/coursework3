package com.shifuu.aawws.model;

import java.util.Objects;

public class Shelf implements ModelEntity{
    private String name;

    public Shelf() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelf shelf = (Shelf) o;
        return Objects.equals(name, shelf.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Shelf{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int getLayer() {
        return LAYER_SHELVES;
    }
}
