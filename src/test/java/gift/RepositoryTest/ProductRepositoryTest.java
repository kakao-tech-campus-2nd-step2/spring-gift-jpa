package gift.RepositoryTest;

import gift.Model.Product;
import gift.Repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    void saveTest(){
        Product product = new Product("아메리카노", 4000,"아메리카노url");
        assertThat(product.getId()).isNull();
        var actual = productRepository.save(product);
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void findByIdTest(){
        Product product = productRepository.save(new Product("아메리카노", 4000, "아메리카노url"));
        Optional<Product> actual = productRepository.findById(product.getId());
        assertThat(actual.get().getName()).isEqualTo("아메리카노");
    }

    @Test
    void updateTest(){
        Product product1 = productRepository.save(new Product("아메리카노", 4000, "아메리카노url"));
        Optional<Product> optionalProduct = productRepository.findById(product1.getId());
        Product product = optionalProduct.get();
        product.setName("카푸치노");

        var actual = productRepository.findById(product.getId());
        assertThat(actual.get().getName()).isEqualTo("카푸치노");
    }

    @Test
    void deleteTest(){
        Product product1 = productRepository.save(new Product("아메리카노", 4000, "아메리카노url"));
        Optional<Product> optionalProduct = productRepository.findById(product1.getId());
        productRepository.deleteById(optionalProduct.get().getId());
        Optional<Product> actual  = productRepository.findById(optionalProduct.get().getId());
        assertThat(actual).isEmpty();
    }
}
