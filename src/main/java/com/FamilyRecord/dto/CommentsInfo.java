package com.FamilyRecord.dto;

import com.FamilyRecord.apps.article.entity.Comments;

/**
 * Created by yuan on 2018/4/2.
 */
public class CommentsInfo extends Comments {

    private String parentNickName;
    private String replyNickName;
    private String articleTitle;

    public String getParentNickName() {
        return parentNickName;
    }

    public void setParentNickName(String parentNickName) {
        this.parentNickName = parentNickName;
    }

    public String getReplyNickName() {
        return replyNickName;
    }

    public void setReplyNickName(String replyNickName) {
        this.replyNickName = replyNickName;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }
}
