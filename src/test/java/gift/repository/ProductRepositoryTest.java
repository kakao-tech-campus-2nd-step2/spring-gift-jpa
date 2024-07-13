package gift.repository;

import gift.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void updateById() {
        // given
        String name = "name1";
        int price = 1000;
        String imageUrl = "imageUrl1";
        String imageUrl2 = "imageUrl2";
        Product product = new Product(name, price, imageUrl);
        Product original = productRepository.save(product);

        // when
        Product updated = productRepository.findById(original.getId()).orElse(null);
        assert updated != null;
        updated.updateProduct(name, price, imageUrl2);
        Product actual = productRepository.save(updated);

        // then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo(name);
        assertThat(actual.getPrice()).isEqualTo(price);
        assertThat(actual.getImageUrl()).isEqualTo(imageUrl2);
        assertThat(actual.getCreatedAt()).isNotNull();
        assertThat(actual.getUpdatedAt()).isNotNull();
    }

    @Test
    void save() {
        // given
        String name = "name1";
        int price = 1000;
        String imageUrl = "imageUrl1";
        Product product = new Product(name, price, imageUrl);

        // when
        Product actual = productRepository.save(product);

        // then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo(name);
        assertThat(actual.getPrice()).isEqualTo(price);
        assertThat(actual.getImageUrl()).isEqualTo(imageUrl);
        assertThat(actual.getCreatedAt()).isNotNull();
        assertThat(actual.getUpdatedAt()).isNotNull();
    }

}