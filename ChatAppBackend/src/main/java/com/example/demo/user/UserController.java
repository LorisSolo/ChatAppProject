package com.example.demo.user;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String addUser(@RequestBody AddUserForm addUserForm) {
        String phoneNumber = userService.registerUser(addUserForm);
        if (phoneNumber == null) return "{\"user\":\"" + "exists" + "\"}";
        return "{\"phoneNumber\":\"" + phoneNumber + "\"}";
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginUserForm loginUserForm, HttpServletResponse response) {
        String loginResponse = userService.loginUser(loginUserForm);

        if (loginResponse != null) {
            User user = userService.findByPhoneNumber(loginUserForm.getPhoneNumber());

            Cookie newCookie = new Cookie("token", loginResponse);
            newCookie.setPath("/");
            Cookie newCookie2 = new Cookie("phoneNumber", loginUserForm.getPhoneNumber());
            newCookie2.setPath("/");
            Cookie newCookie3 = new Cookie("username", user.getUsername().replace(" ","_"));
            newCookie3.setPath("/");


            response.addCookie(newCookie);
            response.addCookie(newCookie2);
            response.addCookie(newCookie3);
        } else {
            return "{\"error\":\"" + "Wrong password" + "\"}";
        }

        return "{\"loginResponse\":\"" + loginResponse + "\"}";
    }



    @PostMapping("/getUsername")
    public String getUsername(@RequestBody GetUsernameForm getUsernameForm) {
        User user = userService.findByPhoneNumber(getUsernameForm.getPhoneNumber());
        if (user != null) {
            return "{\"username\":\"" + user.getUsername() + "\"}";
        } else {
            return "";
        }
    }


}
