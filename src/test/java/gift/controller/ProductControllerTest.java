package gift.controller;

import gift.entity.Product;
import gift.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void getAllProducts() throws Exception {
        given(productService.findAll()).willReturn(Arrays.asList(
                new Product(1L, 100, "Product1", "url1"),
                new Product(2L, 200, "Product2", "url2")
        ));

        mockMvc.perform(get("/api/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Product1"))
                .andExpect(jsonPath("$[1].name").value("Product2"));
    }

    @Test
    public void addProduct() throws Exception {
        Product product = new Product(1L, 100, "Product1", "url1");
        given(productService.save(any(Product.class))).willReturn(product);

        mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"price\":100,\"name\":\"Product1\",\"imageUrl\":\"url1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Product1"));
    }

    @Test
    public void updateProduct() throws Exception {
        Product product = new Product(1L, 100, "Product1", "url1");
        given(productService.save(any(Product.class))).willReturn(product);

        mockMvc.perform(put("/api/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"price\":100,\"name\":\"Product1\",\"imageUrl\":\"url1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Product1"));
    }

    @Test
    public void deleteProduct() throws Exception {
        mockMvc.perform(delete("/api/product/1"))
                .andExpect(status().isNoContent());
    }
}
