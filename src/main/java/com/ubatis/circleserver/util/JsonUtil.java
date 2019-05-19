package com.ubatis.circleserver.util;

import com.google.gson.Gson;
import com.ubatis.circleserver.bean.basic.MyParams;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil<T> {

    private static Gson GSON = new Gson();

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }

    public static <T> T fromMap(Map map, Class<T> classOfT) {
        return GSON.fromJson(toJson(map), classOfT);
    }

    public static <T> T fromMap(Class<T> classOfT, Map... maps) {
        Map allMap = new HashMap();
        for (Map map: maps) {
            allMap.putAll(map);
        }
        return GSON.fromJson(toJson(allMap), classOfT);
    }

    public static <T> T fromJson(String json, Type type) {
        return GSON.fromJson(json, type);
    }

    public static <T> T fromJson(Map<String,Object> jsonmap, Class<T> classOfT) {
        return GSON.fromJson(toJson(jsonmap), classOfT);
    }

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    public static <T> List<T> getListFromJSON(String json, Class<T[]> type) {
//        LatestToken[].class
        T[] list = new Gson().fromJson(json, type);
        return Arrays.asList(list);
    }

    public static <T> List<T> getListFromHashMapList(List<Map<String, Object>> mapList,Class<T[]> type) {
        return getListFromJSON(toJson(mapList),type);
    }

    public static <T> T makeReturnBean(MyParams params, Class<T> bean) {
        return fromJson(toJson(params), bean);
    }
}
