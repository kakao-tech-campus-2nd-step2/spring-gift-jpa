package gift.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gift.dto.LoginRequest;
import gift.dto.WishProductAddRequest;
import gift.dto.WishProductResponse;
import gift.dto.WishProductUpdateRequest;
import gift.service.WishProductService;
import gift.service.auth.AuthService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WishProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WishProductService wishProductService;
    @Autowired
    private AuthService authService;
    private String managerToken;
    private String memberToken;
    private final Long memberId = 2L;

    @BeforeEach
    @DisplayName("관리자, 이용자의 토큰 값 세팅하기")
    void setAccessToken() {
        managerToken = authService.login(new LoginRequest("admin@naver.com", "password")).token();
        memberToken = authService.login(new LoginRequest("member@naver.com", "password")).token();
    }

    @Test
    @DisplayName("잘못된 수량으로 된 위시 리스트 상품 추가하기")
    void addWishProductFailWithZeroCount() throws Exception {
        var result = mockMvc.perform(post("/api/wishes/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new WishProductAddRequest(1L, 0))));

        result.andExpect(status().isBadRequest())
                .andExpect(content().string("상품의 수량은 반드시 1개 이상이어야 합니다."));
    }

    @Test
    @DisplayName("위시 리스트 상품 추가하기")
    void addWishProductSuccess() throws Exception {
        var result = mockMvc.perform(post("/api/wishes/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new WishProductAddRequest(1L, 10))));

        result.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("위시 리스트 상품 조회하기")
    void readWishProductSuccess() throws Exception {
        var wishProduct = wishProductService
                .addWishProduct(new WishProductAddRequest(1L, 10), memberId);
        var readResult = mockMvc.perform(get("/api/wishes")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken));
        var wishResult = readResult.andExpect(status().isOk()).andReturn();
        var wishResponseContent = wishResult.getResponse().getContentAsString();
        var wishProducts = objectMapper.readValue(wishResponseContent, new TypeReference<List<WishProductResponse>>() {
        });

        Assertions.assertThat(wishProducts.size()).isEqualTo(1);

        wishProductService.deleteWishProduct(wishProduct.id());
    }

    @Test
    @DisplayName("이미 위시 리스트에 저장된 상품 추가로 저장시 수량이 늘어난다")
    void addWishProductAlreadyExistWishProductSuccess() throws Exception {
        var wishProductAddRequest = new WishProductAddRequest(1L, 10);
        var wishProduct = wishProductService.addWishProduct(wishProductAddRequest, memberId);
        var addResult = mockMvc.perform(post("/api/wishes/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(wishProductAddRequest)));

        addResult.andExpect(status().isCreated());

        var wishProducts = wishProductService.getWishProducts(memberId);

        Assertions.assertThat(wishProducts.size()).isEqualTo(1);
        Assertions.assertThat(wishProducts.get(0).count()).isEqualTo(20);

        wishProductService.deleteWishProduct(wishProduct.id());
    }

    @Test
    @DisplayName("이용자끼리의 위시리스트가 다르다")
    void addWishProductAndReadMemberAndManagerSuccess() throws Exception {
        var wishProduct1AddRequest = new WishProductAddRequest(1L, 10);
        var wishProduct2AddRequest = new WishProductAddRequest(2L, 10);

        wishProductService.addWishProduct(wishProduct1AddRequest, memberId);
        wishProductService.addWishProduct(wishProduct2AddRequest, memberId);

        var managerReadResult = mockMvc.perform(get("/api/wishes")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + managerToken));
        var managerWishResult = managerReadResult.andExpect(status().isOk()).andReturn();
        var wishLength = managerWishResult.getResponse().getContentLength();

        Assertions.assertThat(wishLength).isEqualTo(0);

        var wishProducts = wishProductService.getWishProducts(memberId);

        Assertions.assertThat(wishProducts.size()).isEqualTo(2);

        for (var wishProduct : wishProducts) {
            wishProductService.deleteWishProduct(wishProduct.id());
        }
    }

    @Test
    @DisplayName("위시 리스트 수량 변경하기")
    void addWishProductAndUpdateCountSuccess() throws Exception {
        var wishProduct = wishProductService
                .addWishProduct(new WishProductAddRequest(1L, 10), memberId);

        mockMvc.perform(put("/api/wishes/update/" + wishProduct.id())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new WishProductUpdateRequest(30))));

        var wishProducts = wishProductService.getWishProducts(memberId);

        Assertions.assertThat(wishProducts.size()).isEqualTo(1);
        Assertions.assertThat(wishProducts.get(0).count()).isEqualTo(30);

        wishProductService.deleteWishProduct(wishProduct.id());
    }

    @Test
    @DisplayName("위시 리스트 상품 추가후 수량 0으로 변경하기")
    void addWishProductAndUpdateCountZeroSuccess() throws Exception {
        var wishProduct = wishProductService
                .addWishProduct(new WishProductAddRequest(1L, 10),
                        memberId);

        mockMvc.perform(put("/api/wishes/update/" + wishProduct.id())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new WishProductUpdateRequest(0))));

        var wishProducts = wishProductService.getWishProducts(memberId);

        Assertions.assertThat(wishProducts.size()).isEqualTo(0);
    }
}
