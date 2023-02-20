package com.example.demo.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddUserForm {
    private String username;
    private String password;
    private String phoneNumber;

    public AddUserForm(@JsonProperty("username") String username,
                       @JsonProperty("password") String password,
                       @JsonProperty("phoneNumber") String phoneNumber) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
