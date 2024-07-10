package gift;

import gift.model.Product;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.shouldHaveThrown;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository products;

    @BeforeEach
    void setUp(){
        products.deleteAll();
        products.save(new Product("Product1", 1000, "1.img"));
        products.save(new Product("Product2", 5000, "2.img"));
        products.save(new Product("Product3", 15000, "3.img"));
    }

    @Test
    void save(){
        Product expected = new Product("newProduct", 1000, "newimg.img");
        Product actual = products.save(expected);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getAllProduct(){
        List<Product> actual = products.findAll();
        List<Product> expected = List.of(new Product("Product1", 1000, "1.img"),
                new Product("Product2", 5000, "2.img"),
                new Product("Product3", 15000, "3.img"));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getProductbyName(){
        Product expected = new Product("Product1", 1000, "1.img");
        Product actual = products.findByName("Product1");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getProductbyID(){
        Product expected = products.findByName("Product1");
        Optional<Product> actualOptional = products.findById(expected.getId());
        Product actual = null;
        if (actualOptional.isPresent()){
            actual = actualOptional.get();
        }
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void deleteByID(){
        Product product = products.findByName("Product1");
        products.deleteById(product.getId());
        assertThat(products.existsById(product.getId())).isFalse();
    }
}