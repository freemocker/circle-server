package com.ubatis.circleserver.util;


import com.ubatis.circleserver.modules.basic.bean.DocBean;

import java.util.ArrayList;

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
