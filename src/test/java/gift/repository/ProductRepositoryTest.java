package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import gift.model.Product;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.hibernate.exception.DataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;
    Product product = new Product("productA", 1000, "https://a.com");

    @Test
    @DisplayName("Product insert 테스트")
    void insert() {
        assertThat(product.getId()).isNull();
        Product saveProduct = productRepository.save(product);
        assertThat(saveProduct.getId()).isNotNull();
    }

    @Test
    @DisplayName("Product update 테스트")
    void update() {
        Product savedProduct = productRepository.save(product);
        Long id = productRepository.findById(savedProduct.getId()).get().getId();
        Product modifiedProduct = new Product(id, "productAB", 5000, "https://b.com");
        Product modifiedSavedProduct = productRepository.save(modifiedProduct);

        assertThat(modifiedSavedProduct.getId()).isEqualTo(id);
        assertThat(modifiedSavedProduct.getName()).isEqualTo(modifiedProduct.getName());
        assertThat(modifiedSavedProduct.getPrice()).isEqualTo(modifiedProduct.getPrice());
        assertThat(modifiedSavedProduct.getImageUrl()).isEqualTo(modifiedProduct.getImageUrl());
    }

    @Test
    @DisplayName("Product delete 테스트")
    void delete() {
        Product savedProduct = productRepository.save(product);
        productRepository.delete(savedProduct);

        // 삭제된 상품을 조회할 수 없어야 한다.
        assertThat( productRepository.findById(savedProduct.getId())).isEmpty();
    }

    @Test
    @DisplayName("Product findAll 테스트")
    void findAll() {

        List<Product> products = new ArrayList<>();
        IntStream.range(0, 10)
            .forEach( i -> {
                products.add(new Product("product"+i, 1000, "https://a.com"));
            });
        productRepository.saveAll(products);

        List<Product> findProducts = productRepository.findAll();
        assertThat(findProducts).hasSize(10);

        IntStream.range(0, 10)
            .forEach( i -> {
                Product p = findProducts.get(i);
                assertThat(p.getId()).isNotNull();
                assertThat(p).isEqualTo(products.get(i));
            });

    }

    @Test
    @DisplayName("Product findById 테스트")
    void findById() {
        Product savedProduct = productRepository.save(product);
        Product findProduct = productRepository.findById(savedProduct.getId()).get();

        assertThat(findProduct).isEqualTo(savedProduct);

        //존재하지 않는 상품 조회
        Long notExisted = 999L;
        assertThatThrownBy(
            () -> productRepository.findById(notExisted).get())
            .isInstanceOf(NoSuchElementException.class);

    }


}