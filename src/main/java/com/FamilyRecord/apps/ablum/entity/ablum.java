package com.FamilyRecord.apps.ablum.entity;

import com.FamilyRecord.abstractApps.BaseEntity;

/**
 * Created by yuan on 2018/3/23.
 */
public class ablum extends BaseEntity {

    private String albumName;   //相册名称
    private int type;           //相册类型 1.照片相册  2.视频相册
    private String creator;     //创建人

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
