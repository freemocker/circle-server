package com.ubatis.circleserver.util.common;

/** Constance
 * Created by lance on 2017/4/26.
 */
public class CS {

    //定义的返回码
    /**成功*/
    public static final int SUCCESS = 0;
    /**未知错误*/
    public static final int UNKNOWN_ERROR = -1000;
    /**请求ua异常，请求失败*/
    public static final int UA_REQUEST_FAIL = -1001;
    /**请求缺少参数*/
    public static final int MISS_PARAMS = -1002;
    /**请求签名验证失败*/
    public static final int SIGN_REQUEST_FAIL = -1003;
    /**请求超时*/
    public static final int REQUEST_OVER_TIME = -1004;
    /**请求内容不正常-不存在*/
    public static final int REQUEST_CONTENT_ERROR = -1005;
    /**token不正确*/
    public static final int TOKEN_INCORRECT = -1006;
    /**token超时*/
    public static final int TOKEN_OVER_TIME = -1007;
    /**refresh token 不正确*/
    public static final int REFRESHTOKEN_INCORRECT = -1008;
    /**refresh token 超时*/
    public static final int REFRESHTOKEN_OVER_TIME = -1009;
    /**数据库错误*/
    public static final int DB_FAIL = -1010;
    /**重复*/
    public static final int REPEAT = -1111;
    /**参数格式错误*/
    public static final int INVALID_PARAMS = -1212;
    /**没有权限*/
    public static final int NO_PERMISSION = -1213;
    /**最新的token已过期*/
    public static final int LATEST_TOKEN_EXPIRE_7_DAYS = -1214;
    /**token已过期*/
    public static final int LATEST_TOKEN_EXPIRE_2_HOURS = -1215;

}
