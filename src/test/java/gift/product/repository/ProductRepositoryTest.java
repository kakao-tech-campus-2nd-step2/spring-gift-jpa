package gift.product.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import gift.product.entity.Product;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayName("상품 리파지토리 테스트")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        // 임의의 상품 3개
        List<Product> products = List.of(
                new Product("상품1", 1000, "keyboard.png"),
                new Product("상품2", 2000, "mouse.png"),
                new Product("상품3", 3000, "monitor.png")
        );

        productRepository.saveAll(products);
    }
    
    @Test
    @DisplayName("상품 생성")
    void addProduct() {
        //given
        Product product = new Product("상품4", 4000, "headphone.png");
        
        //when
        Product savedProduct = productRepository.save(product);
        
        //then
        assertAll(
                () -> assertNotNull(savedProduct.getId()),
                () -> assertEquals(product.getName(), savedProduct.getName()),
                () -> assertEquals(product.getPrice(), savedProduct.getPrice()),
                () -> assertEquals(product.getImageUrl(), savedProduct.getImageUrl())
        );
    }

    @Test
    @DisplayName("상품 조회")
    void findProduct() {
        //given
        Product product = new Product("상품5", 5000, "speaker.png");
        productRepository.save(product);

        //when
        List<Product> products = productRepository.findAll();

        //then
        assertAll(
                () -> assertEquals(4, products.size()),
                () -> assertEquals("상품1", products.get(0).getName()),
                () -> assertEquals("상품2", products.get(1).getName()),
                () -> assertEquals("상품3", products.get(2).getName()),
                () -> assertEquals("상품5", products.get(3).getName())
        );

        // 존재하지 않는 상품 조회
        assertFalse(productRepository.findById(100L).isPresent());
    }

    @Test
    @DisplayName("상품 수정")
    void updateProduct() {
        //given
        Product product = new Product("상품6", 6000, "webcam.png");
        Product savedProduct = productRepository.save(product);

        //when
        savedProduct.setName("상품7");
        savedProduct.setPrice(7000);
        savedProduct.setImageUrl("camera.png");

        Product updatedProduct = productRepository.findById(savedProduct.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        //then
        assertAll(
                () -> assertEquals(savedProduct.getId(), updatedProduct.getId()),
                () -> assertEquals("상품7", updatedProduct.getName()),
                () -> assertEquals(7000, updatedProduct.getPrice()),
                () -> assertEquals("camera.png", updatedProduct.getImageUrl())
        );
    }

    @Test
    @DisplayName("상품 삭제")
    void deleteProduct() {
        //given
        Product product = new Product("상품6", 6000, "webcam.png");
        Product savedProduct = productRepository.save(product);

        //when
        productRepository.deleteById(savedProduct.getId());

        //then
        assertFalse(productRepository.findById(savedProduct.getId()).isPresent());
    }
}