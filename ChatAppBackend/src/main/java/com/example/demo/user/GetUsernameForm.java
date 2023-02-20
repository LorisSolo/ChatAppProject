package com.example.demo.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetUsernameForm {
    public String phoneNumber;

    public GetUsernameForm(@JsonProperty("phoneNumber") String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
