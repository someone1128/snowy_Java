package vip.xiaonuo.common.util;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: 志源大魔王
 * @Date: 2021/9/17 17:16
 * @description: Servlet 工具类
 */
@Slf4j
public class RequestUtils {

	/**
	 * 获取当前请求会话
	 *
	 * @return
	 */
	public static ServletRequestAttributes getServletRequestAttributes() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
	}

	/**
	 * 获取当前请求
	 *
	 * @return
	 */
	public static HttpServletRequest getHttpServletRequest() {
		try {
			return getServletRequestAttributes().getRequest();
		} catch (Exception e) {
			LogPrintUtils.info(log, "找不到当前线程中的请求");
			return null;
		}
	}

	/**
	 * 获取当前响应对象
	 *
	 * @return
	 */
	public static HttpServletResponse getHttpServletResponse() {
		return getServletRequestAttributes().getResponse();
	}

	/**
	 * 获取所有请求参数
	 *
	 * @param joinPoint
	 * @return
	 */
	public static Map<String, Object> getRequestParams(JoinPoint joinPoint) {
		Map<String, Object> map = new LinkedHashMap<>();
		String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
		Object[] args = joinPoint.getArgs();
		for (int i = 0; i < args.length; i++) {
			if (!isFilterObject(args[i])) {
				map.put(parameterNames[i], args[i]);
			}
		}
		return map;
	}

	/**
	 * 考虑数据是文件、httpRequest 还是响应
	 *
	 * @param o
	 * @return 如果匹配返回真，否则返回假
	 */
	private static boolean isFilterObject(final Object o) {
		return o instanceof HttpServletRequest || o instanceof HttpServletResponse || o instanceof MultipartFile;
	}

	/**
	 * 获取所有请求头
	 *
	 * @param request
	 * @return
	 */
	public static Map<String, String> getHeaders(HttpServletRequest request) {
		Map<String, String> map = new HashMap<>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}
		return map;
	}

	public static Map<String, String> getHeaders() {
		return getHeaders(getHttpServletRequest());
	}

	public static String getHeader(String key) {
		return getHeaders().get(key);
	}

	public static String getRequestParamsConvertStr(JoinPoint joinPoint) {
		Map<String, Object> requestParams = getRequestParams(joinPoint);
		try {
			return JSON.toJSONString(requestParams);
		} catch (Exception e) {
			return String.valueOf(requestParams);
		}
	}

	public static String getMethodName(ProceedingJoinPoint joinPoint) {
		return ((MethodSignature) joinPoint.getSignature()).getMethod().getName();
	}

	public static String getClassName(ProceedingJoinPoint joinPoint) {
		return joinPoint.getSignature().getDeclaringTypeName();
	}

	/**
	 * 是否为安卓系统
	 */
	public static boolean isAndroid() {
		return getOS().contains("Android");
	}

	private static String getOS() {
		return UserAgentUtils.parseOsAndBrowser(getHttpServletRequest().getHeader("User-Agent")).get("os");
	}

	public static String getUri() {
		HttpServletRequest httpServletRequest = getHttpServletRequest();
		if (httpServletRequest != null) {
			return httpServletRequest.getRequestURI();
		}
		return null;
	}

	/**
	 * 获取流量来源
	 *
	 * @return h5 Android IOS
	 */
	public static String getFlowSource() {
		HttpServletRequest request = getHttpServletRequest();
		String flowSource = request.getHeader("flowSource");
		String h5 = "h5";
		if (h5.equals(flowSource)) {
			return h5;
		}
		return isAndroid() ? "Android" : "IOS";
	}
}
