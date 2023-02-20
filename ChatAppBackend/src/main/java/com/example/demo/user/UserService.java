package com.example.demo.user;

import java.util.List;

public interface UserService {
    public User saveUser(User user);

    public List<User> getAllUsers();

    public User findByUsername(String username);

    public User findByPhoneNumber(String phoneNumber);

    public String registerUser(AddUserForm addUserForm);
    public String loginUser(LoginUserForm loginUserForm);
    public User checkAuthenticated(String token, String username);

    public List<User> findByGroup(String group);
}
