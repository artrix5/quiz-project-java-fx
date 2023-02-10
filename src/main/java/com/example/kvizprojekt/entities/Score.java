package com.example.kvizprojekt.entities;

public class Score {

    String username;
    int pointsEarned;
    String datePlayed;
    String categoryPlayed;

    public Score(String username, int pointsEarned, String datePlayed, String categoryPlayed) {
        this.username = username;
        this.pointsEarned = pointsEarned;
        this.datePlayed = datePlayed;
        this.categoryPlayed = categoryPlayed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPointsEarned() {
        return pointsEarned;
    }

    public void setPointsEarned(int pointsEarned) {
        this.pointsEarned = pointsEarned;
    }

    public String getDatePlayed() {
        return datePlayed;
    }

    public void setDatePlayed(String datePlayed) {
        this.datePlayed = datePlayed;
    }

    public String getCategoryPlayed() {
        return categoryPlayed;
    }

    public void setCategoryPlayed(String categoryPlayed) {
        this.categoryPlayed = categoryPlayed;
    }
}


