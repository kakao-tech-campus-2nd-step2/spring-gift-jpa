package gift.config;

import gift.filter.JwtFilter;
import gift.util.UserUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Value("${spring.var.token-prefix}")
    private String tokenPrefix;
    private UserUtility userUtility;

    public FilterConfig(UserUtility userUtility) {
        this.userUtility = userUtility;
    }

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter() {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtFilter(tokenPrefix, userUtility));
        registrationBean.addUrlPatterns("/api/wishlist/*");
        return registrationBean;
    }
}
