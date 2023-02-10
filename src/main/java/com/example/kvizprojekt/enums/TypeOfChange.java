package com.example.kvizprojekt.enums;

public enum TypeOfChange {
    ADDED("DODANO"), DELETED("IZBRISANO"), CHANGED("PROMIJENJENO");
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
