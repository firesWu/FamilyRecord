package com.FamilyRecord.apps.article.entity;

import com.FamilyRecord.abstractApps.BaseEntity;

/**
 * Created by yuan on 2018/3/28.
 */
public class Comments extends BaseEntity {

    private String articleId;
    private String parentId;
    private String replyId;
    private String comment;
    private String floor;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

}
