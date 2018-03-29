package com.FamilyRecord.abstractApps;

/**
 * Created by yuan on 2018/3/23.
 */
public abstract class BaseEntity {

    private String id;
    private String createTime;
    private int isDelete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }
}
