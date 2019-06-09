package com.ubatis.circleserver.bean.basic;

import com.ubatis.circleserver.util.daoutils.BaseDao;
import com.ubatis.circleserver.util.daoutils.MysqlGenerator;

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
    private BaseDao dao;
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

    public void setDao(BaseDao dao) {
        this.dao = dao;
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

    public int save() {
       String SQL = MysqlGenerator.allFieldsInsertSQL(this, BEAN_TABLE_NAME);
       Map<String, Object> params = getParams();
       return dao.insert(SQL, params);
    }

}
