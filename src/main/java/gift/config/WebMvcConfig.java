package gift.config;

import gift.controller.LoginMemberArgumentResolver;
import gift.interceptor.JwtInterceptor;
import gift.service.UserService;
import gift.util.JwtUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final UserService userService;
    private final JwtInterceptor jwtInterceptor;
    private final JwtUtil jwtUtil;

    @Autowired
    WebMvcConfig(UserService userService, JwtInterceptor jwtInterceptor, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtInterceptor = jwtInterceptor;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/api/products/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new LoginMemberArgumentResolver(userService, jwtUtil));
    }
}
