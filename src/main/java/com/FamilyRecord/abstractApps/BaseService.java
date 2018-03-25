package com.FamilyRecord.abstractApps;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by yuan on 2018/3/23.
 */
public abstract class BaseService {

    @Autowired
    public SqlSessionTemplate sqlSessionTemplate;

}
