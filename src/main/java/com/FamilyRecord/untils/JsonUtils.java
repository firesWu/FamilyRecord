package com.FamilyRecord.untils;

import java.io.OutputStream;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yuan on 2018/3/23.
 */
public class JsonUtils extends JsonUtilBase {
    public static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    public JsonUtils() {
    }

    public static String genUpdateDataReturnJsonStr(boolean success, String msg) {
        HashMap remap = genUpdateDataReturnMap(success, msg, (Object)null, (String)null);
        String rejson = "";

        try {
            rejson = oMapper.writeValueAsString(remap);
        } catch (Exception var5) {
            logger.error("genUpdateDataReturnJsonStr 1 json 转换失败。\n", var5);
            logger.error("msg:" + msg + "----success:" + success);
        }

        return rejson;
    }

    public static <T> void genUpdateDataReturnJsonStr(boolean success, String msg, T data, OutputStream os) {
        HashMap remap = genUpdateDataReturnMap(success, msg, data, (String)null);

        try {
            genUpdateDataReturnJsonStr(remap, os);
        } catch (Exception var6) {
            logger.error("retMap：" + data + "----success:" + success + "------msg:" + msg);
            logger.error("genUpdateDataReturnJsonStr 7 json 转换失败。\n", var6);
        }

    }

    public static <T> void genUpdateDataReturnJsonStr(boolean success, String msg, T data, HttpServletResponse response) {
        HashMap remap = genUpdateDataReturnMap(success, msg, data, (String)null);

        try {
            genUpdateDataReturnJsonStr(remap, response);
        } catch (Exception var6) {
            logger.error("retMap：" + data + "----success:" + success + "------msg:" + msg);
            logger.error("genUpdateDataReturnJsonStr 6 json 转换失败。\n", var6);
        }

    }

    public static <T> void genUpdateDataReturnJsonStr(T data, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");

        try {
            genUpdateDataReturnJsonStr(data, response.getOutputStream());
        } catch (Exception var3) {
            logger.error("retMap：" + data);
            logger.error("genUpdateDataReturnJsonStr 4 json 转换失败。\n", var3);
        }

    }

    public static void main(String[] args) throws Exception {
        System.out.println(genUpdateDataReturnJsonStr(false, "msg", "data", (String)"status"));
        genUpdateDataReturnJsonStrByCode("msg", "data", "status");
    }
}
