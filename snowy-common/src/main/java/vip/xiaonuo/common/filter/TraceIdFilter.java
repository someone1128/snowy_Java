package vip.xiaonuo.common.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.thread.ThreadLocalContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 类名称：TraceIdFilter
 * ********************************
 * 类描述：TraceId过滤器
 *
 * @author Administrator
 */
@Component
@WebFilter(urlPatterns = "/*") //拦截所有请求
@Order(Ordered.HIGHEST_PRECEDENCE) // 设置为最高优先级
@Slf4j
public class TraceIdFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		ThreadLocalContext.initContext();
		// 设置请求 ID
		TraceIdUtil.setRequestId();
		// 把 traceId 写到响应头中
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
		String requestId = TraceIdUtil.getTraceId();
		httpResponse.setHeader(TraceIdUtil.TRACE_ID, requestId);
		try {
			filterChain.doFilter(servletRequest, servletResponse);
		} finally {
			TraceIdUtil.clear();
		}
	}

}