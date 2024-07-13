package gift.config;

import gift.custom_annotation.resolver.PageInfoResolver;
import gift.custom_annotation.resolver.TokenEmailResolver;
import gift.security.authfilter.AuthenticationFilter;
import gift.security.jwt.TokenExtractor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final TokenExtractor tokenExtractor;

    public WebConfig(TokenExtractor tokenExtractor) {
        this.tokenExtractor = tokenExtractor;
    }

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilterRegistrationBean(TokenExtractor tokenExtractor) {
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthenticationFilter(tokenExtractor));
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new TokenEmailResolver(tokenExtractor));
        argumentResolvers.add(new PageInfoResolver());
    }
}
