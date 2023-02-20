package com.example.demo.message;

import com.example.demo.user.User;
import com.example.demo.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public String addMessage(
            @CookieValue(value = "token", defaultValue = "defaultCookieValue") String token,
            @CookieValue(value = "phoneNumber", defaultValue = "defaultCookieValue") String phoneNumber,
            @RequestBody AddMessageForm addMessageForm
    ) {
        if (userService.checkAuthenticated(token, phoneNumber) != null) {

            User user = userService.checkAuthenticated(token, phoneNumber);
            Boolean userInGroup = false;

            String list = user.getGroupList();

            list = list.trim();
            for (String word : list.split(" ")) {

                if (word.equals(addMessageForm.getGroupCode())) {

                    userInGroup = true;
                }
            }
            if (!userInGroup) return "{\"addMessageResponse\":\"" + "Not in group" + "\"}";

            Message message = new Message();
            message.setContent(addMessageForm.getContent());

            message.setGroupCode(addMessageForm.getGroupCode());

            Date date = new Date();
            message.setTimestamp(date.toString());

            message.setAuthor(user.getUsername());

            messageService.saveMessage(message);
            return "{\"addMessageResponse\":\"" + "A new message is added" + "\"}";
        } else {
            return "{\"addMessageResponse\":\"" + "Not authenticated" + "\"}";
        }
    }




    @PostMapping("/getMessages")
    public List<Message> getMessagesByGroup(
            @CookieValue(value = "token", defaultValue = "defaultCookieValue") String token,
            @CookieValue(value = "phoneNumber", defaultValue = "defaultCookieValue") String phoneNumber,
            @RequestBody GetMessageForm getMessageForm
    ) {
        if (userService.checkAuthenticated(token, phoneNumber) != null) {
            User user = userService.checkAuthenticated(token, phoneNumber);
            String list = user.getGroupList();

            for (String word : list.split(" ")) {
                if (word.equals(getMessageForm.getGroupCode())) {
                    return messageService.getMessagesByGroupCode(getMessageForm.getGroupCode());
                }
            }

            return null;
        }
        return null;
    }







}
