package com.ubatis.circleserver.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存map类的数据。线程安全
 * Created by lance on 2017/8/14.
 */
public class MyMapCache {

    public Map temp = new ConcurrentHashMap();


    private static MyMapCache singleton = null;

    public static MyMapCache getInstance() {
        if (singleton == null) {
            singleton = new MyMapCache();
        }
        return singleton;
    }

}
