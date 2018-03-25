package com.FamilyRecord.apps.familyGroup.service;

import com.FamilyRecord.abstractApps.BaseService;
import com.FamilyRecord.apps.familyGroup.entity.FamilyGroup;
import org.springframework.stereotype.Service;

/**
 * Created by yuan on 2018/3/23.
 */
@Service
public class FamilyGroupService extends BaseService {

    public boolean insert(FamilyGroup familyGroup){
        int i = sqlSessionTemplate.insert("familyGroup.insert",familyGroup);
        return i>0;
    }

}
