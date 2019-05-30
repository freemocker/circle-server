package com.ubatis.circleserver.util.generator;

import com.ubatis.circleserver.config.GeneratorConfig;
import com.ubatis.circleserver.util.JsonUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenMain {

	private static String JDBC_DRIVER;
	private static String JDBC_URL;
	private static String JDBC_USERNAME;
	private static String JDBC_PASSWORD;
	private static String DATABASE_NAME;
	private static String tableNamePackage;
	private static String tableNamePath;

	// 数据库连接
	private static Connection mConn = null;

	public static String getJavaTypeName(int type) {
		String ret = null;
		switch (type) {
			case Types.TINYINT:
			case Types.SMALLINT:
			case Types.INTEGER:
			case Types.BIGINT:
				ret = "int";
				break;

			case Types.FLOAT:
			case Types.REAL:
			case Types.DOUBLE:
			case Types.NUMERIC:
			case Types.DECIMAL:
				ret = "double";
				break;

			default:
				ret = "String";
				break;
		}
		return ret;
	}

	public static Connection getConnection() throws Exception {

		if (mConn != null && !mConn.isClosed())
			return mConn;

		Class.forName(JDBC_DRIVER);
		mConn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
		return mConn;

	}

	public static List<Map<String, Object>> getAllTables() throws Exception {
		List<Map<String, Object>> tableNameList = new ArrayList<>();
		Statement statement = getConnection().createStatement();
		ResultSet resultSet = null;
		resultSet = statement.executeQuery(" SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.TABLES WHERE table_schema='"+DATABASE_NAME+"' ");
		ResultSetMetaData md = resultSet.getMetaData();
		while (resultSet.next()) {
			Map rowData = new HashMap();
			for (int i = 1; i <= md.getColumnCount(); i++) {
				rowData.put(md.getColumnName(i), resultSet.getObject(i));
			}
			// System.out.println("表："+rowData.get("TABLE_NAME") + " - " + rowData.get("TABLE_COMMENT"));
			tableNameList.add(rowData);
		}
		System.out.println(JsonUtil.toJson(tableNameList));
		return tableNameList;
	}

	// 获取字段
	public static void start(GenConfig genConfig) {

		try {
			List<Map<String, Object>> tables = getAllTables();
			//
			for (Map<String, Object> tableMap : tables) {
				List<GenFieldBean> fieldList = new ArrayList<>();// 一个表的
				Statement statement = getConnection().createStatement();
				ResultSet resultSet = null;
				try {
					resultSet = statement.executeQuery("SELECT * from " + tableMap.get("TABLE_NAME") + " LIMIT 1 ");
				} catch (SQLSyntaxErrorException e) {
					System.out.println(tableMap + "不存在");
					continue;
				}
				ResultSetMetaData metaData = resultSet.getMetaData();
				for (int i = 0; i < metaData.getColumnCount(); i++) {
					// resultSet 数据下标从1开始
					String columnName = metaData.getColumnName(i + 1);
					int type = metaData.getColumnType(i + 1);
					GenFieldBean fieldBean = new GenFieldBean(columnName,	getJavaTypeName(type));
					// System.out.print(" "+columnName + " " + getJavaTypeName(type));
					fieldList.add(fieldBean);
				}
				// 获取一个表
				genClassFiles(genConfig, tableMap.get("TABLE_NAME").toString(), fieldList);
			}
			if (genConfig.isNormal()) {
				// 生成TableName
				genTableName(tables);
			}
		} catch (Exception e) {
			System.out.println("出错");
			e.printStackTrace();
		}

	}

	public static void genClassFiles(GenConfig genConfig, String tablename, List<GenFieldBean> fieldList) {
		String extendClass = genConfig.getExtendClass();
		String className = GenUtil.toCamelName(genConfig.getPrefix() + GenUtil.firstLetterUpperCase(tablename)
				+ genConfig.getSuffix());
		StringBuilder ret = new StringBuilder();
		ret.append("package ").append(genConfig.getPackageName()).append(";").append("\n").append("\n");
		ret.append("import java.io.Serializable;").append("\n");
		if (!genConfig.isNormal()) {
			ret.append("import " + extendClass + ";");
			ret.append("\n");
		}
		ret.append("\n");
		ret.append("public class " + className);

		if (!genConfig.isNormal()) {
			ret.append(" extends " + extendClass.split("\\.")[extendClass.split("\\.").length - 1]);
		}
		ret.append(" implements Serializable {");
		ret.append("\n");
		ret.append("\n");
		ret.append("	// ");
		for (int i = 0; i < fieldList.size(); i++) {
			ret.append((i == 0 ? "":",") + fieldList.get(i).getName());
		}
		ret.append("\n");
		ret.append("    private static final long serialVersionUID = 1L;").append("\n");
		ret.append("\n");
		for (GenFieldBean fieldBean : fieldList) {
			ret.append("    private " + fieldBean.getType() + " " + fieldBean.getName()).append(";").append("\n");
		}
		ret.append("\n");
		ret.append("    //getter");
		ret.append("\n");
		for (GenFieldBean fieldBean : fieldList) {
			ret.append(fieldBean.genGetter());
			ret.append("\n");
		}
		ret.append("\n");
		ret.append("    //setter");
		ret.append("\n");
		for (GenFieldBean fieldBean : fieldList) {
			ret.append(fieldBean.genSetter(genConfig));
			ret.append("\n");
		}
		ret.append("\n");
		//toString()
		ret.append("    @Override").append("\n");
		ret.append("    public String toString() {").append("\n");
		ret.append("        StringBuilder sb = new StringBuilder();").append("\n");
		ret.append("        sb.append(getClass().getSimpleName());").append("\n");
		ret.append("        sb.append(\" [\");").append("\n");
		ret.append("        sb.append(\"Hash = \").append(hashCode());").append("\n");
		for (GenFieldBean fieldBean : fieldList) {
			ret.append("        sb.append(\", "+fieldBean.getName()+"=\").append("+fieldBean.getName()+");").append("\n");
		}
		ret.append("        sb.append(\", serialVersionUID=\").append(serialVersionUID);").append("\n");
		ret.append("        sb.append(\"]\");").append("\n");
		ret.append("        return sb.toString();").append("\n");
		ret.append("    }");

		ret.append("\n");
		ret.append("\n");
		ret.append("}");
		// System.out.println(ret.toString());
		writeFile(genConfig.getOutDir(), className, ret.toString());
	}

	public static void genTableName(List<Map<String, Object>> tableNameList) {
		StringBuilder ret = new StringBuilder();
		ret.append("package ").append(tableNamePackage).append(";").append("\n").append("\n");
		ret.append("\n");
		ret.append("public class TableName {");
		ret.append("\n");
		ret.append("\n");
		for (Map tableNameMap : tableNameList) {
			ret.append("    /** " + tableNameMap.get("TABLE_COMMENT") +" */").append("\n");
			ret.append("    public static final String " + tableNameMap.get("TABLE_NAME").toString().toUpperCase() + " = \"" + tableNameMap.get("TABLE_NAME") + "\";").append("\n");
		}
		ret.append("\n");
		ret.append("}");
		// System.out.println(ret.toString());
		writeFile(tableNamePath, "TableName", ret.toString());
	}

	public static void writeFile(String output, String className, String content) {
		try {
			if (output == null || output.equals("")) {
				output = "gens";
			}
			File file = new File(output + File.separator + className + ".java");
			System.out.println(file.getAbsolutePath());
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsolutePath());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void initAndStart(String jdbc_driver, String jdbc_url, String jdbc_username, String jdbc_password,String database_name, GeneratorConfig generatorConfig) {
		// 初始化配置
		JDBC_DRIVER = jdbc_driver;
		JDBC_URL = jdbc_url;
		JDBC_USERNAME = jdbc_username;
		JDBC_PASSWORD = jdbc_password;
		DATABASE_NAME = database_name;
		tableNamePackage = generatorConfig.getTablename_package();
		tableNamePath = generatorConfig.getTablename_path();
		refreshBeans(generatorConfig);
	}

	/**
	 * 生成beans
	 * @param generatorConfig
	 */
	public static void refreshBeans(GeneratorConfig generatorConfig) {
		// 配置
		GenConfig genConfigNormal = new GenConfig(true
				, generatorConfig.getNormal_package_name()
				, generatorConfig.getNormal_extend_class()
				, generatorConfig.getNormal_prefix()
				, generatorConfig.getNormal_suffix()
				, generatorConfig.getNormal_out_dir());

		GenConfig genConfigParams = new GenConfig(false
				, generatorConfig.getParam_package_name()
				, generatorConfig.getParam_extend_class()
				, generatorConfig.getParam_prefix()
				, generatorConfig.getParam_suffix()
				, generatorConfig.getParam_out_dir());

		start(genConfigNormal);
		start(genConfigParams);
	}

}
