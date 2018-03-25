package com.FamilyRecord.untils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by YuanGe on 2016/11/5.
 */
public class RegularMatch {

    private static Pattern pattern = null;
    private static Matcher matcher = null;
    private static String patternStr = null;


    //传入字符串和正则表达式，输出是否匹配
    public static boolean matchPatternAndString(String str,String patternStr){
        pattern = Pattern.compile(patternStr);
        matcher = pattern.matcher(str);
        return matcher.matches();
    }

}
