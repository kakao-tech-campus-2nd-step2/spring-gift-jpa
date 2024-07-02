package gift.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gift.dto.ProductDTO;
import gift.exception.InvalidProductPriceException;
import gift.exception.ProductNotFoundException;
import gift.service.ProductService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private ProductDTO productDTO;

    @BeforeEach
    public void setUp() {
        productDTO = new ProductDTO(1L, "Test Product", 100, "test.jpg");
    }

    @Test
    public void testGetAllProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of(productDTO));

        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("Test Product"));
    }

    @Test
    public void testGetProductById() throws Exception {
        when(productService.getProductById(1L)).thenReturn(productDTO);

        mockMvc.perform(get("/api/products/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    public void testGetProductByIdNotFound() throws Exception {
        when(productService.getProductById(1L)).thenThrow(new ProductNotFoundException("상품을 다음의 id로 찾을 수 없습니다. id: 1"));

        mockMvc.perform(get("/api/products/1"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").value("상품을 다음의 id로 찾을 수 없습니다. id: 1"));
    }

    @Test
    public void testAddProduct() throws Exception {
        when(productService.addProduct(any(ProductDTO.class))).thenReturn(productDTO);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Test Product\", \"price\": 100, \"imageUrl\": \"test.jpg\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    public void testAddProductInvalidPrice() throws Exception {
        when(productService.addProduct(any(ProductDTO.class))).thenThrow(new InvalidProductPriceException("가격은 0 이상으로 설정되어야 합니다."));

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Test Product\", \"price\": -100, \"imageUrl\": \"test.jpg\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("가격은 0 이상으로 설정되어야 합니다."));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        when(productService.updateProduct(eq(1L), any(ProductDTO.class))).thenReturn(productDTO);

        mockMvc.perform(put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Updated Product\", \"price\": 200, \"imageUrl\": \"updated.jpg\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    public void testUpdateProductNotFound() throws Exception {
        when(productService.updateProduct(eq(1L), any(ProductDTO.class))).thenThrow(new ProductNotFoundException("상품을 다음의 id로 찾을 수 없습니다. id: 1"));

        mockMvc.perform(put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Updated Product\", \"price\": 200, \"imageUrl\": \"updated.jpg\"}"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").value("상품을 다음의 id로 찾을 수 없습니다. id: 1"));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/products/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteProductNotFound() throws Exception {
        doNothing().when(productService).deleteProduct(1L);
        doThrow(new ProductNotFoundException("상품을 다음의 id로 찾을 수 없습니다. id: 1")).when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/products/1"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").value("상품을 다음의 id로 찾을 수 없습니다. id: 1"));
    }
}
