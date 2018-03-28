package com.FamilyRecord.apps.upload.service;

import com.FamilyRecord.abstractApps.BaseService;
import com.FamilyRecord.apps.upload.entity.CommonFile;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yuan on 2018/3/27.
 */
@Service
public class CommonFileService extends BaseService {

    public List selectPhotoOrVideo(CommonFile commonFile){
        return sqlSessionTemplate.selectList("commonFile.select",commonFile);
    }

    public boolean addFiles(List files){
        int i = sqlSessionTemplate.insert("commonFile.insertFiles",files);
        return i>0;
    }

}
