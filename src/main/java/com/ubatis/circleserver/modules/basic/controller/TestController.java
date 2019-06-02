package com.ubatis.circleserver.modules.basic.controller;

import com.ubatis.circleserver.bean.AcCircleBean;
import com.ubatis.circleserver.bean.basic.JsonBase;
import com.ubatis.circleserver.config.SysConfig;
import com.ubatis.circleserver.modules.basic.bean.SystemInfoBean;
import com.ubatis.circleserver.modules.basic.dao.CommonDao;
import com.ubatis.circleserver.modules.basic.service.CommonService;
import com.ubatis.circleserver.util.DateUtil;
import com.ubatis.circleserver.util.JsonUtil;
import com.ubatis.circleserver.util.MyCache;
import com.ubatis.circleserver.util.common.CM;
import com.ubatis.circleserver.util.common.CS;
import com.ubatis.circleserver.util.redis.MyRedisClient;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lance on 2017/10/31.
 */
@RestController
@RequestMapping("/test")
public class TestController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private CommonDao commonDao;

    @RequestMapping("/t")
    public JsonBase t(){
        String sql = " select id,circle_code,circle_name,gmt_create,gmt_update from ac_circle ";
        List<Map<String, Object>> list = commonDao.queryForList(sql);
//        List<AcCircleBean> list2 = commonDao.getNamedParameterJdbcTemplate().queryForList(sql, new HashMap<>(),);
//        List<Map<String, Object>> list2 = commonDao.getJdbcTemplate().queryForList(sql, new BeanPropertyRowMapper(AcCircleBean.class));
        List<AcCircleBean> list2 = commonDao.queryForList(sql, new BeanPropertyRowMapper(AcCircleBean.class));
//        return CM.getSuccessMsg(JsonUtil.getListFromHashMapList(list, AcCircleBean[].class), "aa");
        logger.info("date:{}", list2.get(0).getGmt_create());
        logger.error("e date:{}", list2.get(0).getGmt_create());
        logger.info("id:{}", commonDao.getID());
        return CM.getSuccessMsg(list2, "");
    }

}
