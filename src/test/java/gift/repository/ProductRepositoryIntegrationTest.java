package gift.repository;

import gift.model.Product;
import gift.model.ProductDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductRepositoryIntegrationTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    public void save() {
        // given
        ProductDTO product = makeProducts(1).get(0);

        // when
        Product savedProduct = productRepository.save(product);
        Product findProduct = productRepository.findById(savedProduct.getId());

        // then
        Assertions.assertAll(
                () -> assertThat(product.getName()).isEqualTo(findProduct.getName()),
                () -> assertThat(product.getPrice()).isEqualTo(findProduct.getPrice()),
                () -> assertThat(product.getImageUrl()).isEqualTo(findProduct.getImageUrl())
        );
    }

    @Test
    public void delete() {
        // given
        ProductDTO product = makeProducts(1).get(0);
        Product savedProduct = productRepository.save(product);

        // when
        boolean result = productRepository.delete(savedProduct.getId());

        // then
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void edit() {
        // given
        List<ProductDTO> products = makeProducts(2);
        Product savedProduct = productRepository.save(products.get(0));

        // when
        ProductDTO editProductForm = products.get(1);
        Product editProduct = productRepository.edit(savedProduct.getId(), editProductForm);
        Product findProduct = productRepository.findById(savedProduct.getId());

        // then
        assertThat(findProduct.getName()).isEqualTo(editProduct.getName());
    }

    @Test
    public void findAll() {
        // given
        List<ProductDTO> products = makeProducts(3);

        // when
        for (ProductDTO product : products) {
            productRepository.save(product);
        }
        List<Product> allProducts = productRepository.findAll();

        // then
        assertThat(allProducts.size()).isEqualTo(3);
    }

    public List<ProductDTO> makeProducts(int n) {
        ArrayList<ProductDTO> products = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            products.add(new ProductDTO("name" + Integer.toString(i), i, "imageUrl" + Integer.toString(i)));
        }
        return products;
    }
}
