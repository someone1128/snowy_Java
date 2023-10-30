package vip.xiaonuo.common.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 志源大魔王
 * @Date: 2021/9/15 16:10
 * @description: Jackson Object Mapper
 */
public class FastjsonUtils {

	private static ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 将指定对象转换成 json 格式字符串返回
	 *
	 * @param obj
	 * @return
	 */
	public static String writeValueAsString(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return obj.toString();
		}
	}

	/**
	 * 将对象转换成 map 返回，保留 null 属性一起转换
	 *
	 * @param obj
	 * @return
	 */
	public static <T> Map<String, T> writeValueAsMap(Object obj) {
		try {
			String jsonString = JSON.toJSONString(obj);
			return JSON.parseObject(jsonString, new TypeReference<Map<String, T>>() {
			});
		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<>();
		}
	}

	/**
	 * 将 json 格式字符串转换成指定类型对象
	 *
	 * @param content
	 * @param valueType
	 * @param <T>
	 * @return
	 */
	public static <T> T readValue(String content, Class<T> valueType) {
		if (StrUtil.isBlank(content)) {
			return null;
		}
		try {
			return objectMapper.readValue(content, valueType);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将输入流转换成指定类型对象
	 *
	 * @param is
	 * @param valueType
	 * @param <T>
	 * @return
	 */
	public static <T> T readValue(InputStream is, Class<T> valueType) throws IOException {
		return objectMapper.readValue(is, valueType);
	}

	public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
		return objectMapper.convertValue(fromValue, toValueType);
	}

	public static JSONObject convertJsonObj(Object obj) {
		return JSON.parseObject(JSON.toJSONString(obj));
	}

}
