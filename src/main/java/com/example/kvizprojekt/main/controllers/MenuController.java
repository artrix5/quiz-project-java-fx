package com.example.kvizprojekt.main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class MenuController {

    @FXML
    private Button showStartScreenButton;
    @FXML
    private Button showDatabaseButton;
    @FXML
    private Button showChangesButton;
    @FXML
    private Button showUserResultsButton;
    @FXML
    private Button signOutButton;
    @FXML
    private Button closeButton;

    public void showScreen(Button button, String name) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(name));
        Parent root = loader.load();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();
    }

    @FXML
    public void showMainScreen() throws IOException {
        showScreen(showStartScreenButton, "/com/example/kvizprojekt/main/controllers/start_screen.fxml");
    }
    @FXML
    public void showDatabaseTable() throws IOException {
        showScreen(showDatabaseButton, "/com/example/kvizprojekt/main/controllers/table.fxml");
    }

    @FXML
    public void showUserResultsTable() throws IOException {
        showScreen(showUserResultsButton, "/com/example/kvizprojekt/main/controllers/user_results.fxml");
    }
    @FXML
    public void showChangesTable() throws IOException {
        showScreen(showChangesButton, "/com/example/kvizprojekt/main/controllers/changes.fxml");
    }
    @FXML
    public void signOut() throws IOException {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Potvrdite odjavu");
        confirmDialog.setHeaderText("Jeste li sigurni da se želite odjaviti?");
        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            showScreen(signOutButton, "login.fxml");
        }
    }
    @FXML
    public void closeApplication() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
