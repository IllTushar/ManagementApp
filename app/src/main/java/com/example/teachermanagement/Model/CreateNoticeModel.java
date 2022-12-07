package com.example.teachermanagement.Model;

public class CreateNoticeModel {
    private String title,date,noticeUri;

    public CreateNoticeModel() {
    }

    public CreateNoticeModel(String title, String date, String noticeUri) {
        this.title = title;
        this.date = date;
        this.noticeUri = noticeUri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNoticeUri() {
        return noticeUri;
    }

    public void setNoticeUri(String noticeUri) {
        this.noticeUri = noticeUri;
    }
}
