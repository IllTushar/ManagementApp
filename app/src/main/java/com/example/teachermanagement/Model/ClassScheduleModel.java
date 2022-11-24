package com.example.teachermanagement.Model;

public class ClassScheduleModel
{
 String name,subject,year,date,time;

    public ClassScheduleModel() {
    }

    public ClassScheduleModel(String name, String subject, String year, String date, String time) {
        this.name = name;
        this.subject = subject;
        this.year = year;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
