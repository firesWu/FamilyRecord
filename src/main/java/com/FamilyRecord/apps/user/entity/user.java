package com.FamilyRecord.apps.user.entity;

import com.FamilyRecord.abstractApps.BaseEntity;

/**
 * Created by yuan on 2018/3/23.
 */
public class user extends BaseEntity {

    private String account;     //用户账号
    private String password;    //用户密码
    private String nickName;    //用户昵称
    private String birthday;    //用户生日

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
