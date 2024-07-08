package gift.config;

import gift.aop.TimeTraceAop;
import gift.controller.AdminController;
import gift.repository.*;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final EntityManager em;

    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public AdminController adminController() {
        return new AdminController(productRepository());
    }

    @Bean
    public ProductRepository productRepository() {
        return new JpaProductRepository(em);
    }

    @Bean
    public UserRepository userRepository() {
        return new JpaUserRepository(em);
    }

    @Bean
    public WishlistRepository wishlistRepository() {
        return new JpaWishlistRepository(em);
    }

//    @Bean
//    public TimeTraceAop timeTraceAop() {
//        return new TimeTraceAop();
//    }
}
