package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.Model.Product;
import gift.Repository.ProductRepository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void findAll(){
        Product expected1 = new Product(1L,"A",1000,"A");
        Product expected2 = new Product(2L,"B",2000,"B");

        productRepository.save(expected1);
        productRepository.save(expected2);
        List<Product> products = productRepository.findAll();

        Product actual1 = products.get(0);
        Product actual2 = products.get(1);

        assertAll(
            () -> assertThat(actual1.getId()).isNotNull(),
            () -> assertThat(actual1.getName()).isEqualTo(expected1.getName()),
            () -> assertThat(actual1.getPrice()).isEqualTo(expected1.getPrice()),
            () -> assertThat(actual1.getImageUrl()).isEqualTo(expected1.getImageUrl()),

            () -> assertThat(actual2.getId()).isNotNull(),
            () -> assertThat(actual2.getName()).isEqualTo(expected2.getName()),
            () -> assertThat(actual2.getPrice()).isEqualTo(expected2.getPrice()),
            () -> assertThat(actual2.getImageUrl()).isEqualTo(expected2.getImageUrl())
        );

    }
    @Test
    void findProductById(){
        Product expected = new Product(1L,"A",1000,"A");
        Product product = productRepository.save(expected);
        Product actual = productRepository.findProductById(product.getId());
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl())
        );
    }
    @Test
    void save(){
        Product expected = new Product(1L,"A",1000,"A");
        Product actual = productRepository.save(expected);
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl())
        );
    }
    @Test
    void deleteById(){
        Product expected = new Product(1L,"A",1000,"A");
        Product product = productRepository.save(expected);
        productRepository.deleteById(product.getId());
        assertAll(
            () -> assertThat(productRepository.findProductById(product.getId())).isNull()
        );
    }
}
