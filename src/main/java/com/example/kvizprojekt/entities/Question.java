package com.example.kvizprojekt.entities;

import java.io.Serializable;

public final class Question extends Category implements Serializable {

    private int id;
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String correctAnswer;

    public Question() {
      super();
  }

    public Question(String category, int id, String question, String answer1, String answer2, String answer3, String answer4, String correctAnswer) {
        super(category);
        this.id = id;
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.correctAnswer = correctAnswer;
    }

    //table insertion, ID auto-increments
    public Question(String category, String question, String answer1, String answer2, String answer3, String answer4, String correctAnswer) {
        super(category);
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.correctAnswer = correctAnswer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getAnswer1() {
        return answer1;
    }
    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }
    public String getAnswer2() {
        return answer2;
    }
    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }
    public String getAnswer3() {
        return answer3;
    }
    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }
    public String getAnswer4() {
        return answer4;
    }
    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}



