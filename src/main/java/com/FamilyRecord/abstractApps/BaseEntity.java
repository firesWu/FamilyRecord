package com.FamilyRecord.abstractApps;

/**
 * Created by yuan on 2018/3/23.
 */
public abstract class BaseEntity {

    private int id;
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
