package com.FamilyRecord.apps.ablum.service;

import com.FamilyRecord.abstractApps.BaseService;
import com.FamilyRecord.apps.ablum.entity.Album;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yuan on 2018/3/26.
 */
@Service
public class AlbumService extends BaseService{

    public boolean insert(Album album){
        int i = sqlSessionTemplate.insert("album.insert",album);
        return i>0;
    }

    public List selectAlbumInfo(Album album){
        return sqlSessionTemplate.selectList("album.selectAlbumInfo",album);
    }

}
