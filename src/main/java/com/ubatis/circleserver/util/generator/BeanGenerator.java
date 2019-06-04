package com.ubatis.circleserver.util.generator;

import com.ubatis.circleserver.config.GeneratorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BeanGenerator {

	@Value("${spring.datasource.hikari.driver-class-name}")
	private String JDBC_DRIVER;
	@Value("${spring.datasource.url}")
	private String JDBC_URL;
	@Value("${spring.datasource.hikari.username}")
	private String JDBC_USERNAME;
	@Value("${spring.datasource.hikari.password}")
	private String JDBC_PASSWORD;
	@Value("${database.name}")
	private String DATABASE_NAME;
	@Value("${gen-config.tablename_package}")
	private String TABLE_NAME_PAKCAGE;
	@Value("${gen-config.tablename_path}")
	private String TABLE_NAME_PATH;

	@Value("${gen-config.normal_package_name}")
	private String normal_package_name;
	@Value("${gen-config.normal_extend_class}")
	private String normal_extend_class;
	@Value("${gen-config.normal_prefix}")
	private String normal_prefix;
	@Value("${gen-config.normal_suffix}")
	private String normal_suffix;
	@Value("${gen-config.normal_out_dir}")
	private String normal_out_dir;
	@Value("${gen-config.param_package_name}")
	private String param_package_name;
	@Value("${gen-config.param_extend_class}")
	private String param_extend_class;
	@Value("${gen-config.param_prefix}")
	private String param_prefix;
	@Value("${gen-config.param_suffix}")
	private String param_suffix;
	@Value("${gen-config.param_out_dir}")
	private String param_out_dir;
	@Value("${gen-config.tablename_package}")
	private String tablename_package;
	@Value("${gen-config.tablename_path}")
	private String tablename_path;



	// 数据库连接
	private Connection mConn = null;

	private String getJavaTypeName(int type) {
		String ret = null;
		switch (type) {
			case Types.TINYINT:
			case Types.SMALLINT:
			case Types.INTEGER:
				ret = "int";
				break;

			case Types.BIGINT:
				ret = "long";
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

	private String getJavaTypeName(String typeName) {
		// System.out.println("typeName = " + typeName);
		String ret = null;
		switch (typeName) {
			case "INT":
			case "TINYINT":
			case "SMALLINT":
			case "INTEGER":
				ret = "int";
				break;

			case "BIGINT":
				ret = "long";
				break;

			case "FLOAT":
			case "REAL":
			case "DOUBLE":
			case "NUMERIC":
			case "DECIMAL":
				ret = "double";
				break;

			default:
				ret = "String";
				break;
		}
		return ret;
	}

	private Connection getConnection() throws Exception {

		if (mConn != null && !mConn.isClosed())
			return mConn;

		Class.forName(JDBC_DRIVER);
		mConn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
		return mConn;

	}

	private List<Map<String, Object>> getAllTables() throws Exception {
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
		// System.out.println(JsonUtil.toJson(tableNameList));
		return tableNameList;
	}

	// 获取字段
	private void start(GenConfig genConfig) {

		try {
			List<Map<String, Object>> tables = getAllTables();
			//
			for (Map<String, Object> tableMap : tables) {
				List<GenFieldBean> fieldList = new ArrayList<>();// 一个表的
				ResultSet resultSet = null;
				try {
					resultSet = getConnection().getMetaData().getColumns(null, getSchema(),tableMap.get("TABLE_NAME").toString(), "%");
				} catch (SQLSyntaxErrorException e) {
					System.out.println(tableMap.get("TABLE_NAME") + "不存在");
					continue;
				}
				while (resultSet.next()) {
					GenFieldBean fieldBean = new GenFieldBean(resultSet.getString("COLUMN_NAME"), resultSet.getString("REMARKS"), getJavaTypeName(resultSet.getString("TYPE_NAME")));
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

	private String getSchema() throws Exception {
		String schema;
		schema = getConnection().getMetaData().getUserName();
		if ((schema == null) || (schema.length() == 0)) {
			throw new Exception("ORACLE数据库模式不允许为空");
		}
		return schema.toUpperCase().toString();
	}

	private void genClassFiles(GenConfig genConfig, String tablename, List<GenFieldBean> fieldList) {
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
			ret.append("    /** " + fieldBean.getComment() + " */ ").append("\n");
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

	private void genTableName(List<Map<String, Object>> tableNameList) {
		StringBuilder ret = new StringBuilder();
		ret.append("package ").append(TABLE_NAME_PAKCAGE).append(";").append("\n").append("\n");
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
		writeFile(TABLE_NAME_PATH, "TableName", ret.toString());
	}

	private void writeFile(String output, String className, String content) {
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

	/**
	 * 生成beans
	 */
	public void initAndStart() {
		GeneratorConfig generatorConfig = new GeneratorConfig(
				normal_package_name,
				normal_extend_class,
				normal_prefix,
				normal_suffix,
				normal_out_dir,
				param_package_name,
				param_extend_class,
				param_prefix,
				param_suffix,
				param_out_dir,
				tablename_package,
				tablename_path
		);
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