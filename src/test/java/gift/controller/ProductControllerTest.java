package gift.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gift.constants.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql("/sql/truncateIdentity.sql")
class ProductControllerTest {

    private @Autowired MockMvc mockMvc;

    @Test
    @DisplayName("상품 추가 테스트")
    void addProduct() throws Exception {
        String requestJson = """
            {"name": "커피", "price": 5500,"imageUrl": "https://..."}
            """;

        mockMvc.perform(post("/api/products/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void editProduct() throws Exception {
        String requestJson1 = """
            {"name": "커피", "price": 5500,"imageUrl": "https://..."}
            """;
        String requestJson2 = """
            {"id": 1, "name": "달다구리 커피", "price": 6500,"imageUrl": "https://..."}
            """;

        mockMvc.perform(post("/api/products/product")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson1));

        mockMvc.perform(put("/api/products/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson2))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteProduct() throws Exception {
        String requestJson = """
            {"name": "커피", "price": 5500,"imageUrl": "https://..."}
            """;

        mockMvc.perform(post("/api/products/product")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson));

        mockMvc.perform(delete("/api/products/product/1"))
            .andExpect(status().isOk());
    }

    @DisplayName("상품명 유효성 검증 성공 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"커피", "coffee", "1234cof피", "커피(예가체프)", "커피[아무거나]",
        "커+ffee", "012345678901234", "커&피", "(커/피]", "(커][[fee))()", "+-&커__()fe&/_"})
    void addProductSuccess(String name) throws Exception {
        String requestJson = String.format("""
            {"name": "%s", "price": 5500,"imageUrl": "https://..."}
            """, name);
        mockMvc.perform(post("/api/products/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(status().isOk());

    }

    @DisplayName("상품명 유효성 검증 실패 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"", "    ", "0123456789012345", "커피{블랙}", "커@피", "커피(카카오)",
        "카카오 선물", "이건카카오커피", "커피😀", "커피커피커피커피커피커피커피커피커피"})
    void addProductError(String name) throws Exception {
        String requestJson = String.format("""
            {"name": "%s", "price": 5500,"imageUrl": "https://..."}
            """, name);
        mockMvc.perform(post("/api/products/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("비어있는 상품명 입력 시 에러 메시지 테스트")
    void productNameNotBlankErrorMsg() throws Exception {
        String requestJson = """
            {"name": null, "price": 5500,"imageUrl": "https://..."}
            """;

        mockMvc.perform(post("/api/products/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(content().string(ErrorMessage.PRODUCT_NAME_VALID_NOT_BLANK_MSG));
    }

    @Test
    @DisplayName("15자를 초과하는 상품명 입력 시 에러 메시지 테스트")
    void productNameSizeErrorMsg() throws Exception {
        String requestJson = """
            {"name": "0123456789012345", "price": 5500,"imageUrl": "https://..."}
            """;

        mockMvc.perform(post("/api/products/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(content().string(ErrorMessage.PRODUCT_NAME_VALID_SIZE_MSG));
    }

    @Test
    @DisplayName("상품명에 허용되지 않는 특수문자 입력 시 에러 메시지 테스트")
    void productNameNotAllowCharErrorMsg() throws Exception {
        String requestJson = """
            {"name": "{커피}", "price": 5500,"imageUrl": "https://..."}
            """;

        mockMvc.perform(post("/api/products/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(content().string(ErrorMessage.PRODUCT_NAME_VALID_CHAR_MSG));
    }

    @Test
    @DisplayName("상품명에 카카오 문구 입력 시 에러 메시지 테스트")
    void productNameIncludeKakaoErrorMsg() throws Exception {
        String requestJson = """
            {"name": "카카오 커피", "price": 5500,"imageUrl": "https://..."}
            """;

        mockMvc.perform(post("/api/products/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(content().string(ErrorMessage.PRODUCT_NAME_VALID_KAKAO_MSG));
    }

    @Test
    @DisplayName("중복된 이름의 상품을 추가하는 실패 테스트")
    void addDuplicateProduct() throws Exception {
        String requestJson1 = """
            {"name": "커피", "price": 5500,"imageUrl": "https://..."}
            """;
        String requestJson2 = """
            {"name": "커피", "price": 5500,"imageUrl": "https://..."}
            """;

        mockMvc.perform(post("/api/products/product")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson1));

        mockMvc.perform(post("/api/products/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson2))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(ErrorMessage.PRODUCT_ALREADY_EXISTS_MSG));
    }

    @Test
    @DisplayName("해당하는 ID가 없는 상품의 수정 페이지를 요청하는 실패 테스트")
    void editFormNotExistProduct() throws Exception {
        mockMvc.perform(get("/api/products/product/10")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(ErrorMessage.PRODUCT_NOT_EXISTS_MSG));
    }

    @Test
    @DisplayName("해당하는 ID가 없는 상품을 수정하는 실패 테스트")
    void editNotExistProduct() throws Exception {
        String requestJson = """
            {"id": 11, "name": "커피", "price": 5500,"imageUrl": "https://..."}
            """;

        mockMvc.perform(put("/api/products/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(ErrorMessage.PRODUCT_NOT_EXISTS_MSG));
    }

    @Test
    @DisplayName("해당하는 ID가 없는 상품을 삭제하는 실패 테스트")
    void deleteNotExistProduct() throws Exception {
        mockMvc.perform(delete("/api/products/product/10"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(ErrorMessage.PRODUCT_NOT_EXISTS_MSG));
    }
}