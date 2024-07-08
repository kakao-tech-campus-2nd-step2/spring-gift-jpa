package gift.product.presentation;

import gift.product.application.command.ProductCreateCommand;
import gift.product.domain.Product;
import gift.product.domain.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        jdbcTemplate.execute("TRUNCATE TABLE product");
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
        assertTrue(responseContent.contains("상품 이름은 최대 15자까지 입력할 수 있습니다."));
    }

    @Test
    void 모든상품조회시_상품목록반환() throws Exception {
        // Given

        // When
        MvcResult mvcResult = mockMvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String responseContent = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertTrue(responseContent.contains("[]"));
    }

    @Test
    void 상품아이디로조회시_상품반환() throws Exception {
        // Given
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Valid",
                            "price": 1000,
                            "imageUrl": "http://example.com/image.jpg"
                        }
                        """));
        Product createdProduct = productRepository.findAll().get(0);

        // When
        MvcResult mvcResult = mockMvc.perform(get("/api/products/" + createdProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String responseContent = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertTrue(responseContent.contains("\"id\":" + createdProduct.getId()));
        assertTrue(responseContent.contains("\"name\":\"Valid\""));
        assertTrue(responseContent.contains("\"price\":1000"));
        assertTrue(responseContent.contains("\"imageUrl\":\"http://example.com/image.jpg\""));
    }

    @Test
    void 유효한요청으로상품수정시_정상응답반환() throws Exception {
        // Given
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Original",
                            "price": 1000,
                            "imageUrl": "http://example.com/original-image.jpg"
                        }
                        """));
        Product createdProduct = productRepository.findAll().get(0);

        String requestBody = """
                {
                    "name": "Updated",
                    "price": 2000,
                    "imageUrl": "http://example.com/updated-image.jpg"
                }
                """;

        // When
        mockMvc.perform(put("/api/products/" + createdProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

        // Then
        Optional<Product> updatedProduct = productRepository.findById(createdProduct.getId());
        assertTrue(updatedProduct.isPresent());
        Assertions.assertThat(updatedProduct.get().getName()).isEqualTo("Updated");
        Assertions.assertThat(updatedProduct.get().getPrice()).isEqualTo(2000);
        Assertions.assertThat(updatedProduct.get().getImageUrl()).isEqualTo("http://example.com/updated-image.jpg");
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

        // When
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

        // Then
        Product createdProduct = productRepository.findAll().get(0);
        Assertions.assertThat(createdProduct.getName()).isEqualTo("Valid");
        Assertions.assertThat(createdProduct.getPrice()).isEqualTo(1000);
        Assertions.assertThat(createdProduct.getImageUrl()).isEqualTo("http://example.com/image.jpg");
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
        assertTrue(responseContent.contains("상품 이름은 최대 15자까지 입력할 수 있습니다."));
    }

    @Test
    void 상품삭제시_정상응답반환() throws Exception {
        // Given
        productRepository.addProduct(new ProductCreateCommand("Valid", 1000, "http://example.com/image.jpg").toProduct());
        Product createdProduct = productRepository.findAll().get(0);

        // When
        mockMvc.perform(delete("/api/products/" + createdProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Then
        Optional<Product> deletedProduct = productRepository.findById(createdProduct.getId());
        assertTrue(deletedProduct.isEmpty());
    }
}
