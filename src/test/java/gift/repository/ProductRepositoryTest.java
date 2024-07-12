package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.domain.Product;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;


    @Test
    @DisplayName("아이디로 찾기 테스트")
    void findById() {
        Product product = new Product("kakao", 1000, "img");
        Product savedProduct = productRepository.save(product);

        Optional<Product> byId = productRepository.findById(savedProduct.getId());

        assertThat(byId).isPresent();
        assertThat(byId.get().getId()).isEqualTo(savedProduct.getId());
        assertThat(byId.get().getName()).isEqualTo("kakao");
        assertThat(byId.get().getPrice()).isEqualTo(1000);
        assertThat(byId.get().getImageUrl()).isEqualTo("img");
    }

    @Test
    @DisplayName("전체 상품 테스트")
    void findALL() {
        Product product1 = new Product("kakao", 1000, "img1");
        Product product2 = new Product("pnu", 2000, "img2");
        Product product3 = new Product("uni", 3000, "img3");

        productRepository.saveAll(Arrays.asList(product1, product2, product3));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> all = productRepository.findAll(pageable);

        assertThat(all).isNotNull();
        assertThat(all.getContent()).hasSize(3);
        assertThat(all.getContent()).extracting(Product::getName)
            .containsExactlyInAnyOrder("kakao", "pnu", "uni");
        assertThat(all.getContent()).extracting(Product::getPrice)
            .containsExactlyInAnyOrder(1000.0, 2000.0, 3000.0);
        assertThat(all.getContent()).extracting(Product::getImageUrl)
            .containsExactlyInAnyOrder("img1", "img2", "img3");
        assertThat(all.getTotalElements()).isEqualTo(3);
        assertThat(all.getTotalPages()).isEqualTo(1);
    }

    @Test
    @DisplayName("존재하지 않는 ID로 찾기 테스트")
    void findByNonExistentId() {
        Optional<Product> byId = productRepository.findById(999L);
        assertThat(byId).isEmpty();
    }

    @Test
    @DisplayName("제품 업데이트 테스트")
    void updateProduct() {
        Product product = new Product("original", 1000, "img");
        Product savedProduct = productRepository.save(product);

        savedProduct.setName("updated");
        savedProduct.setPrice(2000);
        productRepository.save(savedProduct);

        Product updatedProduct = productRepository.findById(savedProduct.getId()).orElseThrow();
        assertThat(updatedProduct.getName()).isEqualTo("updated");
        assertThat(updatedProduct.getPrice()).isEqualTo(2000);
    }

    @Test
    @DisplayName("제품 삭제 테스트")
    void deleteProduct() {
        Product product = new Product("toDelete", 1000, "img");
        Product savedProduct = productRepository.save(product);

        productRepository.deleteById(savedProduct.getId());

        Optional<Product> deletedProduct = productRepository.findById(savedProduct.getId());
        assertThat(deletedProduct).isEmpty();
    }


}
