package com.example.teachermanagement.Model;

public class CreateBookModel {
    private String bookUrl,bookTitle,author;

    public CreateBookModel(String bookUrl, String bookTitle,String author) {
        this.bookUrl = bookUrl;
        this.bookTitle = bookTitle;
        this.author = author;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public String getAuthor() {
        return author;
    }

    public String getBookTitle() {
        return bookTitle;
    }
}
