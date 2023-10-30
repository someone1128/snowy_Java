package vip.xiaonuo.common.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import org.slf4j.Logger;
import vip.xiaonuo.common.thread.ThreadLocalContext;

import java.util.Date;
import java.util.List;

/**
 * @author 黄志源大魔王
 * @date 2023/2/7 10:47
 * @project manager_system_server_axh
 * @company 智影科技
 * @description 日志打印工具（将日志保存到本地线程中，然后随操作日志一起持久化到数据库）
 */
public class LogPrintUtils {

	// TODO 后续看看 lombok 源码优化成像 @Sl4j 注解形式，使用工具类太麻烦了

	private static final String LOG_PRINT = "LOG_PRINT";

	public static void info(Logger log, String format, Object... arguments) {
		String printStr = StrUtil.format(format, arguments);
		LogPrintUtils.info(printStr);
		log.info(printStr);
	}

	public static void info(String format, Object... arguments) {
		setLogPrint(StrUtil.format("【info】" + format, arguments));
	}

	public static void warn(Logger log, String format, Object... arguments) {
		String printStr = StrUtil.format(format, arguments);
		setLogPrint("【warn】" + printStr);
		log.warn(printStr);
	}

	public static void info(Logger log, String msg, Throwable t) {
		log.info(msg, t);
	}

	public static List<String> getLogPrint() {
		String logPrintStr = getLogPrintStr();
		if (StrUtil.isBlank(logPrintStr)) {
			return null;
		}
		return FastjsonUtils.readValue(logPrintStr, List.class);
	}

	private static void setLogPrint(String logStr) {
		List<String> logPrintList = ListUtils.filterEmpty(getLogPrint());
		try {
			logPrintList.add(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS") + " " + logStr);
		} finally {
			ThreadLocalContext.put(LOG_PRINT, JSON.toJSONString(logPrintList));
		}
	}

	public static String getLogPrintStr() {
		return ThreadLocalContext.get(LOG_PRINT, String.class);
	}


}
