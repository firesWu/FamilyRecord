package com.FamilyRecord.apps.article.service;

import com.FamilyRecord.abstractApps.BaseService;
import com.FamilyRecord.apps.article.entity.Article;
import com.FamilyRecord.apps.article.entity.Comments;
import com.FamilyRecord.apps.system.entity.HomeRequest;
import com.github.pagehelper.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yuan on 2018/3/29.
 */
@Service
public class ArticleService extends BaseService {

    public boolean createArticle(Article article){
        int i = sqlSessionTemplate.insert("article.insert",article);
        return i>0;
    }

    public boolean updateArticle(Article article){
        int i = sqlSessionTemplate.insert("article.update",article);
        return i>0;
    }

    public Page selectArticle(Article article, RowBounds rowBounds){
        return (Page)sqlSessionTemplate.selectList("article.select", article, rowBounds);
    }

    public boolean deleteArticle(String id){
        int i = sqlSessionTemplate.update("article.deleteArticle",id);
        return i>0;
    }

    public Page selectComments(Comments comments, RowBounds rowBounds){
        return (Page)sqlSessionTemplate.selectList("comments.select",comments,rowBounds);
    }

    //回复帖子
    public boolean replyArticle(Comments comments){
        int i = sqlSessionTemplate.insert("comments.insert",comments);
        return i>0;
    }

    public List getUnreadMessage(String account){
        return sqlSessionTemplate.selectList("comments.getUnreadMessage",account);
    }

    public boolean setUnreadToRead(List comments){
        int i = sqlSessionTemplate.update("comments.setUnreadToRead",comments);
        return i>0;
    }

    public List selectTopArticle(HomeRequest homeRequest){
        return sqlSessionTemplate.selectList("article.selectTopArticle",homeRequest);
    }

}
