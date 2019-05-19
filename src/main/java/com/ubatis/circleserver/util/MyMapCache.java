package com.ubatis.circleserver.util;

import io.netty.util.internal.ConcurrentSet;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存map类的数据。线程安全
 * Created by lance on 2017/8/14.
 */
public class MyMapCache {

    // 用户id 集合
    public Set<String> userIdSet = new ConcurrentSet<>();
    // 轮播图
    public Map<String, List<Map<String, Object>>> swiperListMap = new ConcurrentHashMap<>();
    // 字典缓存,key 是 parent_id
    public Map<String, List<Map<String, Object>>> dictListMap = new ConcurrentHashMap<>();
    // 缓存一次性验证key
    public Map<String, Boolean> oneOffKeyMap = new ConcurrentHashMap<>();
    
    //
    public void consumeOneOffKey(String key){
        oneOffKeyMap.remove(key);
        // 定量清空全部
        if (oneOffKeyMap.size() > 1000) oneOffKeyMap.clear();
    }

    private static MyMapCache singleton = null;

    public static MyMapCache getInstance() {
        if (singleton == null) {
            singleton = new MyMapCache();
        }
        return singleton;
    }

}
