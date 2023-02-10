package com.example.kvizprojekt.main.controllers;

import com.example.kvizprojekt.files.FilePaths;
import com.example.kvizprojekt.database.DatabaseConnection;
import com.example.kvizprojekt.entities.Change;
import com.example.kvizprojekt.entities.Question;
import com.example.kvizprojekt.entities.TableValidation;
import com.example.kvizprojekt.enums.TypeOfChange;
import com.example.kvizprojekt.exceptions.DuplicateAnswersException;
import com.example.kvizprojekt.exceptions.EmptyFieldsException;
import com.example.kvizprojekt.exceptions.MissingAnswerException;
import com.example.kvizprojekt.main.QuizApplication;
import com.example.kvizprojekt.util.SerializationUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public class TableController extends TableValidation {

    @FXML
    private TextField addCategory;
    @FXML
    private TextField addQuestion;
    @FXML
    private TextField addAnswer1;
    @FXML
    private TextField addAnswer2;
    @FXML
    private TextField addAnswer3;
    @FXML
    private TextField addAnswer4;
    @FXML
    private TextField addCorrectAnswer;
    @FXML
    private TableView<Question> tableView;
    @FXML
    private TableColumn<Question, String> categoryColumn;
    @FXML
    private TableColumn<Question, String> questionColumn;
    @FXML
    private TableColumn<Question, String> answer1Column;
    @FXML
    private TableColumn<Question, String> answer2Column;
    @FXML
    private TableColumn<Question, String> answer3Column;
    @FXML
    private TableColumn<Question, String> answer4Column;
    @FXML
    private TableColumn<Question, String> correctAnswerColumn;

    private ObservableList<Question> data = FXCollections.observableArrayList();

    private final DatabaseConnection database = new DatabaseConnection();

    LocalDateTime now = LocalDateTime.now();
    String formattedDateTime = now.format(QuizApplication.DATE_TIME_FORMAT_FULL);

    @FXML
    public void initialize() {

        categoryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategory()));
        questionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getQuestion()));
        answer1Column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAnswer1()));
        answer2Column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAnswer2()));
        answer3Column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAnswer3()));
        answer4Column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAnswer4()));
        correctAnswerColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCorrectAnswer()));

        data.addAll(database.loadData());
        tableView.setItems(data);
    }

    @FXML
    private void handleAddButton() {
        String category = addCategory.getText();
        String question = addQuestion.getText();
        String answer1 = addAnswer1.getText();
        String answer2 = addAnswer2.getText();
        String answer3 = addAnswer3.getText();
        String answer4 = addAnswer4.getText();
        String correctAnswer = addCorrectAnswer.getText();

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Potvrda unosa pitanja");
        confirmAlert.setHeaderText("Jeste li sigurni da želite unijeti ovo pitanje?");
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (isAdmin(QuizApplication.username)) {
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {

                    if (checkEmptyFields(category, question, answer1, answer2, answer3, answer4, correctAnswer)) {
                        throw new EmptyFieldsException("Jedno ili više polja je prazno.");
                    }

                    if (checkDuplicates(answer1, answer2, answer3, answer4)) {
                        throw new DuplicateAnswersException("Pronađeni isti odgovori. Svaki ponuđeni odgovor mora biti jedinstven.");
                    }

                    if (checkMissingAnswer(answer1, answer2, answer3, answer4, correctAnswer)) {
                        throw new MissingAnswerException("Rješenje koje ste unijeli ne postoji u ponuđenim odgovorima! Unesite ga kao jedan od ponuđenih odgovora.");
                    }

                    database.addQuestion(category, question, answer1, answer2, answer3, answer4, correctAnswer);
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Uspjeh");
                    successAlert.setHeaderText("Pitanje je uspješno uneseno");
                    successAlert.showAndWait();
                    Question addedQuestion = new Question(category, question, answer1, answer2, answer3, answer4, correctAnswer);
                    data.add(addedQuestion);

                    Question emptyQuestion = new Question(null, "/", null, null, null, null, "/");
                    Change add = Change.newBuilder()
                            .withUsername(QuizApplication.username)
                            .withDateTime(formattedDateTime)
                            .withTypeOfChange(TypeOfChange.ADDED.getDescription())
                            .withQuestionOldValue(emptyQuestion)
                            .withQuestionNewValue(addedQuestion)
                            .build();
                    SerializationUtil.serialize(add, FilePaths.CHANGES_FILE);
                    QuizApplication.logger.info("Novo pitanje je uspješno dodano.");

                } catch (EmptyFieldsException e) {
                    showErrorDialog("Prazna polja.", e.getMessage());
                } catch (DuplicateAnswersException e) {
                    showErrorDialog("Pronađeni duplikati u odgovorima.", e.getMessage());
                } catch (MissingAnswerException e) {
                    showErrorDialog("Odgovor za ponuđeno rješenje ne postoji.", e.getMessage());
                } catch (IOException e) {
                    QuizApplication.logger.error("Pogreška kod serijaliziranja objekta.", e);
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
        else {
            showErrorDialog("Neovlašten pristup!", "Samo admin može dodavati nova pitanja.");
        }
    }

    @FXML
    public void handleDeleteButton() throws IOException {
        Question selectedQuestion = tableView.getSelectionModel().getSelectedItem();
        if (selectedQuestion == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nema odabira.");
            alert.setHeaderText("Niste odabrali pitanje.");
            alert.setContentText("Odaberite pitanje iz tablice.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrda brisanja.");
            alert.setHeaderText("Jeste li sigurni da želite obrisati odabrano pitanje?");
            Optional<ButtonType> result = alert.showAndWait();
            if (isAdmin(QuizApplication.username)) {
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    database.deleteQuestion(selectedQuestion.getId());
                    data.remove(selectedQuestion);
                    tableView.setItems(data);

                    Question emptyQuestion = new Question(null, "/", null, null, null, null, "/");
                    Change delete = Change.newBuilder()
                            .withUsername(QuizApplication.username)
                            .withDateTime(formattedDateTime)
                            .withTypeOfChange(TypeOfChange.DELETED.getDescription())
                            .withQuestionOldValue(selectedQuestion)
                            .withQuestionNewValue(emptyQuestion)
                            .build();

                    SerializationUtil.serialize(delete, FilePaths.CHANGES_FILE);
                    QuizApplication.logger.info("Odabrano pitanje je uspješno izbrisano.");
                }
            }
            else {
                showErrorDialog("Neovlašten pristup!", "Samo admin može brisati pitanja.");
            }
        }
    }

    @FXML
    public void handleFilterButton() {

        List<Question> filtered = data.stream()
                    .filter(s -> s.getCategory().toLowerCase().contains(addCategory.getText().toLowerCase()))
                    .filter(s -> s.getQuestion().toLowerCase().contains(addQuestion.getText().toLowerCase()))
                    .filter(s -> s.getAnswer1().toLowerCase().contains(addAnswer1.getText().toLowerCase()))
                    .filter(s -> s.getAnswer2().toLowerCase().contains(addAnswer2.getText().toLowerCase()))
                    .filter(s -> s.getAnswer3().toLowerCase().contains(addAnswer3.getText().toLowerCase()))
                    .filter(s -> s.getAnswer4().toLowerCase().contains(addAnswer4.getText().toLowerCase()))
                    .filter(s -> s.getCorrectAnswer().toLowerCase().contains(addCorrectAnswer.getText().toLowerCase()))
                    .toList();

        tableView.setItems(FXCollections.observableList(filtered));
    }

    @FXML
    public void handleSaveButton() {

        Question selectedQuestion = tableView.getSelectionModel().getSelectedItem();

        if (selectedQuestion != null) {

            String newCategory = addCategory.getText().trim().isEmpty() ? selectedQuestion.getCategory() : addCategory.getText().toLowerCase();
            String newQuestion = addQuestion.getText().trim().isEmpty() ? selectedQuestion.getQuestion() : addQuestion.getText();
            String newAnswer1 = addAnswer1.getText().trim().isEmpty() ? selectedQuestion.getAnswer1() : addAnswer1.getText();
            String newAnswer2 = addAnswer2.getText().trim().isEmpty() ? selectedQuestion.getAnswer2() : addAnswer2.getText();
            String newAnswer3 = addAnswer3.getText().trim().isEmpty() ? selectedQuestion.getAnswer3() : addAnswer3.getText();
            String newAnswer4 = addAnswer4.getText().trim().isEmpty() ? selectedQuestion.getAnswer4() : addAnswer4.getText();
            String newCorrectAnswer = addCorrectAnswer.getText().trim().isEmpty() ? selectedQuestion.getCorrectAnswer() : addCorrectAnswer.getText();

            Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDialog.setTitle("Potvrdite promjene.");
            confirmDialog.setHeaderText("Potvrdite promjene koje ste napravili:");
            confirmDialog.setContentText("Kategorija: " + newCategory + "\nPitanje: " + newQuestion + "\n1. odgovor: " + newAnswer1 + "\n2. odgovor: " + newAnswer2 + "\n3. odgovor: " + newAnswer3 + "\n4. odgovor: " + newAnswer4 + "\nTočan odgovor: " + newCorrectAnswer);

            Optional<ButtonType> result = confirmDialog.showAndWait();
            if (isAdmin(QuizApplication.username)) {
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    try {

                        if (checkDuplicates(newAnswer1, newAnswer2, newAnswer3, newAnswer4)) {
                            throw new DuplicateAnswersException("Pronađeni isti odgovori. Svaki ponuđeni odgovor mora biti jedinstven.");
                        }

                        if (checkMissingAnswer(newAnswer1, newAnswer2, newAnswer3, newAnswer4, newCorrectAnswer)) {
                            throw new MissingAnswerException("Rješenje koje ste unijeli ne postoji u ponuđenim odgovorima! Unesite ga kao jedan od ponuđenih odgovora.");
                        }

                        Question updatedQuestion = new Question(newCategory, selectedQuestion.getId(), newQuestion, newCorrectAnswer, newAnswer1, newAnswer2, newAnswer3, newAnswer4);
                        database.updateQuestion(updatedQuestion);

                        Change save = Change.newBuilder()
                                .withUsername(QuizApplication.username)
                                .withDateTime(formattedDateTime)
                                .withTypeOfChange(TypeOfChange.CHANGED.getDescription())
                                .withQuestionOldValue(selectedQuestion)
                                .withQuestionNewValue(updatedQuestion)
                                .build();
                        SerializationUtil.serialize(save, FilePaths.CHANGES_FILE);
                        QuizApplication.logger.info("Pitanje je uspješno promijenjeno.");

                        selectedQuestion.setCategory(newCategory);
                        selectedQuestion.setQuestion(newQuestion);
                        selectedQuestion.setAnswer1(newAnswer1);
                        selectedQuestion.setAnswer2(newAnswer2);
                        selectedQuestion.setAnswer3(newAnswer3);
                        selectedQuestion.setAnswer4(newAnswer4);
                        selectedQuestion.setCorrectAnswer(newCorrectAnswer);

                        tableView.refresh();

                    } catch (MissingAnswerException e) {
                        showErrorDialog("Odgovor za ponuđeno rješenje ne postoji.", e.getMessage());
                    } catch (DuplicateAnswersException e) {
                        showErrorDialog("Pronađeni duplikati u odgovorima.", e.getMessage());
                    } catch (SQLException | IOException e) {
                        QuizApplication.logger.error("Greška kod ažuriranja podataka.", e);
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            } else {
                showErrorDialog("Neovlašten pristup!", "Samo admin može mijenjati podatke.");
            }
        } else {
            showErrorDialog("Pitanje nije odabrano.","Odaberite pitanje iz tablice kako bi napravili željene promjene.");
        }
    }

    @FXML
    public void handleClearButton() {
        addCategory.clear();
        addQuestion.clear();
        addAnswer1.clear();
        addAnswer2.clear();
        addAnswer3.clear();
        addAnswer4.clear();
        addCorrectAnswer.clear();
    }

    public void showErrorDialog(String headerText, String contentText) {
        Alert errorDialog = new Alert(Alert.AlertType.ERROR);
        errorDialog.setTitle("Greška!");
        errorDialog.setHeaderText(headerText);
        errorDialog.setContentText(contentText);
        errorDialog.showAndWait();
    }
}
