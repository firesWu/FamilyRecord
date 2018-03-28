package com.FamilyRecord.dto;

import com.FamilyRecord.apps.ablum.entity.Album;

/**
 * Created by yuan on 2018/3/26.
 */
public class AlbumInfo extends Album {

    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
