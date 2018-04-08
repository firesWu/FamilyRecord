package com.FamilyRecord.apps.system.entity;

import com.FamilyRecord.apps.article.entity.Article;
import com.FamilyRecord.apps.upload.entity.CommonFile;
import com.FamilyRecord.apps.user.entity.BirthdayReminding;

import java.util.List;

/**
 * Created by yuan on 2018/4/8.
 */
public class HomePage {

    private List<Article> articleList;
    private List<CommonFile> photoList;
    private List<CommonFile> videoList;
    private List<BirthdayReminding> birthdayRemindingList;

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public List<CommonFile> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<CommonFile> photoList) {
        this.photoList = photoList;
    }

    public List<CommonFile> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<CommonFile> videoList) {
        this.videoList = videoList;
    }

    public List<BirthdayReminding> getBirthdayRemindingList() {
        return birthdayRemindingList;
    }

    public void setBirthdayRemindingList(List<BirthdayReminding> birthdayRemindingList) {
        this.birthdayRemindingList = birthdayRemindingList;
    }
}
