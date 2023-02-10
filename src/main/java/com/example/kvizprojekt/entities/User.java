package com.example.kvizprojekt.entities;

public class User {

    String userName;
    String hashedPassword;
    String role;
    int highScore;

    public User(String userName, String hashedPassword, String role, int highScore) {
        this.userName = userName;
        this.hashedPassword = hashedPassword;
        this.role = role;
        this.highScore = highScore;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}
