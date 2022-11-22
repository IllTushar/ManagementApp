package com.example.teachermanagement.Model;

public class AuthenticationModel
{
private String name,email,password,imageuri;

    public AuthenticationModel() {
    }

    public AuthenticationModel(String name, String email, String password, String imageuri) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.imageuri = imageuri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }
}
