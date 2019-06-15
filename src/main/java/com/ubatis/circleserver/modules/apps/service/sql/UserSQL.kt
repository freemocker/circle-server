package com.ubatis.circleserver.modules.apps.service.sql

fun getCircleInfoListSQL(): String {

    var SQL = """
        SELECT
        c.*,t.mp_appid
        , t.mp_secret, t.mp_access_token, t.mp_expires_time
        FROM ac_circle c
        LEFT JOIN sys_access_token t ON (c.ac_token_id = t.id)
        WHERE 1 = 1
        AND c.id = :circle_id
    """.replace("\n"," ")
    return SQL
}

fun getUserInfoListSQL(params: Map<String, Object>): String {

    var openid = params["openid"]?.toString()?:""

    var SQL = """
        SELECT u.* FROM ac_user u
        WHERE 1 = 1
        ${ if(openid.isNullOrEmpty()) " " else " AND u.openid = :openid "}
    """.replace("\n"," ")
    return SQL
}