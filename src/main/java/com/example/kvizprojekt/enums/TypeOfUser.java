package com.example.kvizprojekt.enums;

public enum TypeOfUser {
    STANDARD_USER("STANDARD_USER"), ADMIN("ADMIN");
    private String description;
    TypeOfUser(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
