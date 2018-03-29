package com.FamilyRecord.apps.article.entity;

import com.FamilyRecord.abstractApps.BaseEntity;

/**
 * Created by yuan on 2018/3/28.
 */
public class Article extends BaseEntity {

    private String rId;
    private String title;
    private String content;
    private String creator;

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
