package com.FamilyRecord.apps.upload.action;

import com.FamilyRecord.apps.upload.entity.CommonFile;
import com.FamilyRecord.apps.upload.service.CommonFileService;
import com.FamilyRecord.untils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yuan on 2018/3/26.
 */

@RestController
@RequestMapping("commonFile")
public class CommonFileAction {

    @Autowired
    CommonFileService commonFileService;

    @RequestMapping("selectPhotoOrVideo")
    public String selectPhotoOrVideo(CommonFile commonFile){
        return JsonUtils.genUpdateDataReturnJsonStr(true,"查询成功",commonFileService.selectPhotoOrVideo(commonFile));
    }

}
