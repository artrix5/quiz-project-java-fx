package com.example.kvizprojekt.main.controllers;

import com.example.kvizprojekt.entities.Change;
import com.example.kvizprojekt.files.FilePaths;
import com.example.kvizprojekt.main.QuizApplication;
import com.example.kvizprojekt.util.SerializationUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ChangesController {

    @FXML
    private TextField enterUserTextField;
    @FXML
    private TextField enterDateTimeTextField;
    @FXML
    private TextField enterChangeTextField;
    @FXML
    private TableView<Change> changesTableView;
    @FXML
    private TableColumn<Change, String> userColumn;
    @FXML
    private TableColumn<Change, String> dateColumn;
    @FXML
    private TableColumn<Change, String> typeColumn;
    @FXML
    private TableColumn<Change, String> questionOldValueColumn;
    @FXML
    private TableColumn<Change, String> questionNewValueColumn;
    @FXML
    private TableColumn<Change, String> oldAnswerValueColumn;
    @FXML
    private TableColumn<Change, String> newAnswerValueColumn;

    private List<Change> changesMade = new ArrayList<>();

    @FXML
    public void initialize() {

        loadData();

        userColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUsername()));
        dateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDateTime()));
        typeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTypeOfChange()));
        questionOldValueColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getQuestionOldValue().getQuestion()));
        questionNewValueColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getQuestionNewValue().getQuestion()));
        oldAnswerValueColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getQuestionOldValue().getCorrectAnswer()));
        newAnswerValueColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getQuestionNewValue().getCorrectAnswer()));

        changesTableView.setItems(FXCollections.observableList(changesMade));
    }

    public void loadData() {

        try {
            if (Files.exists(FilePaths.CHANGES_FILE) && Files.size(FilePaths.CHANGES_FILE) > 0) {
                changesMade = SerializationUtil.deserialize(FilePaths.CHANGES_FILE);
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

        String name = enterUserTextField.getText();

        String dateTime = enterDateTimeTextField.getText();

        String changeType = enterChangeTextField.getText();

        List<Change> filteredChanges = changesMade.stream()
                .filter(s -> s.getUsername().contains(name))
                .filter(s -> s.getDateTime().contains(dateTime))
                .filter(s -> s.getTypeOfChange().contains(changeType.toUpperCase()))
                .toList();

        changesTableView.setItems(FXCollections.observableList(filteredChanges));
    }
}













