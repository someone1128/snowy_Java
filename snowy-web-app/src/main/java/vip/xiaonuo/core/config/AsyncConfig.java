package vip.xiaonuo.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AsyncConfig implements WebMvcConfigurer {

	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		// 设置异步请求的默认超时时间为 500 秒
		configurer.setDefaultTimeout(500000);
	}

}