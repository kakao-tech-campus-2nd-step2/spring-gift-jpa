package gift;

import gift.model.Product;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void save() {
        Product product = Product.builder()
                .name("열라면")
                .price(1600)
                .imageurl("https://i.namu.wiki/i/fuvd7qkb8P6PA_sD5ufjgpKUhRgxxTrIWnkPIg5H_UAPMUaArn1U1DweD7T_f_8RVxTDjqaiFwKr-quURwc_eQ.webp")
                .build();

        Product savedProduct = productRepository.save(product);

        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("열라면");
    }
}
