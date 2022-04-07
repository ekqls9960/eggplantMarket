package teamB.market.domain.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import teamB.market.web.interceptor.LoginCheckInterceptor;

@Configuration
public class MVCConfig implements WebMvcConfigurer {

	// interceptor
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginCheckInterceptor()).addPathPatterns("/**").excludePathPatterns("/",
				"/item/detail/*", "/myapp/**", "/member/add", "/member/socialAdd", "/member/emailAuthCallBack",
				"/login/**", "/logout", "/css/**", "/images/**", "/icons/**", "/javascript/**", "/error/**",
				"/item/search/*");
	}
}
