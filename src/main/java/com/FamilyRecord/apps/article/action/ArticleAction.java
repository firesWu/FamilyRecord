package com.FamilyRecord.apps.article.action;

import com.FamilyRecord.abstractApps.BaseAction;
import com.FamilyRecord.apps.article.entity.Article;
import com.FamilyRecord.apps.article.entity.Comments;
import com.FamilyRecord.apps.article.service.ArticleService;
import com.FamilyRecord.dto.CommentsInfo;
import com.FamilyRecord.untils.JsonUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yuan on 2018/3/29.
 */
@RestController
@RequestMapping("article")
public class ArticleAction extends BaseAction {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("createArticle")
    public String createArticle(Article article){

        boolean result;
        if(article.getId().equals("")){
            result = articleService.createArticle(article);
        }else{
            result = articleService.updateArticle(article);
        }
        return result? JsonUtils.genUpdateDataReturnJsonStr(true,"发表成功"):JsonUtils.genUpdateDataReturnJsonStr(false,"发表失败");
    }

    @RequestMapping("selectArticle")
    public String selectArticle(Article article,int pageNum, int pageSize){

        RowBounds rowBounds = new RowBounds(pageNum,pageSize);
        Page<Article> list =  articleService.selectArticle(article,rowBounds);
        PageInfo pageInfo = new PageInfo(list);

        return JsonUtils.genUpdateDataReturnJsonStr(true,"查询成功",pageInfo);
    }

    @RequestMapping("deleteArticle")
    public String deleteArticle(String id){
        boolean result = articleService.deleteArticle(id);
        return result?JsonUtils.genUpdateDataReturnJsonStr(true,"删除成功"):JsonUtils.genUpdateDataReturnJsonStr(false,"删除失败");
    }

    //查询评论
    @RequestMapping("selectComments")
    public String selectComments(Comments comments, int pageNum, int pageSize){
        RowBounds rowBounds = new RowBounds(pageNum,pageSize);
        Page<CommentsInfo> list =  articleService.selectComments(comments, rowBounds);
        PageInfo pageInfo = new PageInfo(list);
        return JsonUtils.genUpdateDataReturnJsonStr(true,"查询成功",pageInfo);
    }

    //回复帖子
    @RequestMapping("replyArticle")
    public String replyArticle(Comments comments){
        boolean result = articleService.replyArticle(comments);
        return result?JsonUtils.genUpdateDataReturnJsonStr(true,"回复成功"):JsonUtils.genUpdateDataReturnJsonStr(false,"回复失败");
    }


    //获取未读的消息
    @RequestMapping("getUnreadMessage")
    public String getUnreadMessage(String account){
        boolean reuslt;
        List messageList = articleService.getUnreadMessage(account);
        if(messageList.size()!=0){
            reuslt = articleService.setUnreadToRead(messageList);
        }
        return JsonUtils.genUpdateDataReturnJsonStr(true,"查询成功",messageList);
    }
}
