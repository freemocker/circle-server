package com.ubatis.circleserver.util.sign;

import com.ubatis.circleserver.config.SysConfig;
import com.ubatis.circleserver.util.Md5Util;
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

    @Autowired
    private SysConfig sysConfig;

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

}
