package com.ubatis.circleserver.util.daoutils;

import com.ubatis.circleserver.bean.basic.MyParams;
import java.util.List;

/** sql 生成器 <br/>
 *  注释掉的是利用反射，但是无法判断基本类型有没传值
 * Created by lance on 16-3-23.
 */
public class MysqlGenerator {

    /**
     * 指定字段插入<br/>
     * 会排除空字段
     *
     * @param params       对象bean(extend Myparams)
     * @param tablename    表名
     * @param fields2input 需要插入的字段，逗号隔开
     * @return 相应sql，nameparameters 类型的。
     */
    public static String specificFieldsInsertSQL(MyParams params, String tablename, String fields2input) {
        String[] inputFields = fields2input.split(",");
        StringBuilder sql = new StringBuilder(" INSERT INTO " + tablename + " ( ");
        StringBuilder sqlfiles = new StringBuilder();
        StringBuilder sqlvalues = new StringBuilder();
        for (int i = 0; i < inputFields.length; i++) {
            if (params.get(inputFields[i].trim()) != null) {
                sqlfiles.append((i == 0 ? "" : ",") + inputFields[i]);
                sqlvalues.append((i == 0 ? "" : ",") + ":" + inputFields[i].trim());
            }
        }
        sql.append(sqlfiles.toString()).append(" ) ");
        sql.append(" VALUES( " + sqlvalues.toString() + " )");
        return sql.toString();
    }

    /**
     * 除外字段插入
     *
     * @param params
     * @param tablename
     * @param fieldsExcept
     * @return
     */
    public static String exceptFieldsInsertSQL(MyParams params, String tablename, String fieldsExcept) {
        List<String> filedsNotNull = params.getKeyList();
        StringBuilder sql = new StringBuilder(" INSERT INTO " + tablename + " ( ");
        StringBuilder sqlfiles = new StringBuilder();
        StringBuilder sqlvalues = new StringBuilder();
        for (int i = 0; i < filedsNotNull.size(); i++) {
            if(!isContainField(filedsNotNull.get(i).trim(), fieldsExcept)){
                sqlfiles.append((i == 0 ? "" : ",") + filedsNotNull.get(i).trim());
                sqlvalues.append((i == 0 ? "" : ",") + ":" + filedsNotNull.get(i).trim());
            }
        }
        sql.append(sqlfiles.toString()).append(" ) ");
        sql.append(" VALUES( " + sqlvalues.toString() + " )");
        return sql.toString();
    }

    /**
     * 全部字段插入sql。
     * 如果用了自增id的话，需要把params的id设置为null
     *
     * @param params
     * @param tablename 表名。
     * @return
     */
    public static String allFieldsInsertSQL(MyParams params, String tablename) {
        List<String> filedsNotNull = params.getKeyList();
        StringBuilder sql = new StringBuilder(" INSERT INTO " + tablename + " ( ");
        StringBuilder sqlfiles = new StringBuilder();
        StringBuilder sqlvalues = new StringBuilder();
        for (int i = 0; i < filedsNotNull.size(); i++) {
            sqlfiles.append((i == 0 ? "" : ",") + filedsNotNull.get(i).trim());
            sqlvalues.append((i == 0 ? "" : ",") + ":" + filedsNotNull.get(i).trim());
        }
        sql.append(sqlfiles.toString()).append(" ) ");
        sql.append(" VALUES( " + sqlvalues.toString() + " )");
        return sql.toString();
    }

    /**
     * 更新所有非空字段
     *
     * @param params
     * @param tablename
     * @param idname
     * @return
     */
    public static String allFieldsUpdateSQL(MyParams params, String tablename, String idname) {
        if(idname.contains(",")){
            return allFieldsUpdateSQL(params, tablename, idname.split(","));
        }
        List<String> filedsNotNull = params.getKeyList();
        StringBuilder sql = new StringBuilder(" UPDATE " + tablename + " SET " + idname + " = :" + idname.trim() + " ");
        for (String filed : filedsNotNull) {
            sql.append("," + filed + " = :" + filed.trim() + " ");
        }
        sql.append(" WHERE " + idname + " = :" + idname.trim() + " ");
        return sql.toString();
    }
    public static String allFieldsUpdateSQL(MyParams params, String tablename, String[] ids) {
        List<String> filedsNotNull = params.getKeyList();
        StringBuilder sql = new StringBuilder(" UPDATE " + tablename + " SET " + ids[0].trim() + " = :" + ids[0].trim() + " ");
        for (String filed : filedsNotNull) {
            sql.append("," + filed + " = :" + filed.trim() + " ");
        }
        sql.append(" WHERE 1 = 1 ");
        for(String id : ids){
            sql.append(" AND " + id.trim() + " = :" + id.trim());
        }
        return sql.toString();
    }

