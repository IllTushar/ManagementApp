package com.example.teachermanagement.Model;

public class uploadAssignmentModel
{
    private String assignmentTitle,publishDate,deadLine,assigmentUri,classCodes;

    public uploadAssignmentModel() {
    }

    public uploadAssignmentModel(String assignmentTitle, String publishDate, String deadLine,String assigmentUri,String classCodes) {
        this.assignmentTitle = assignmentTitle;
        this.publishDate = publishDate;
        this.deadLine = deadLine;
        this.assigmentUri = assigmentUri;
        this.classCodes = classCodes;
    }

    public String getClassCodes() {
        return classCodes;
    }

    public void setClassCodes(String classCodes) {
        this.classCodes = classCodes;
    }

    public String getAssigmentUri() {
        return assigmentUri;
    }

    public void setAssigmentUri(String assigmentUri) {
        this.assigmentUri = assigmentUri;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public void setAssignmentTitle(String assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

}
