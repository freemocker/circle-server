package com.ubatis.circleserver.util.redis;

import com.ubatis.circleserver.exception.MyException;
import com.ubatis.circleserver.util.JsonUtil;
import com.ubatis.circleserver.util.constant.CS;
import com.ubatis.circleserver.util.token.LatestToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/** Redis 操作
 * Created by lance on 2017/4/21.
 */
@Component
public class MyRedisClient {

    private static Logger logger = LoggerFactory.getLogger(MyRedisClient.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /** 缓存 BoundHashOperations 到内存1000个 */
    private Map<String,BoundHashOperations> boundHashOperationsHashMap = new HashMap<String,BoundHashOperations>();
    private synchronized BoundHashOperations getBoundHashOperations(String key){
        if(boundHashOperationsHashMap.containsKey(key)){
            return boundHashOperationsHashMap.get(key);
        }else {
            BoundHashOperations<String, String, String> ops = stringRedisTemplate.boundHashOps(key);
            boundHashOperationsHashMap.put(key, ops);
            if(boundHashOperationsHashMap.size() > 1000){
                int delindex = (int)(Math.random() * 999) + 1;//删除
                int count = 1;
                for(String opskey: boundHashOperationsHashMap.keySet()){
                    if(count == delindex){
                         boundHashOperationsHashMap.remove(opskey);
                         break;
                    }
                    count++;
                }
            }
            return ops;
        }
    }

    //缓存list
    private Map<String,BoundListOperations> boundListOperationsHashMap = new HashMap<String,BoundListOperations>();
    private synchronized BoundListOperations getBoundListOperations(String key){
        if(boundListOperationsHashMap.containsKey(key)){
            return boundListOperationsHashMap.get(key);
        }else {
            BoundListOperations<String, String> ops = stringRedisTemplate.boundListOps(key);
            boundListOperationsHashMap.put(key, ops);
            if(boundListOperationsHashMap.size() > 1000){
                int delindex = (int)(Math.random() * 999) + 1;//删除
                int count = 1;
                for(String opskey: boundListOperationsHashMap.keySet()){
                    if(count == delindex){
                        boundListOperationsHashMap.remove(opskey);
                        break;
                    }
                    count++;
                }
            }
            return ops;
        }
    }

    /**
     * hmset 命令
     * @param key
     * @param field
     * @param value
     */
    public void hmset(String key, String field, String value){
        stringRedisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * hmget 命令
     * @param key
     * @param field
     * @return
     */
    public String hmget(String key, String field){
        BoundHashOperations<String, String, String> ops = getBoundHashOperations(key);
        return ops.get(field);
    }

    /**
     * 删除整个记录
     * @param key
     */
    public void hmdelete(String key){
        stringRedisTemplate.delete(key);
    }

    public String get(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }
    public void set(String key, String value){
        stringRedisTemplate.opsForValue().set(key, value);
    }
    public void delete(String key){
        stringRedisTemplate.delete(key);
    }

    /**
     * 获取所有的字段
     * @param key
     * @return
     */
    public Set<String> getFields(String key){
        BoundHashOperations<String, String, String> ops = getBoundHashOperations(key);
        return ops.keys();
    }

    /**
     * 删除字段
     * @param key
     * @param field
     */
    public void deleteField(String key, String field){
        BoundHashOperations<String, String, String> ops = getBoundHashOperations(key);
        ops.delete(field);
    }

    /**
     * 列表添加项目
     * @param key
     * @param tokenItem
     */
    public void rPoplPushTokenItem(String key, String tokenItem){
        if(stringRedisTemplate.opsForList().size(key) > 10){
            stringRedisTemplate.opsForList().rightPopAndLeftPush(key, tokenItem);
        }
        stringRedisTemplate.opsForList().leftPush(key, tokenItem);
    }

    /**
     * 清空列表
     * @param key
     */
    public void clearList(String key){
        BoundListOperations<String, String> ops = getBoundListOperations(key);
        while (ops.size() > 0){
            ops.leftPop();
        }
    }

    /**
     * 获取token列表
     * @param key
     * @return
     */
    public List<LatestToken> rangTokenBean(String key){
        List<String> jsonList = stringRedisTemplate.opsForList().range(key, 0,-1);
        List<LatestToken> list = new ArrayList<>();
        for(String tokenStr: jsonList){
            list.add(JsonUtil.fromJson(tokenStr, LatestToken.class));
        }
        return list;
    }

    /**
     * 获取token列表的第一个。最新那个
     * @param key
     * @return 一个token bean
     */
    public LatestToken getListItem0(String key){
        String itemStr = getListItem(key, 0);
        if (itemStr == null) throw new MyException(CS.RETURN_CODE_TOKEN_INCORRECT, "找不到token");
        LatestToken latestToken = JsonUtil.fromJson(itemStr, LatestToken.class);
        return latestToken;
    }

    /**
     * 更新list item
     * @param key
     * @param index
     * @param value
     */
    public void listset(String key, long index, String value){
        stringRedisTemplate.opsForList().set(key, index, value);
    }

    public String getListItem(String key, long index) {
        return stringRedisTemplate.opsForList().index(key, index);
    }

    /**
     * 设置定时过期key
     * @param key
     * @param value
     * @param expire_time 过期时间，秒
     */
    public void setExpireKey(String key, String value, int expire_time){
        stringRedisTemplate.boundValueOps(key).set(value, expire_time, TimeUnit.SECONDS);
    }

    /**
     * 获取所有key，支持模糊匹配
     * @param key
     * @return
     */
    public Set<String> getKeys(String key) {
        return stringRedisTemplate.keys(key);
    }

}
