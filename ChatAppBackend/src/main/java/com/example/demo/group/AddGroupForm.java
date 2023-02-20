package com.example.demo.group;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class AddGroupForm {
    private String groupName;
    private ArrayList<String> userList;

    public AddGroupForm(@JsonProperty("groupName") String groupName, @JsonProperty("userList") ArrayList userList) {
        this.groupName = groupName;
        this.userList = userList;
    }

    public String getGroupName() {
        return groupName;
    }

    public ArrayList<String> getUserList() {
        return userList;
    }
}
