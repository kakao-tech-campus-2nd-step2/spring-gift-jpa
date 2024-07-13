package gift.repository;

import gift.entity.Product;
import gift.entity.ProductDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void save() {
        // given
        List<ProductDTO> products = makeProducts(1);
        ProductDTO actual = products.get(0);

        // when
        Product expected = productRepository.save(new Product(actual));

        // then
        Assertions.assertAll(
                () -> assertThat(expected.getId()).isNotNull(),
                () -> assertThat(expected.getName()).isEqualTo(actual.getName()),
                () -> assertThat(expected.getPrice()).isEqualTo(actual.getPrice()),
                () -> assertThat(expected.getImageurl()).isEqualTo(actual.getImageurl())
        );
    }

    @Test
    void find() {
        // given
        List<ProductDTO> products = makeProducts(1);
        ProductDTO actual = products.get(0);

        // when
        Product saved = productRepository.save(new Product(actual));
        Long id = saved.getId();
        Product expected = productRepository.findById(id).get();

        // then
        Assertions.assertAll(
                () -> assertThat(expected.getId()).isNotNull(),
                () -> assertThat(expected.getName()).isEqualTo(actual.getName()),
                () -> assertThat(expected.getPrice()).isEqualTo(actual.getPrice()),
                () -> assertThat(expected.getImageurl()).isEqualTo(actual.getImageurl())
        );
    }

    @Test
    void edit() {
        // given
        List<ProductDTO> products = makeProducts(2);
        ProductDTO product = products.get(0);
        Product saved = productRepository.save(new Product(product));
        Long id = saved.getId();

        // when
        ProductDTO actual = products.get(1);
        saved.setName(actual.getName());
        saved.setPrice(actual.getPrice());
        saved.setImageurl(actual.getImageurl());
        Product expected = productRepository.save(saved);

        // then
        Assertions.assertAll(
                () -> assertThat(expected.getId()).isNotNull(),
                () -> assertThat(expected.getName()).isEqualTo(actual.getName()),
                () -> assertThat(expected.getPrice()).isEqualTo(actual.getPrice()),
                () -> assertThat(expected.getImageurl()).isEqualTo(actual.getImageurl())
        );
    }

    @Test
    void delete() {
        // given
        List<ProductDTO> products = makeProducts(1);
        ProductDTO product = products.get(0);
        Product saved = productRepository.save(new Product(product));
        Long id = saved.getId();

        // when
        productRepository.delete(saved);
        Optional<Product> expect = productRepository.findById(id);

        // then
        assertThat(expect.isPresent()).isFalse();
    }

    @Test
    void findAll() {
        // given
        List<ProductDTO> products = makeProducts(2);
        for (ProductDTO product : products) {
            productRepository.save(new Product(product));
        }

        // when
        List<Product> expect = productRepository.findAll();

        // then
        assertThat(expect.size()).isEqualTo(2);
    }

    public List<ProductDTO> makeProducts(int n) {
        ArrayList<ProductDTO> products = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            products.add(new ProductDTO("name" + Integer.toString(i), i, "imageUrl" + Integer.toString(i)));
        }
        return products;
    }
}
