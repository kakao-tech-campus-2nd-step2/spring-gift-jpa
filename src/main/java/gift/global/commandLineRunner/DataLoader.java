package gift.global.commandLineRunner;

import gift.domain.product.JpaProductRepository;
import gift.domain.product.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final JpaProductRepository jpaProductRepository;

    public DataLoader(JpaProductRepository jpaProductRepository) {
        this.jpaProductRepository = jpaProductRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        jpaProductRepository.save(new Product("아이스 아메리카노 T", 4500, "https://example.com/image.jpg"));
        jpaProductRepository.save(new Product("아이스 카푸치노 M", 4700, "https://example.com/image.jpg"));
        jpaProductRepository.save(new Product("핫 말차라떼 L", 6800, "https://example.com/image.jpg"));
    }
}
