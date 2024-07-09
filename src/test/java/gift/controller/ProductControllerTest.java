package gift.controller;

import gift.dto.request.ProductRequest;
import gift.domain.Product;
import gift.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getProducts() throws Exception {
        Product product1 = new Product(1L, "Product 1", 100L, "url-1");
        Product product2 = new Product(2L, "Product 2", 200L, "url-2");

        when(productService.findProducts())
                .thenReturn(Arrays.asList(product1, product2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
                .andExpect(MockMvcResultMatchers.view().name("product-list"));
    }

    @Test
    void getProduct() throws Exception {
        Long productId = 1L;
        Product product = new Product(productId, "Test Product", 150L, "test-url");

        when(productService.findOne(productId))
                .thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/{id}", productId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
                .andExpect(MockMvcResultMatchers.view().name("product-list"));
    }

    @Test
    void newProductForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("product"))
                .andExpect(MockMvcResultMatchers.view().name("product-add-form"));
    }

    @Test
    void addProduct() throws Exception {
        ProductRequest productRequest = new ProductRequest("New Product", 100L, "new-product-url");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
                        .param("name", productRequest.getName())
                        .param("price", String.valueOf(productRequest.getPrice()))
                        .param("imageUrl", productRequest.getImageUrl()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/api/products"));

        verify(productService, times(1)).register(any(ProductRequest.class));
    }

    @Test
    void editProductForm() throws Exception {
        Long productId = 1L;
        Product product = new Product(productId, "Editable Product", 200L, "edit-url");

        when(productService.findOne(productId))
                .thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/edit/{id}", productId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("product"))
                .andExpect(MockMvcResultMatchers.view().name("product-edit-form"));
    }

    @Test
    void updateProduct() throws Exception {
        Long productId = 1L;
        ProductRequest updatedProductRequest = new ProductRequest("Updated Product", 300L, "updated-url");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/products/edit/{id}", productId)
                        .param("name", updatedProductRequest.getName())
                        .param("price", String.valueOf(updatedProductRequest.getPrice()))
                        .param("imageUrl", updatedProductRequest.getImageUrl()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/api/products"));

        verify(productService, times(1)).update(eq(productId), any(ProductRequest.class));
    }

    @Test
    void deleteProduct() throws Exception {
        Long productId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/delete/{id}", productId))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/api/products"));

        verify(productService, times(1)).delete(productId);
    }
}
