package gift.config;

import gift.filter.AuthFilter;
import gift.filter.LoginFilter;
import gift.repository.token.TokenSpringDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.servlet.Filter;

@Configuration
public class FilterConfiguration {

    private final TokenSpringDataJpaRepository tokenRepository;

    @Autowired
    public FilterConfiguration(TokenSpringDataJpaRepository tokenRepository) {
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
