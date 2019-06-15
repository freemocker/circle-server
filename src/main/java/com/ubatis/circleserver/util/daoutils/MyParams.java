package com.ubatis.circleserver.util.daoutils;

import com.ubatis.circleserver.util.SpringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lance on 2017/10/19.
 */
public class MyParams {

    public MyParams() {
    }

    public MyParams(int page, int pagesize) {
        this.page = page;
        this.pagesize = pagesize;
    }

    public MyParams(Object pageStr, Object pagesizeStr) {
        if (pageStr == null || pagesizeStr == null) return;
        int page;
        int pagesize;
        try {
            page = Integer.parseInt(pageStr.toString());
        } catch (NumberFormatException e) {
            page = Page.DEFAULT_CURRENT_PAGE_NUM;
        }
        try {
            pagesize = Integer.parseInt(pagesizeStr.toString());
        } catch (NumberFormatException e) {
            pagesize = Page.DEFAULT_PAGE_SIZE;
        }
        this.page = page;
        this.pagesize = pagesize;
    }

    private int page;
    private int pagesize;
    private BaseDao dao = (BaseDao) SpringUtil.getBean("baseDao");
    public String BEAN_TABLE_NAME;

    // 存参数键值对
    private Map<String, Object> params = new HashMap<>();
    //非空的字段
    private List<String> keyList;
    private List<Object> valueList;//值

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public BaseDao getDao() {
        return dao;
    }

    public long getGenId() {
        return this.dao.getID();
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void setKeyList(List<String> keyList) {
        this.keyList = keyList;
    }

    public void setValueList(List<Object> valueList) {
        this.valueList = valueList;
    }

    /**
     * 返回值非空的key
     */
    public List<String> getKeyList() {
        if (keyList == null) {
            keyList = new ArrayList<String>();
        }
        keyList.clear();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if(this.getParams().get(entry.getKey()) != null){
                keyList.add(entry.getKey().trim());
            }
        }
        return keyList;
    }

