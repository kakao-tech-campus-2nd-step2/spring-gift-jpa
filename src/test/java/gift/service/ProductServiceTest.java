package gift.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gift.dto.ProductDTO;
import gift.exception.InvalidProductPriceException;
import gift.exception.ProductNotFoundException;
import gift.model.Product;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import java.util.Optional;
import java.util.List;

public class ProductServiceTest {

    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    public void testGetAllProducts() {
        Product product = new Product(1L, "Test Product", 100, "test.jpg");
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductDTO> products = productService.getAllProducts();
        assertEquals(1, products.size());
        assertEquals("Test Product", products.get(0).name());
    }

    @Test
    public void testGetProductById() {
        Product product = new Product(1L, "Test Product", 100, "test.jpg");
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductDTO productDTO = productService.getProductById(1L);
        assertEquals("Test Product", productDTO.name());
    }

    @Test
    public void testGetProductByIdNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductById(1L);
        });

        assertEquals("상품을 다음의 id로 찾을 수 없습니다. id: 1", exception.getMessage());
    }

    @Test
    public void testAddProduct() {
        Product product = new Product(1L, "Test Product", 100, "test.jpg");
        ProductDTO productDTO = new ProductDTO(null, "Test Product", 100, "test.jpg");
        when(productRepository.create(any(Product.class))).thenReturn(product);

        ProductDTO createdProduct = productService.addProduct(productDTO);
        assertEquals("Test Product", createdProduct.name());
    }

    @Test
    public void testAddProductInvalidPrice() {
        ProductDTO productDTO = new ProductDTO(null, "Test Product", -100, "test.jpg");

        InvalidProductPriceException exception = assertThrows(InvalidProductPriceException.class, () -> {
            productService.addProduct(productDTO);
        });

        assertEquals("가격은 0 이상으로 설정되어야 합니다.", exception.getMessage());
    }

    @Test
    public void testUpdateProduct() {
        Product existingProduct = new Product(1L, "Old Product", 100, "old.jpg");
        Product updatedProduct = new Product(1L, "Updated Product", 200, "updated.jpg");
        ProductDTO productDTO = new ProductDTO(1L, "Updated Product", 200, "updated.jpg");

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.update(any(Product.class))).thenReturn(updatedProduct);

        ProductDTO result = productService.updateProduct(1L, productDTO);
        assertEquals("Updated Product", result.name());
        assertEquals(200, result.price());
    }

    @Test
    public void testUpdateProductNotFound() {
        ProductDTO productDTO = new ProductDTO(1L, "Updated Product", 200, "updated.jpg");

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.updateProduct(1L, productDTO);
        });

        assertEquals("상품을 다음의 id로 찾을 수 없습니다. id: 1", exception.getMessage());
    }

    @Test
    public void testDeleteProduct() {
        Product product = new Product(1L, "Test Product", 100, "test.jpg");
        when(productRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productRepository).delete(1L);

        productService.deleteProduct(1L);
        verify(productRepository, times(1)).delete(1L);
    }

    @Test
    public void testDeleteProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.deleteProduct(1L);
        });

        assertEquals("상품을 다음의 id로 찾을 수 없습니다. id: 1", exception.getMessage());
    }
}
