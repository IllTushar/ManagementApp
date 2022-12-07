package com.example.teachermanagement.Model;

public class CreateClassModel {
    private String classCode;
    private String classTeacherName,classSubject,classYear;
    public CreateClassModel() {
    }

    public CreateClassModel(String classTeacherName, String classSubject, String classYear, String classCode) {
        this.classTeacherName = classTeacherName;
        this.classSubject = classSubject;
        this.classYear = classYear;
        this.classCode = classCode;
    }

    public String getClassTeacherName() {
        return classTeacherName;
    }

    public void setClassTeacherName(String classTeacherName) {
        this.classTeacherName = classTeacherName;
    }

    public String getClassSubject() {
        return classSubject;
    }

    public void setClassSubject(String classSubject) {
        this.classSubject = classSubject;
    }

    public String getClassYear() {
        return classYear;
    }

    public void setClassYear(String classYear) {
        this.classYear = classYear;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }


}
