package com.ubatis.circleserver.modules.common.controller;

import com.ubatis.circleserver.bean.basic.JsonBase;
import com.ubatis.circleserver.config.SysConfig;
import com.ubatis.circleserver.modules.common.bean.SystemInfoBean;
import com.ubatis.circleserver.modules.common.dao.CommonDao;
import com.ubatis.circleserver.modules.common.service.CommonService;
import com.ubatis.circleserver.util.DateUtil;
import com.ubatis.circleserver.util.MyCache;
import com.ubatis.circleserver.util.constant.CS;
import com.ubatis.circleserver.util.daoutils.CM;
import com.ubatis.circleserver.util.redis.MyRedisClient;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.*;
import java.time.ZoneId;

/**
 * Created by lance on 2017/10/31.
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    private static Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private SysConfig sysConfig;
    @Autowired
    private MyRedisClient myRedisClient;
    @Autowired
    private CommonDao dao;
    @Autowired
    private CommonService commonService;

    @RequestMapping("/script/run")
    public JsonBase runScript(@RequestParam String fileName, @RequestParam(required = false) String path){
        String command = "./" + fileName;
        ProcessBuilder pb = new ProcessBuilder(command);
        if (StringUtil.isNullOrEmpty(path)) {
            String classPath = ClassUtils.getDefaultClassLoader().getResource("").getPath();
            path = new File(new File(classPath).getParent()).getParent();
        }

        logger.info("path:{}, command:{}", path, command);
        pb.directory(new File(path));
        int runningStatus = 0;
        String s = null;
        try {
            Process p = null;
            p = pb.start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((s = stdInput.readLine()) != null) {

            }
            while ((s = stdError.readLine()) != null) {

            }
            runningStatus = p.waitFor();
        }catch (Exception e){
            e.printStackTrace();
            return CM.getFailInfo(CS.RETURN_CODE_INVALID_PARAMETER, "文件不存在");
        }
        logger.info("runningStatus:{}", runningStatus);
        return CM.getReturnInfo("runningStatus------->"+runningStatus);
    }

    /**
     * 获取服务器时间戳
     * @return
     */
    @RequestMapping("/timestamp")
    public JsonBase getTimestamp(){
        SystemInfoBean systemInfoBean = new SystemInfoBean(ZoneId.systemDefault().getId(), DateUtil.getCurrentTimestamp());
        return CM.getReturnHeader(systemInfoBean);
    }

    /**
     * 手动清理缓存
     * @param name
     * @return
     */
    @RequestMapping("/clearCache/{name}")
    public JsonBase clearCache(@PathVariable String name){
        String ret = null;
        switch (name){
            case "doc":
                MyCache.getInstance().apiDocs = null;
                ret = "清除 doc";
                break;
            case "docKey":
                MyCache.getInstance().docKey = null;
                ret = "清除 docKey";
                break;
            case "database":
                MyCache.getInstance().databaseDocs = null;
                ret = "清除 database doc";
                break;
            default:
                ret = "未定义该缓存";
                break;
        }
        return CM.getReturnInfo(ret);
    }

}
