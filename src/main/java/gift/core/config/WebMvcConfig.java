package gift.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import gift.core.authorization.interceptor.AuthorizationInterceptor;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
	private final AuthorizationInterceptor authorizationInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authorizationInterceptor)
			.excludePathPatterns("/css/**", "/images/**", "/js/**")
			.excludePathPatterns("/api/v1/user/signup")
			.excludePathPatterns("/api/v1/user/login")
			.excludePathPatterns("/user/login");
	}
}
