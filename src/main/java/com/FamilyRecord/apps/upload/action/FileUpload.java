package com.FamilyRecord.apps.upload.action;

import com.FamilyRecord.abstractApps.BaseAction;
import com.FamilyRecord.apps.upload.entity.CommonFile;
import com.FamilyRecord.apps.upload.service.CommonFileService;
import com.FamilyRecord.apps.user.entity.User;
import com.FamilyRecord.apps.user.service.UserService;
import com.FamilyRecord.untils.Constants;
import com.FamilyRecord.untils.JsonUtils;
import com.FamilyRecord.untils.RandomGuid;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yuan on 2018/3/26.
 */
@RestController
@RequestMapping("fileUpload")
public class FileUpload extends BaseAction {

    private static Logger logger = LoggerFactory.getLogger(FileUpload.class);

    @Autowired
    private UserService userService;

    @Autowired
    private CommonFileService commonFileService;

    @Autowired
    private CommonsMultipartResolver multipartResolver;

    @RequestMapping("/upload")
    public String upload(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        // 判断 request 是否有文件上传,即多部分请求
        response.setCharacterEncoding("GBK");
        List arrFileNames = new ArrayList();
        int rid = Integer.parseInt(request.getParameter("rId").trim());
        int type = Integer.parseInt(request.getParameter("type").trim());
        String creator = request.getParameter("creator").trim();
        String callback=request.getParameter("callback").trim();

        String realPath = request.getServletContext().getRealPath("/");

        if (multipartResolver.isMultipart(request)) {
            // 生成关联ID

            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                List<MultipartFile> files = multiRequest.getFiles(iter.next());

                for(MultipartFile file : files){
                    if (file != null) {
                        // 取得当前上传文件的文件名称
                        String myFileName = file.getOriginalFilename();
                        // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
                        if (!myFileName.trim().equals("")) {
                            //判断是否为可执行文件
                            if (!(myFileName.endsWith(".exe") || myFileName.endsWith(".bat"))){
                                String fileSuffix = myFileName.substring(myFileName.lastIndexOf('.'));
                                String fileName = file.getOriginalFilename();
                                // 定义上传路径
                                String path =  Constants.UPLOAD_FILEPATH ;
                                try {
                                    FileUtils.forceMkdir(new File(realPath+path));
                                } catch (IOException e1) {
                                    logger.error("上传文件异常", e1);
                                    return scriptWrap(JsonUtils.genUpdateDataReturnJsonStr(false, "1", ""), callback);//对应服务器异常错误
                                }
                                String realFileName = RandomGuid.getGuid() + fileSuffix;
                                File localFile = new File(realPath+path, realFileName);

                                try {
                                    file.transferTo(localFile);
                                    CommonFile cf = new CommonFile();
                                    cf.setrId(rid);
                                    cf.setType(type);
                                    cf.setFileName(fileName);
                                    cf.setFilePath("/" + path + realFileName);
                                    cf.setCreator(creator);
                                    arrFileNames.add(cf);
                                } catch (IllegalStateException | IOException e) {
                                    logger.error("上传文件异常", e);
                                    return scriptWrap(JsonUtils.genUpdateDataReturnJsonStr(false, "1", ""), callback);
                                }
                            }else{return scriptWrap(JsonUtils.genUpdateDataReturnJsonStr(false, "2"), callback);//1. 对应前台错误数组的可执行文件不可上传错误
                            }
                        }
                    }
                }

            }
            //如果有上传文件
            if(arrFileNames.size()>0){
                boolean b = commonFileService.addFiles(arrFileNames);
                // 添加失败直接返回
                if (!b) {
                    return scriptWrap(JsonUtils.genUpdateDataReturnJsonStr(false, "false", ""), callback);
                }
            }else{
                return scriptWrap(JsonUtils.genUpdateDataReturnJsonStr(false, "3"),callback);//1. 对应前台错误数组的没有文件错误
            }

        }
        return scriptWrap(JsonUtils.genUpdateDataReturnJsonStr(true, "Upload complete", arrFileNames), callback);
    }

    public String scriptWrap(String json,String callBack){
        String script="<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head><script charset=\"UTF-8\" type=\"text/javascript\">"
                + callBack+"("+json+")"
                + "</script></html>";
        return script;
    }

    @RequestMapping("uploadHeadImage")
    public String uploadHeadImage(String img,String account,HttpServletRequest request){

        String realPath = request.getServletContext().getRealPath("/");
        String headImage = img.substring(img.indexOf("base64")+7);
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] imgbyte;

        try{
            imgbyte = decoder.decodeBuffer(headImage);
            String path = Constants.UPLOAD_HEAD_IMAGE + System.currentTimeMillis()+".jpg";
            OutputStream os = new FileOutputStream(new File(realPath + path));
            os.write(imgbyte,0,imgbyte.length);
            os.close();

            User user = new User();
            user.setAccount(account);
            user.setHeadImageUrl(path);

            boolean result = userService.update(user);

            if(result){
                return JsonUtils.genUpdateDataReturnJsonStr(true,"上传成功",user);
            }else{
                return JsonUtils.genUpdateDataReturnJsonStr(false,"上传失败");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return JsonUtils.genUpdateDataReturnJsonStr(false,"上传失败");
        }
    }

    @RequestMapping("/uploadForAndroid")
    public String uploadForAndroid(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        // 判断 request 是否有文件上传,即多部分请求
        response.setCharacterEncoding("GBK");
        List arrFileNames = new ArrayList();
        int rid = Integer.parseInt(request.getParameter("rId").trim());
        int type = Integer.parseInt(request.getParameter("type").trim());
        String creator = request.getParameter("creator").trim();

        String realPath = request.getServletContext().getRealPath("/");

        if (multipartResolver.isMultipart(request)) {
            // 生成关联ID

            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                List<MultipartFile> files = multiRequest.getFiles(iter.next());

                for(MultipartFile file : files){
                    if (file != null) {
                        // 取得当前上传文件的文件名称
                        String myFileName = file.getOriginalFilename();
                        // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
                        if (!myFileName.trim().equals("")) {
                            //判断是否为可执行文件
                            if (!(myFileName.endsWith(".exe") || myFileName.endsWith(".bat"))){
                                String fileSuffix = myFileName.substring(myFileName.lastIndexOf('.'));
                                String fileName = file.getOriginalFilename();
                                // 定义上传路径
                                String path =  Constants.UPLOAD_FILEPATH ;
                                try {
                                    FileUtils.forceMkdir(new File(realPath+path));
                                } catch (IOException e1) {
                                    logger.error("上传文件异常", e1);
                                    return JsonUtils.genUpdateDataReturnJsonStr(false, "1", "");//对应服务器异常错误
                                }
                                String realFileName = RandomGuid.getGuid() + fileSuffix;
                                File localFile = new File(realPath+path, realFileName);

                                try {
                                    file.transferTo(localFile);
                                    CommonFile cf = new CommonFile();
                                    cf.setrId(rid);
                                    cf.setType(type);
                                    cf.setFileName(fileName);
                                    cf.setFilePath(path + realFileName);
                                    cf.setCreator(creator);
                                    arrFileNames.add(cf);
                                } catch (IllegalStateException | IOException e) {
                                    logger.error("上传文件异常", e);
                                    return JsonUtils.genUpdateDataReturnJsonStr(false, "1", "");
                                }
                            }else{return JsonUtils.genUpdateDataReturnJsonStr(false, "2");//1. 对应前台错误数组的可执行文件不可上传错误
                            }
                        }
                    }
                }

            }
            //如果有上传文件
            if(arrFileNames.size()>0){
                boolean b = commonFileService.addFiles(arrFileNames);
                // 添加失败直接返回
                if (!b) {
                    return JsonUtils.genUpdateDataReturnJsonStr(false, "false", "");
                }
            }else{
                return JsonUtils.genUpdateDataReturnJsonStr(false, "3");//1. 对应前台错误数组的没有文件错误
            }

        }
        return JsonUtils.genUpdateDataReturnJsonStr(true, "Upload complete", arrFileNames);
    }
}
