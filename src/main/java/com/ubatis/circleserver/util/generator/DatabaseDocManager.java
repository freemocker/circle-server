package com.ubatis.circleserver.util.generator;

import com.ubatis.circleserver.modules.common.bean.DocBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseDocManager {

    private final static Logger logger = LoggerFactory.getLogger(DatabaseDocManager.class);

    private static List<Map<String, Object>> convertList(ResultSet rs) throws SQLException{
        List list = new ArrayList();
        ResultSetMetaData md = rs.getMetaData();//获取键名
        int columnCount = md.getColumnCount();//获取行的数量
        while (rs.next()) {
            Map rowData = new HashMap();//声明Map
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));//获取键名及值
            }
            list.add(rowData);
        }
        return list;
    }

    private static String getTableDetailSql(String tablename, String databasename) {
        return "SELECT COLUMN_NAME,COLUMN_COMMENT,DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name='"+tablename+"' AND table_schema='"+databasename+"'";
    }

//    public static void writeFile(String fileName, String content) {
//        try {
//            File file = new File("src" + File.separator
//                    + "main" + File.separator
//                    + "resources" + File.separator
//                    + "static" + File.separator
//                    + "database" + File.separator
//                    + fileName + ".md");
//            System.out.println(file.getAbsolutePath());
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//            FileWriter fw = new FileWriter(file.getAbsolutePath());
//            BufferedWriter bw = new BufferedWriter(fw);
//            bw.write(content);
//            bw.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static ArrayList<DocBean> getDatabaseDoc(Connection connection, String databaseName){
        List<GenFieldBean> fieldList = new ArrayList<>();
        ArrayList<DocBean> docList = new ArrayList<>();
        try {
            final Statement statement = connection.createStatement();
            String SQL_GET_TABLE_INFO_LIST
                    = "SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.TABLES WHERE table_schema='"+databaseName+"'";
            ResultSet resultSet = statement.executeQuery(SQL_GET_TABLE_INFO_LIST);
            List<Map<String, Object>> tableInfoList = convertList(resultSet);
            tableInfoList.stream().forEach( tableMap -> {
                fieldList.clear();
                // logger.info("表名:{} {}", tableMap.get("TABLE_NAME"), tableMap.get("TABLE_COMMENT"));
                String sql = getTableDetailSql(tableMap.get("TABLE_NAME").toString(), databaseName);
                try {
                    ResultSet rs = statement.executeQuery(sql);
                    List<Map<String, Object>> tableInfoDetailList = convertList(rs);
                    tableInfoDetailList.stream().forEach(tableDetailMap -> {
                        // logger.info("字段:{} {}", tableDetailMap.get("COLUMN_NAME"), tableDetailMap.get("COLUMN_COMMENT"));
                        GenFieldBean fieldBean = new GenFieldBean();
                        fieldBean.setName(tableDetailMap.get("COLUMN_NAME").toString());
                        fieldBean.setComment(tableDetailMap.get("COLUMN_COMMENT").toString());
                        fieldBean.setType(tableDetailMap.get("DATA_TYPE").toString());
                        fieldList.add(fieldBean);
                    });
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //
                String content = DocTemperateKt.getDocContent(tableMap.get("TABLE_NAME").toString()
                        , tableMap.get("TABLE_COMMENT").toString()
                        , fieldList);
                DocBean docBean = new DocBean();
                docBean.setParent("/database");
                docBean.setDiretory(false);
                docBean.setName(tableMap.get("TABLE_NAME").toString());
                docBean.setValue(content);
                docList.add(docBean);
                // logger.info("content:\n\r{}", content);
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return docList;
    }

}
