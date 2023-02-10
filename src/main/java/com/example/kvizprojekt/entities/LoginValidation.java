package com.example.kvizprojekt.entities;

import com.example.kvizprojekt.main.QuizApplication;
import com.example.kvizprojekt.files.FilePaths;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class LoginValidation {

    //checks if the username exists, if it does it check the password
    protected boolean checkCredentials(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(FilePaths.CREDENTIALS_FILE.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].equals(username)) {
                    if (verifyPassword(password, parts[1])) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            QuizApplication.logger.error("Dogodila se greška tijekom provjere podataka: ", e);
            e.printStackTrace();
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greška");
        alert.setHeaderText("Netočni podaci");
        alert.setContentText("Korisničko ime ili šifra koju ste unijeli je netočna. Molimo pokušajte ponovno.");
        alert.showAndWait();
        return false;
    }

    //hashes the entered password and checks if the password equals already saved password hash in the credentials.txt
    protected boolean verifyPassword(String plainPassword, String hashedPassword) {
        String hashedPlainPassword = hashPassword(plainPassword);
        return hashedPlainPassword.equals(hashedPassword);
    }

    //SHA-256 algorithm for hashing passwords
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            QuizApplication.logger.error("Greška tijekom provjere šifre: ", e);
            e.printStackTrace();
            return null;
        }
    }
}
