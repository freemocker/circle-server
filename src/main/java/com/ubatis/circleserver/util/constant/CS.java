package com.ubatis.circleserver.util.constant;

/**
 * 字典表 (generate automatically from sys_cs, don't edit)
 */
public class CS {

	// ========== 返回码 ==========
    /** 成功 */
    public static final int RETURN_CODE_SUCCESS = 0;
    /** 未知错误 */
    public static final int RETURN_CODE_UNKNOWN_ERROR = -1000;
    /** 缺少参数 */
    public static final int RETURN_CODE_MISS_PARAMETER = -1001;
    /** 缺少header */
    public static final int RETURN_CODE_MISS_HEADER = -1002;
    /** 参数格式错误 */
    public static final int RETURN_CODE_PARAMETER_FORMAT_INCORRECT = -1003;
    /** header格式错误 */
    public static final int RETURN_CODE_HEADER_FORMAT_INCORRECT = -1004;
    /** 签名错误 */
    public static final int RETURN_CODE_SIGNATURE_INCORRECT = -1005;
    /** 签名超时 */
    public static final int RETURN_CODE_SIGNATURE_TIMEOUT = -1006;
    /** 请求超时 */
    public static final int RETURN_CODE_REQUEST_TIMEOUT = -1007;
    /** 目标找不到 */
    public static final int RETURN_CODE_TARGET_NOT_FOUND = -1008;
    /** token不正确 */
    public static final int RETURN_CODE_TOKEN_INCORRECT = -1009;
    /** token超时 */
    public static final int RETURN_CODE_TOKEN_TIMEOUT = -1010;
    /** refresh token不正确 */
    public static final int RETURN_CODE_REFRESHTOKEN_INCORRECT = -1011;
    /** refresh token 超时 */
    public static final int RETURN_CODE_REFRESHTOKEN_TIMEOUT = -1012;
    /** latest token 7天超时 */
    public static final int RETURN_CODE_LATEST_TOKEN_EXPIRE_7_DAYS = -1013;
    /** latest token 2小时超时 */
    public static final int RETURN_CODE_LATEST_TOKEN_EXPIRE_2_HOURS = -1014;
    /** 无效参数 */
    public static final int RETURN_CODE_INVALID_PARAMETER = -1015;
    /** 没有权限 */
    public static final int RETURN_CODE_PERMISSION_DENIED = -1016;
    /** 数据库发生错误 */
    public static final int RETURN_CODE_DB_ERROR = -1017;
    /** 重复 */
    public static final int RETURN_CODE_REPEAT = -1111;

	// ========== 拼车单状态 ==========
    /** 进行中 */
    public static final String SHARECAR_ORDER_STATUS_ING = "ING";
    /** 已结束 */
    public static final String SHARECAR_ORDER_STATUS_FINISH = "FINISH";

}