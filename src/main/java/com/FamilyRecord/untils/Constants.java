package com.FamilyRecord.untils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by yuan on 2018/3/26.
 */
public final class Constants {
    private static final Properties props = new Properties();

    static {
        Resource resource = new ClassPathResource("appConf/context.properties");
        try {
            props.load(resource.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final static String UPLOAD_FILEPATH = "/images/photo/";
    public final static String UPLOAD_HEAD_IMAGE = props.getProperty("headImagePath");

    public static void main(String[] args) {
        System.out.println(UPLOAD_HEAD_IMAGE);
    }

}
