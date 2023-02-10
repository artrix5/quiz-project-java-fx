package com.example.kvizprojekt.main.controllers;

import com.example.kvizprojekt.entities.LoginValidation;
import com.example.kvizprojekt.main.QuizApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController extends LoginValidation {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    public void handleLogin() {

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (checkCredentials(username, password)) {
            try {
                    QuizApplication.username = username;
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/kvizprojekt/main/controllers/start_screen.fxml"));
                    Parent root = fxmlLoader.load();
                    Scene currentScene = usernameField.getScene();
                    currentScene.setRoot(root);

            } catch (IOException | IllegalStateException e) {
                QuizApplication.logger.error("Dogodila se greška tijekom prijave: ", e);
                e.printStackTrace();
            }
        } else {
            QuizApplication.logger.warn("Neispravno uneseno korisničko ime ili šifra!");
            System.out.println("Access denied!");
        }
    }

    @FXML
    public void registerHyperlink() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/kvizprojekt/main/controllers/register.fxml"));
            Parent root = fxmlLoader.load();
            Scene currentScene = usernameField.getScene();
            currentScene.setRoot(root);

        } catch (IOException | IllegalStateException e) {
            QuizApplication.logger.error("Greška tijekom otvaranja ekrana za registraciju: ", e);
            e.printStackTrace();
        }

    }
}
