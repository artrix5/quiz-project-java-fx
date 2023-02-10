package com.example.kvizprojekt.threads;

import com.example.kvizprojekt.entities.Score;
import com.example.kvizprojekt.files.FileManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreUpdaterThread implements Runnable {

    private static final Object lock = new Object();
    private final boolean isUpdaterThread;
    public static Map<String, Integer> scoresByUser = new HashMap<>();
    public ScoreUpdaterThread(boolean isUpdaterThread) {
        this.isUpdaterThread = isUpdaterThread;
    }

    @Override
    public void run() {
        synchronized (lock) {
            if (isUpdaterThread) {

                List<Score> scores = FileManager.loadScores();

                for (Score score : scores) {
                    String username = score.getUsername();
                    int currentScore = scoresByUser.getOrDefault(username, 0);
                    scoresByUser.put(username, currentScore + score.getPointsEarned());
                }

                for (Map.Entry<String, Integer> entry : scoresByUser.entrySet()) {
                    FileManager.updateHighScore(entry.getKey(), entry.getValue());
                    System.out.printf("Thread 1 - Updating: %s %d\n", entry.getKey(), entry.getValue());
                }
            }
            else {
                int highestScore = 0;
                String highestScoringUser = null;
                for (Map.Entry<String, Integer> entry : scoresByUser.entrySet()) {
                    if (entry.getValue() > highestScore) {
                        highestScore = entry.getValue();
                        highestScoringUser = entry.getKey();
                    }
                }
                scoresByUser.clear();
                System.out.println("Thread 2 - User " + highestScoringUser + " has the highest score: " + highestScore);
                System.out.println(highestScoringUser);
            }
        }
    }
}