package gift.repository;

import static org.junit.jupiter.api.Assertions.*;

import gift.entity.Product;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void save() {
        //given
        Product expected = new Product("product", 100, "image.jpg");

        //when
        Product actual = productRepository.save(expected);

        //then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo("product"),
            () -> assertThat(actual.getPrice()).isEqualTo(100),
            () -> assertThat(actual.getImageUrl()).isEqualTo("image.jpg")
        );
    }

    @Test
    void findAll() {
        //given
        Product expected = new Product("product", 100, "image.jpg");
        Product expected1 = new Product("product1", 1000, "image1.jpg");
        productRepository.save(expected);
        productRepository.save(expected1);

        //when
        List<Product> actual = productRepository.findAll();

        //then
        assertAll(
            () -> assertThat(actual.size()).isEqualTo(2),
            () -> assertThat(actual).containsExactlyInAnyOrder(expected, expected1)

        );

    }

    @Test
    void findById() {
        //given
        Product expected = new Product("product", 100, "image.jpg");
        productRepository.save(expected);

        //when
        Optional<Product> actual = productRepository.findById(expected.getId());

        //then
        assertAll(
            ()->assertThat(actual).isPresent(),
            () -> assertThat(actual.get().getName()).isEqualTo("product"),
            () -> assertThat(actual.get().getPrice()).isEqualTo(100),
            () -> assertThat(actual.get().getImageUrl()).isEqualTo("image.jpg")

        );

    }

    @Test
    void update() {
        //given
        Product expected = new Product("product", 100, "image.jpg");
        Product updateProduct = productRepository.save(expected);
        Product productFromDB = productRepository.findById(updateProduct.getId()).get();
        expected.setName("updated");
        expected.setPrice(1000);
        expected.setImageUrl("updatedImage.jpg");

        //when
        Product actual = productRepository.save(productFromDB);

        //then
        assertAll(
            () -> assertThat(actual.getId()).isEqualTo(updateProduct.getId()),
            () -> assertThat(actual.getName()).isEqualTo("updated"),
            () -> assertThat(actual.getPrice()).isEqualTo(1000),
            () -> assertThat(actual.getImageUrl()).isEqualTo("updatedImage.jpg")

        );
    }

    @Test
    void deleteById() {
        //given
        Product product = new Product("product", 100, "image.jpg");
        productRepository.save(product);
        Long id = product.getId();

        //when
        productRepository.deleteById(id);
        Optional<Product> expected = productRepository.findById(id);

        assertThat(expected.isPresent()).isFalse();

    }


}