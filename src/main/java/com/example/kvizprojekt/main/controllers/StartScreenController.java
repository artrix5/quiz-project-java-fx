package com.example.kvizprojekt.main.controllers;

import com.example.kvizprojekt.database.DatabaseConnection;
import com.example.kvizprojekt.exceptions.NotEnoughQuestionsException;
import com.example.kvizprojekt.main.QuizApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class StartScreenController {

    @FXML
    private Label usernameLabel;
    @FXML
    private ChoiceBox<String> categoryChoiceBox;
    @FXML
    private ChoiceBox<String> questionsChoiceBox;
    @FXML
    private Button startButton;
    private final DatabaseConnection database = new DatabaseConnection();

    @FXML
    public void initialize() {

        usernameLabel.setText("Welcome, " + QuizApplication.username + ".");
        List<String> categories = database.getCategories();
        categoryChoiceBox.getItems().addAll(categories);
        categoryChoiceBox.getSelectionModel().selectFirst();
        questionsChoiceBox.getItems().addAll("5", "10", "15", "20");
        questionsChoiceBox.getSelectionModel().selectFirst();

    }
    @FXML
    private void handleStartButtonOnClick() {
        String selectedCategory = categoryChoiceBox.getValue();
        int selectedNumberOfQuestions = Integer.parseInt(questionsChoiceBox.getValue());

        if (selectedCategory == null || selectedNumberOfQuestions == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Empty fields.");
            alert.setContentText("You must choose a category and number of questions.");
            alert.showAndWait();
        }

        else {
            try {
                if (database.getNumberOfQuestionsInCategory(selectedCategory) < selectedNumberOfQuestions) {
                    throw new NotEnoughQuestionsException("Not enough questions in selected category.");
                }
                QuizController.category = selectedCategory;
                QuizController.numberOfQuestions = selectedNumberOfQuestions;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kvizprojekt/main/controllers/quiz.fxml"));
                Parent quizRoot = loader.load();
                Stage stage = (Stage) startButton.getScene().getWindow();
                stage.getScene().setRoot(quizRoot);
                stage.show();
            } catch (NotEnoughQuestionsException e) {
                QuizApplication.logger.warn("Nedovoljan broj pitanja u odabranoj kategoriji!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("Not enough questions.");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            } catch (IOException e) {
                QuizApplication.logger.error("Greška kod učitavanja kviza: ", e);
                e.printStackTrace();
            }
        }
    }
}
