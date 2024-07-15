//package gift;
//
//import gift.product.model.Product;
//import gift.product.repository.ProductRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class ProductRepositoryTest {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Test
//    public void saveProduct() {
//        // Given
//        Product product = new Product("test", 100, "https://www.google.com");
//
//        // When
//        product = productRepository.save(product);
//
//        // Then
//        assertThat(productRepository.findById(product.id())).isPresent();
//    }
//
//    @Test
//    public void findProductById() {
//        // Given
//        Product product = new Product("test", 100, "https://www.google.com");
//        product = productRepository.save(product);
//
//        // When
//        Product foundProduct = productRepository.findById(product.id()).orElse(null);
//
//        // Then
//        assertThat(foundProduct).isNotNull();
//        assertThat(foundProduct.id()).isEqualTo(product.id());
//    }
//
//    @Test
//    public void deleteProduct() {
//        // Given
//        Product product = new Product("test", 100, "https://www.google.com");
//        product = productRepository.save(product);
//
//        // When
//        productRepository.deleteById(product.id());
//
//        // Then
//        assertThat(productRepository.findById(product.id())).isEmpty();
//    }
//}