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

    public static String genUpdateDataReturnJsonStr(boolean success, String mgs) {
        HashMap remap = genUpdateDataReturnMap(success, mgs, (Object)null, (String)null);
        String rejson = "";

        try {
            rejson = oMapper.writeValueAsString(remap);
        } catch (Exception var5) {
            logger.error("genUpdateDataReturnJsonStr 1 json ×ª»»Ê§°Ü¡£\n", var5);
            logger.error("mgs:" + mgs + "----success:" + success);
        }

        return rejson;
    }

    public static <T> void genUpdateDataReturnJsonStr(boolean success, String mgs, T data, OutputStream os) {
        HashMap remap = genUpdateDataReturnMap(success, mgs, data, (String)null);

        try {
            genUpdateDataReturnJsonStr(remap, os);
        } catch (Exception var6) {
            logger.error("retMap£º" + data + "----success:" + success + "------mgs:" + mgs);
            logger.error("genUpdateDataReturnJsonStr 7 json ×ª»»Ê§°Ü¡£\n", var6);
        }

    }

    public static <T> void genUpdateDataReturnJsonStr(boolean success, String mgs, T data, HttpServletResponse response) {
        HashMap remap = genUpdateDataReturnMap(success, mgs, data, (String)null);

        try {
            genUpdateDataReturnJsonStr(remap, response);
        } catch (Exception var6) {
            logger.error("retMap£º" + data + "----success:" + success + "------mgs:" + mgs);
            logger.error("genUpdateDataReturnJsonStr 6 json ×ª»»Ê§°Ü¡£\n", var6);
        }

    }

    public static <T> void genUpdateDataReturnJsonStr(T data, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");

        try {
            genUpdateDataReturnJsonStr(data, response.getOutputStream());
        } catch (Exception var3) {
            logger.error("retMap£º" + data);
            logger.error("genUpdateDataReturnJsonStr 4 json ×ª»»Ê§°Ü¡£\n", var3);
        }

    }

    public static void main(String[] args) throws Exception {
        System.out.println(genUpdateDataReturnJsonStr(false, "mgs", "data", (String)"status"));
        genUpdateDataReturnJsonStrByCode("mgs", "data", "status");
    }
}
