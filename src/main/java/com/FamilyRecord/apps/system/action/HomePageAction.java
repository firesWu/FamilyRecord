package com.FamilyRecord.apps.system.action;

import com.FamilyRecord.abstractApps.BaseAction;
import com.FamilyRecord.apps.article.service.ArticleService;
import com.FamilyRecord.apps.system.entity.HomePage;
import com.FamilyRecord.apps.system.entity.HomeRequest;
import com.FamilyRecord.apps.system.service.HomePageService;
import com.FamilyRecord.apps.upload.service.CommonFileService;
import com.FamilyRecord.apps.user.service.UserService;
import com.FamilyRecord.untils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yuan on 2018/4/8.
 */
@RestController
@RequestMapping("homePage")
public class HomePageAction extends BaseAction {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommonFileService commonFileService;

    @Autowired
    private UserService userService;

    @RequestMapping("getHomePageInfo")
    public String getHomePageInfo(HomeRequest homeRequest){
        HomePage homePage = new HomePage();
        homePage.setArticleList(articleService.selectTopArticle(homeRequest));
        homePage.setPhotoList(commonFileService.selectTopPhotho(homeRequest));
        homePage.setVideoList(commonFileService.selectTopVideo(homeRequest));
        homePage.setBirthdayRemindingList(userService.selectBirthdayReminding(homeRequest));
        return JsonUtils.genUpdateDataReturnJsonStr(true,"get info",homePage);
    }

}
