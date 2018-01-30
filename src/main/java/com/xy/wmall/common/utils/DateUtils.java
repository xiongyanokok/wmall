package com.xy.wmall.common.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.xy.wmall.exception.WmallException;


/**
 * 时间工具类
 *
 *
 * @author xiongyan
 * @date 2017年10月26日 下午10:07:38
 */
public class DateUtils {
	
	private DateUtils() {
		
	}

	/** 标准日期格式 */
	public static final String NORM_DATE_PATTERN = "yyyy-MM-dd";
	
	/** 标准时间格式 */
	public static final String NORM_TIME_PATTERN = "HH:mm:ss";
	
	/** 标准日期时间格式，精确到分 */
	public static final String NORM_DATETIME_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
	
	/** 标准日期时间格式，精确到秒 */
	public static final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	/** 标准日期时间格式，精确到毫秒 */
	public static final String NORM_DATETIME_MS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
	
	/** 12个月 */
	protected static final List<String> MONTHS = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
    
	/**
     * 标准日期（不含时间）格式化器
     */
    public static final ThreadLocal<SimpleDateFormat> NORMDATEFORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat(NORM_DATE_PATTERN));
    
    /**
     * 标准时间格式化器
     */
    public static final ThreadLocal<SimpleDateFormat> NORMTIMEFORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat(NORM_TIME_PATTERN));
    
    /**
     * 标准日期时间格式化器
     */
    public static final ThreadLocal<SimpleDateFormat> NORMDATETIMEFORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat(NORM_DATETIME_PATTERN));
    
    /**
     * 格式yyyy-MM-dd HH:mm:ss
     *
     * @param dateString 标准形式的时间字符串
     * @return 日期对象
     */
    public static Date parseDateTime(String dateString) {
        return parse(dateString, NORMDATETIMEFORMAT.get());
    }

    /**
     * 格式yyyy-MM-dd
     *
     * @param dateString 标准形式的日期字符串
     * @return 日期对象
     */
    public static Date parseDate(String dateString) {
        return parse(dateString, NORMDATEFORMAT.get());
    }

    /**
     * 格式HH:mm:ss
     *
     * @param timeString 标准形式的日期字符串
     * @return 日期对象
     */
    public static Date parseTime(String timeString) {
        return parse(timeString, NORMTIMEFORMAT.get());
    }
    
    /**
     * 构建Date对象
     *
     * @param dateString       Date字符串
     * @param simpleDateFormat 格式化器
     * @return 日期对象
     */
    public static Date parse(String dateString, SimpleDateFormat simpleDateFormat) {
        try {
            return simpleDateFormat.parse(dateString);
        } catch (Exception e) {
            throw new WmallException("Parse [" + simpleDateFormat.toPattern() + "] with format [" + dateString + "] error!");
        }
    }

    /**
     * 将特定格式的日期转换为Date对象
     *
     * @param dateString 特定格式的日期
     * @param format     格式，例如yyyy-MM-dd
     * @return 日期对象
     */
    public static Date parse(String dateString, String format) {
        return parse(dateString, new SimpleDateFormat(format));
    }
    
	/**
     * 格式：<br>
     * 1、yyyy-MM-dd HH:mm:ss<br>
     * 2、yyyy-MM-dd<br>
     * 3、HH:mm:ss<br>
     * 4、yyyy-MM-dd HH:mm 5、yyyy-MM-dd HH:mm:ss.SSS
     *
     * @param dateString 日期字符串
     * @return 日期对象
     */
    public static Date parse(String dateString) {
        if (StringUtils.isEmpty(dateString)) {
            return null;
        }
        String dateStr = dateString.trim();
        int length = dateStr.length();
        try {
            if (length == NORM_DATETIME_PATTERN.length()) {
                return parseDate(dateStr);
            } else if (length == NORM_DATE_PATTERN.length()) {
                return parseDate(dateStr);
            } else if (length == NORM_TIME_PATTERN.length()) {
                return parseTime(dateStr);
            } else if (length == NORM_DATETIME_MINUTE_PATTERN.length()) {
                return parse(dateStr, NORM_DATETIME_MINUTE_PATTERN);
            } else if (length >= NORM_DATETIME_MS_PATTERN.length() - 2) {
                return parse(dateStr, NORM_DATETIME_MS_PATTERN);
            }
        } catch (Exception e) {
            throw new WmallException("Parse [" + dateStr + "] with format normal error!", e);
        }

        // 没有更多匹配的时间格式
        throw new WmallException("[" + dateStr + "] format is not fit for date pattern!");
    }
    
    /**
     * 根据特定格式格式化日期
     *
     * @param date   被格式化的日期
     * @param format 格式
     * @return 格式化后的字符串
     */
    public static String format(Date date, String format) {
    	if (null == date || StringUtils.isEmpty(format)) {
    		return null;
    	}
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 格式 yyyy-MM-dd HH:mm:ss
     *
     * @param date 被格式化的日期
     * @return 格式化后的日期
     */
    public static String formatDateTime(Date date) {
        if (null == date) {
            return null;
        }
        return NORMDATETIMEFORMAT.get().format(date);
    }

    /**
     * 格式 yyyy-MM-dd
     *
     * @param date 被格式化的日期
     * @return 格式化后的字符串
     */
    public static String formatDate(Date date) {
        if (null == date) {
            return null;
        }
        return NORMDATEFORMAT.get().format(date);
    }

    /**
	 * 当前年
	 * @return
	 */
	public static String currentYear() {
		return format(new Date(), "yyyy");
	}
	
	/**
	 * 当前月
	 * @return
	 */
	public static String currentMonth() {
		return format(new Date(), "MM");
	}
	
	/**
	 * 年
	 * @return
	 */
	public static List<String> listYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		List<String> years = new ArrayList<>();
		c.add(Calendar.YEAR, -1);
		years.add(String.valueOf(c.get(Calendar.YEAR)));
		for (int i = 0; i < 4; i++) {
			c.add(Calendar.YEAR, 1);
			years.add(String.valueOf(c.get(Calendar.YEAR)));
		}
		return years;
	}
	
	/**
	 * 月
	 * @return
	 */
	public static List<String> listMonth() {
		return MONTHS;
	}
	
	/**
	 * 获取当前日期是星期几
	 * 
	 * @return
	 */
	public static String getWeek() {
		String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDays[w];
	}
	
	/**
	 * 自然月 yyyy-MM
	 * 
	 * @return
	 */
	public static String natureMonth() {
    	return format(new Date(), "yyyy-MM");
    }
	
}
