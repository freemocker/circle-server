package com.ubatis.circleserver.modules.basic.controller;

import com.ubatis.circleserver.bean.basic.JsonBase;
import com.ubatis.circleserver.modules.basic.bean.DocBean;
import com.ubatis.circleserver.modules.basic.dao.CommonDao;
import com.ubatis.circleserver.util.MyCache;
import com.ubatis.circleserver.util.constant.CS;
import com.ubatis.circleserver.util.daoutils.CM;
import com.ubatis.circleserver.util.constant.TableName;
import com.ubatis.circleserver.util.generator.DatabaseDocManager;
import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by lance on 2017/10/20.
 */
@RestController
@RequestMapping("/doc")
public class APIDocController {

    private static Logger logger = LoggerFactory.getLogger(APIDocController.class);

    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private CommonDao commonDao;

    // 数据库名
    @Value("${database.name}")
    private String database_name;

    /**
     * 获取文档
     * @param key 秘钥
     * @return
     */
    @RequestMapping("/api")
    public JsonBase getApiDocList(@RequestParam String key){
        if (MyCache.getInstance().docKey == null) {
            MyCache.getInstance().docKey = (String) commonDao.queryForMap(" select config_value from " + TableName.SYS_CONFIG + " where config_name = 'DOC_KEY' ").get("config_value");
        }
        logger.info("mDocKey :{}", MyCache.getInstance().docKey);
        if (!MyCache.getInstance().docKey.equals(key)) return CM.getFailInfo(CS.RETURN_CODE_PERMISSION_DENIED, "没有权限");
        if(MyCache.getInstance().apiDocs != null && MyCache.getInstance().apiDocs.size() > 0){
            return CM.getSuccessMsg(MyCache.getInstance().apiDocs,"api文档（缓存）");
        }
        //
        Resource resource  = resourceLoader.getResource("classpath:/static/md");
        try {
            MyCache.getInstance().apiDocs = getAPIDoc(resource.getFile(), "/md");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CM.getSuccessMsg(MyCache.getInstance().apiDocs,"api文档.");
    }

    @RequestMapping("/database")
    public JsonBase getDatabaseList(@RequestParam String key){
        if (MyCache.getInstance().databaseDocKey == null) {
            MyCache.getInstance().databaseDocKey = (String) commonDao.queryForMap(" select config_value from " + TableName.SYS_CONFIG + " where config_name = 'DATABASE_DOC_KEY' ").get("config_value");
        }
        if (!MyCache.getInstance().databaseDocKey.equals(key)) return CM.getFailInfo(CS.RETURN_CODE_PERMISSION_DENIED, "没有权限");
        if(MyCache.getInstance().databaseDocs != null && MyCache.getInstance().databaseDocs.size() > 0){
            return CM.getSuccessMsg(MyCache.getInstance().databaseDocs,"数据库文档（缓存）");
        }
        MyCache.getInstance().databaseDocs = getDatabaseDoc();
        return CM.getSuccessMsg(MyCache.getInstance().databaseDocs,"数据库文档.");
    }

    private ArrayList<DocBean> getDatabaseDoc() {
        try {
            return DatabaseDocManager.getDatabaseDoc(commonDao.getDataSource().getConnection(), database_name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<DocBean> getAPIDoc(File file, String parent){
        ArrayList<DocBean> apiDocs = new ArrayList<>();
        File[] files = file.listFiles();
        if(files != null && files.length > 0){
            Arrays.sort(files);
            for(File subfile: files){
                DocBean docBean = new DocBean();
                //Debugger.so("subfile", subfile.getName());
                //Debugger.so("filename encoding", System.getProperty("sun.jnu.encoding"));
                if(subfile.isDirectory()){
                    docBean.setDiretory(true);
                    docBean.setParent(parent);
                    docBean.setName(subfile.getName());
                    docBean.setMdList(getAPIDoc(subfile, parent + "/" + subfile.getName()));

                }else {
                    docBean.setDiretory(false);
                    docBean.setParent(parent);
                    docBean.setName(subfile.getName());
                    docBean.setMdList(getAPIDoc(subfile, parent + "/" + subfile.getName()));
                    docBean.setValue(getContentFromFile(subfile));
                }
                apiDocs.add(docBean);
            }
        }
        return apiDocs;
    }

    private String getContentFromFile(File file){
        try {
            return FileUtil.readAsString(file);
        } catch (IOException e) {
            e.printStackTrace();
            return "# 文档读取失败"+e.toString();
        }
    }

}
