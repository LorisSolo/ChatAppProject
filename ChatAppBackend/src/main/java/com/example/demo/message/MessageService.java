package com.example.demo.message;

import java.util.List;

public interface MessageService {
    public Message saveMessage(Message message);

    public List<Message> getMessagesByGroupCode(String groupCode);
}
