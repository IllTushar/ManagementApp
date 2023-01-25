package com.example.teachermanagement.Model;

public class QuizzModel {
    private String question,classCode,answer;

    public QuizzModel() {
    }

    public QuizzModel(String question, String classCode, String answer) {
        this.question = question;
        this.classCode = classCode;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
