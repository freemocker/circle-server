package com.ubatis.circleserver.util.daoutils;

import com.ubatis.circleserver.bean.basic.Page;
import com.ubatis.circleserver.exception.MyException;
import com.ubatis.circleserver.util.common.CS;
import com.zaxxer.hikari.HikariDataSource;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * mysql专用分页
 * spring jdbc temperate
 * //map to object
 * //List<UserBean> userlist = JsonParser.getListFromHashMapList(dataPage.getPageDatas(), UserBean[].class);
 *
 * @param <T> get返回对象。用Gson
 */
public class BaseDao<T> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Autowired
    private HikariDataSource dataSource;

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public int getID(String table_name){
        if (StringUtil.isNullOrEmpty(table_name)) throw new MyException(CS.INVALID_PARAMS, "tableName为空");
        int id = queryForInt(" SELECT get_id('"+table_name.trim()+"') ");
        if(id == 0) throw new MyException(CS.INVALID_PARAMS, "没有此table_name->" + table_name);
        return id;
    }
    // uuid
    public String getUUID() {
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * @param sql
     * @param params
     * @return
     */
    public int insert(String sql, Map<String, Object> params) {
        return this.getNamedParameterJdbcTemplate().update(sql, params);
    }
    public int insert(String sql, Map<String, Object> params, String idname) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(params);
        getNamedParameterJdbcTemplate().update(sql,paramSource, keyHolder, new String[]{idname});
        return keyHolder.getKey().intValue();
    }

    /**
     * @param sql
     * @param values
     * @param types  Types.VARCHAR
     * @return
     */
    public int insert(String sql, Object[] values, int[] types) {
        return this.getJdbcTemplate().update(sql, values, types);
    }

    /**
     * @param sql
     * @return
     */
    public int insert(String sql) {
        return this.getJdbcTemplate().update(sql);
    }

    /**
     * @param sql
     * @param params
     * @return
     */
    public int update(String sql, Map<String, Object> params) {
        return this.getNamedParameterJdbcTemplate().update(sql, params);
    }

    //
    public int update(String sql, Object[] values, int[] types) {
        return this.getJdbcTemplate().update(sql, values, types);
    }

    /**
     * @param sql
     * @return
     */
    public int update(String sql) {
        return this.getJdbcTemplate().update(sql);
    }

    /**
     * @param sql
     * @param params
     * @return
     */
    public int delete(String sql, Map<String, Object> params) {
        return this.getNamedParameterJdbcTemplate().update(sql, params);
    }

    public int delete(String sql, Object[] values, int[] types) {
        return this.getJdbcTemplate().update(sql, values, types);
    }

    /**
     * @param sql
     * @return
     */
    public int delete(String sql) {
        return this.getJdbcTemplate().update(sql);
    }

    /**
     * @param sql
     * @param params
     * @return
     */
    public List<Map<String, Object>> queryForList(String sql, Map<String, Object> params) {
        return this.getNamedParameterJdbcTemplate().queryForList(sql, params);
    }

    /**
     * 根据绑定变量及其绑定变量类型语句查询数据，返回格式为一个List，<br />
     * 其中包含若干个以数据库字段名作为key,字段值作为value的Map,一行数据被封装成一个Map
     *
     * @param sql        要执行的包含绑定变量的sql语句
     * @param values     绑定变量值数组
     * @param valueTypes 绑定变量类型数组,eg:Types.VARCHAR等
     * @return 包含每行一个Map的List
     */
    public List<Map<String, Object>> queryForList(String sql, Object[] values, int[] valueTypes) {
        return this.getJdbcTemplate().queryForList(sql, values, valueTypes);
    }

    /**
     * @param sql
     * @return
     */
    public List<Map<String, Object>> queryForList(String sql) {
        return this.getJdbcTemplate().queryForList(sql);
    }

    //queryforlist by rowmapper
    //

    /**
     * @param sql
     * @param params
     * @return
     */
    public Map<String, Object> queryForMap(String sql, Map<String, Object> params) {
        try {
            return this.getNamedParameterJdbcTemplate().queryForMap(sql, params);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据绑定变量及其绑定变量类型语句查询数据，返回单一结果值，<br />
     * (<b><font color="red">注意，一定要保证只返回一行记录，否则出错</font></b>)<br />
     * 类型为Map，以字段名作为Map的key,字段值作为value,<br />
     * 可用于根据主键等唯一结果查询数据<br />
     *
     * @param sql        要执行的包含绑定变量的sql语句
     * @param values     绑定变量值数组
     * @param valueTypes 绑定变量类型数组
     * @return 以字段名作为key，字段值作为value的Map
     */
    public Map<String, Object> queryForMap(String sql, Object[] values,int[] valueTypes) {
        try {
            return this.getJdbcTemplate().queryForMap(sql, values, valueTypes);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param sql
     * @return
     */
    public Map<String, Object> queryForMap(String sql) {
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询数据，返回单一结果值<br />
     * (<b><font color="red">注意，一定要保证只返回一个值，否则出错</font></b>)，<br />
     * 类型为int，将查询出来的结果转换成int，类型转换不成功则抛异常<br />
     * 可用于根据主键等唯一结果查询数据
     *
     * @param sql 要执行的sql语句
     * @return 整形值
     */
    public int queryForInt(String sql) {
        try {
            return this.getJdbcTemplate().queryForObject(sql, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int queryForInt(String sql, Map<String, Object> params) {
        try {
            return this.getNamedParameterJdbcTemplate().queryForObject(sql, params, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 查询数据，返回单一结果值<br />
     * (<b><font color="red">注意，一定要保证只返回一个值，否则出错</font></b>)，<br />
     * 类型为int，将查询出来的结果转换成int，类型转换不成功则抛异常<br />
     * 可用于根据主键等唯一结果查询数据
     *
     * @param sql 要执行的sql语句
     * @return 长整形值
     */
    public long queryForLong(String sql) {
        try {
            return this.getJdbcTemplate().queryForObject(sql, Long.class);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public long queryForLong(String sql, Map<String, Object> params) {
        try {
            return this.getNamedParameterJdbcTemplate().queryForObject(sql, params, Long.class);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public Map<String, Object> queryForObject(String sql, Map<String, Object> params) {
        try {
            return this.queryForList(sql, params).get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> queryForObject(String sql) {
        try {
            return this.queryForList(sql).get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 得到分页sql
     *
     * @param sql            要执行的sql
     * @param currentPageNum 开始
     * @param pageSize       每页显示记录数
     * @return 分页sql
     */
    public String getPageSql(String sql, int currentPageNum, int pageSize) {

        StringBuilder pageSql = new StringBuilder();

        int min = (currentPageNum - 1) * pageSize;// 查询起始行

        pageSql.append(" SELECT * FROM (");
        pageSql.append(sql);
        pageSql.append(" ) PAGE ");
        pageSql.append(" LIMIT " + min + "," + pageSize);

        return pageSql.toString();
    }

    /**
     * 得到记录总数
     *
     * @param sql 要查询的sql
     * @return 总记录数
     */
    public int getToealRecords(String sql) {
        String countSql = "select count(1) from (" + sql + ") C ";
        return this.queryForInt(countSql);
    }

    public int getToealRecords(String sql, Map<String, Object> params) {
        String countSql = "select count(1) from (" + sql + ") C ";
        return this.queryForInt(countSql, params);
    }

    @SuppressWarnings("rawtypes")
    public Page queryForPage(String sql, Page page, Map<String, Object> params) {
        int totalRecords = this.getToealRecords(sql, params);
        int currentPageNum = page.getCurrentPageNum();
        page.setTotalRecords(totalRecords);
        int totalPages = page.getTotalPages();
        currentPageNum = currentPageNum > totalPages ? totalPages : currentPageNum;
        page.setCurrentPageNum(currentPageNum);
        String pageSql = this.getPageSql(sql, page.getCurrentPageNum(),page.getPageSize());
        List pagaDatas = this.queryForList(pageSql, params);
        // 设置分页相关值
        page.setPageDatas(pagaDatas);
        page.setTotalRecords(totalRecords);
        page.setTotalPages(totalPages);
        return page;
    }

    // 如果sql带参数会报错
    public Page queryForPage(String sql, Page page) {
        int totalRecords = this.getToealRecords(sql);
        int currentPageNum = page.getCurrentPageNum();
        page.setTotalRecords(totalRecords);
        int totalPages = page.getTotalPages();
        currentPageNum = currentPageNum > totalPages ? totalPages : currentPageNum;
        page.setCurrentPageNum(currentPageNum);
        String pageSql = this.getPageSql(sql, page.getCurrentPageNum(),
                page.getPageSize());
        List pagaDatas = this.queryForList(pageSql);
        // 设置分页相关值
        page.setPageDatas(pagaDatas);
        page.setTotalRecords(totalRecords);
        page.setTotalPages(totalPages);
        return page;
    }

    public Page queryForPage(String sql, Page page, Object[] values, int[] types) {
        int totalRecords = this.getToealRecords(sql);
        int currentPageNum = page.getCurrentPageNum();
        page.setTotalRecords(totalRecords);
        int totalPages = page.getTotalPages();
        currentPageNum = currentPageNum > totalPages ? totalPages : currentPageNum;
        page.setCurrentPageNum(currentPageNum);
        String pageSql = this.getPageSql(sql, page.getCurrentPageNum(), page.getPageSize());
        List pagaDatas = this.queryForList(pageSql);
        // 设置分页相关值
        page.setPageDatas(pagaDatas);
        page.setTotalRecords(totalRecords);
        page.setTotalPages(totalPages);
        return page;
    }

    /**
     * 执行数据定义操作，比如建表
     *
     * @param sql 要执行的数据定义操作DDL语句
     * @throws Exception
     */
    public void ddl(String sql) {
        this.getJdbcTemplate().execute(sql);
    }

    /**
     * 批量更新数据（增，删，改）
     *
     * @param sql 要进行批量更新的sql语句数组
     * @return 影响行数
     */

    public int batchUpdate(String[] sql) {
        int[] batchResult = this.getJdbcTemplate().batchUpdate(sql);

        int affectRows = 0;
        for (int i = 0; i < batchResult.length; i++) {
            if (batchResult[i] > 0
                    || batchResult[i] == PreparedStatement.SUCCESS_NO_INFO)
                affectRows += 1;
        }

        return affectRows;
    }

    /**
     * 批量更新数据（增，删，改）
     *
     * @param sql  要进行批量更新的sql语句
     * @param bpss 绑定变量参数
     * @return 影响行数
     */

    public int batchUpdate(String sql, BatchPreparedStatementSetter bpss) {
        int[] batchResult = this.getJdbcTemplate().batchUpdate(sql, bpss);

        int affectRows = 0;
        for (int i = 0; i < batchResult.length; i++) {
            if (batchResult[i] > 0
                    || batchResult[i] == PreparedStatement.SUCCESS_NO_INFO)
                affectRows += 1;
        }

        return affectRows;
    }


    public int batchUpdate(String sql, Map<String, Object>[] params) {
        int[] batchResult = this.getNamedParameterJdbcTemplate().batchUpdate(sql, params);

        int affectRows = 0;
        for (int i = 0; i < batchResult.length; i++) {
            if (batchResult[i] > 0
                    || batchResult[i] == PreparedStatement.SUCCESS_NO_INFO)
                affectRows += 1;
        }

        return affectRows;
    }

    /**
     * TODO 未完成。
     * 执行带参数的存储过程并返回结果。
     * https://blog.csdn.net/qq_27888773/article/details/78493537
     * @param SQL call 存储过程语句(?,?,?),index从1开始
     * @param retNumber 结果数量
     * @param args 参数。和SQL里的问号对应
     * @return
     */
    public Object[] callStoredProceduresGetResults(String SQL, int retNumber, Object... args){
        CallableStatement statement = null;
        String[] rets = new String[retNumber];
        try {
            statement = getDataSource().getConnection().prepareCall(SQL);
            for (int i = 0; i < args.length; i++) {
                statement.setObject((i+1), args[i]);
            }
            int paramMaxindex = args.length - retNumber;
            for (int i = 0; i < paramMaxindex; i++) {
                statement.registerOutParameter(2, Types.VARCHAR);
            }
            statement.execute();
            for (int i = 0; i < paramMaxindex; i++) {
//                rets[]
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}