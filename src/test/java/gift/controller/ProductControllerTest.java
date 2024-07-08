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

import gift.dto.product.ProductRequest;
import gift.dto.product.ProductResponse;
import gift.exception.product.InvalidProductPriceException;
import gift.exception.product.ProductNotFoundException;
import gift.service.ProductService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    private ProductResponse productDTO;

    @BeforeEach
    public void setUp() {
        productDTO = new ProductResponse(1L, "Test Product", 100, "test.jpg");
    }

    @Test
    @DisplayName("모든 상품 조회")
    public void testGetAllProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of(productDTO));

        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("Test Product"));
    }

    @Test
    @DisplayName("상품 ID로 조회")
    public void testGetProductById() throws Exception {
        when(productService.getProductById(1L)).thenReturn(productDTO);

        mockMvc.perform(get("/api/products/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    @DisplayName("존재하지 않는 상품 ID로 조회")
    public void testGetProductByIdNotFound() throws Exception {
        when(productService.getProductById(1L)).thenThrow(new ProductNotFoundException("상품을 다음의 id로 찾을 수 없습니다. id: 1"));

        mockMvc.perform(get("/api/products/1"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").value("상품을 다음의 id로 찾을 수 없습니다. id: 1"));
    }

    @Test
    @DisplayName("상품 추가")
    public void testAddProduct() throws Exception {
        when(productService.addProduct(any(ProductRequest.class))).thenReturn(productDTO);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Test Product\", \"price\": 100, \"imageUrl\": \"test.jpg\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    @DisplayName("유효하지 않은 가격으로 상품 추가")
    public void testAddProductInvalidPrice() throws Exception {
        when(productService.addProduct(any(ProductRequest.class))).thenThrow(new InvalidProductPriceException("가격은 0 이상으로 설정되어야 합니다."));

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Test Product\", \"price\": -100, \"imageUrl\": \"test.jpg\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("가격은 0 이상으로 설정되어야 합니다."));
    }

    @Test
    @DisplayName("상품 업데이트")
    public void testUpdateProduct() throws Exception {
        when(productService.updateProduct(eq(1L), any(ProductRequest.class))).thenReturn(productDTO);

        mockMvc.perform(put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Updated Product\", \"price\": 200, \"imageUrl\": \"updated.jpg\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    @DisplayName("존재하지 않는 상품 ID로 업데이트")
    public void testUpdateProductNotFound() throws Exception {
        when(productService.updateProduct(eq(1L), any(ProductRequest.class))).thenThrow(new ProductNotFoundException("상품을 다음의 id로 찾을 수 없습니다. id: 1"));

        mockMvc.perform(put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Updated Product\", \"price\": 200, \"imageUrl\": \"updated.jpg\"}"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").value("상품을 다음의 id로 찾을 수 없습니다. id: 1"));
    }

    @Test
    @DisplayName("상품 삭제")
    public void testDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/products/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("존재하지 않는 상품 ID로 삭제")
    public void testDeleteProductNotFound() throws Exception {
        doNothing().when(productService).deleteProduct(1L);
        doThrow(new ProductNotFoundException("상품을 다음의 id로 찾을 수 없습니다. id: 1")).when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/products/1"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").value("상품을 다음의 id로 찾을 수 없습니다. id: 1"));
    }
}
