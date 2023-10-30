package vip.xiaonuo.common.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author ruoyi
 */
public class DateUtil extends org.apache.commons.lang3.time.DateUtils {
	public static String YYYY = "yyyy";

	public static String YYYY_MM = "yyyy-MM";

	public static String YYYY_MM_DD = "yyyy-MM-dd";

	public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";


	private static String[] parsePatterns = {
			"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
			"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 获取当前Date型日期
	 *
	 * @return Date() 当前日期
	 */
	public static Date getNowDate() {
		return new Date();
	}

	/**
	 * 获取当前日期, 默认格式为yyyy-MM-dd
	 *
	 * @return String
	 */
	public static String getDate() {
		return dateTimeNow(YYYY_MM_DD);
	}


	public static final String getTime() {
		return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
	}

	public static final String dateTimeNow() {
		return dateTimeNow(YYYYMMDDHHMMSS);
	}

	public static final String dateTimeNow(final String format) {
		return parseDateToStr(format, new Date());
	}

	public static final String dateTime(final Date date) {
		return parseDateToStr(YYYY_MM_DD, date);
	}

	public static final String dateTimeYYYY_MM_DD_HH_MM_SS(final Date date) {
		return parseDateToStr(YYYY_MM_DD_HH_MM_SS, date);
	}

	public static final String parseDateToStr(final String format, final Date date) {
		return new SimpleDateFormat(format).format(date);
	}

	public static final Date dateTime(final String format, final String ts) {
		try {
			return new SimpleDateFormat(format).parse(ts);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 日期路径 即年/月/日 如2018/08/08
	 */
	public static final String datePath() {
		Date now = new Date();
		return DateFormatUtils.format(now, "yyyy/MM/dd");
	}

	/**
	 * 日期路径 即年/月/日 如20180808
	 */
	public static final String dateTime() {
		Date now = new Date();
		return DateFormatUtils.format(now, "yyyyMMdd");
	}

	/**
	 * 日期型字符串转化为日期 格式
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取服务器启动时间
	 */
	public static Date getServerStartDate() {
		long time = ManagementFactory.getRuntimeMXBean().getStartTime();
		return new Date(time);
	}

	/**
	 * 计算相差天数
	 */
	public static int differentDaysByMillisecond(Date date1, Date date2) {
		return Math.abs((int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24)));
	}

	/**
	 * 计算两个时间差
	 */
	public static String getDatePoor(Date endDate, Date nowDate) {
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		// long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = endDate.getTime() - nowDate.getTime();
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		// 计算差多少秒//输出结果
		// long sec = diff % nd % nh % nm / ns;
		return day + "天" + hour + "小时" + min + "分钟";
	}

	/**
	 * 判断当前时间是否在[startTime, endTime]区间，注意三个参数的时间格式要一致
	 *
	 * @param nowTime
	 * @param startTime
	 * @param endTime
	 * @return 在时间段内返回true，不在返回false
	 */
	public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
		if (nowTime.getTime() == startTime.getTime()
				|| nowTime.getTime() == endTime.getTime()) {
			return true;
		}
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(startTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		return date.after(begin) && date.before(end);
	}

	/**
	 * 增加 LocalDateTime ==> Date
	 */
	public static Date toDate(LocalDateTime temporalAccessor) {
		ZonedDateTime zdt = temporalAccessor.atZone(ZoneId.systemDefault());
		return Date.from(zdt.toInstant());
	}

	/**
	 * 增加 LocalDate ==> Date
	 */
	public static Date toDate(LocalDate temporalAccessor) {
		LocalDateTime localDateTime = LocalDateTime.of(temporalAccessor, LocalTime.of(0, 0, 0));
		ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
		return Date.from(zdt.toInstant());
	}

	/**
	 * 获取当天甚剩余秒数
	 */
	public static long getNextDaySeconds() {
		LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
		return ChronoUnit.SECONDS.between(LocalDateTime.now(), midnight);
	}

	public static void main(String[] args) {
		System.out.println(getTheDayBefore(0));
	}

	/**
	 * 获取前几天时间  -1 =前一天 1= 未来一天 0=当天
	 */
	public static String getTheDayBefore(int temp) {
		long now = System.currentTimeMillis();
		long day = 1000 * 60 * 60 * 24;
		if (temp > 0) {
			return parseDateToStr(YYYY_MM_DD, new Date(now + (day * Math.abs(temp))));
		} else if (temp < 0) {
			return parseDateToStr(YYYY_MM_DD, new Date(now - (day * Math.abs(temp))));
		}
		return getDate();
	}

	/**
	 * 获取前几天时间  -1 =前一天 1= 未来一天 0=当天 返回Date
	 */
	public static Date getTheDayBeforeReturnDate(int temp) {
		long now = System.currentTimeMillis();
		long day = 1000 * 60 * 60 * 24;
		if (temp > 0) {
			return new Date(now + (day * Math.abs(temp)));
		} else if (temp < 0) {
			return new Date(now - (day * Math.abs(temp)));
		}
		return new Date();
	}

	/**
	 * 获取指定分钟之前的时间
	 *
	 * @return
	 */
	public static String getBeforeDateTimeStrByMinute(long minute) {
		// 当api日志中有未成功的撞库，则直接返回撞库失败
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		//获取当前时间
		LocalDateTime date = LocalDateTime.now();
		//获取当前时间的前半小时
		LocalDateTime localDateTime = date.minusMinutes(minute);
		return dateTimeFormatter.format(localDateTime);
	}
}
