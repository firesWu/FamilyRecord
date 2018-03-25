package com.FamilyRecord.apps.user.action;

import com.FamilyRecord.apps.user.entity.User;
import com.FamilyRecord.apps.user.service.UserService;
import com.FamilyRecord.untils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yuan on 2018/3/23.
 */
@RestController
@RequestMapping("user")
public class UserAction {

    private static Logger logger = LoggerFactory.getLogger(UserAction.class);

    @Autowired
    private UserService userService;

    @RequestMapping("insert")
    public String insert(User user){

        boolean result;

        try{
            result = userService.insert(user);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("用户注册失败" + e.toString());
            return JsonUtils.genUpdateDataReturnJsonStr(false,"注册失败");
        }

        if(result){
            return JsonUtils.genUpdateDataReturnJsonStr(true,"注册成功");
        }else{
            return JsonUtils.genUpdateDataReturnJsonStr(false,"注册失败");
        }

    }

}
