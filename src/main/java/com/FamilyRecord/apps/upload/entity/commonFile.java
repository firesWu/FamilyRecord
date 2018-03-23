package com.FamilyRecord.apps.upload.entity;

import com.FamilyRecord.abstractApps.BaseEntity;

/**
 * Created by yuan on 2018/3/23.
 */
public class commonFile extends BaseEntity {

    private int rId;                //关联ablum
    private String filePath;        //文件路径
    private String fileName;        //文件名称
    private int type;               //文件类型 1.照片 2.视频
    private String creator;         //上传者
    private int isDelete;           //是否删除

    public int getrId() {
        return rId;
    }

    public void setrId(int rId) {
        this.rId = rId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }
}
