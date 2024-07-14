package gift.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Collections;

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
import org.springframework.validation.BindingResult;

import gift.controller.ProductController;
import gift.model.Product;
import gift.service.ProductService;

public class ProductTest {
	
	@Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllProducts() {
    	Pageable pageable = PageRequest.of(0, 10);
    	Page<Product> productPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
    	
        when(productService.getAllProducts(pageable)).thenReturn(productPage);

        ResponseEntity<Page<Product>> response = productController.getAllProducts(pageable);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(productPage, response.getBody());
        assertEquals(Collections.emptyList(), response.getBody().getContent());
    }

    @Test
    public void testGetProduct() {
        Product product = new Product("아이스 아메리카노 T", 4500, "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");

        when(productService.getProduct(1L)).thenReturn(product);

        ResponseEntity<Product> response = productController.getProduct(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testAddProduct() {
        Product product = new Product("아이스 아메리카노 T", 4500, "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");

        when(productService.createProduct(any(Product.class), any(BindingResult.class))).thenReturn(product);

        ResponseEntity<Product> response = (ResponseEntity<Product>) productController.addProduct(product, bindingResult);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product("아이스 아메리카노 T", 5000, "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");

        doNothing().when(productService).updateProduct(eq(1L), any(Product.class), any(BindingResult.class));

        ResponseEntity<String> response = productController.updateProduct(1L, product, bindingResult);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Product updated successfylly.", response.getBody());
    }

    @Test
    public void testDeleteProduct() {
        doNothing().when(productService).deleteProduct(1L);

        ResponseEntity<String> response = productController.deleteProduct(1L);

        assertEquals(204, response.getStatusCodeValue());
        assertEquals("Product deleted successfully.", response.getBody());
    }
}
