package gift.domain.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.domain.dto.request.ProductRequest;
import gift.domain.dto.response.ProductResponse;
import gift.domain.service.MemberService;
import gift.domain.service.ProductService;
import gift.global.util.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private MemberService memberService;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("상품 추가 이름검증 - 이름이 문제 없는 경우")
    void addProduct() throws Exception {
        //given
        ProductRequest validRequest = new ProductRequest("ValidName", 1000, "http://example.com/image.jpg");
        ProductResponse productResponse = new ProductResponse(1L, "ValidName", 1000, "http://example.com/image.jpg");

        given(productService.addProduct(any(ProductRequest.class))).willReturn(productResponse);

        //when
        mockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(validRequest))
        )

        //then
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.created-product.name").value("ValidName"))
            .andExpect(jsonPath("$.created-product.price").value(1000))
            .andExpect(jsonPath("$.created-product.image-url").value("http://example.com/image.jpg"))
            .andExpect(header().string("Location", "/api/products/1"));
    }

    @Test
    @DisplayName("상품 추가 이름검증 - 검증이 실패하는 3가지 경우")
    void addProduct_ValidationFails() throws Exception {
        //given
        ProductRequest[] invalidRequest = {
            new ProductRequest("toooo long product name", 1000, "http://example.com/image.jpg"),
            new ProductRequest("SpecialChar $*#", 1000, "http://example.com/image.jpg"),
            new ProductRequest("name is 카카오", 1000, "http://example.com/image.jpg")};
        String[] expected = {
            "상품 이름은 공백을 포함하여 최대 15자까지 입력할 수 있습니다.",
            "상품 이름에 (), [], +, -, &, /, _ 이외의 특수 문자는 사용할 수 없습니다.",
            "상품 이름에 '카카오'가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다."};

        for (int i = 0; i < invalidRequest.length; i++) {
            //when
            mockMvc.perform(post("/api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidRequest[i]))
                )

            //then
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("name: " + expected[i]));
        }
    }
}
