package com.example.kvizprojekt.main.controllers;

import com.example.kvizprojekt.entities.Score;
import com.example.kvizprojekt.entities.User;
import com.example.kvizprojekt.files.FileManager;
import com.example.kvizprojekt.files.FilePaths;
import com.example.kvizprojekt.main.QuizApplication;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class UserResultsController {
    @FXML
    private TextField enterUsernameTextField;
    @FXML
    private TableView<Score> tableViewScore;
    @FXML
    private TableColumn<Score, String> userScoreColumn;
    @FXML
    private TableColumn<Score, String> datePlayedColumn;
    @FXML
    private TableColumn<Score, String> pointsColumn;
    @FXML
    private TableColumn<Score, String> categoryPlayedColumn;
    @FXML
    private TableView<User> tableViewHighScore;
    @FXML
    private TableColumn<User, String> userHighScoreColumn;
    @FXML
    private TableColumn<User, String> highScoreColumn;
    private ObservableList<User> users = FXCollections.observableArrayList();
    private ObservableList<Score> scores = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

            loadDataScores();
            loadDataUsers();

            userScoreColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUsername()));
            datePlayedColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDatePlayed()));
            pointsColumn.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getPointsEarned())));
            categoryPlayedColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategoryPlayed()));
            scores = FileManager.loadScores();
            tableViewScore.setItems(scores);

            userHighScoreColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUserName()));
            highScoreColumn.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getHighScore())));
            users = FileManager.loadUsers();
            tableViewHighScore.setItems(users);
    }

    public void loadDataScores() {
        try {
            if (Files.exists(FilePaths.SCORES_FILE) && Files.size(FilePaths.SCORES_FILE) > 0) {
                scores = FileManager.loadScores();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            QuizApplication.logger.error("Problem kod učitavanja podataka.", e);
            throw new RuntimeException();
        }
    }

    public void loadDataUsers() {
        try {
            if (Files.exists(FilePaths.CREDENTIALS_FILE) && Files.size(FilePaths.CREDENTIALS_FILE) > 0) {
                users = FileManager.loadUsers();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            QuizApplication.logger.error("Problem kod učitavanja podataka.", e);
            throw new RuntimeException();
        }
    }

    @FXML
    public void filter() {

        String name = enterUsernameTextField.getText();

        List<Score> filteredScores = scores.stream()
                .filter(s -> s.getUsername().contains(name))
                .toList();

        List<User> filteredUsers = users.stream()
                .filter(s -> s.getUserName().contains(name))
                .toList();

        tableViewScore.setItems(FXCollections.observableList(filteredScores));
        tableViewHighScore.setItems(FXCollections.observableList(filteredUsers));
    }
}







