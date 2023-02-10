package com.example.kvizprojekt.main.controllers;

import com.example.kvizprojekt.database.DatabaseConnection;
import com.example.kvizprojekt.entities.Question;
import com.example.kvizprojekt.main.QuizApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


public class QuizController {
    @FXML
    private Label questionLabel;
    @FXML
    private RadioButton answer1;
    @FXML
    private RadioButton answer2;
    @FXML
    private RadioButton answer3;
    @FXML
    private RadioButton answer4;
    @FXML
    private Button submitButton;
    @FXML
    private ProgressBar progressBar;

    private List<Question> questions;
    private ToggleGroup toggleGroup;
    private final DatabaseConnection database = new DatabaseConnection();
    private int currentQuestionIndex = 0;
    private int questionsSize = 0;
    public static int score = 0;

    public static String category;
    public static int numberOfQuestions;

    @FXML
    public void initialize() {

        score = 0;
        progressBar.setProgress(0);
        questionsSize = numberOfQuestions;
        currentQuestionIndex = 0;

        toggleGroup = new ToggleGroup();
        answer1.setToggleGroup(toggleGroup);
        answer2.setToggleGroup(toggleGroup);
        answer3.setToggleGroup(toggleGroup);
        answer4.setToggleGroup(toggleGroup);

        loadQuestionsFromDatabase();
        showQuestion();

    }

    public void loadQuestionsFromDatabase() {
        questions = database.getQuestions(category);
        Collections.shuffle(questions);
    }

    private void showQuestion() {

        if (currentQuestionIndex >= questionsSize) {
            submitButton.setOnAction(event -> {
                try {
                    showResults();
                } catch (IOException e) {
                    QuizApplication.logger.error("Greška kod otvaranja rezultata.", e);
                    throw new RuntimeException(e);
                }
            });
        }

        if (currentQuestionIndex < questionsSize) {
            submitButton.setText("Potvrdi");
            submitButton.setOnAction(event -> submitButtonClicked());
        }

        Question currentQuestion = questions.get(currentQuestionIndex);
        questionLabel.setText(currentQuestion.getQuestion());
        answer1.setText(currentQuestion.getAnswer1());
        answer1.setStyle("-fx-text-fill: black;");
        answer2.setText(currentQuestion.getAnswer2());
        answer2.setStyle("-fx-text-fill: black;");
        answer3.setText(currentQuestion.getAnswer3());
        answer3.setStyle("-fx-text-fill: black;");
        answer4.setText(currentQuestion.getAnswer4());
        answer4.setStyle("-fx-text-fill: black;");
        toggleGroup.selectToggle(null);

        double progress = (currentQuestionIndex + 1) / (double) questionsSize;
        progressBar.setProgress(progress);

    }
    @FXML
    private void submitButtonClicked() {

        RadioButton selectedAnswer = (RadioButton) toggleGroup.getSelectedToggle();

        if (selectedAnswer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška!");
            alert.setHeaderText("Odgovor nije odabran!");
            alert.setContentText("Morate odabrati odgovor prije nego što potvrdite vaš odabir.");
            alert.showAndWait();
            return;
        }
        String correctAnswer = questions.get(currentQuestionIndex).getCorrectAnswer();

        if (selectedAnswer.getText().equals(correctAnswer)) {
            selectedAnswer.setStyle("-fx-text-fill: #7fff00");
            score += 10;
        } else {
            selectedAnswer.setStyle("-fx-text-fill: red;");

            if (answer1.getText().equals(correctAnswer)) {
                answer1.setStyle("-fx-text-fill: #7fff00;");

            } else if (answer2.getText().equals(correctAnswer)) {
                answer2.setStyle("-fx-text-fill: #7fff00");

            } else if (answer3.getText().equals(correctAnswer)) {
                answer3.setStyle("-fx-text-fill: #7fff00");

            } else if (answer4.getText().equals(correctAnswer)) {
                answer4.setStyle("-fx-text-fill: #7fff00");

            }
        }

        if (currentQuestionIndex < questionsSize- 1) {
            submitButton.setText("Iduće pitanje");
        }
        else {
            submitButton.setText("ZAVRŠI KVIZ");
        }


        submitButton.setOnAction(event -> {
            switch (submitButton.getText()) {
                case "Iduće pitanje" -> {
                    currentQuestionIndex++;
                    showQuestion();
                }
                case "ZAVRŠI KVIZ" -> {
                    try {
                        showResults();
                    } catch (IOException e) {
                        QuizApplication.logger.error("Greška kod otvaranja rezultata.", e);
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    @FXML
    private void showResults() throws IOException {

            QuizResultsController.finalScore = score;
            QuizResultsController.questionSize = numberOfQuestions;
            QuizResultsController.category = category;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kvizprojekt/main/controllers/result.fxml"));
            Parent quizRoot = loader.load();
            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.getScene().setRoot(quizRoot);
            stage.show();

    }
}


