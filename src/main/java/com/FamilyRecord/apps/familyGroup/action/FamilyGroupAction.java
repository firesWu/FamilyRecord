package com.FamilyRecord.apps.familyGroup.action;

import com.FamilyRecord.abstractApps.BaseAction;
import com.FamilyRecord.apps.familyGroup.entity.FamilyGroup;
import com.FamilyRecord.apps.familyGroup.entity.FamilyUser;
import com.FamilyRecord.apps.familyGroup.service.FamilyGroupService;
import com.FamilyRecord.apps.familyGroup.service.FamilyUserService;
import com.FamilyRecord.untils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yuan on 2018/3/23.
 */
@RestController
@RequestMapping("familyGroup")
public class FamilyGroupAction extends BaseAction{

    @Autowired
    private FamilyGroupService familyGroupService;

    @Autowired
    private FamilyUserService familyUserService;

    @RequestMapping("insert")
    public String insert(FamilyGroup familyGroup){

        boolean groupCreateResult = familyGroupService.insert(familyGroup);

        if(groupCreateResult){

            FamilyUser familyUser = new FamilyUser();
            familyUser.setUserId(familyGroup.getCreator());
            familyUser.setFamilyId(familyGroup.getId());
            boolean result = familyUserService.insert(familyUser);

        }else{
            return JsonUtils.genUpdateDataReturnJsonStr(false,"创建家庭组失败");
        }

        return JsonUtils.genUpdateDataReturnJsonStr(true,"创建家庭组成功");
    }

}
