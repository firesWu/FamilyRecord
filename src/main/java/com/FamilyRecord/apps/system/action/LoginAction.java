package com.FamilyRecord.apps.system.action;

import com.FamilyRecord.abstractApps.BaseAction;
import com.FamilyRecord.apps.user.entity.User;
import com.FamilyRecord.apps.user.service.UserService;
import com.FamilyRecord.dto.UserInfo;
import com.FamilyRecord.untils.CookieUtils;
import com.FamilyRecord.untils.JsonUtils;
import com.FamilyRecord.untils.RandomGuid;
import com.FamilyRecord.untils.RegularMatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by yuan on 2018/3/24.
 */
@RestController
@RequestMapping("login")
public class LoginAction extends BaseAction{

    private static Logger logger = LoggerFactory.getLogger(LoginAction.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/loginin.do")
    public String getUser(User user, HttpServletRequest request, HttpServletResponse response) {
        String pattern = "[a-zA-Z0-9]{2,20}";
        if(user.getPassword()==null||user.getPassword().equals("")||user.getAccount()==null||user.getAccount().equals("")||!(RegularMatch.matchPatternAndString(user.getPassword(), pattern)&&RegularMatch.matchPatternAndString(user.getAccount(),pattern))){
            return JsonUtils.genUpdateDataReturnJsonStr(false,"无法登录","");
        }

        String random = RandomGuid.getGuid();
        List<Map> userList = null;
        try {
            userList = userService.login(user);
            if (userList.size()>0){
                request.getSession().setAttribute(random,userList.get(0).get("account"));
                CookieUtils.setCookie(response, "roleCookie", random);
            }else{
                return JsonUtils.genUpdateDataReturnJsonStr(false,"登录失败");
            }

            if(userList.size()>0){
                userList.get(0).remove("password");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtils.genUpdateDataReturnJsonStr(false,"登录失败");
        }
        return JsonUtils.genUpdateDataReturnJsonStr(true,"",user);

    }



    @RequestMapping("/verLogin.do")
    public String validateUser(HttpServletRequest request , HttpServletResponse response) throws Exception {
        List<UserInfo> results = null;
        String roleCookie = CookieUtils.getCookieValue(request,"roleCookie");
        String userAccount = (String)request.getSession().getAttribute(roleCookie);

        if(userAccount!=null&& !"".equals(userAccount)){
            User user = new User();
            user.setAccount(userAccount);
            results = userService.verLogin(user);

        }

        return (results==null || results.size()==0)? JsonUtils.genUpdateDataReturnJsonStr(false, "您还未登录，请登录。。。"):JsonUtils.genUpdateDataReturnJsonStr(true,"登陆成功",results);
    }

    @RequestMapping("register")
    public String register(User user, HttpServletRequest request , HttpServletResponse response){

        boolean result;

        try{
            result = userService.insert(user);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("用户注册失败" + e.toString());
            return JsonUtils.genUpdateDataReturnJsonStr(false,"注册失败");
        }

        if(result){
            String random = RandomGuid.getGuid();
            request.getSession().setAttribute(random,user.getAccount());
            CookieUtils.setCookie(response, "roleCookie", random);
            return JsonUtils.genUpdateDataReturnJsonStr(true,"注册成功");
        }else{
            return JsonUtils.genUpdateDataReturnJsonStr(false,"注册失败");
        }

    }

    @RequestMapping("/delCookie.do")
    public String delCookie(@RequestParam(value = "roleCookie")String roleCookie,HttpServletResponse response,
                            HttpServletRequest request){
        CookieUtils.getCookieValue(request,roleCookie);
        return JsonUtils.genUpdateDataReturnJsonStr(true,"");
    }

}
