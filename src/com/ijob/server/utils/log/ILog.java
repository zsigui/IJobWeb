package com.ijob.server.utils.log;


/**
 * 
 * @description Log接口
 * 
 * @author Jackie Zhuang
 * @date 2014年12月12日
 */
public interface ILog {
	
	void te(String msg);
	void td(String msg);
	void te(Class<?> cls, String msg);
	void td(Class<?> cls, String msg);
	void te(Class<?> cls, String methodName, String msg);
	void td(Class<?> cls, String methodName, String msg);
	void te(String tag, Class<?> cls, String methodName, String msg);
	void td(String tag, Class<?> cls, String methodName, String msg);
	void te(Class<?> cls, String methodName, String msg, int printLevel);
	void td(Class<?> cls, String methodName, String msg, int printLevel);
	void te(String tag, Class<?> cls, String methodName, String msg, int printLevel);
	void td(String tag, Class<?> cls, String methodName, String msg, int printLevel);
	void te(String tag, Class<?> cls, String methodName, String msg, int printLevel, String outputFile);
	void td(String tag, Class<?> cls, String methodName, String msg, int printLevel, String outputFile);
}
