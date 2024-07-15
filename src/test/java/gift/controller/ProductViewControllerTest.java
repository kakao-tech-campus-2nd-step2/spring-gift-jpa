package gift.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/sql/truncateIdentity.sql")
class ProductViewControllerTest {

    private @Autowired MockMvc mockMvc;

    @Test
    @DisplayName("상품 목록 가져오기 테스트")
    void getProducts() throws Exception {
        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품 추가 폼 페이지 가져오기 테스트")
    void addProductForm() throws Exception {
        mockMvc.perform(get("/api/products/product"))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품 수정 폼 페이지 테스트")
    void editProductForm() throws Exception {
        String requestJson = """
            {"name": "커피", "price": 5500,"imageUrl": "https://..."}
            """;

        mockMvc.perform(post("/api/products/product")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson));

        mockMvc.perform(get("/api/products/product/1"))
            .andExpect(status().isOk());
    }
}