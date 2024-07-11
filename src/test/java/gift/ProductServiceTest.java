package gift;

import gift.model.Product;
import gift.repository.ProductRepository;
import gift.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {

  @MockBean
  private ProductRepository productRepository;

  @Autowired
  private ProductService productService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testFindAll() {
    Product product1 = new Product();
    product1.update("아이스 카페 아메리카노 T", 4500, "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
    product1.setId(1L);

    Product product2 = new Product();
    product2.update("아이스 카페 라떼 T", 4500, "https://st.kakaocdn.net/product/gift/product.jpg");
    product2.setId(2L);

    when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

    List<Product> products = productService.findAll();
    assertEquals(2, products.size());
    verify(productRepository, times(1)).findAll();
  }

  @Test
  public void testFindById() {
    Product product = new Product();
    product.update("아이스 카페 아메리카노 T", 4500, "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
    product.setId(1L);

    when(productRepository.findById(1L)).thenReturn(Optional.of(product));

    Optional<Product> foundProduct = productService.findById(1L);
    assertTrue(foundProduct.isPresent());
    assertEquals(product.getId(), foundProduct.get().getId());
    verify(productRepository, times(1)).findById(1L);
  }

  @Test
  public void testSave() {
    Product product = new Product();
    product.update("Product 1", 1000, "http://example.com/product1.jpg");

    Product savedProduct = new Product();
    savedProduct.update("Product 1", 1000, "http://example.com/product1.jpg");
    savedProduct.setId(1L);

    when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

    Product result = productService.save(product);

    assertAll(
            () -> assertThat(result.getId()).isNotNull(),
            () -> assertThat(result.getName()).isEqualTo(product.getName()),
            () -> assertThat(result.getPrice()).isEqualTo(product.getPrice()),
            () -> assertThat(result.getImageUrl()).isEqualTo(product.getImageUrl())
    );

    verify(productRepository, times(1)).save(product);
  }
}
