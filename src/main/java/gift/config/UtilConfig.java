package gift.config;

import gift.model.JwtProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilConfig {
    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider();
    }
}
