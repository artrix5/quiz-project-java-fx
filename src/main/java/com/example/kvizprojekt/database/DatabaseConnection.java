package com.example.kvizprojekt.database;

import com.example.kvizprojekt.main.QuizApplication;
import com.example.kvizprojekt.entities.Question;
import com.example.kvizprojekt.exceptions.DatabaseConnectionException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
public final class DatabaseConnection implements DatabaseFunctions<Question, String> {

    //connects to database
    public Connection connectToDatabase() throws SQLException, IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("dat/db.properties"));
        Connection connection = DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("username"),
                properties.getProperty("password"));

        return connection;
    }

    //loads data into tableview
    public List<Question> loadData() {

        List<Question> data = new ArrayList<>();

        try(Connection connection = connectToDatabase()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM questions");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String category = rs.getString("category");
                    String question = rs.getString("question");
                    String answer1 = rs.getString("answer1");
                    String answer2 = rs.getString("answer2");
                    String answer3 = rs.getString("answer3");
                    String answer4 = rs.getString("answer4");
                    String correctAnswer = rs.getString("correct_answer");
                    data.add(new Question(category, id, question, answer1, answer2, answer3, answer4, correctAnswer));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            QuizApplication.logger.error("Problem kod spajanja na bazu podataka!", e);
            throw new DatabaseConnectionException("Problem kod spajanja na bazu podataka!", e);
        }
        return data;
    }

    //adds question to the tableview
    public void addQuestion(String category, String question, String answer1, String answer2, String answer3, String answer4, String correctAnswer) {
        try (Connection connection = connectToDatabase()) {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO questions (category, question, answer1, answer2, answer3, answer4, correct_answer) VALUES (?,?,?,?,?,?,?)");
                statement.setString(1, category);
                statement.setString(2, question);
                statement.setString(3, answer1);
                statement.setString(4, answer2);
                statement.setString(5, answer3);
                statement.setString(6, answer4);
                statement.setString(7, correctAnswer);
                statement.executeUpdate();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            QuizApplication.logger.error("Problem kod spajanja na bazu podataka!", e);
            throw new DatabaseConnectionException("Problem kod spajanja na bazu podataka!", e);
        }
    }

    //deletes question from the tableview
    public void deleteQuestion(int id) {
            try (Connection connection = connectToDatabase()) {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM questions WHERE id = ?");
                statement.setInt(1, id);
                statement.executeUpdate();
            }
            catch (SQLException | IOException e) {
                e.printStackTrace();
                QuizApplication.logger.error("Problem kod spajanja na bazu podataka!", e);
                throw new DatabaseConnectionException("Problem kod spajanja na bazu podataka!", e);
            }
    }

    //updates any changes made to the question
    public void updateQuestion(Question question) throws SQLException, IOException {
        try (Connection connection = connectToDatabase()) {
            String sql = "UPDATE questions SET category = ?, question = ?, answer1 = ?, answer2 = ?, answer3 = ?, answer4 = ?, correct_answer = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, question.getCategory());
            statement.setString(2, question.getQuestion());
            statement.setString(3, question.getAnswer1());
            statement.setString(4, question.getAnswer2());
            statement.setString(5, question.getAnswer3());
            statement.setString(6, question.getAnswer4());
            statement.setString(7, question.getCorrectAnswer());
            statement.setInt(8, question.getId());
            statement.executeUpdate();
        }
        catch (SQLException | IOException e) {
            e.printStackTrace();
            QuizApplication.logger.error("Problem kod spajanja na bazu podataka!", e);
            throw new DatabaseConnectionException("Problem kod spajanja na bazu podataka!", e);
        }
    }

    //gets all DISTINCT categories from the database
    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        try (Connection connection = connectToDatabase()) {
             Statement stmt = connection.createStatement();
                //Distinct removes duplicates
                ResultSet rs = stmt.executeQuery("SELECT DISTINCT category FROM questions");
                while (rs.next()) {
                    String category = rs.getString("category");
                    categories.add(category);
                 }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            QuizApplication.logger.error("Problem kod spajanja na bazu podataka!", e);
            throw new DatabaseConnectionException("Problem kod spajanja na bazu podataka!", e);
        }
        return categories;
    }

    //loads all questions from the database for selected category into a list
    public List<Question> getQuestions(String category) {
        List<Question> questions = new ArrayList<>();
        try (Connection connection = connectToDatabase()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM questions WHERE category = ?");
            statement.setString(1, category);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String question = rs.getString("question");
                String correctAnswer = rs.getString("correct_answer");
                String answer1 = rs.getString("answer1");
                String answer2 = rs.getString("answer2");
                String answer3 = rs.getString("answer3");
                String answer4 = rs.getString("answer4");
                Question q = new Question(category, id, question, answer1, answer2, answer3, answer4, correctAnswer);
                questions.add(q);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            QuizApplication.logger.error("Problem kod spajanja na bazu podataka!", e);
            throw new DatabaseConnectionException("Problem kod spajanja na bazu podataka!", e);

        }
        return questions;
    }

    //gets number of questions from the selected category
    public int getNumberOfQuestionsInCategory(String category) {
        int numOfQuestions = 0;
        try (Connection connection = connectToDatabase()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) FROM questions WHERE category = ?");
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                numOfQuestions = rs.getInt(1);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            QuizApplication.logger.error("Problem kod spajanja na bazu podataka!", e);
            throw new DatabaseConnectionException("Problem kod spajanja na bazu podataka!", e);
        }
        return numOfQuestions;
    }
}

