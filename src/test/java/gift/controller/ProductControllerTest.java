package gift.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.dto.LoginRequest;
import gift.dto.ProductRequest;
import gift.service.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuthService authService;
    private String managerToken;
    private String memberToken;

    @BeforeEach
    @DisplayName("관리자, 이용자의 토큰 값 세팅하기")
    void setAccessToken() {
        managerToken = authService.login(new LoginRequest("admin@naver.com", "password")).token();
        memberToken = authService.login(new LoginRequest("member@naver.com", "password")).token();
    }

    @Test
    @DisplayName("잘못된 가격으로 된 오류 상품 생성하기")
    void addProductFailWithPrice() throws Exception {
        var result = mockMvc.perform(post("/api/products/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new ProductRequest("상품1", -1000, "이미지 주소"))));

        result.andExpect(status().isBadRequest())
                .andExpect(content().string("금액은 0보다 크거나 같아야 합니다."));
    }

    @Test
    @DisplayName("이름의 길이가 15초과인 오류 상품 생성하기")
    void addProductFailWithNameLength() throws Exception {
        var result = mockMvc.perform(post("/api/products/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new ProductRequest("햄버거햄버거햄버거햄버거햄버거햄", 1000, "이미지 주소"))));

        result.andExpect(status().isBadRequest())
                .andExpect(content().string("이름의 길이는 15자를 초과할 수 없습니다."));
    }

    @Test
    @DisplayName("카카오를 포함한 이름을 가진 오류 상품 생성하기")
    void addProductFailWithNameKAKAO() throws Exception {
        var result = mockMvc.perform(post("/api/products/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new ProductRequest("카카오456", 1000, "이미지 주소"))));

        result.andExpect(status().isBadRequest())
                .andExpect(content().string("카카오가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다."));
    }

    @Test
    @DisplayName("카카오를 포함한 이름을 가진 오류 상품 생성하기")
    void addProductSuccessWithNameKAKAO() throws Exception {
        var result = mockMvc.perform(post("/api/products/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + managerToken)
                .content(objectMapper.writeValueAsString(new ProductRequest("카카오456", 1000, "이미지 주소"))));

        result.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("빈 이름을 가진 오류 상품 생성하기")
    void addProductFailWithEmptyName() throws Exception {
        var result = mockMvc.perform(post("/api/products/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new ProductRequest("", 1000, "이미지 주소"))));

        result.andExpect(status().isBadRequest())
                .andExpect(content().string("이름의 길이는 최소 1자 이상이어야 합니다."));
    }

    @Test
    @DisplayName("정상 상품 생성하기 - 특수문자 포함")
    void addProductSuccessWithSpecialChar() throws Exception {
        var result = mockMvc.perform(post("/api/products/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new ProductRequest("햄버거()[]+-&/_", 1000, "이미지 주소"))));

        result.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("정상 상품 생성하기 - 공백 포함")
    void addProductSuccessWithEmptySpace() throws Exception {
        var result = mockMvc.perform(post("/api/products/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new ProductRequest("햄버거 햄버거 햄버거", 1000, "이미지 주소"))));

        result.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("오류 상품 생성하기 - 허용되지 않은 특수문자 포함")
    void addProductFailWithSpecialChar() throws Exception {
        var result = mockMvc.perform(post("/api/products/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new ProductRequest("햄버거()[]+-&/_**", 1000, "이미지 주소"))));

        result.andExpect(status().isBadRequest())
                .andExpect(content().string("허용되지 않은 형식의 이름입니다."));
    }
}
