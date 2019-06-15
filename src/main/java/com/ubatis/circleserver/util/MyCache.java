package com.ubatis.circleserver.util;


import com.ubatis.circleserver.modules.common.bean.DocBean;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 缓存list和一些对象。线程不安全
 * Created by lance on 2017/8/14.
 */
public class MyCache {

    // 文档
    public ArrayList<DocBean> apiDocs;
    // 数据库文档
    public ArrayList<DocBean> databaseDocs;
    // api文档key
    public String docKey;
    // 数据库文档key
    public String databaseDocKey;

//    new CopyOnWriteArrayList

//    /** 小圈信息列表 */
//    public Map<String, Object> circleInfoMap = new ConcurrentHashMap();




    private static volatile MyCache singleton = null;

    public static MyCache getInstance() {
        if (singleton == null) {
            synchronized (MyCache.class) {
                singleton = new MyCache();
            }
        }
        return singleton;
    }

}
