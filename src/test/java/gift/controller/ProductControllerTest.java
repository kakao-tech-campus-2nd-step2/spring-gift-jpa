package gift.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.request.ProductRequest;
import gift.response.ProductResponse;
import gift.constant.ErrorMessage;
import gift.service.ProductService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mvc;

    @DisplayName("[GET] 모든 상품 정보를 조회한다.")
    @Test
    void productList() throws Exception {
        //given
        given(productService.getProducts()).willReturn(List.of());

        //when
        ResultActions result = mvc.perform(get("/api/products"));

        //then
        result
            .andExpect(status().isOk());

        then(productService).should().getProducts();
    }

    @DisplayName("[GET] 하나의 상품 정보를 조회한다.")
    @Test
    void productOne() throws Exception {
        //given
        Long productId = 1L;
        ProductResponse product = new ProductResponse();

        given(productService.getProduct(productId)).willReturn(product);

        //when
        ResultActions result = mvc.perform(get("/api/products/{productId}", productId));

        //then
        result
            .andExpect(status().isOk());

        then(productService).should().getProduct(productId);
    }

    @DisplayName("[POST] 상품 하나를 추가한다.")
    @Test
    void productAdd() throws Exception {
        //given
        ProductRequest request = new ProductRequest("아이스티", 2500, "https://example.com");

        willDoNothing().given(productService).addProduct(any(ProductRequest.class));

        //when
        ResultActions result = mvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(request)));

        //then
        result
            .andExpect(status().isCreated());

        then(productService).should().addProduct(any(ProductRequest.class));
    }

    @DisplayName("[POST/Exception] 상품 하나를 추가하는데, 상품 이름이 주어지지 않으면 예외를 던진다.")
    @Test
    void productAddWithoutName() throws Exception {
        //given
        String request = "{\"price\": 2500, \"imageUrl\": \"https://example.com\"}";

        //when
        ResultActions result = mvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request));

        //then
        result
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(ErrorMessage.VALIDATION_ERROR))
            .andExpect(jsonPath("$.validation.name").value(ErrorMessage.PRODUCT_NAME_NOT_BLANK));
    }

    @DisplayName("[POST/Exception] 상품 하나를 추가하는데, 상품명이 15자가 넘으면 예외를 던진다.")
    @Test
    void productAddWithNameExceedingMaxLength() throws Exception {
        //given
        ProductRequest request = new ProductRequest("프리미엄 오가닉 그린티 블렌드", 2500,
            "https://example.com");

        //when
        ResultActions result = mvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(request)));

        //then
        result
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(ErrorMessage.VALIDATION_ERROR))
            .andExpect(
                jsonPath("$.validation.name").value(ErrorMessage.PRODUCT_NAME_EXCEEDS_MAX_LENGTH));
    }

    @DisplayName("[POST/Exception] 상품 하나를 추가하는데, 상품명에 허용되지 않는 특수문자가 포함되어 있으면 예외를 던진다.")
    @Test
    void productAddWithInvalidCharactersInName() throws Exception {
        //given
        ProductRequest request = new ProductRequest("그린티 *", 2500, "https://example.com");

        //when
        ResultActions result = mvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(request)));

        //then
        result
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(ErrorMessage.VALIDATION_ERROR))
            .andExpect(jsonPath("$.validation.name").value(ErrorMessage.PRODUCT_NAME_INVALID_CHAR));
    }

    @DisplayName("[POST/Exception] 상품 하나를 추가하는데, 상품명에 '카카오'가 포함되어 있으면 예외를 던진다.")
    @Test
    void productAddWithNameContainingKakao() throws Exception {
        //given
        ProductRequest request = new ProductRequest("카카오 굿즈", 2500, "https://example.com");

        //when
        ResultActions result = mvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(request)));

        //then
        result
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(ErrorMessage.VALIDATION_ERROR))
            .andExpect(
                jsonPath("$.validation.name").value(ErrorMessage.PRODUCT_NAME_CONTAINS_KAKAO));
    }

    @DisplayName("[POST/Exception] 상품 하나를 추가하는데, 가격 정보가 주어지지 않으면 예외를 던진다.")
    @Test
    void productAddWithoutPrice() throws Exception {
        //given
        String request = "{\"name\": \"아이스티\", \"imageUrl\": \"https://example.com\"}";

        //when
        ResultActions result = mvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request));

        //then
        result
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(ErrorMessage.VALIDATION_ERROR))
            .andExpect(jsonPath("$.validation.price").value(ErrorMessage.PRODUCT_PRICE_NOT_NULL));
    }

    @DisplayName("[PUT] 상품 정보를 수정한다.")
    @Test
    void productEdit() throws Exception {
        //given
        ProductRequest request = new ProductRequest("아이스티", 2500, "https://example.com");
        Long productId = 1L;

        willDoNothing().given(productService).editProduct(anyLong(), any(ProductRequest.class));

        //when
        ResultActions result = mvc.perform(put("/api/products/{productId}", productId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)));

        //then
        result
            .andExpect(status().isOk());

        then(productService).should().editProduct(anyLong(), any(ProductRequest.class));
    }


    @DisplayName("[PUT/Exception] 상품 정보를 수정하는데, 상품 이름이 주어지지 않으면 예외를 던진다.")
    @Test
    void productEditWithoutName() throws Exception {
        //given
        String request = "{\"price\": 2500, \"imageUrl\": \"https://example.com\"}";
        Long productId = 1L;

        //when
        ResultActions result = mvc.perform(put("/api/products/{productId}", productId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request));

        //then
        result
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(ErrorMessage.VALIDATION_ERROR))
            .andExpect(jsonPath("$.validation.name").value(ErrorMessage.PRODUCT_NAME_NOT_BLANK));
    }

    @DisplayName("[PUT/Exception] 상품 정보를 수정하는데, 상품명이 15자가 넘으면 예외를 던진다.")
    @Test
    void productEditWithNameExceedingMaxLength() throws Exception {
        //given
        ProductRequest request = new ProductRequest("프리미엄 오가닉 그린티 블렌드", 2500,
            "https://example.com");
        Long productId = 1L;

        //when
        ResultActions result = mvc.perform(put("/api/products/{productId}", productId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(request)));

        //then
        result
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(ErrorMessage.VALIDATION_ERROR))
            .andExpect(
                jsonPath("$.validation.name").value(ErrorMessage.PRODUCT_NAME_EXCEEDS_MAX_LENGTH));
    }

    @DisplayName("[PUT/Exception] 상품 정보를 수정하는데, 상품명에 허용되지 않는 특수문자가 포함되어 있으면 예외를 던진다.")
    @Test
    void productEditWithInvalidCharactersInName() throws Exception {
        //given
        ProductRequest request = new ProductRequest("그린티 *", 2500, "https://example.com");
        Long productId = 1L;

        //when
        ResultActions result = mvc.perform(put("/api/products/{productId}", productId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(request)));

        //then
        result
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(ErrorMessage.VALIDATION_ERROR))
            .andExpect(jsonPath("$.validation.name").value(ErrorMessage.PRODUCT_NAME_INVALID_CHAR));
    }

    @DisplayName("[PUT/Exception] 상품 정보를 수정하는데, 상품명에 '카카오'가 포함되어 있으면 예외를 던진다.")
    @Test
    void productEditWithNameContainingKakao() throws Exception {
        //given
        ProductRequest request = new ProductRequest("카카오 굿즈", 2500, "https://example.com");
        Long productId = 1L;

        //when
        ResultActions result = mvc.perform(put("/api/products/{productId}", productId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(request)));

        //then
        result
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(ErrorMessage.VALIDATION_ERROR))
            .andExpect(
                jsonPath("$.validation.name").value(ErrorMessage.PRODUCT_NAME_CONTAINS_KAKAO));
    }

    @DisplayName("[PUT/Exception] 상품 정보를 수정하는데, 가격 정보가 주어지지 않으면 예외를 던진다.")
    @Test
    void productEditWithoutPrice() throws Exception {
        //given
        String request = "{\"name\": \"아이스티\", \"imageUrl\": \"https://example.com\"}";
        Long productId = 1L;

        //when
        ResultActions result = mvc.perform(put("/api/products/{productId}", productId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request));

        //then
        result
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(ErrorMessage.VALIDATION_ERROR))
            .andExpect(jsonPath("$.validation.price").value(ErrorMessage.PRODUCT_PRICE_NOT_NULL));
    }

    @DisplayName("[DELETE] 상품 하나를 삭제한다.")
    @Test
    void productRemove() throws Exception {
        //given
        Long productId = 1L;

        willDoNothing().given(productService).removeProduct(productId);

        //when
        ResultActions result = mvc.perform(delete("/api/products/{productId}", productId));

        //then
        result
            .andExpect(status().isOk());

        then(productService).should().removeProduct(productId);
    }

}
