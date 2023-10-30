package vip.xiaonuo.common.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author 黄志源大魔王
 * @date 2022/7/4 18:12
 * @project manager_system_server
 * @company 智影科技
 * @description 包装类型工具
 */
public class FilterNullUtil {

	public static int defaultIfNull(Integer i) {
		return defaultIfNull(i, 0);
	}

	public static double defaultIfNull(Double i) {
		return defaultIfNull(i, 0.0);
	}

	public static long defaultIfNull(Long i) {
		return defaultIfNull(i, 0L);
	}

	public static <T> T defaultIfNull(T value, T defaultValue) {
		return Optional.ofNullable(value).orElse(defaultValue);
	}

	public static String defaultIfNull(String value) {
		return defaultIfNull(value, "");
	}

	public static List<?> defaultIfNull(List<?> value) {
		return defaultIfNull(value, new ArrayList<>());
	}

	public static Boolean defaultIfNull(Boolean value) {
		return defaultIfNull(value, Boolean.FALSE);
	}

	public static BigDecimal defaultIfNull(BigDecimal value) {
		return defaultIfNull(value, BigDecimal.ZERO);
	}

}
