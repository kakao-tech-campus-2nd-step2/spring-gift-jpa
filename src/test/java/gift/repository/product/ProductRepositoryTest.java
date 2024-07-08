package gift.repository.product;

import gift.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("저장 테스트")
    void 저장_테스트(){
        //given
        Product product = new Product.Builder()
                .name("테스트")
                .price(123)
                .imageUrl("abc.png")
                .build();

        //when
        Product savedProduct = productRepository.save(product);

        //then
        assertAll(
                () -> assertThat(savedProduct.getId()).isNotNull(),
                () -> assertThat(savedProduct.getName()).isEqualTo(product.getName()),
                () -> assertThat(savedProduct.getPrice()).isEqualTo(product.getPrice()),
                () -> assertThat(savedProduct.getImageUrl()).isEqualTo(product.getImageUrl())
        );
    }

    @Test
    @DisplayName("단건 조회")
    void 단건_조회_테스트(){
        //given
        Product product = new Product.Builder()
                .name("테스트")
                .price(123)
                .imageUrl("abc.png")
                .build();

        Product savedProduct = productRepository.save(product);

        //when
        Product findProduct = productRepository.findById(savedProduct.getId()).get();

        //then
        assertAll(
                () -> assertThat(findProduct.getId()).isNotNull(),
                () -> assertThat(findProduct.getName()).isEqualTo(product.getName()),
                () -> assertThat(findProduct.getPrice()).isEqualTo(product.getPrice()),
                () -> assertThat(findProduct.getImageUrl()).isEqualTo(product.getImageUrl())
        );
    }

    @Test
    @DisplayName("전체 조회")
    void 전체_조회_테스트(){
        //given
        Product product1 = new Product.Builder()
                .name("테스트1")
                .price(123)
                .imageUrl("abc.png")
                .build();

        Product product2 = new Product.Builder()
                .name("테스트2")
                .price(123)
                .imageUrl("abc.png")
                .build();

        Product product3 = new Product.Builder()
                .name("테스트3")
                .price(123)
                .imageUrl("abc.png")
                .build();

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        //when
        List<Product> products = productRepository.findAll();

        //then
        assertThat(products.size()).isEqualTo(3);
    }

}