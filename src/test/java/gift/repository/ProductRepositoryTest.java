package gift.repository;

import gift.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository products;

    @DisplayName("새로운 상품 저장")
    @Test
    void save(){
        Product expected = new Product("newProduct", 1000, "newimg.img");
        Product actual = products.save(expected);
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("모든 상품 리스트 반환")
    @Test
    void getAllProduct(){
        products.save(new Product("Product1", 1000, "1.img"));
        products.save(new Product("Product2", 5000, "2.img"));
        products.save(new Product("Product3", 15000, "3.img"));
        List<Product> actual = products.findAll();
        List<Product> expected = List.of(new Product("Product1", 1000, "1.img"),
                new Product("Product2", 5000, "2.img"),
                new Product("Product3", 15000, "3.img"));
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("이름(unique하다고가정)으로 해당하는 객체 반환")
    @Test
    void getProductByName(){
        Product expected = new Product("Product1", 1000, "1.img");
        products.save(expected);
        Product actual = products.findByName("Product1").orElseThrow();
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("ID로 상품 반환")
    @Test
    void getProductByID(){
        products.save(new Product("Product1", 1000, "1.img"));
        Product expected = products.findByName("Product1").orElseThrow();
        Product actual = products.findById(expected.getId()).orElseThrow();
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("ID로 상품 삭제")
    @Test
    void deleteByID(){
        products.save(new Product("Product1", 1000, "1.img"));
        Product product = products.findByName("Product1").orElseThrow();
        products.deleteById(product.getId());
        assertThat(products.existsById(product.getId())).isFalse();
    }
}