package com.shifuu.aawws.model;

import java.util.Objects;

public class Rack implements ModelEntity{

    private String name;

    public Rack() {
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
        Rack rack = (Rack) o;
        return Objects.equals(name, rack.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Rack{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int getLayer() {
        return LAYER_RACKS;
    }
}
