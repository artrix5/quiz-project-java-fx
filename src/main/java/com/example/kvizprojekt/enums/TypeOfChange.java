package com.example.kvizprojekt.enums;

public enum TypeOfChange {
    ADDED("ADDED"), DELETED("DELETED"), CHANGED("CHANGED");
    private String description;
    TypeOfChange(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}
