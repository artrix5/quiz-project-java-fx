package com.example.kvizprojekt.entities;

import com.example.kvizprojekt.main.QuizApplication;
import com.example.kvizprojekt.exceptions.UnsupportedAlgorithmException;
import com.example.kvizprojekt.files.FilePaths;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class RegisterValidation {

    //checks if the entered username already exists in the file
    protected boolean isUsernameTaken(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader(FilePaths.CREDENTIALS_FILE.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            QuizApplication.logger.error("Dogodila se greška tijekom čitanja datoteke: ", e);
            e.printStackTrace();
        }
        return false;
    }

    //SHA-256 algorithm for hashing passwords
    protected String hashPassword(String password) {
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
            QuizApplication.logger.error("Dogodila se greška tijekom hashiranja šifre: ", e);
            e.printStackTrace();
            throw new UnsupportedAlgorithmException("Dogodila se greška kod hashiranja šifre.");
        }
    }

    //function for writing user data to a file
    protected boolean writeCredentialsToFile(User newUser) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FilePaths.CREDENTIALS_FILE.toFile(), true))) {
            bw.write(newUser.getUserName() + ":" + newUser.getHashedPassword() + ":" + newUser.getRole() + ":" + newUser.getHighScore());
            bw.newLine();
            return true;
        } catch (IOException e) {
            QuizApplication.logger.error("Dogodila se greška prilikom pisanja u datoteku: ", e);
            e.printStackTrace();
            return false;
        }
    }
}
