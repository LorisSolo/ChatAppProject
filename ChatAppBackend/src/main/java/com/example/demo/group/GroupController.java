package com.example.demo.group;

import com.example.demo.user.User;
import com.example.demo.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public String addGroup(
            @CookieValue(value = "token", defaultValue = "defaultCookieValue") String token,
            @CookieValue(value = "phoneNumber", defaultValue = "defaultCookieValue") String phoneNumber,
            @RequestBody AddGroupForm groupForm
    ) {
        if (userService.checkAuthenticated(token, phoneNumber) != null) {
            User user = userService.checkAuthenticated(token, phoneNumber);
            Group group = new Group();
            group.setGroupName(groupForm.getGroupName());


            ArrayList<String> userNumbers = groupForm.getUserList();

            for (String number : userNumbers) {
                String num = number;
                if (!num.equals(user.getPhoneNumber())) {
                    User foundUser = userService.findByPhoneNumber(num);

                    foundUser.addToGroupList(group.getGroupCode());
                    userService.saveUser(foundUser);
                }
            }

            if (groupForm.getUserList().size() > 1) {
                group.setDM(false);
            } else {
                group.setDM(true);
                group.setGroupName(userNumbers.get(0));
            }


            user.addToGroupList(group.getGroupCode());
            group.setGroupAdminNumber(user.getPhoneNumber());

            groupService.saveGroup(group);
        } else {
            return "{\"status\":\"" + "Not authenticated" + "\"}";
        }
        return "{\"status\":\"" + "OK" + "\"}";
    }


    @PatchMapping("/leave")
    public String leaveGroup(
            @CookieValue(value = "token", defaultValue = "defaultCookieValue") String token,
            @CookieValue(value = "phoneNumber", defaultValue = "defaultCookieValue") String phoneNumber,
            @RequestBody LeaveGroupForm leaveGroupForm
    ) {
        if (userService.checkAuthenticated(token, phoneNumber) != null) {
            String groupCode = leaveGroupForm.getGroupCode();

            User user = userService.checkAuthenticated(token, phoneNumber);
            String list = user.getGroupList();

            list = list.replaceAll(groupCode, "");
            list = list.replaceAll("\\s+", " ");
            list = list.trim();
            user.setGroupList(list);
            userService.saveUser(user);
        }

        return "";
    }


    @DeleteMapping("/delete")
    public String deleteGroup(
            @CookieValue(value = "token", defaultValue = "defaultCookieValue") String token,
            @CookieValue(value = "phoneNumber", defaultValue = "defaultCookieValue") String phoneNumber,
            @RequestBody LeaveGroupForm leaveGroupForm
    ) {
        if (userService.checkAuthenticated(token, phoneNumber) != null) {
            String groupCode = leaveGroupForm.getGroupCode();

            User user = userService.checkAuthenticated(token, phoneNumber);
            Group group = groupService.getByGroupCode(groupCode);

            if (group == null) {
                return "";
            }

            String groupAdminNumber = group.getGroupAdminNumber();
            if (groupAdminNumber.equals(user.getPhoneNumber())) {
                List<User> users = userService.findByGroup(groupCode);

                for (User selectedUser : users) {
                    String list = selectedUser.getGroupList();
                    list = list.replaceAll(groupCode, "");
                    list = list.replaceAll("\\s+", " ");
                    list = list.trim();
                    selectedUser.setGroupList(list);
                    userService.saveUser(selectedUser);
                }

                group.setGroupCode("deleted");
                groupService.saveGroup(group);
            }
        }

        return "";
    }



    @GetMapping("/getGroups")
    public List<Group> getGroups(
            @CookieValue(value = "token", defaultValue = "defaultCookieValue") String token,
            @CookieValue(value = "phoneNumber", defaultValue = "defaultCookieValue") String phoneNumber
    ) {

        if (userService.checkAuthenticated(token, phoneNumber) != null) {
            User user = userService.checkAuthenticated(token, phoneNumber);
            String list = user.getGroupList();


            List<Group> groups = new ArrayList<>();

            for (String word : list.split(" ")) {
                Group gr = groupService.getByGroupCode(word);

                if (gr.getDM() == true) {
                    gr.setGroupName(
                            userService.findByPhoneNumber(
                                    gr.getGroupName()
                            ).getUsername()
                    );

                    gr.setGroupAdminNumber(
                            userService.findByPhoneNumber(
                                    gr.getGroupAdminNumber()
                            ).getUsername()
                    );
                }


                groups.add(gr);
            }


            return groups;
        }

        return null;
    }


}
