package com.example.kvizprojekt.main;

import com.example.kvizprojekt.threads.ScoreUpdaterThread;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class QuizApplication extends Application {

    public static final Logger logger = LoggerFactory.getLogger(QuizApplication.class);
    public static final DateTimeFormatter DATE_TIME_FORMAT_FULL = DateTimeFormatter.ofPattern("d.M.yyyy. H:mm");

    public static String username;
    @Override
    public void start(Stage stage) throws IOException {
        logger.info("Program je pokrenut.");
        FXMLLoader fxmlLoader = new FXMLLoader(QuizApplication.class.getResource("/com/example/kvizprojekt/main/controllers/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("QuizProject");
        stage.setScene(scene);
        stage.show();

        //threads that update the score of each user and find the highest score
        ScoreUpdaterThread thread1 = new ScoreUpdaterThread(true);
        Thread t1 = new Thread(thread1);
        ScoreUpdaterThread thread2 = new ScoreUpdaterThread(false);
        Thread t2 = new Thread(thread2);
        t1.start();
        try {
            t1.join();
            t2.start();
            t2.join(2000);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        launch();
    }
}