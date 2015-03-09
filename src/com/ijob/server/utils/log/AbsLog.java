package com.ijob.server.utils.log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ijob.server.constants.GlobalConfig;
import com.ijob.server.utils.DateUtils;
import com.ijob.server.utils.FileUtils;
import com.ijob.server.utils.TextUtils;

/**
 * 
 * @description 基本实现ILog接口的Log抽象类
 * 
 * @author Jackie Zhuang
 * @date 2014年12月13日
 */
public abstract class AbsLog implements ILog {

	/**
	 * 
	 * @description 表示LOG打印等级的枚举类<br>
	 *              变量有 CONSOLE, FILE, BOTH
	 * 
	 * @author Jackie Zhuang
	 * @date 2014年12月13日
	 */
	public static enum PrintLevel {
		CONSOLE(0), FILE(1), BOTH(2);
		private int v = -1;

		private PrintLevel(int v) {
			this.v = v;
		}

		public int val() {
			return this.v;
		}
	};

	/**
	 * 定义是否debug模式
	 */
	protected static final boolean IS_DEBUG = true;
	/**
	 * 单一LOG打印线程池，保证LOG顺序输出
	 */
	protected static final ExecutorService SINGLE_LOG_OUTPUT_THREADE = Executors
			.newSingleThreadExecutor();
	/**
	 * 默认LOG输出文件夹
	 */
	protected static final String DEFAULT_LOG_FILE = "IJOB_LOG";
	/**
	 * 默认指示抛出类
	 */
	protected static final Class<?> DEFAULT_CLASS = ILog.class;
	/**
	 * 默认指示抛出方法
	 */
	protected static final String DEFAULT_METHOD = "DEFAULT";
	/**
	 * 默认标签名
	 */
	protected static final String DEFAULT_TAG = "IJOB_SERVER";
	/**
	 * 默认打印等级(控制台输出)
	 */
	protected static final int DEFAULT_PRINT_LEVEL = PrintLevel.CONSOLE.val();

	@Override
	public void te(String msg) {
		te(DEFAULT_CLASS, msg);
	}

	@Override
	public void td(String msg) {
		td(DEFAULT_CLASS, msg);
	}

	@Override
	public void te(Class<?> cls, String msg) {
		te(cls, DEFAULT_METHOD, msg);
	}

	@Override
	public void td(Class<?> cls, String msg) {
		td(cls, DEFAULT_METHOD, msg);
	}

	@Override
	public void te(Class<?> cls, String methodName, String msg) {
		te(DEFAULT_TAG, cls, methodName, msg);
	}

	@Override
	public void td(Class<?> cls, String methodName, String msg) {
		td(DEFAULT_TAG, cls, methodName, msg);
	}

	@Override
	public void te(String tag, Class<?> cls, String methodName, String msg) {
		te(tag, cls, methodName, msg, DEFAULT_PRINT_LEVEL);
	}

	@Override
	public void td(String tag, Class<?> cls, String methodName, String msg) {
		td(tag, cls, methodName, msg, DEFAULT_PRINT_LEVEL);
	}

	@Override
	public void te(Class<?> cls, String methodName, String msg, int printLevel) {
		te(DEFAULT_TAG, cls, methodName, msg, printLevel);
	}

	@Override
	public void td(Class<?> cls, String methodName, String msg, int printLevel) {
		td(DEFAULT_TAG, cls, methodName, msg, printLevel);
	}

	@Override
	public void te(String tag, Class<?> cls, String methodName, String msg,
			int printLevel) {
		te(tag, cls, methodName, msg, printLevel, DEFAULT_LOG_FILE);
	}

	@Override
	public void td(String tag, Class<?> cls, String methodName, String msg,
			int printLevel) {
		td(tag, cls, methodName, msg, printLevel, DEFAULT_LOG_FILE);
	}

