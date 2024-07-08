package gift.config;

import gift.product.persistence.ProductDao;
import gift.product.persistence.ProductRepository;
import gift.user.persistence.UserDao;
import gift.user.persistence.UserRepository;
import gift.wish.persistence.WishDao;
import gift.wish.persistence.WishRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class RepositoryConfig {
    @Bean
    public ProductRepository productRepository(JdbcTemplate jdbcTemplate) {
        return new ProductDao(jdbcTemplate);
    }

    @Bean
    public UserRepository userRepository(JdbcTemplate jdbcTemplate) {
        return new UserDao(jdbcTemplate);
    }

    @Bean
    public WishRepository wishRepository(JdbcTemplate jdbcTemplate) {
        return new WishDao(jdbcTemplate);
    }
}
