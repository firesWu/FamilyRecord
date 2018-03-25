package com.FamilyRecord.apps.user.service;

import com.FamilyRecord.abstractApps.BaseService;
import com.FamilyRecord.apps.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yuan on 2018/3/23.
 */
@Service
public class UserService extends BaseService {

    public boolean insert(User user){
        int i = sqlSessionTemplate.insert("user.insert",user);
        return i>0;
    }

    public List select(User user){
        List userList = sqlSessionTemplate.selectList("user.select",user);
        return userList;
    }

    public List login(User user){
        List userList = sqlSessionTemplate.selectList("user.login",user);
        return userList;
    }

    public List verLogin(User user){
        List userList = sqlSessionTemplate.selectList("user.verLogin",user);
        return userList;
    }
}
