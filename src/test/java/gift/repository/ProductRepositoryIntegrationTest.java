package gift.repository;

import gift.config.FilterConfig;
import gift.config.SpringConfig;
import gift.model.Product;
import gift.model.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryIntegrationTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    public void save() {
        ProductDTO product = new ProductDTO("abc", 123, "test.com");
        Product savedProduct = productRepository.save(product);

        Product findProduct = productRepository.findById(savedProduct.getId());
        assertThat(product.getName()).isEqualTo(findProduct.getName());
        assertThat(product.getPrice()).isEqualTo(findProduct.getPrice());
        assertThat(product.getImageUrl()).isEqualTo(findProduct.getImageUrl());
    }

    @Test
    public void delete() {
        ProductDTO product = new ProductDTO("abc", 123, "test.com");
        Product savedProduct = productRepository.save(product);

        boolean result = productRepository.delete(savedProduct.getId());
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void edit() {
        ProductDTO product = new ProductDTO("abc", 123, "test.com");
        Product savedProduct = productRepository.save(product);

        ProductDTO editProductForm = new ProductDTO("def", product.getPrice(),
                product.getImageUrl());

        Product editProduct = productRepository.edit(savedProduct.getId(), editProductForm);
        Product findProduct = productRepository.findById(savedProduct.getId());
        assertThat(findProduct.getName()).isEqualTo(editProduct.getName());
    }

    @Test
    public void findAll() {
        ProductDTO product1 = new ProductDTO("abc", 123, "test1.com");
        ProductDTO product2 = new ProductDTO("def", 234, "test2.com");
        ProductDTO product3 = new ProductDTO("ghi", 345, "test3.com");

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        List<Product> allProducts = productRepository.findAll();
        assertThat(allProducts.size()).isEqualTo(3);
    }

}
