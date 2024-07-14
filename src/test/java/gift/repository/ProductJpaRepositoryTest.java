package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.model.product.Product;
import gift.repository.product.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
public class ProductJpaRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 저장")
    void save() {
        // given
        Product product = new Product(1L, "product1", 1000, "product1.jpg");
        // when
        productRepository.save(product);
        // then
        assertAll(
            () -> assertThat(productRepository.findById(1L).get().getName()).isEqualTo(
                "product1"),
            () -> assertThat(productRepository.findById(1L).get().getPrice()).isEqualTo(1000),
            () -> assertThat(productRepository.findById(1L).get().getImageUrl()).isEqualTo(
                "product1.jpg")
        );
    }

    @Test
    @DisplayName("상품 삭제")
    void delete() {
        // given
        Product product = new Product(1L, "product1", 1000, "product1.jpg");
        productRepository.save(product);
        // when
        productRepository.deleteById(1L);
        // then
        assertThat(productRepository.findById(1L)).isEmpty();
    }

    @Test
    @DisplayName("상품 수정")
    void update() {
        // given
        Product product = new Product(1L, "product1", 1000, "product1.jpg");
        productRepository.save(product);
        // when
        product.update("product2", 2000, "product2.jpg");
        productRepository.save(product);
        // then
        assertAll(
            () -> assertThat(productRepository.findById(1L).get().getName()).isEqualTo(
                "product2"),
            () -> assertThat(productRepository.findById(1L).get().getPrice()).isEqualTo(2000),
            () -> assertThat(productRepository.findById(1L).get().getImageUrl()).isEqualTo(
                "product2.jpg")
        );
    }

    @Test
    @DisplayName("상품 조회")
    void findById() {
        // given
        Product product = new Product(1L, "product1", 1000, "product1.jpg");
        productRepository.save(product);
        // when
        Product findProduct = productRepository.findById(1L).get();
        // then
        assertAll(
            () -> assertThat(findProduct.getName()).isEqualTo("product1"),
            () -> assertThat(findProduct.getPrice()).isEqualTo(1000),
            () -> assertThat(findProduct.getImageUrl()).isEqualTo("product1.jpg")
        );
    }

    @Test
    @DisplayName("상품 가격 정렬 조회")
    void testFindAllOrderByPrice() {
        //given
        Product product1 = new Product(1L, "product1", 300, "product1.jpg");
        Product product2 = new Product(2L, "product2", 200, "product2.jpg");
        Product product3 = new Product(3L, "product3", 100, "product3.jpg");
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        //when
        Page<Product> result = productRepository.findAllOrderByPrice(PageRequest.of(0, 10));

        //then
        List<Product> products = result.getContent();
        assertThat(products.get(0).getPrice()).isEqualTo(100);
        assertThat(products.get(1).getPrice()).isEqualTo(200);
        assertThat(products.get(2).getPrice()).isEqualTo(300);
    }

    @Test
    @DisplayName("상품 이름으로 조회")
    void testFindByNameContaining() {
        //given
        Product product1 = new Product(1L, "sampleProduct1", 300, "product1.jpg");
        Product product2 = new Product(2L, "product2", 200, "product2.jpg");
        Product product3 = new Product(3L, "sampleProduct3", 100, "product3.jpg");
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        //when
        Page<Product> result = productRepository.findByNameContaining("sample",
            PageRequest.of(0, 10));

        //then
        List<Product> products = result.getContent();
        assertThat(products.size()).isEqualTo(2);
    }

}
