package com.zhaopin.uitest.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHandler {

	/**
	 * 获取时间戳
	 * @return
	 */
	public static String getTimeStamp(){
		return getFormatDate(new Date(),"yyyyMMddHHmmssSS");
	}

	/**
	 * 获得当前时间
	 * @return
	 */
	public static String getNowDay(){
		return getFormatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
	}

	public static String getForwardDay(String date, int increment){
		Calendar AddDay = Calendar.getInstance();
		AddDay.setTime(ParseDate(date, "yyyy-MM-dd"));
		AddDay.add(Calendar.DATE, increment);
		return getFormatDate(AddDay.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 获取格式化日期
	 * @param date date
	 * @param reg 日期格式
	 * @return
	 */
	private static String getFormatDate(Date date, String reg){
		SimpleDateFormat dateFormat=new SimpleDateFormat(reg);
		return dateFormat.format(date);
	}

	private static Date ParseDate(String date, String reg) {
		SimpleDateFormat dateFormat=new SimpleDateFormat(reg);
		try {
			return dateFormat.parse(date);
			}
		catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args){
		System.out.println(getTimeStamp());
		System.out.println(getNowDay());
		System.out.println(getForwardDay(getNowDay(),3));
	}
}
