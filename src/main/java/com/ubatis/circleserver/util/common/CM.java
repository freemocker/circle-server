package com.ubatis.circleserver.util.common;


import com.ubatis.circleserver.bean.basic.JsonBase;

import java.util.List;

/** Common Method
 * Created by lance on 2017/4/26.
 */
public class CM {

    public static <T> JsonBase getSuccessMsg(List<T> list, String info) {
        JsonBase<T> jsonBase = new JsonBase<T>();
        jsonBase.setCode(CS.SUCCESS);
        jsonBase.setData(list);
        jsonBase.setInfo(info);
        return jsonBase;
    }

    public static <T> JsonBase getSuccessMsg(Object header, List<T> list) {
        JsonBase<T> jsonBase = new JsonBase<T>();
        jsonBase.setCode(CS.SUCCESS);
        jsonBase.setHeader(header);
        jsonBase.setData(list);
        return jsonBase;
    }

    public static <T> JsonBase getSuccessMsg(Object header, List<T> list, Object info) {
        JsonBase<T> jsonBase = new JsonBase<T>();
        jsonBase.setCode(CS.SUCCESS);
        jsonBase.setHeader(header);
        jsonBase.setData(list);
        jsonBase.setInfo(info);
        return jsonBase;
    }

    public static <T> JsonBase getFailInfo(int c, Object info) {
        JsonBase<T> jsonBase = new JsonBase<T>();
        jsonBase.setCode(c);
        jsonBase.setInfo(info);
        return jsonBase;
    }

    public static JsonBase getReturnHeaderInfo(Object header, Object info) {
        JsonBase jsonBase = new JsonBase();
        jsonBase.setCode(CS.SUCCESS);
        jsonBase.setHeader(header);
        jsonBase.setInfo(info);
        return jsonBase;
    }

    public static JsonBase getReturnHeader(Object header) {
        JsonBase jsonBase = new JsonBase();
        jsonBase.setCode(CS.SUCCESS);
        jsonBase.setHeader(header);
        return jsonBase;
    }

    public static JsonBase getReturnInfo(Object info) {
        JsonBase jsonBase = new JsonBase();
        jsonBase.setCode(CS.SUCCESS);
        jsonBase.setInfo(info);
        return jsonBase;
    }

}
