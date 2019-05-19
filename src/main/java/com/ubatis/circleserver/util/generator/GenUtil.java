package com.ubatis.circleserver.util.generator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenUtil {
	//首字母大写
	public static String firstLetterUpperCase(String str) {
		return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
	}
	
	// 转驼峰名
	public static String toCamelName(String name){
        StringBuilder sb = new StringBuilder(name);
        Matcher mc = Pattern.compile("_").matcher(name);
        int i = 0;
        while (mc.find()) {
            int position = mc.end() - (i++);
            sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
        }
        return firstLetterUpperCase(sb.toString());
	}
}