    /**
     * 指定字段更新
     *
     * @param params
     * @param tablename
     * @param idname
     * @param fields4input
     * @return
     */
    public static String specificFieldsUpdateSQL(MyParams params, String tablename, String idname, String fields4input) {
        if(idname.contains(",")){
            return specificFieldsUpdateSQL(params, tablename, idname.split(","), fields4input);
        }
        String[] inputFields = fields4input.split(",");
        StringBuilder sql = new StringBuilder(" UPDATE " + tablename + " SET " + idname + " = :" + idname.trim() + " ");
        for (String filed : inputFields) {
            if (params.get(filed.trim()) != null) {
                sql.append("," + filed.trim() + " = :" + filed.trim() + " ");
            }
        }
        sql.append(" WHERE " + idname + " = :" + idname.trim() + " ");
        return sql.toString();
    }

    public static String specificFieldsUpdateSQL(MyParams params, String tablename, String[] ids, String fields4input) {
        String[] inputFields = fields4input.split(",");
        StringBuilder sql = new StringBuilder(" UPDATE " + tablename + " SET " + ids[0].trim() + " = :" + ids[0].trim() + " ");
        for (String filed : inputFields) {
            if (params.get(filed.trim()) != null) {
                sql.append("," + filed + " = :" + filed.trim() + " ");
            }
        }
        sql.append(" WHERE 1 = 1 ");
        for(String id: ids){
            sql.append(" AND " + id.trim() + " = :" + id.trim());
        }
        return sql.toString();
    }

    /**
     * 除外字段更新
     *
     * @param params
     * @param tablename
     * @param idname
     * @param fieldsExcept
     * @return
     */
    public static String exceptFieldsUpdateSQL(MyParams params, String tablename, String idname, String fieldsExcept) {
        if(idname.contains(",")){
            return exceptFieldsUpdateSQL(params, tablename, idname.split(","), fieldsExcept);
        }
        List<String> filedsNotNull = params.getKeyList();
        StringBuilder sql = new StringBuilder(" UPDATE " + tablename + " SET " + idname + " = :" + idname.trim() + " ");
        for (String filed : filedsNotNull) {
            if (!isContainField(filed, fieldsExcept)) {
                sql.append("," + filed + " = :" + filed.trim() + " ");
            }
        }
        sql.append(" WHERE " + idname + " = :" + idname.trim() + " ");
        return sql.toString();
    }
    public static String exceptFieldsUpdateSQL(MyParams params, String tablename, String[] ids, String fieldsExcept) {
        List<String> filedsNotNull = params.getKeyList();
        StringBuilder sql = new StringBuilder(" UPDATE " + tablename + " SET " + ids[0].trim() + " = :" + ids[0].trim() + " ");
        for (String filed : filedsNotNull) {
            if (!isContainField(filed, fieldsExcept)) {
                sql.append("," + filed + " = :" + filed.trim() + " ");
            }
        }
        sql.append(" WHERE 1 = 1 ");
        for(String id : ids){
            sql.append(" AND " + id.trim() + " = :" + id.trim());
        }
        return sql.toString();
    }

    /**
     * 指定搜索字段
     *
     * @param fieldsout
     * @param tablename
     * @param fields4search
     * @return
     */
    public static String specificFieldsSearchSQL(String fieldsout, String tablename, String fields4search) {
        StringBuilder sql = new StringBuilder(" SELECT " + fieldsout + " FROM " + tablename + " WHERE 1=1 AND ( ");
        String[] fields = fields4search.split(",");
        for (int i = 0; i < fields.length; i++) {
            sql.append((i == 0 ? " " : " OR ") + fields[i].trim() + " LIKE :content ");
        }
        sql.append(" ) ");
        return sql.toString();
    }

    /**
     * 删除语句
     *
     * @param tablename
     * @param ids4del
     * @return
     */
    public static String deleteSQL(String tablename, String ids4del) {
        String[] ids = ids4del.split(",");
        if(ids.length == 0) return "";
        StringBuilder sql = new StringBuilder(" DELETE FROM " + tablename + " WHERE " + ids[0] + " = :" + ids[0].trim() + " ");
        for (int i = 1; i < ids.length; i++) {
            sql.append(" AND ").append(ids[i]).append(" = ").append(":").append(ids[i].trim());
        }
        return  sql.toString();
    }

    /**
     * 判断是否包含指定字段
     * @param testField
     * @param allFields
     * @return
     */
    public static boolean isContainField(String testField, String allFields){
        boolean isContain = false;
        String[] fields = allFields.split(",");
        for(String field: fields){
            if(field.equals(testField)){
                isContain = true;
                break;
            }
        }
        return isContain;
    }

}
