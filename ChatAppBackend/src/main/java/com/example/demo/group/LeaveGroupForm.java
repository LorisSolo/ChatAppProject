package com.example.demo.group;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LeaveGroupForm {
    String groupCode;

    public LeaveGroupForm(@JsonProperty("groupCode") String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupCode() {
        return groupCode;
    }
}