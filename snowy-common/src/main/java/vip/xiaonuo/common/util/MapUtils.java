package vip.xiaonuo.common.util;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 黄志源大魔王
 * @date 2022/12/20 14:33
 * @project manager_system_server_axh
 * @company 弘瑞创享
 * @description
 */
public class MapUtils {

	public static <T> String convertMap2String(Map<String, T> paramsMap) {
		StringBuilder content = new StringBuilder();
		List<String> keys = new ArrayList<>(paramsMap.keySet());
		Collections.sort(keys);
		int index = 0;
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			Object value = paramsMap.get(key);
			if (!key.isEmpty() && value != null) {
				content.append(index == 0 ? "" : "&").append(key).append("=").append(value);
				index++;
			}
		}
		return content.toString();
	}

	/**
	 * 将String转Map
	 */
	public static Map<String, String> convertString2Map(String targetString) {
		Map<String, String> map = new HashMap<>();
		String[] strings = targetString.split("&");
		for (int i = 0; i < strings.length; i++) {
			String[] keyValue = strings[i].split("=");
			map.put(keyValue[0], keyValue[1]);
		}
		return map;
	}

	public static String getSign(Map<String, Object> postParameters, String connector, String spacer) {
		System.out.println("加签前：" + postParameters);
		Set<String> keySet = postParameters.keySet();
		return keySet.stream().sorted(Comparator.naturalOrder()).map(s -> s + connector + postParameters.get(s)).collect(Collectors.joining(spacer));
	}

	public static boolean isNotBlank(Map map) {
		return !isBlank(map);
	}

	public static boolean isBlank(Map map) {
		return map == null || map.isEmpty();
	}
}
