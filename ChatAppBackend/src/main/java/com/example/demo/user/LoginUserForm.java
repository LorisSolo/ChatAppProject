package com.example.demo.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginUserForm {
    private String password;

    private String phoneNumber;

    public LoginUserForm(@JsonProperty("password") String password,
                       @JsonProperty("phoneNumber") String phoneNumber) {
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
