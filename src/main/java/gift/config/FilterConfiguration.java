package gift.config;

import gift.repository.TokenRepository;
import gift.filter.AuthFilter;
import gift.filter.LoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.servlet.Filter;

@Configuration
public class FilterConfiguration {

    private final TokenRepository tokenRepository;

    @Autowired
    public FilterConfiguration(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Bean(name = "authFilterBean")
    public FilterRegistrationBean<Filter> authFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new AuthFilter(tokenRepository));
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    @Bean(name = "loginFilterBean")
    public FilterRegistrationBean<Filter> loginFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginFilter(tokenRepository));
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/members/login");
        return filterRegistrationBean;
    }
}
