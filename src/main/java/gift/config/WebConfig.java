package gift.config;

import gift.filter.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import jakarta.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@Configuration
public class WebConfig {

    private final JwtTokenFilter jwtTokenFilter;

    @Autowired
    public WebConfig(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public FilterRegistrationBean<Filter> jwtFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtTokenFilter);

        registrationBean.addUrlPatterns("/api/admin/products/edit/*");
        registrationBean.addUrlPatterns("/api/admin/products/delete/*");
        return registrationBean;
    }
}
