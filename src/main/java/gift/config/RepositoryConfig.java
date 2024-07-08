package gift.config;

import gift.model.dao.ProductDao;
import gift.model.dao.UserDao;
import gift.model.dao.WishDao;
import gift.model.repository.ProductRepository;
import gift.model.repository.UserRepository;
import gift.model.repository.WishRepository;
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
