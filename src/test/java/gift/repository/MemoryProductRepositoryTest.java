package gift.repository;

import gift.model.Product;
import gift.model.ProductDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoryProductRepositoryTest {

    MemoryProductRepository repository = new MemoryProductRepository();

    @AfterEach
    public void afterEach() {
        repository.clearDB();
    }

    @Test
    public void save() {
        ProductDTO product = new ProductDTO("abc", 123, "www.test.com");

        Product savedProduct = repository.save(product);

        Product result = repository.findById(savedProduct.getId());

        assertThat(result).isEqualTo(savedProduct);
    }

    @Test
    @DisplayName("existing id")
    public void delete_exist() {
        ProductDTO product = new ProductDTO("abc", 123, "www.test.com");
        Product savedProduct = repository.save(product);

        boolean result = repository.delete(savedProduct.getId());

        assertThat(result).isEqualTo(true);
    }

    @Test
    @DisplayName("nonexistent id")
    public void delete_nonexistent() {
        ProductDTO product = new ProductDTO("abc", 123, "www.test.com");
        repository.save(product);

        boolean result = repository.delete(0L);

        assertThat(result).isEqualTo(false);
    }

    @Test
    public void edit() {
        ProductDTO product = new ProductDTO("abc", 123, "www.test.com");
        Product savedProduct = repository.save(product);

        Product editedProduct = repository.edit(savedProduct.getId(),
            new ProductDTO("def", 123, "www.test.com"));
        Product product2 = repository.findById(editedProduct.getId());

        assertThat(product2.getName()).isEqualTo("def");
    }

    @Test
    public void findAll() {
        ProductDTO product1 = new ProductDTO("abc", 123, "www.test1.com");
        ProductDTO product2 = new ProductDTO("def", 456, "www.test2.com");

        repository.save(product1);
        repository.save(product2);

        List<Product> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
