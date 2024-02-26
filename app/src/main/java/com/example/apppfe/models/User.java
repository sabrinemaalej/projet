package com.example.apppfe.models;

public class User {
    private String email;
    private String password;
    private String confirmpswrd;

    public User() {
    }

    public User(String email, String password, String confirmpswrd) {
        this.email = email;
        this.password = password;
        this.confirmpswrd = confirmpswrd;
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

    public String getConfirmpswrd() {
        return confirmpswrd;
    }

    public void setConfirmpswrd(String confirmpswrd) {
        this.confirmpswrd = confirmpswrd;
    }
}
