package com.ijob.server.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ijob.server.constants.GlobalConfig;

public class DBUtils {

	private static Connection mConn;
	
	public static void initOrOpenConn() throws ClassNotFoundException, SQLException {
		if (mConn == null || mConn.isClosed()) {
			Class.forName(GlobalConfig.DEFAULT_DB_DRIVER);
			mConn = DriverManager.getConnection(GlobalConfig.DEFAULT_DB_URL,
					GlobalConfig.DEFAULT_DB_USERNAME, GlobalConfig.DEFAULT_DB_PWD);
		}
	}
	
	public static void closeConn() throws SQLException {
		if (mConn != null && !mConn.isClosed()) {
			mConn.close();
			mConn = null;
		}
	}
	
	public static boolean execute(String sql, List<Object> params) {
		boolean result = false;
		try {
			initOrOpenConn();
			PreparedStatement ps = mConn.prepareStatement(sql);
			int i = 0;
			for (Object param : params) {
				ps.setObject(++i, param);
			}
			result = ps.execute();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean executeQuery(String sql, List<Object> params, QueryDBCallback callback) {
		boolean result = false;
		try {
			initOrOpenConn();
			PreparedStatement ps = mConn.prepareStatement(sql);
			int i = 0;
			for (Object param : params) {
				ps.setObject(++i, param);
			}
			result = callback.dealDataSet(ps.executeQuery());
			ps.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static int executeUpdate(String sql, List<Object> params) {
		int result = -1;
		try {
			initOrOpenConn();
			PreparedStatement ps = mConn.prepareStatement(sql);
			int i = 0;
			for (Object param : params) {
				ps.setObject(++i, param);
			}
			result = ps.executeUpdate();
			ps.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static Object executeQueryOne(String sql, List<Object> params) {
		Object result = -1;
		try {
			initOrOpenConn();
			PreparedStatement ps = mConn.prepareStatement(sql);
			int i = 0;
			for (Object param : params) {
				ps.setObject(++i, param);
			}
			ResultSet rs = ps.executeQuery();
			rs.beforeFirst();
			if (rs.next())
				result = rs.getObject(1);
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public interface QueryDBCallback {
		public boolean dealDataSet(ResultSet dataSet);
	}
}
