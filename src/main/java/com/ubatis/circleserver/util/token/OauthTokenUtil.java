package com.ubatis.circleserver.util.token;

import com.ubatis.circleserver.config.SysConfig;
import com.ubatis.circleserver.util.JsonUtil;
import com.ubatis.circleserver.util.common.CS;
import com.ubatis.circleserver.util.redis.MyRedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;

/**
 * Created by lance on 2017/4/25.
 */
@Component
public class OauthTokenUtil {

    @Autowired
    private SysConfig sysConfig;

    @Autowired
    private MyRedisClient redisClient;

    /** token过期时间。默认2小时 */
    private final long TOKEN_EXPIRE_IN = 2 * 60 * 60;
    /** refresh token 过期时间，默认7天 */
    private final long REFRESH_EXPIRE_IN = 7 * 24 * 60 * 60;
    /** token一天内最大刷新次数 */
    private final int MAX_REFRESH_TIMES = 20;//如果给第三方维护用，就要刷新多一点，每1小时50分刷新一次

    /**
     * 获取UUID
     * @return
     */
    private static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * 获取key
     * @param id
     * @return
     */
    private String getTokenKey(String id){
        return sysConfig.getApp_name()+":oauth_token:id:"+id;
    }

    private long getTodayTimestamp(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime().getTime()/1000;
    }

    public int getCurrentTimestamp(){
        return (int) (System.currentTimeMillis() / 1000);
    }


    /**
     * 生成Token
     * @param id 应用用户的唯一标识
     * @return
     */
    public TokenBean generateToken(String id){
        return generateToken(id, false);
    }

    /**
     * 排他登录
     * @param id
     * @return
     */
    public TokenBean generateSingleToken(String id){
        return generateToken(id, true);
    }

    private TokenBean generateToken(String id, boolean single){
        String key = getTokenKey(id);
        long today = getTodayTimestamp();
        long currentTimestamp = getCurrentTimestamp();
        //
        if(single){
            redisClient.hmdelete(key);
        }else{
            //只删除过期的token
            Set<String> fields = redisClient.getFields(key);
            for(String field: fields){
                String jsonStr = redisClient.hmget(key, field);
                TokenBean oldToken = JsonUtil.fromJson(jsonStr,TokenBean.class);
                if(oldToken.getRefresh_expire() < currentTimestamp){
                    redisClient.deleteField(key, field);
                }
            }
        }
        // 新token
        String token = getUUID();
        String refresh_token = getUUID();
        TokenBean tokenBean = new TokenBean(token, refresh_token, currentTimestamp+REFRESH_EXPIRE_IN, currentTimestamp+TOKEN_EXPIRE_IN,
                0,today,today);
        redisClient.hmset(key, token, JsonUtil.toJson(tokenBean));
        return tokenBean;
    }

    /**
     * 校验token
     * @param id id
     * @param tokenStr
     * @return
     */
    public OauthValidResult valid(String id, String tokenStr){
        String key = getTokenKey(id);
        long today = getTodayTimestamp();
        long currentTimestamp = getCurrentTimestamp();
        String tokenbeanStr = redisClient.hmget(key, tokenStr);
        if(tokenbeanStr == null){
            return new OauthValidResult(false, CS.TOKEN_INCORRECT, "token不正确");
        }
        TokenBean tokenBean = JsonUtil.fromJson(tokenbeanStr, TokenBean.class);
        if(tokenBean.getToken_expire() < getCurrentTimestamp()){
            return new OauthValidResult(false, CS.TOKEN_OVER_TIME, "token超时");
        }
        if(tokenBean.getLast_token_timestamp() != today){
            //延迟refresh_token过期时间
            tokenBean.setRefresh_expire(currentTimestamp + REFRESH_EXPIRE_IN);
            tokenBean.setLast_token_timestamp(today);
        }
        //存回去
        redisClient.hmset(key, tokenStr, JsonUtil.toJson(tokenBean));
        return new OauthValidResult(true, CS.SUCCESS, "ok");
    }

    /**
     * 刷新token
     * @param id
     * @param tokenStr
     * @param refresh_token
     * @return
     */
    public RefreshResult refresh(String id, String tokenStr, String refresh_token){
        String key = getTokenKey(id);
        long today = getTodayTimestamp();
        long currentTimestamp = getCurrentTimestamp();
        String tokenbeanStr = redisClient.hmget(key, tokenStr);
        if(tokenbeanStr == null){
            return new RefreshResult(CS.UA_REQUEST_FAIL, "id不对");
        }
        TokenBean tokenBean = JsonUtil.fromJson(tokenbeanStr, TokenBean.class);
        if(!tokenBean.getRefresh_token().equals(refresh_token)){
            return new RefreshResult(CS.REFRESHTOKEN_INCORRECT, "refresh_token 不正确");
        }
        if(tokenBean.getRefresh_times() >= MAX_REFRESH_TIMES){
            return new RefreshResult(CS.REFRESHTOKEN_OVER_TIME,"今日刷新次数过多");
        }
        //开始刷新
        String newTokenStr = getUUID();
        String newRefreshTokenStr = getUUID();
        tokenBean.setToken_expire(currentTimestamp + TOKEN_EXPIRE_IN);
        //如果今天重复使用，+1,隔天，设置为1
        if(tokenBean.getLast_refresh_token_timestamp() == today){
            tokenBean.setRefresh_times(tokenBean.getRefresh_times()+1);
        }else{
            tokenBean.setRefresh_times(1);
        }
        //设置今天使用
        tokenBean.setLast_refresh_token_timestamp(today);
        tokenBean.setToken(newTokenStr);
        tokenBean.setRefresh_token(newRefreshTokenStr);
        //删除旧的
        redisClient.deleteField(key, tokenStr);
        //
        redisClient.hmset(key, newTokenStr, JsonUtil.toJson(tokenBean));
        return new RefreshResult(CS.SUCCESS, tokenBean);
    }

}
