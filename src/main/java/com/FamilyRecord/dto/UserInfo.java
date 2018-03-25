package com.FamilyRecord.dto;

import com.FamilyRecord.apps.user.entity.User;

/**
 * Created by yuan on 2018/3/24.
 */
public class UserInfo extends User {

    private String groupId;
    private String groupName;        //家庭组名称
    private String creator;     //创建人

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
