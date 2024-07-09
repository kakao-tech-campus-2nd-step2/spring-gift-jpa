package gift;

import gift.entity.Product;
import gift.entity.User;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner{
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Application(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        /*
         * dummy data 삽입
         */
        // Product dummy data
        productRepository.save(Product.builder()
                .name("Product A")
                .price(1000)
                .imageUrl("http://example.com/images/product_a.jpg")
                .build());

        productRepository.save(Product.builder()
            .name("Product B")
            .price(2000)
            .imageUrl("http://example.com/images/product_b.jpg")
            .build());

        productRepository.save(Product.builder()
            .name("Product C")
            .price(3000)
            .imageUrl("http://example.com/images/product_c.jpg")
            .build());

        productRepository.save(Product.builder()
            .name("Product D")
            .price(4000)
            .imageUrl("http://example.com/images/product_d.jpg")
            .build());

        productRepository.save(Product.builder()
            .name("Product E")
            .price(5000)
            .imageUrl("http://example.com/images/product_e.jpg")
            .build());

        // User dummy data
        userRepository.save(User.builder()
            .email("user1@example.com")
            .password("password1")
            .build());

        userRepository.save(User.builder()
            .email("user2@example.com")
            .password("password2")
            .build());

        userRepository.save(User.builder()
            .email("user3@example.com")
            .password("password3")
            .build());

        userRepository.save(User.builder()
            .email("user4@example.com")
            .password("password4")
            .build());

        userRepository.save(User.builder()
            .email("user5@example.com")
            .password("password5")
            .build());
//
//        jdbcTemplate.execute(
//            "INSERT INTO wishes (user_id, product_id, quantity) VALUES (1, 1, 2);"
//            + "INSERT INTO wishes (user_id, product_id, quantity) VALUES (1, 3, 1);"
//            + "INSERT INTO wishes (user_id, product_id, quantity) VALUES (2, 2, 1);"
//            + "INSERT INTO wishes (user_id, product_id, quantity) VALUES (3, 4, 3);"
//            + "INSERT INTO wishes (user_id, product_id, quantity) VALUES (4, 1, 1);"
//            + "INSERT INTO wishes (user_id, product_id, quantity) VALUES (4, 5, 2);"
//            + "INSERT INTO wishes (user_id, product_id, quantity) VALUES (5, 2, 1);"
//            + "INSERT INTO wishes (user_id, product_id, quantity) VALUES (5, 3, 1);"
//            + "INSERT INTO wishes (user_id, product_id, quantity) VALUES (5, 4, 1)"
//        );
    }
}
