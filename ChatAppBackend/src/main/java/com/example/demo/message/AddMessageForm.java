package com.example.demo.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddMessageForm {

    private String content;
    private String groupCode;

    public AddMessageForm(@JsonProperty("content") String content,
                          @JsonProperty("groupCode") String groupCode) {
        this.content = content;
        this.groupCode = groupCode;
    }

    public String getContent() {
        return content;
    }

    public String getGroupCode() {
        return groupCode;
    }
}
