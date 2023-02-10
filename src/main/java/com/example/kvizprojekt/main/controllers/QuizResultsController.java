package com.example.kvizprojekt.main.controllers;

import com.example.kvizprojekt.entities.CalculateScore;
import com.example.kvizprojekt.files.FileManager;
import com.example.kvizprojekt.main.QuizApplication;
import com.example.kvizprojekt.threads.ScoreUpdaterThread;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;

public class QuizResultsController {

    @FXML
    private Label scoreLabel;
    @FXML
    private Button returnToStartScreenButton;
    @FXML
    private ProgressIndicator progress;
    public static int finalScore;
    public static int questionSize;

    public static String category;
    public static int maxPoints;

    @FXML
    public void initialize() throws InterruptedException {

        maxPoints = questionSize * 10;
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = now.format(QuizApplication.DATE_TIME_FORMAT_FULL);

        CalculateScore<Integer>percentage = new CalculateScore<>(finalScore, maxPoints);
        progress.setProgress(percentage.getResult());

        FileManager.writeScoreToFile(QuizApplication.username, finalScore, formattedDateTime, category);
        scoreLabel.setText(finalScore + " od "+ maxPoints + " bodova");

        ScoreUpdaterThread thread1 = new ScoreUpdaterThread(true);
        Thread t1 = new Thread(thread1);
        ScoreUpdaterThread thread2 = new ScoreUpdaterThread(false);
        Thread t2 = new Thread(thread2);
        t1.start();
        t1.join();
        t2.start();
        t2.join(2000);
    }

    @FXML
    private void backButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kvizprojekt/main/controllers/start_screen.fxml"));
        Parent quizRoot = loader.load();
        Stage stage = (Stage) returnToStartScreenButton.getScene().getWindow();
        stage.getScene().setRoot(quizRoot);
        stage.show();
    }
}