package vip.xiaonuo.common.resolver;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author 黄志源大魔王
 * @date 2022/8/15 16:43
 * @project manager_system_server
 * @company 弘瑞创享
 * @description
 */
@Configuration
public class CustomParamResolverConfigurer implements WebMvcConfigurer {

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new CustomMethodArgumentResolver());
		WebMvcConfigurer.super.addArgumentResolvers(resolvers);
	}

}
