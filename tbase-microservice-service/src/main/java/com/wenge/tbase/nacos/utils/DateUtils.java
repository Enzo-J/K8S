package com.wenge.tbase.nacos.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
/**
 * 
 * @author dw
 *
 */
public class DateUtils {
	public static String transferLongToDate(String dateFormat, Long millSecond) {
		Date time = new Date(millSecond);
		SimpleDateFormat formats = new SimpleDateFormat(dateFormat);
		return formats.format(time);
	}

	public static int getWeekOfYear(Date date) {
		GregorianCalendar g = new GregorianCalendar();
		g.setTime(date);
		return g.get(Calendar.WEEK_OF_YEAR); // 获得周数
	}

	/**
	 * 获取当前时间离一天结束剩余秒数
	 * 
	 * @param currentDate
	 * @return
	 */
	public static Integer getRemainSecondsOneDay(Date currentDate) {
		LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(), ZoneId.systemDefault()).plusDays(1)
				.withHour(0).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(), ZoneId.systemDefault());
		long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
		return (int) seconds;
	}
    /**
     * 获取当天0点时间
     * @return long 毫秒
     */
	public static Long getTimesMorning() {
		return getTimes(0);
	}
	/**
     * 获取当天24点时间
     * @return long 毫秒
     */
	public static Long getTimesNight() {
		return getTimes(24);
	}

	private static Long getTimes(Integer hour) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime().getTime();
	}
}
