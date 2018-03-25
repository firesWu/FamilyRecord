package com.FamilyRecord.apps.familyGroup.service;

import com.FamilyRecord.abstractApps.BaseService;
import com.FamilyRecord.apps.familyGroup.entity.FamilyUser;
import org.springframework.stereotype.Service;

/**
 * Created by yuan on 2018/3/23.
 */
@Service
public class FamilyUserService extends BaseService{

    public boolean insert(FamilyUser familyUser){
        int i = sqlSessionTemplate.insert("familyUser.insert",familyUser);
        return i>0;
    }

    public boolean delete(FamilyUser familyUser){
        int i = sqlSessionTemplate.delete("familyUser.insert", familyUser);
        return i>0;
    }

}
