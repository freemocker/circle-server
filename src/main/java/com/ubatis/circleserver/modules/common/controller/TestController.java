package com.ubatis.circleserver.modules.common.controller;

import com.ubatis.circleserver.bean.AcCircleBean;
import com.ubatis.circleserver.bean.basic.JsonBase;
import com.ubatis.circleserver.util.daoutils.BaseDao;
import com.ubatis.circleserver.util.daoutils.CM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private BaseDao dao;

    @RequestMapping("/t")
    public JsonBase t(){
        String sql = " select id,circle_code,circle_name,gmt_create,gmt_update from ac_circle ";
        List<Map<String, Object>> list = dao.queryForList(sql);
//        List<AcCircleBean> list2 = commonDao.getNamedParameterJdbcTemplate().queryForList(sql, new HashMap<>(),);
//        List<Map<String, Object>> list2 = commonDao.getJdbcTemplate().queryForList(sql, new BeanPropertyRowMapper(AcCircleBean.class));
        List<AcCircleBean> list2 = dao.queryForList(sql, new BeanPropertyRowMapper(AcCircleBean.class));
//        return CM.getSuccessMsg(JsonUtil.getListFromHashMapList(list, AcCircleBean[].class), "aa");
        logger.info("date:{}", list2.get(0).getGmt_create());
        logger.error("e date:{}", list2.get(0).getGmt_create());
        logger.info("id:{}", dao.getID());
        return CM.getSuccessMsg(list2, "");
    }

    @RequestMapping("/p")
    public JsonBase p(){
        return null;
    }

}
