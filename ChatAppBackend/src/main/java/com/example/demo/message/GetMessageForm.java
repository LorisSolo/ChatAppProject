package com.example.demo.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetMessageForm {

    private String groupCode;

    @JsonCreator
    public GetMessageForm(@JsonProperty("groupCode") String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupCode() {
        return groupCode;
    }
}
