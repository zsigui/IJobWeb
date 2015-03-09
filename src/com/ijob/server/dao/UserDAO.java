package com.ijob.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ijob.server.model.User;
import com.ijob.server.utils.DBUtils;

public class UserDAO {

	public static String querySalt(String uname) {
		String sql = "SELECT user_salt FROM db_ijob.user WHERE user_name = ? OR user_email = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(uname);
		params.add(uname);
		Object obj = DBUtils.executeQueryOne(sql, params);
		if (obj != null && obj instanceof String) {
			return (String) obj;
		}
		return null;
	}
	
	public static User verifyLogin(String uname, String upwd) {
		final User user = new User();
		String sql = "SELECT user_id, user_name, user_nickname, user_salt, user_email, "
				+ "user_mobliephone, user_security_question, user_security_answer, user_student_no, "
				+ "user_student_pwd, user_gender, user_description FROM db_ijob.user "
				+ "WHERE (user_name = ? OR user_email = ?) AND user_password = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(uname);
		params.add(uname);
		params.add(upwd);
		boolean execRet = DBUtils.executeQuery(sql, params, new DBUtils.QueryDBCallback() {
			
			@Override
			public boolean dealDataSet(ResultSet dataSet) {
				
				boolean result = false;
				try {
					dataSet.beforeFirst();
					if (dataSet.next()) {
						user.setUId(dataSet.getInt(1));
						user.setName(dataSet.getString(2));
						user.setNickName(dataSet.getString(3));
						user.setSalt(dataSet.getString(4));
						user.setEmail(dataSet.getString(5));
						user.setMobilePhone(dataSet.getString(6));
						user.setSecQuestion(dataSet.getString(7));
						user.setSecAnswer(dataSet.getString(8));
						user.setStudNo(dataSet.getString(9));
						user.setStudPwd(dataSet.getString(10));
						user.setGender(dataSet.getString(11));
						user.setDescription(dataSet.getString(12));
						result = true;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				return result;
			}
		});
		if (execRet)
			return user;
		return null;
	}
}
