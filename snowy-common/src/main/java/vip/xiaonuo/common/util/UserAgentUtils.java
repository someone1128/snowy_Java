package vip.xiaonuo.common.util;

import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 志源大魔王
 * @Date: 2021/9/15 13:56
 * @description: UserAgent 解析工具类
 */
@Component
public class UserAgentUtils {

	private static UserAgentAnalyzer userAgentAnalyzer;

	static {
		userAgentAnalyzer = UserAgentAnalyzer
				.newBuilder()
				.hideMatcherLoadStats()
				.withField("OperatingSystemNameVersionMajor")
				.withField("AgentNameVersion")
				.build();
	}

	public static Map<String, String> parseOsAndBrowser() {
		HttpServletRequest request = RequestUtils.getHttpServletRequest();
		return parseOsAndBrowser(request.getHeader("User-Agent"));
	}


	/**
	 * 从User-Agent解析客户端操作系统和浏览器版本
	 *
	 * @param userAgent
	 * @return
	 */
	public static Map<String, String> parseOsAndBrowser(String userAgent) {
		UserAgent agent = userAgentAnalyzer.parse(userAgent);
		/*for (String s : agent.getAvailableFieldNamesSorted()) {
			System.out.println("s = " + s);
		}*/
		Map<String, String> map = new HashMap<>();
		map.put("os", agent.getValue("OperatingSystemNameVersionMajor"));
		map.put("osVersion", agent.getValue("OperatingSystemVersion"));
		map.put("browser", agent.getValue("AgentNameVersion"));
		return map;
	}

	public static void main(String[] args) {
		System.out.println(parseOsAndBrowser("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36 Edg/109.0.1518.78"));
	}

	/**
	 * 获取操作系统
	 *
	 * @return
	 */
	public static String getOsAddVersion() {
		try {
			Map<String, String> userAgentMap = parseOsAndBrowser();
			return userAgentMap.get("os");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取操作系统版本
	 *
	 * @return
	 */
	public static String getOsVersion() {
		try {
			Map<String, String> userAgentMap = parseOsAndBrowser();
			return userAgentMap.get("osVersion");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

}
