package com.FamilyRecord.dto;

import com.FamilyRecord.apps.familyGroup.entity.FamilyUser;

/**
 * Created by yuan on 2018/3/26.
 */
public class MemberInfo extends FamilyUser {
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
