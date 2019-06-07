package com.ubatis.circleserver.util.token;

import com.ubatis.circleserver.config.SysConfig;
import com.ubatis.circleserver.util.JsonUtil;
import com.ubatis.circleserver.util.constant.CS;
import com.ubatis.circleserver.util.redis.MyRedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/** 单端有效的验证
 * Created by lance on 2017/4/26.
 */
@Component
public class LatestTokenUtil {

    private final static Logger logger = LoggerFactory.getLogger(LatestTokenUtil.class);

    @Autowired
    private SysConfig sysConfig;
    @Autowired
    private MyRedisClient redisClient;

    /** token过期时间。默认2小时 */
    private long TOKEN_EXPIRE_IN = 2 * 60 * 60 * 1000;
    /** latest token 过期时间，默认7天 */
    private long LATEST_EXPIRE_IN = 7 * 24 * 60 * 60 * 1000;

    /**
     * 获取UUID
     * @return
     */
    private static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * 获取last_token的key
     * @param clientId
     * @param id
     * @return
     */
    private String getTokenKey(String clientId, String id){
        return (sysConfig.getApp_name()+":"+clientId+":latest_token:id:"+id).trim();
    }

    public static long getCurrentTimestamp(){
        return System.currentTimeMillis();
    }

    /**
     * 生成token
     * @param clientId 客户端id
     * @param id
     * @param single 单端登录？true的话要把其他clientId的清空掉
     * @return
     */
    public LatestToken generateToken(String clientId, String id, boolean single){
        String key = getTokenKey(clientId, id);
        redisClient.clearList(key);//清空旧的
        if(single) {//清除所有客户端token
            Set<String> keys = redisClient.getKeys(sysConfig.getApp_name()+":"+"*"+":latest_token:id:"+id);
            for(String item: keys) {
                redisClient.delete(item);
            }
        }
        String token = getUUID();
        long currentTimestamp = getCurrentTimestamp();
        LatestToken latestToken = new LatestToken(token, currentTimestamp + LATEST_EXPIRE_IN);
        redisClient.rPoplPushTokenItem(key, JsonUtil.toJson(latestToken));
        return latestToken;
    }

    public LatestResult valid(String clientId, String id, String token){
        String key = getTokenKey(clientId, id);
        logger.info("key:[{}]", key);
        long currentTimestamp = getCurrentTimestamp();
        //验证
        List<LatestToken> latestTokenList = redisClient.rangTokenBean(key);
        if (latestTokenList.size() == 0) return new LatestResult(CS.RETURN_CODE_TOKEN_INCORRECT, "token不存在");
        if(latestTokenList.get(0).getToken().equals(token)){
            if(latestTokenList.get(0).getExpire() < currentTimestamp){
                return new LatestResult(CS.RETURN_CODE_LATEST_TOKEN_EXPIRE_7_DAYS,"全部token过期，请重新登录");
            }
            //最新的话会返回一个新的
            LatestToken newToken = refreshLatestTokenList(key);
            return new LatestResult(CS.RETURN_CODE_SUCCESS, newToken);
        }
        for (int i = 1; i < latestTokenList.size(); i++) {
            if(latestTokenList.get(i).getToken().equals(token)){
                if(latestTokenList.get(i).getExpire() > currentTimestamp){
                    return new LatestResult(CS.RETURN_CODE_SUCCESS,"valid ok");
                }else{
                    return new LatestResult(CS.RETURN_CODE_LATEST_TOKEN_EXPIRE_2_HOURS,"token 过期");
                }
            }
        }
        //都不对
        return new LatestResult(CS.RETURN_CODE_TOKEN_INCORRECT, "token不正确");
    }

    /**
     * 刷新列表
     * @param key
     * @return
     */
    public LatestToken refreshLatestTokenList(String key){
        String newToken = getUUID();
        long currentTimestamp = getCurrentTimestamp();
        LatestToken latestToken = new LatestToken(newToken, currentTimestamp + LATEST_EXPIRE_IN);
        redisClient.rPoplPushTokenItem(key,JsonUtil.toJson(latestToken));
        //设置第二项2小时过期
        LatestToken secondToken = JsonUtil.fromJson(redisClient.getListItem(key,1),LatestToken.class);
        secondToken.setExpire(currentTimestamp + TOKEN_EXPIRE_IN);
        redisClient.listset(key, 1, JsonUtil.toJson(secondToken));
        return latestToken;
    }

    /**
     * 获取列表最新的token，统一方法
     * 如果直接使用valid方法返回的结果，可能会返回的是旧的token
     * @param client_id
     * @param uid
     * @return
     */
    public LatestToken getLatestToken(String client_id, String uid) {
        return redisClient.getListItem0(getTokenKey(client_id, uid));
    }
}
