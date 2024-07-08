package gift.config;

import gift.user.service.JwtProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilConfig {
    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider();
    }
}
