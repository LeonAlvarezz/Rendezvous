package com.example.rendezvous.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {

    private int user_id;
    private String email;
    private String user_name;
    private String user_password;
    private Mode mode;

    public User() {
    }

    public User(String email, String user_password) {
        this.email = email;
        this.user_password = user_password;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_password='" + user_password + '\'' +
                ", mode=" + mode +
                '}';
    }
}
