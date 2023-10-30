package vip.xiaonuo.common.util;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @ClassName: StringUtil
 * @Description: 字符串处理类
 * @date 2015-12-24 下午2:15:03
 */
public class StringUtil {
	public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";

	/**
	 * 获取堆栈信息
	 *
	 * @param throwable
	 * @return
	 */
	public static String getStackTrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		try (PrintWriter pw = new PrintWriter(sw)) {
			throwable.printStackTrace(pw);
			return sw.toString();
		}
	}


	/**
	 * 获取调用者
	 *
	 * @return
	 */
	public static String getInvokeName() {
		StackTraceElement stackTraceElement = new Throwable().getStackTrace()[2];
		return CharSequenceUtil.format("{}:{}:{}", stackTraceElement.getClassName(), stackTraceElement.getMethodName(), stackTraceElement.getLineNumber());
	}

	public static List<String> getLatelyInvokeNames() {
		ArrayList<String> list = new ArrayList<>();
		StackTraceElement[] stackTraceElement = new Throwable().getStackTrace();
		for (StackTraceElement traceElement : stackTraceElement) {
			String className = traceElement.getClassName();
			int lineNumber = traceElement.getLineNumber();
			if (className.contains("vip.xiaonuo") && lineNumber > 0) {
				list.add(CharSequenceUtil.format("【{}】【{}】【{}】", traceElement.getClassName(), traceElement.getMethodName(), lineNumber));
			}
		}
		list.remove(0);
		return list;
	}

	public static List<String> toListComma(String s) {
		if (StrUtil.isBlank(s)) {
			return new ArrayList<>();
		}
		Optional<String> optional = Optional.ofNullable(s);
		return Arrays.stream(optional.orElse("").split(",")).collect(Collectors.toList());
	}
}