	/**
	 * 输出错误信息
	 * 
	 * @param tag
	 *            指示标签
	 * @param cls
	 *            抛出类
	 * @param methodName
	 *            抛出方法
	 * @param msg
	 *            输出消息
	 * @param printLevel
	 *            打印等级 {@link PrintLevel}
	 * @param outputFile
	 *            log文件夹存放位置(如果输出的话，默认输出 outputFile/{time}.error.log )
	 */
	@Override
	public void te(String tag, Class<?> cls, String methodName,
			final String msg, final int printLevel, String outputFile) {
		if (IS_DEBUG) {
			final String logType = "ERROR";
			final String logFileSuffix = ".error.log";
			dealOutput(logType, tag, cls, methodName, msg, printLevel,
					outputFile, logFileSuffix);
		}
	}

	/**
	 * 输出调试信息
	 * 
	 * @param tag
	 *            指示标签
	 * @param cls
	 *            抛出类
	 * @param methodName
	 *            抛出方法
	 * @param msg
	 *            输出消息
	 * @param printLevel
	 *            打印等级 {@link PrintLevel}
	 * @param outputFile
	 *            log文件夹存放位置(如果输出的话，默认输出 outputFile/{time}.debug.log
	 */
	@Override
	public void td(String tag, Class<?> cls, String methodName,
			final String msg, final int printLevel, String outputFile) {
		if (IS_DEBUG) {
			final String logType = "DEBUG";
			final String logFileSuffix = ".debug.log";
			dealOutput(logType, tag, cls, methodName, msg, printLevel,
					outputFile, logFileSuffix);
		}
	}

	/**
	 * log进行输出的实际处理过程
	 * 
	 * @param logType
	 * @param tag
	 * @param cls
	 * @param methodName
	 * @param msg
	 * @param printLevel
	 * @param outputFile
	 * @param logFileSuffix
	 */
	private void dealOutput(final String logType, String tag, Class<?> cls,
			String methodName, final String msg, final int printLevel,
			String outputFile, final String logFileSuffix) {
		final String dateStr = DateUtils.getVal("yyyyMMdd");
		final String timeStr = DateUtils.getVal("HH:mm:ss");
		final StringBuilder builder = new StringBuilder();
		if (TextUtils.isEmpty(tag))
			tag = DEFAULT_TAG;
		if (cls == null)
			cls = DEFAULT_CLASS;
		if (TextUtils.isEmpty(outputFile))
			outputFile = DEFAULT_LOG_FILE;
		if (TextUtils.isEmpty(methodName))
			methodName = DEFAULT_METHOD;
		builder.append(logType).append(":").append(tag).append("/Method.")
				.append(methodName).append(" in ").append(cls.toString())
				.append(" at ").append(timeStr).append("=>").append(msg)
				.append(FileUtils.ENTER);

		// 执行线程池线程
		SINGLE_LOG_OUTPUT_THREADE.execute(new Runnable() {

			@Override
			public void run() {
				if (printLevel == PrintLevel.CONSOLE.val()) {
					System.out.print(builder.toString());
				} else if (printLevel == PrintLevel.FILE.val()) {
					write2File(builder.toString(), DEFAULT_LOG_FILE, dateStr
							+ logFileSuffix);
				} else if (printLevel == PrintLevel.BOTH.val()) {
					System.out.print(builder.toString());
					write2File(builder.toString(), DEFAULT_LOG_FILE, dateStr
							+ logFileSuffix);
				}
			}
		});
	}

	public void shutdownExecutor() {
		SINGLE_LOG_OUTPUT_THREADE.shutdown();
	}
	
	/**
	 * 将Log消息输出到文件
	 * 
	 * @param msg
	 * @param logFile
	 * @param logName
	 */
	private void write2File(final String msg, final String logFile,
			final String logName) {
		String fullPath = logFile + FileUtils.SEP + logName;
		FileUtils.createPath(logFile);
		FileUtils.write2File(msg, fullPath, GlobalConfig.DEFAULT_CHARSET, true);
	}
}
