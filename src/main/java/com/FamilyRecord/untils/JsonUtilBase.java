package com.FamilyRecord.untils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by yuan on 2018/3/23.
 */
public class JsonUtilBase {
    public static Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    protected static ObjectMapper oMapper = null;

    public JsonUtilBase() {
    }

    public static Writer quote(String string, Writer w) throws IOException {
        if(string != null && string.length() != 0) {
            char c = 0;
            int len = string.length();
            w.write(34);

            for(int i = 0; i < len; ++i) {
                char b = c;
                c = string.charAt(i);
                switch(c) {
                    case '\b':
                        w.write("\\b");
                        continue;
                    case '\t':
                        w.write("\\t");
                        continue;
                    case '\n':
                        w.write("\\n");
                        continue;
                    case '\f':
                        w.write("\\f");
                        continue;
                    case '\r':
                        w.write("\\r");
                        continue;
                    case '\"':
                    case '\\':
                        w.write(92);
                        w.write(c);
                        continue;
                    case '/':
                        if(b == 60) {
                            w.write(92);
                        }

                        w.write(c);
                        continue;
                }

                if(c >= 32 && (c < 128 || c >= 160) && (c < 8192 || c >= 8448)) {
                    w.write(c);
                } else {
                    w.write("\\u");
                    String hhhh = Integer.toHexString(c);
                    w.write("0000", 0, 4 - hhhh.length());
                    w.write(hhhh);
                }
            }

            w.write(34);
            return w;
        } else {
            w.write("\"\"");
            return w;
        }
    }

    public static ObjectMapper getObjectMapper() {
        return oMapper;
    }

    public static String quote(String string) {
        StringWriter sw = new StringWriter();

        try {
            return quote(string, sw).toString();
        } catch (IOException var3) {
            return "";
        }
    }

    public static <T> Object stringToJSON(String str, Class<?> clazz) throws JsonParseException, JsonMappingException, IOException {
        return oMapper.readValue(str, clazz);
    }

    public static <T> String getRetJsonStr(T data) {
        String rejson = "";

        try {
            rejson = oMapper.writeValueAsString(data);
        } catch (Exception var3) {
            logger.error("retMap：" + data);
            logger.error("genUpdateDataReturnJsonStr 2 json 转换失败。\n", var3);
        }

        return rejson;
    }

    public static <T> String genUpdateDataReturnJsonStr(boolean success, String msg, T data) {
        HashMap remap = genUpdateDataReturnMap(success, msg, data, (String)null);
        String rejson = "";

        try {
            rejson = oMapper.writeValueAsString(remap);
        } catch (Exception var6) {
            logger.error("retMap：" + data + "----success:" + success + "------msg:" + msg);
            logger.error("genUpdateDataReturnJsonStr 2 json 转换失败。\n", var6);
        }

        return rejson;
    }

    public static <T> String genUpdateDataReturnJsonStr(boolean success, String msg, T data, String code) {
        HashMap remap = genUpdateDataReturnMap(success, msg, data, code);
        String rejson = "";

        try {
            rejson = oMapper.writeValueAsString(remap);
        } catch (Exception var7) {
            logger.error("retMap：" + data + "----success:" + success + "------msg:" + msg + "------code:" + code);
            logger.error("genUpdateDataReturnJsonStr 2 json 转换失败。\n", var7);
        }

        return rejson;
    }

    public static <T> String genUpdateDataReturnJsonStrByCode(String code, String msg, T data) {
        return genUpdateDataReturnJsonStr(true, msg, data, code);
    }

    public static <T> String genUpdateDataReturnJsonStrByCode(int code, String msg, T data) {
        return genUpdateDataReturnJsonStr(true, msg, data, String.valueOf(code));
    }

    public static <T> String genUpdateDataReturnJsonStr(T data) {
        String rejson = "";

        try {
            rejson = oMapper.writeValueAsString(data);
        } catch (Exception var3) {
            logger.error("retMap：" + data);
            logger.error("genUpdateDataReturnJsonStr 3 json 转换失败。\n", var3);
        }

        return rejson;
    }

    public static <T> void genUpdateDataReturnJsonStr(T data, OutputStream os) {
        try {
            long x = System.currentTimeMillis();
            oMapper.writeValue(os, data);
            long d2 = System.currentTimeMillis();
            os.flush();
            os.close();
            logger.debug("json to outputStream time : " + (d2 - x));
            System.out.println("json to outputStream time : " + (d2 - x));
        } catch (Exception var6) {
            logger.error("retMap：" + data);
            logger.error("genUpdateDataReturnJsonStr 5 json 转换失败。\n", var6);
        }

    }

    public static <T> HashMap genUpdateDataReturnMap(boolean success, String msg, T data, String status) {
        HashMap remap = new HashMap();
        remap.put("success", Boolean.valueOf(success));
        remap.put("msg", msg);
        remap.put("data", data);
        remap.put("code", status);
        return remap;
    }

    static {
        oMapper = new ObjectMapper();
        oMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        oMapper.configure(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        oMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        oMapper.configure(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        oMapper.configure(Feature.ALLOW_COMMENTS, true);
        oMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }
}
