package com.example.demo.group;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String groupName;

    private String groupAdminNumber;

    private String groupCode;

    private Boolean isDM;

    public Group() {
        UUID uuid = UUID.randomUUID();
        String reducedUuid = uuid.toString().substring(0, 5);
        groupCode = reducedUuid;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupAdminNumber() {
        return groupAdminNumber;
    }

    public void setGroupAdminNumber(String groupAdminNumber) {
        this.groupAdminNumber = groupAdminNumber;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public Boolean getDM() {
        return isDM;
    }

    public void setDM(Boolean DM) {
        isDM = DM;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                ", groupAdminNumber='" + groupAdminNumber + '\'' +
                ", groupCode='" + groupCode + '\'' +
                ", isDM=" + isDM +
                '}';
    }
}
