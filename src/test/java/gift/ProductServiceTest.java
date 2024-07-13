package gift;

import gift.model.Product;
import gift.model.ProductDto;
import gift.repository.ProductRepository;
import gift.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private ProductService productService;

  @Test
  public void testFindAll() {
    Product product1 = new Product("Product 1", 1000, "http://example.com/product1.jpg");
    Product product2 = new Product("Product 2", 2000, "http://example.com/product2.jpg");

    Pageable pageable = PageRequest.of(0, 10);
    Page<Product> productPage = new PageImpl<>(Arrays.asList(product1, product2), pageable, 2);

    when(productRepository.findAll(pageable)).thenReturn(productPage);

    var products = productService.findAll(pageable);

    assertEquals(2, products.getTotalElements());
    assertEquals("Product 1", products.getContent().get(0).getName());
    assertEquals("Product 2", products.getContent().get(1).getName());

    verify(productRepository, times(1)).findAll(pageable);
  }

  @Test
  public void testFindById() {
    Product product = new Product("Product 1", 1000, "http://example.com/product1.jpg");

    when(productRepository.findById(1L)).thenReturn(Optional.of(product));

    var productDto = productService.findById(1L);

    assertTrue(productDto.isPresent());
    assertEquals("Product 1", productDto.get().getName());

    verify(productRepository, times(1)).findById(1L);
  }

  @Test
  public void testSave() {
    ProductDto productDto = new ProductDto(1L,"Product 1", 1000, "http://example.com/product1.jpg");
    Product product = new Product("Product 1", 1000, "http://example.com/product1.jpg");

    when(productRepository.save(any(Product.class))).thenReturn(product);

    var savedProductDto = productService.save(productDto);

    assertEquals("Product 1", savedProductDto.getName());

    verify(productRepository, times(1)).save(any(Product.class));
  }

  @Test
  public void testUpdateProduct() {
    Product product = new Product("Product 1", 1000, "http://example.com/product1.jpg");
    ProductDto productDto = new ProductDto(1L,"Updated Product", 1500, "http://example.com/updated.jpg");

    when(productRepository.findById(1L)).thenReturn(Optional.of(product));

    boolean isUpdated = productService.updateProduct(1L, productDto);

    assertTrue(isUpdated);
    assertEquals("Updated Product", product.getName());

    verify(productRepository, times(1)).findById(1L);
    verify(productRepository, times(1)).save(product);
  }

  @Test
  public void testDeleteById() {
    productService.deleteById(1L);

    verify(productRepository, times(1)).deleteById(1L);
  }
}
