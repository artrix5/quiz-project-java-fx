package com.example.kvizprojekt.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public sealed interface DatabaseFunctions<T, V> permits DatabaseConnection {

    Connection connectToDatabase() throws SQLException, IOException;

    List<T> loadData() throws SQLException, IOException;

    void addQuestion(String category, String question, String answer1, String answer2, String answer3, String answer4, String correctAnswer);

    void deleteQuestion(int id);

    void updateQuestion(T question) throws IOException, SQLException;

    List<V> getCategories();

    int getNumberOfQuestionsInCategory(String category);

    List<T> getQuestions(String category);
}
