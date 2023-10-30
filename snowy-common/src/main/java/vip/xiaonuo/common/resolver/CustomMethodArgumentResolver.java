package vip.xiaonuo.common.resolver;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import vip.xiaonuo.common.annotation.CustomParam;
import vip.xiaonuo.common.exception.BusinessException;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author 黄志源大魔王
 * @date 2023/5/6 15:12
 * @project snowy-master
 * @company 智影科技
 * @description
 */
@Slf4j
public class CustomMethodArgumentResolver implements HandlerMethodArgumentResolver {

	private static final String APPLICATION_JSON = "application/json";

	/**
	 * 判断是否处理该参数
	 *
	 * @param parameter
	 * @return
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(CustomParam.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
		String contentType = Objects.requireNonNull(servletRequest).getContentType();

		if (contentType == null || !contentType.contains(APPLICATION_JSON)) {
			log.error("解析参数异常，contentType需为{}", APPLICATION_JSON);
		}

		return bindRequestParams(parameter, servletRequest);
	}

	private Object bindRequestParams(MethodParameter parameter, HttpServletRequest servletRequest) {
		CustomParam customParam = parameter.getParameterAnnotation(CustomParam.class);
		Class<?> parameterType = parameter.getParameterType();
		String requestBody = getRequestBody(servletRequest);
		Map<String, Object> params = JSON.parseObject(requestBody);
		params = MapUtils.isEmpty(params) ? new HashMap<>(0) : params;
		String name = StringUtils.isBlank(customParam.value()) ? parameter.getParameterName() : customParam.value();
		Object value = params.get(name);
		if (parameterType.equals(String.class)) {
			if (StringUtils.isBlank((String) value)) {
				log.error("参数解析异常,String类型参数不能为空");
			}
		}
		// 参数是否必填
		if (customParam.required()) {
			if (value == null) {
				log.error("参数解析异常,require=true,值不能为空");
				throw new BusinessException("参数解析异常,require=true,值不能为空");
			}
		} else {
			if (customParam.defaultValue().equals(ValueConstants.DEFAULT_NONE)) {
				log.error("参数解析异常,require=false,必须指定默认值");
				throw new BusinessException("参数解析异常,require=false,必须指定默认值");
			}
			if (value == null) {
				value = customParam.defaultValue();
			}
		}
		return ConvertUtils.convert(String.valueOf(value), parameterType);
	}

	/**
	 * 获取请求body
	 *
	 * @param servletRequest request
	 * @return 请求body
	 */
	private String getRequestBody(HttpServletRequest servletRequest) {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader reader = servletRequest.getReader();
			char[] buf = new char[1024];
			int length;
			while ((length = reader.read(buf)) != -1) {
				stringBuilder.append(buf, 0, length);
			}
		} catch (IOException e) {
			log.error("读取流异常", e);
			throw new BusinessException("读取流异常");
		}
		return stringBuilder.toString();
	}
}
