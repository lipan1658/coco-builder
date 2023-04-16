package com.coco.builder.utils;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CommonUtil
 *
 * @author lp
 * @version 1.0
 * @description CommonUtil
 * @date 2023/3/7 20:51
 */
public class CommonUtil {


    private static final Pattern linePattern = Pattern.compile("_(\\w)");

    public static String lineToHump(String str) {
        if(!str.contains("_")){
            return str;
        }
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuilder builder = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(builder, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(builder);
        return builder.toString();
    }

    public static MysqlType getMysqlType(String type) {
        MysqlType[] values = MysqlType.values();
        for (MysqlType value : values) {
            if(type.toUpperCase().equals(value.getName())){
                return value;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(lineToHump("typeName"));
    }
}
