package gift.product.config;

import gift.product.JwtCookieToHeaderInterceptor;
import gift.product.TokenValidationInterceptor;
import gift.product.repository.AuthRepository;
import gift.product.repository.JpaAuthRepository;
import gift.product.repository.JpaProductRepository;
import gift.product.repository.JpaWishRepository;
import gift.product.repository.ProductRepository;
import gift.product.repository.WishRepository;
import gift.product.service.AuthService;
import gift.product.service.ProductService;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final EntityManager em;

    private final ProductRepository productRepository;

    public WebConfig(EntityManager em, ProductRepository productRepository) {
        this.em = em;
        this.productRepository = productRepository;
    }

    @Bean
    public ProductService productService() {
        return new ProductService(productRepository);
    }

    @Bean
    public WishRepository wishRepository() {
        return new JpaWishRepository(em);
    }

    @Bean
    public AuthRepository authRepository() {
        return new JpaAuthRepository(em);
    }

    @Bean
    public TokenValidationInterceptor tokenValidationInterceptor() {
        return new TokenValidationInterceptor(authRepository());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenValidationInterceptor())
            .order(2)
            .addPathPatterns("/admin/wishes/**")
            .addPathPatterns("/api/wishes/**");

        registry.addInterceptor(new JwtCookieToHeaderInterceptor())
            .order(1)
            .addPathPatterns("/admin/wishes/**");
    }
}
