package gift;

import gift.entity.Product;
import gift.entity.User;
import gift.entity.Wish;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import gift.repository.WishRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    public ApplicationRunner initializer(ProductRepository productRepository,
        UserRepository userRepository, WishRepository wishRepository) {
        return args -> {
            // 유저 생성
            User user = new User("testuser@example.com", "password");
            userRepository.save(user);

            // 제품 더미 데이터 생성
            List<Product> products = new ArrayList<>();
            for (int i = 1; i <= 30; i++) {
                products.add(new Product("Product " + i, i * 100, "image" + i + ".jpg"));
            }
            productRepository.saveAll(products);

            // 위시리스트 더미 데이터 생성
            List<Wish> wishes = new ArrayList<>();
            for (int i = 1; i <= 30; i++) {
                wishes.add(new Wish(user, products.get(i - 1), i * 2));
            }
            wishRepository.saveAll(wishes);
        };
    }
}
