package com.FamilyRecord.apps.familyGroup.entity;

import com.FamilyRecord.abstractApps.BaseEntity;

/**
 * Created by yuan on 2018/3/23.
 */
public class FamilyGroup extends BaseEntity {

    private String name;        //家庭组名称
    private String creator;     //创建人

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
