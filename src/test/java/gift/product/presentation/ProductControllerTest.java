package gift.product.presentation;

import gift.product.application.ProductResponse;
import gift.product.application.ProductService;
import gift.product.application.command.ProductCreateCommand;
import gift.product.application.command.ProductUpdateCommand;
import gift.auth.TokenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private TokenService tokenService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 이름이유효하지않을경우_상품생성시_잘못된요청응답반환() throws Exception {
        // Given
        String requestBody = """
                {
                    "name": "이름이너무길어서유효성검사에걸리는경우",
                    "price": 1000,
                    "imageUrl": "http://example.com/image.jpg"
                }
                """;

        // When
        MvcResult mvcResult = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        String responseContent = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Assertions.assertTrue(responseContent.contains("상품 이름은 최대 15자까지 입력할 수 있습니다."));
    }

    @Test
    void 모든상품조회시_상품목록반환() throws Exception {
        // Given
        ProductResponse response1 = new ProductResponse(1L, "Product1", 1000, "http://example.com/image1.jpg");
        ProductResponse response2 = new ProductResponse(2L, "Product2", 2000, "http://example.com/image2.jpg");
        Page<ProductResponse> page = new PageImpl<>(List.of(response1, response2), PageRequest.of(0, 2), 2);
        when(productService.findAll(any(Pageable.class))).thenReturn(page);

        // When
        MvcResult mvcResult = mockMvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String responseContent = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Assertions.assertTrue(responseContent.contains("\"content\":[{\"id\":1,\"name\":\"Product1\""));
        Assertions.assertTrue(responseContent.contains("\"id\":2,\"name\":\"Product2\""));
    }

    @Test
    void 상품아이디로조회시_상품반환() throws Exception {
        // Given
        ProductResponse productResponse = new ProductResponse(1L, "Valid", 1000, "http://example.com/image.jpg");
        when(productService.findById(1L)).thenReturn(productResponse);

        // When
        MvcResult mvcResult = mockMvc.perform(get("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String responseContent = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Assertions.assertTrue(responseContent.contains("\"id\":1"));
        Assertions.assertTrue(responseContent.contains("\"name\":\"Valid\""));
        Assertions.assertTrue(responseContent.contains("\"price\":1000"));
        Assertions.assertTrue(responseContent.contains("\"imageUrl\":\"http://example.com/image.jpg\""));
    }

    @Test
    void 유효한요청으로상품수정시_정상응답반환() throws Exception {
        // Given
        String requestBody = """
                {
                    "name": "Updated",
                    "price": 2000,
                    "imageUrl": "http://example.com/updated-image.jpg"
                }
                """;

        doNothing().when(productService).update(any(ProductUpdateCommand.class));

        // When & Then
        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

        verify(productService, times(1)).update(any(ProductUpdateCommand.class));
    }

    @Test
    void 유효한요청으로상품생성시_정상응답반환() throws Exception {
        // Given
        String requestBody = """
                {
                    "name": "Valid",
                    "price": 1000,
                    "imageUrl": "http://example.com/image.jpg"
                }
                """;

        doNothing().when(productService).save(any(ProductCreateCommand.class));

        // When & Then
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

        verify(productService, times(1)).save(any(ProductCreateCommand.class));
    }

    @Test
    void 이름이유효하지않을경우_상품수정시_잘못된요청응답반환() throws Exception {
        // Given
        String requestBody = """
                {
                    "name": "이름이너무길어서유효성검사에걸리는경우",
                    "price": 2000,
                    "imageUrl": "http://example.com/updated-image.jpg"
                }
                """;

        // When
        MvcResult mvcResult = mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        String responseContent = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Assertions.assertTrue(responseContent.contains("상품 이름은 최대 15자까지 입력할 수 있습니다."));
    }

    @Test
    void 상품삭제시_정상응답반환() throws Exception {
        // Given
        doNothing().when(productService).delete(1L);

        // When & Then
        mockMvc.perform(delete("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(productService, times(1)).delete(1L);
    }
}
