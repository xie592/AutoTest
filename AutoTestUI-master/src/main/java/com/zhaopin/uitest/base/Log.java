package com.zhaopin.uitest.base;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;


public class Log {
	
	private static Logger logger = Logger.getLogger(Log.class.getName());
	
	static {
		DOMConfigurator.configure("Log4j.xml");
	}
	
	/**
	 * Debug级别LOG
	 * @param message 用户赋值，期望打印的内容
	 */
	public synchronized static void debug(String message){
		logger.log(Log.class.getName(), Level.DEBUG, message, null);
	}
	
	/**
	 * Info级别LOG
	 * @param message 用户赋值，期望打印的内容
	 */
	public synchronized static void info(String message){
		logger.log(Log.class.getName(), Level.INFO, message, null);
	}
	
	/**
	 * Warn级别的LOG
	 * @param message 用户赋值，期望打印的内容
	 */
	public synchronized static void warn(String message){
		logger.log(Log.class.getName(), Level.WARN, message, null);
	}
	
	/**
	 * Error级别的LOG
	 * @param message 用户赋值，期望打印的内容
	 */
	public synchronized static void error(String message){
		logger.log(Log.class.getName(), Level.ERROR, message, null);
	}

}
