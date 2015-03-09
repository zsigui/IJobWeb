package com.ijob.server.constants;

import java.util.HashMap;

public class GlobalConfig {
	// 默认全局编码
	public static final String DEFAULT_CHARSET = "UTF-8";

	// 数据库字段默认连接信息
	public static final String DEFAULT_DB_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DEFAULT_DB_URL = "jdbc:mysql://127.0.0.1:3306/db_ijob";
	public static final String DEFAULT_DB_USERNAME = "zsigui";
	public static final String DEFAULT_DB_PWD = "db_kaokkyyzz";
	
	public static HashMap<String, String> mapDeseKeys = new HashMap<String, String>();
}
