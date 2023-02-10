package com.example.kvizprojekt.files;

import com.example.kvizprojekt.entities.Score;
import com.example.kvizprojekt.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;

public final class FileManager {

	//function that loads scores from scores.txt
	public static ObservableList<Score> loadScores() {
		ObservableList<Score> scores = FXCollections.observableArrayList();
		try (BufferedReader br = new BufferedReader(new FileReader(FilePaths.SCORES_FILE.toFile()))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split("-");
				String username = parts[0];
				int score = Integer.parseInt(parts[1]);
				String date = parts[2];
				String category = parts[3];
				scores.add(new Score(username, score, date, category));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return scores;
	}

	//function that writes scores to scores.txt
	public static void writeScoreToFile(String username, int score, String date, String category) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FilePaths.SCORES_FILE.toFile(), true))) {
			bw.write(username + "-" + score + "-" + date + "-" + category);
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//function that loads all users registered
	public static ObservableList<User> loadUsers() {
		ObservableList<User> users = FXCollections.observableArrayList();

		try (BufferedReader br = new BufferedReader(new FileReader(FilePaths.CREDENTIALS_FILE.toFile()))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(":");
				String username = parts[0];
				String hashedPassword = parts[1];
				String type = parts[2];
				int highScore = Integer.parseInt(parts[3]);
				users.add(new User(username,hashedPassword, type, highScore));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return users;
	}

	//function that updates high score, it is used by a thread
	public static void updateHighScore(String username, int highScore) {

		try (BufferedReader br = new BufferedReader(new FileReader(FilePaths.CREDENTIALS_FILE.toFile()))) {
			StringBuilder content = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(":");
				if (parts[0].equals(username)) {
					parts[3] = String.valueOf(highScore);
					line = String.join(":", parts);
				}
				content.append(line).append(System.lineSeparator());
			}
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(FilePaths.CREDENTIALS_FILE.toFile()))) {
			bw.write(content.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