    /**
     * 返回非空的value
     */
    public Object[] getValueList() {
        if (valueList == null) {
            valueList = new ArrayList<Object>();
        }
        valueList.clear();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if(this.getParams().get(entry.getKey()) != null){
                valueList.add(entry.getValue());
            }
        }
        return valueList.toArray();
    }

    /**
     * set 值
     */
    public void put(String key, Object value) {
        if (value == null) return;
        params.put(key, value);
    }

    /**
     * 取值
     */
    public Object get(String key) {
        return params.get(key);
    }

    //获取页码
    public Page getInputPage() {
        return new Page(this.page, this.pagesize);
    }

    //获取页码
    public Page getOffsetPage() {
        return new Page((this.page - 1) * this.pagesize, this.pagesize);
    }

    // DB tools

    /**
     * 指定字段插入<br/>
     * 会排除空字段
     *
     * @param params       对象bean(extend Myparams)
     * @param tablename    表名
     * @param fields2input 需要插入的字段，逗号隔开
     * @return 相应sql，nameparameters 类型的。
     */
    public String designateFieldsInsertSQL(MyParams params, String tablename, String fields2input) {
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
    public String exceptFieldsInsertSQL(MyParams params, String tablename, String fieldsExcept) {
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
    public String allFieldsInsertSQL(MyParams params, String tablename) {
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
    public String allFieldsUpdateSQL(MyParams params, String tablename, String idname) {
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
    public String allFieldsUpdateSQL(MyParams params, String tablename, String[] ids) {
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
    public String allFieldsUpdateSQL(MyParams params, String tablename) {
        return allFieldsUpdateSQL(params, tablename, getFirstFieldName(params));
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
    public String designateFieldsUpdateSQL(MyParams params, String tablename, String idname, String fields4input) {
        if(idname.contains(",")){
            return designateFieldsUpdateSQL(params, tablename, idname.split(","), fields4input);
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

    public String designateFieldsUpdateSQL(MyParams params, String tablename, String[] ids, String fields4input) {
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

    public String designateFieldsUpdateSQL(MyParams params, String tablename, String fields4input) {
        return designateFieldsUpdateSQL(params, tablename, getFirstFieldName(params), fields4input);
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
    public String exceptFieldsUpdateSQL(MyParams params, String tablename, String idname, String fieldsExcept) {
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
    public String exceptFieldsUpdateSQL(MyParams params, String tablename, String[] ids, String fieldsExcept) {
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
    public String exceptFieldsUpdateSQL(MyParams params, String tablename, String fieldsExcept) {
        return exceptFieldsUpdateSQL(params, tablename, getFirstFieldName(params), fieldsExcept);
    }

    /**
     * 指定搜索字段
     *
     * @param fieldsout
     * @param tablename
     * @param fields4search
     * @return
     */
    public String designateFieldsSearchSQL(String fieldsout, String tablename, String fields4search) {
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
     * @param params
     * @param ids4del
     * @return
     */
    public String deleteSQL(MyParams params, String ids4del) {
        String[] ids = ids4del.split(",");
        if(ids.length == 0) return "";
        StringBuilder sql = new StringBuilder(" DELETE FROM " + params.BEAN_TABLE_NAME + " WHERE " + ids[0] + " = :" + ids[0].trim() + " ");
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
    public boolean isContainField(String testField, String allFields){
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

    /**
     * 获取第一个私油字段，认为是id
     * @param params
     * @return
     */
    public String getFirstFieldName(MyParams params) {
        for(Field field: params.getClass().getDeclaredFields()) {
            // private 且 非 static字段
            if (Modifier.isPrivate(field.getModifiers()) && !Modifier.isStatic(field.getModifiers())) {
                return field.getName();
            }
        }
        return "id";
    }

    // DB operation

    // ============ SAVE ===========
    public int save() {
       return dao.insert(allFieldsInsertSQL(this, BEAN_TABLE_NAME), getParams());
    }

    public int saveExceptField(String exceptFields) {
        return dao.insert(exceptFieldsInsertSQL(this, BEAN_TABLE_NAME, exceptFields), getParams());
    }

    public int saveDesignateField(String designateFields) {
        return dao.insert(exceptFieldsInsertSQL(this, BEAN_TABLE_NAME, designateFields), getParams());
    }

    public int saveAndGetId(String idname) {
       return dao.insertAndGetId(allFieldsInsertSQL(this, BEAN_TABLE_NAME), getParams(), idname);
    }

    // ============ UPDATE ===========
    public int update() {
        return dao.update(allFieldsUpdateSQL(this, BEAN_TABLE_NAME), getParams());
    }
    public int update(String idname) {
        return dao.update(allFieldsUpdateSQL(this, BEAN_TABLE_NAME, idname), getParams());
    }
    public int update(String[] idNames) {
        return dao.update(allFieldsUpdateSQL(this, BEAN_TABLE_NAME, idNames), getParams());
    }
    public int updateExceptField(String exceptFields) {
        return dao.update(exceptFieldsUpdateSQL(this, BEAN_TABLE_NAME, exceptFields), getParams());
    }
    public int updateExceptField(String idname, String exceptFields) {
        return dao.update(exceptFieldsUpdateSQL(this, BEAN_TABLE_NAME, idname, exceptFields), getParams());
    }
    public int updateExceptField(String[] idNames, String exceptFields) {
        return dao.update(exceptFieldsUpdateSQL(this, BEAN_TABLE_NAME, idNames, exceptFields), getParams());
    }
    public int updateDesignateField(String designateFields) {
        return dao.update(designateFieldsUpdateSQL(this, BEAN_TABLE_NAME, designateFields), getParams());
    }
    public int updateDesignateField(String idname, String designateFields) {
        return dao.update(designateFieldsUpdateSQL(this, BEAN_TABLE_NAME, idname, designateFields), getParams());
    }
    public int updateDesignateField(String[] idNames, String designateFields) {
        return dao.update(designateFieldsUpdateSQL(this, BEAN_TABLE_NAME, idNames, designateFields), getParams());
    }

    // ============ DELETE ===========
    public int delete() {
        return dao.delete(deleteSQL(this, getFirstFieldName(this)));
    }
    public int delete(String id4delete) {
        return dao.delete(deleteSQL(this, id4delete), params);
    }

    // ============ BATCH ===========


}
