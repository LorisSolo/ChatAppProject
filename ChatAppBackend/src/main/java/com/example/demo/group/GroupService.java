package com.example.demo.group;

public interface GroupService {
    public Group saveGroup(Group group);

    public Group getByGroupCode(String groupCode);
}
