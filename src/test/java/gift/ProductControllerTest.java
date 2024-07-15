package gift;

import gift.controller.ProductController;
import gift.model.ProductDto;
import gift.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ProductControllerTest {
  @InjectMocks
  private ProductController productController;

  @Mock
  private ProductService productService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testGetAllProducts() {
    ProductDto product1 = new ProductDto(1L,"Product 1", 1000, "http://example.com/product1.jpg");
    ProductDto product2 = new ProductDto(1L,"Product 2", 2000, "http://example.com/product2.jpg");

    Pageable pageable = PageRequest.of(0, 10);
    Page<ProductDto> productPage = new PageImpl<>(Arrays.asList(product1, product2), pageable, 2);

    when(productService.findAll(pageable)).thenReturn(productPage);

    ResponseEntity<Page<ProductDto>> response = productController.getAllProducts(pageable);
    Page<ProductDto> products = response.getBody();

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(products).hasSize(2);
    assertThat(products.getContent().get(0).getName()).isEqualTo("Product 1");
    assertThat(products.getContent().get(0).getPrice()).isEqualTo(1000);
    assertThat(products.getContent().get(0).getImageUrl()).isEqualTo("http://example.com/product1.jpg");
    assertThat(products.getContent().get(1).getName()).isEqualTo("Product 2");
    assertThat(products.getContent().get(1).getPrice()).isEqualTo(2000);
    assertThat(products.getContent().get(1).getImageUrl()).isEqualTo("http://example.com/product2.jpg");

    verify(productService, times(1)).findAll(pageable);
  }

  @Test
  public void testGetProductById() {
    ProductDto product = new ProductDto(1L,"Product 1", 1000, "http://example.com/product1.jpg");

    when(productService.findById(1L)).thenReturn(Optional.of(product));

    ResponseEntity<ProductDto> response = productController.getProductById(1L);
    ProductDto result = response.getBody();

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(result).isNotNull();
    assertThat(result.getName()).isEqualTo("Product 1");
    assertThat(result.getPrice()).isEqualTo(1000);
    assertThat(result.getImageUrl()).isEqualTo("http://example.com/product1.jpg");

    verify(productService, times(1)).findById(1L);
  }

  @Test
  public void testCreateProduct() {
    ProductDto product = new ProductDto(1L,"Product 1", 1000, "http://example.com/product1.jpg");

    when(productService.save(any(ProductDto.class))).thenReturn(product);

    ResponseEntity<ProductDto> response = productController.createProduct(product);
    ProductDto result = response.getBody();

    assertThat(response.getStatusCodeValue()).isEqualTo(201);
    assertThat(result).isNotNull();
    assertThat(result.getName()).isEqualTo("Product 1");
    assertThat(result.getPrice()).isEqualTo(1000);
    assertThat(result.getImageUrl()).isEqualTo("http://example.com/product1.jpg");

    verify(productService, times(1)).save(any(ProductDto.class));
  }

  @Test
  public void testUpdateProduct() {
    ProductDto product = new ProductDto(1L,"Updated Product", 1500, "http://example.com/updated.jpg");

    when(productService.updateProduct(anyLong(), any(ProductDto.class))).thenReturn(true);

    ResponseEntity<ProductDto> response = productController.updateProduct(1L, product);
    ProductDto result = response.getBody();

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(result).isNotNull();
    assertThat(result.getName()).isEqualTo("Updated Product");
    assertThat(result.getPrice()).isEqualTo(1500);
    assertThat(result.getImageUrl()).isEqualTo("http://example.com/updated.jpg");

    verify(productService, times(1)).updateProduct(anyLong(), any(ProductDto.class));
  }
}
