package gift.config;

import gift.domain.model.entity.Product;
import gift.domain.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(ProductRepository productRepository) {
        return args -> {
            Random random = new Random();
            List<Product> products = new ArrayList<>();

            for (int i = 1; i <= 100; i++) {
                Product product = new Product(
                    "Product " + i,
                    random.nextLong(100000) + 1000,
                    "http://example.com/image" + i + ".jpg"
                );
                products.add(product);
            }

            productRepository.saveAll(products);
            System.out.println("초기 데이터 생성 완료: 100개의 상품이 추가되었습니다.");
        };
    }
}