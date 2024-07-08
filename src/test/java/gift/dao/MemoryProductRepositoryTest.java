package gift.dao;

import gift.product.domain.Product;
import gift.product.dao.MemoryProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class MemoryProductRepositoryTest {

    private final MemoryProductRepository productRepository = new MemoryProductRepository();

    @AfterEach
    public void afterEach() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("상품 저장 기능 테스트")
    void saveProduct() {
        Product product = new Product(1L, "product", 1000, "https://testshop.com");

        productRepository.save(product);

        Product result = productRepository.findById(product.getId()).get();
        Assertions.assertThat(result).isEqualTo(product);
    }

    @Test
    @DisplayName("상품 상세 조회 기능 테스트")
    void findById() {
        Product product1 = new Product(1L, "product1", 1000, "https://testshop.com");
        Product product2 = new Product(2L, "product2", 2000, "https://testshop.io");
        productRepository.save(product1);
        productRepository.save(product2);

        Product result = productRepository.findById(product1.getId()).get();

        org.junit.jupiter.api.Assertions.assertTrue(result.equals(product1) && !result.equals(product2));
    }

    @Test
    @DisplayName("상품 전체 조회 기능 테스트")
    void findAll() {
        Product product1 = new Product(1L, "product1", 1000, "https://testshop.com");
        Product product2 = new Product(2L, "product2", 2000, "https://testshop.io");
        productRepository.save(product1);
        productRepository.save(product2);

        List<Product> result = productRepository.findAll();

        Assertions.assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("단일 상품 삭제 기능 테스트")
    void deleteById() {
        Product product = new Product(1L, "product1", 1000, "https://testshop.com");
        productRepository.save(product);

        productRepository.deleteById(product.getId());

        Optional<Product> result = productRepository.findById(product.getId());
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("상품 전체 삭제 기능 테스트")
    void deleteAll() {
        Product product1 = new Product(1L, "product1", 1000, "https://testshop.com");
        Product product2 = new Product(2L, "product2", 2000, "https://testshop.io");
        productRepository.save(product1);
        productRepository.save(product2);

        productRepository.deleteAll();

        Optional<Product> result1 = productRepository.findById(product1.getId());
        Optional<Product> result2 = productRepository.findById(product2.getId());
        org.junit.jupiter.api.Assertions.assertTrue(result1.isEmpty() && result2.isEmpty());
    }
}