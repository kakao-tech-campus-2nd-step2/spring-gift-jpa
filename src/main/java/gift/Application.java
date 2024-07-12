package gift;

import gift.entity.Product;
import gift.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    @Autowired
    private ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ApplicationRunner initializer() {
        return args -> {
            List<Product> products = new ArrayList<>();
            for (int i = 1; i <= 30; i++) {
                products.add(new Product("Product " + i, i * 100, "img" + i + ".jpg"));
            }
            productRepository.saveAll(products);
        };
    }
}
