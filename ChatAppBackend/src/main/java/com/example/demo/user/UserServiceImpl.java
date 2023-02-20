package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll() ;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public String registerUser(AddUserForm addUserForm) {
        String username = addUserForm.getUsername();
        String password = addUserForm.getPassword();
        String phoneNumber = addUserForm.getPhoneNumber();

        if (findByPhoneNumber(phoneNumber) == null) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setPhoneNumber(phoneNumber);
            saveUser(newUser);
            return newUser.getPhoneNumber();
        } else {

            return null;
        }
    }

    @Override
    public String loginUser(LoginUserForm loginUserForm) {
        User user = findByPhoneNumber(loginUserForm.getPhoneNumber());
        if (user == null) return null;

        if (Objects.equals(user.getPassword(), loginUserForm.getPassword())) {
            UUID uuid = UUID.randomUUID();
            user.setLoginToken(uuid.toString());
            saveUser(user);
            return user.getLoginToken();
        } else {
            return null;
        }
    }

    @Override
    public User checkAuthenticated(String token, String phoneNumber) {
        User user = findByPhoneNumber(phoneNumber);
        if (user == null) return null;

        if (Objects.equals(token, user.getLoginToken())) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public List<User> findByGroup(String group) {
        List<User> users = getAllUsers();
        List<User> usersInGroup = new ArrayList<>();
        for (User user : users) {
            String list = user.getGroupList();

            for (String word : list.split(" ")) {
                if (word.equals(group)) {
                    usersInGroup.add(user);
                }
            }
        }

        return usersInGroup;
    }


}
