package com.FamilyRecord.apps.familyGroup.action;

import com.FamilyRecord.abstractApps.BaseAction;
import com.FamilyRecord.apps.familyGroup.entity.FamilyGroup;
import com.FamilyRecord.apps.familyGroup.entity.FamilyUser;
import com.FamilyRecord.apps.familyGroup.service.FamilyGroupService;
import com.FamilyRecord.apps.familyGroup.service.FamilyUserService;
import com.FamilyRecord.apps.user.entity.User;
import com.FamilyRecord.apps.user.service.UserService;
import com.FamilyRecord.untils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Autowired
    private UserService userService;

    @RequestMapping("insert")
    public String insertFamilyGroup(FamilyGroup familyGroup){

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

    @RequestMapping("insertFamilyUser")
    public String inertFamilyUser(FamilyUser familyUser){

        List userList = userService.select(new User(){
            {
                setAccount(familyUser.getUserId());
            }
        });

        if(userList.size()==0){
            return JsonUtils.genUpdateDataReturnJsonStr(false,"不存在该用户");
        }

        boolean judge = familyUserService.judge(familyUser);

        if(!judge){
            return JsonUtils.genUpdateDataReturnJsonStr(false,"该成员已有家庭组");
        }

        boolean result = familyUserService.insert(familyUser);

        return result?JsonUtils.genUpdateDataReturnJsonStr(true,"添加成功"):JsonUtils.genUpdateDataReturnJsonStr(false,"添加失败");

    }

    @RequestMapping("selectFamilyUserByGroupId")
    public String selectFamilyUserByGroupId(FamilyUser familyUser){
        return JsonUtils.genUpdateDataReturnJsonStr(true,"查询成功",familyUserService.select(familyUser));
    }

}
