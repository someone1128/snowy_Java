package vip.xiaonuo.common.thread;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.util.FastjsonUtils;

@Slf4j
@Component
public class ThreadLocalContext {

	/**
	 * 获取上下文内容
	 */
	public static <T> T get(String key, Class<T> tClass) {
		String content = MDC.get(key);
		if (tClass.isInstance(content)) {
			return (T) content;
		}
		return FastjsonUtils.readValue(content, tClass);
	}

	/**
	 * 设置上下文参数
	 */
	public static void put(String key, Object value) {
		if (value instanceof String) {
			MDC.put(key, String.valueOf(value));
		} else {
			MDC.put(key, JSON.toJSONString(value));
		}
		return;
	}

	public static void initContext() {
		put("startTime", System.currentTimeMillis());
	}
}
