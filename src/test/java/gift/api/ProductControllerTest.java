package gift.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.auth.security.JwtFilter;
import gift.auth.security.JwtUtil;
import gift.error.CustomException;
import gift.error.ErrorCode;
import gift.product.api.ProductController;
import gift.product.application.ProductService;
import gift.product.dto.ProductRequest;
import gift.product.dto.ProductResponse;
import gift.product.entity.Product;
import gift.product.util.ProductMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @InjectMocks
    private JwtFilter jwtFilter;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    ProductService productService;
    private final String bearerToken = "Bearer token";

    @Test
    @DisplayName("상품 전체 조회 기능 테스트")
    void getAllProducts() throws Exception {
        List<ProductResponse> products = new ArrayList<>();
        ProductResponse productResponse1 = ProductMapper.toResponseDto(
                new Product("product1", 1000, "https://testshop.com")
        );
        ProductResponse productResponse2 = ProductMapper.toResponseDto(
                new Product("product2", 3000, "https://testshop.com")
        );
        products.add(productResponse1);
        products.add(productResponse2);

        Page<ProductResponse> response = new PageImpl<>(products);
        String responseJson = objectMapper.writeValueAsString(response);
        when(productService.getPagedProducts(any())).thenReturn(response);

        mockMvc.perform(get("/api/products")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andDo(print());

        verify(productService).getPagedProducts(any());
    }

    @Test
    @DisplayName("상품 상세 조회 기능 테스트")
    void getProduct() throws Exception {
        ProductResponse response = ProductMapper.toResponseDto(
                new Product("product1", 1000, "https://testshop.com")
        );
        Long responseId = 1L;
        String responseJson = objectMapper.writeValueAsString(response);
        when(productService.getProductByIdOrThrow(any())).thenReturn(response);

        mockMvc.perform(get("/api/products/{id}", responseId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.name").value(response.name()))
                .andExpect(jsonPath("$.price").value(response.price()))
                .andExpect(jsonPath("$.imageUrl").value(response.imageUrl()))
                .andDo(print());

        verify(productService).getProductByIdOrThrow(responseId);
    }

    @Test
    @DisplayName("상품 상세 조회 실패 테스트")
    void getProductFailed() throws Exception {
        Long productId = 1L;
        Throwable exception = new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        when(productService.getProductByIdOrThrow(productId)).thenThrow(exception);

        mockMvc.perform(get("/api/products/{id}", productId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken))
                .andExpect(status().isNotFound())
                .andExpect(content().string(exception.getMessage()));

        verify(productService).getProductByIdOrThrow(productId);
    }

    @Test
    @DisplayName("상품 추가 기능 테스트")
    void addProduct() throws Exception {
        ProductRequest request = new ProductRequest("product1", 1000, "https://testshop.com");
        ProductResponse response = ProductMapper.toResponseDto(
                ProductMapper.toEntity(request)
        );
        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = objectMapper.writeValueAsString(response);
        when(productService.createProduct(any(ProductRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/products")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.name").value(response.name()))
                .andExpect(jsonPath("$.price").value(response.price()))
                .andExpect(jsonPath("$.imageUrl").value(response.imageUrl()))
                .andDo(print());

        verify(productService).createProduct(any(ProductRequest.class));
    }

    @Test
    @DisplayName("단일 상품 삭제 기능 테스트")
    void deleteProduct() throws Exception {
        Long productId = 1L;
        when(productService.deleteProductById(productId)).thenReturn(productId);

        mockMvc.perform(delete("/api/products/{id}", productId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(productId)))
                .andDo(print());

        verify(productService).deleteProductById(productId);
    }

    @Test
    @DisplayName("단일 상품 삭제 실패 테스트")
    void deleteProductFailed() throws Exception {
        Long productId = 1L;
        Throwable exception = new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        when(productService.deleteProductById(productId)).thenThrow(exception);

        mockMvc.perform(delete("/api/products/{id}", productId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken))
                .andExpect(status().isNotFound())
                .andExpect(content().string(exception.getMessage()));

        verify(productService).deleteProductById(productId);
    }

    @Test
    @DisplayName("상품 전체 삭제 기능 테스트")
    void deleteAllProducts() throws Exception {
        mockMvc.perform(delete("/api/products")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken))
                .andExpect(status().isOk())
                .andDo(print());

        verify(productService).deleteAllProducts();
    }

    @Test
    @DisplayName("상품 수정 기능 테스트")
    void updateProduct() throws Exception {
        Long productId = 2L;
        ProductRequest request = new ProductRequest("product2", 3000, "https://testshop.com");
        String requestJson = objectMapper.writeValueAsString(request);
        when(productService.updateProduct(productId, request)).thenReturn(productId);

        mockMvc.perform(patch("/api/products/{id}", productId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(productId)))
                .andDo(print());

        verify(productService).updateProduct(productId, request);
    }

}