package com.ubatis.circleserver.util.sign;

import com.ubatis.circleserver.config.SysConfig;
import com.ubatis.circleserver.util.Md5Util;
import com.ubatis.circleserver.util.redis.MyRedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

/** 自定义签名工具<br/>
 * 签名规则：<br/>
 * 签名秘钥 + openid + 2倍时间戳的字符串，然后翻转，取md5值。
 * 1. str1 = (sign_key + openid + (timestamp + timestamp)).reverse()
 * 2. sign = md5(str1)
 * Created by lance on 2017/11/1.
 */
@Component
public class MySignUtil {

    private static final int expire_time = 7 * 24 * 3600;

    @Autowired
    private SysConfig sysConfig;
    @Autowired
    private MyRedisClient myRedisClient;

    /**
     * 签名校验
     * @param clientSign 客户端签名
     * @param user_id 客户端用户id
     * @param timestamp 时间戳
     * @return
     */
    public boolean valid(String clientSign, String user_id, String timestamp){
        BigInteger bigInteger = new BigInteger(timestamp);
        String str1 = sysConfig.getSign_key() + user_id + (bigInteger.add(bigInteger));
        String sign = Md5Util.Md5(new StringBuilder(str1).reverse().toString());
        return sign.equals(clientSign);
    }

    public void setUserId(String userId) {
        myRedisClient.setExpireKey(sysConfig.getApp_name() + ":uid:" + userId, "1", expire_time);
    }

    public boolean userIdExist(String userId) {
        return myRedisClient.hasKey(sysConfig.getApp_name() + ":uid:" + userId);
    }

}
