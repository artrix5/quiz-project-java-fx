package com.example.kvizprojekt.entities;

import com.example.kvizprojekt.main.QuizApplication;
import com.example.kvizprojekt.files.FilePaths;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public abstract class TableValidation {

    //function that checks if the user logged is an admin
    public boolean isAdmin(String username) {
        String line;
        String[] parts;

        try (BufferedReader br = new BufferedReader(new FileReader(FilePaths.CREDENTIALS_FILE.toFile()))) {
            while ((line = br.readLine()) != null) {
                parts = line.split(":");
                if (parts[0].equals(username) && parts[2].equals("ADMIN")) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            QuizApplication.logger.error("Greška kod čitanja datoteke: " + e.getMessage());
            return false;
        }
    }

    //function that checks if there are any empty fields
    public boolean checkEmptyFields(String category, String question, String answer1, String answer2, String answer3, String answer4, String correctAnswer) {

        return category.isEmpty() || question.isEmpty() || answer1.isEmpty() || answer2.isEmpty() || answer3.isEmpty() || answer4.isEmpty() || correctAnswer.isEmpty();
    }


    //function that checks if there are duplicate answers using a Set
    public boolean checkDuplicates(String answer1, String answer2, String answer3, String answer4) {

        Set<String> answers = new HashSet<>();
        answers.add(answer1);
        answers.add(answer2);
        answers.add(answer3);
        answers.add(answer4);

        return answers.size() != 4;
    }

    //function that checks if the correct answer equals any of the available answers
    public boolean checkMissingAnswer(String answer1, String answer2, String answer3, String answer4, String correctAnswer) {

        return !answer1.equals(correctAnswer) && !answer2.equals(correctAnswer) && !answer3.equals(correctAnswer) && !answer4.equals(correctAnswer);
    }
}
