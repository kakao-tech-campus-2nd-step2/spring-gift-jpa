package gift.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.dto.request.WishListRequest;
import gift.dto.response.TokenResponse;
import gift.dto.response.WishProductResponse;
import gift.service.MemberService;
import gift.service.ProductService;
import gift.service.TokenService;
import gift.service.WishListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class WishListControllerTest {

    private static final String URL = "/api/wishlist";
    private static String TOKEN;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WishListService wishListService;
    @Autowired
    private ProductService productService;
    @Autowired
    TokenService tokenService;
    @Autowired
    MemberService memberService;
    @Autowired
    private ObjectMapper objectMapper;

    private Long START_PRODUCT_ID;

    @BeforeEach
    void setup() {
        START_PRODUCT_ID = addProduct30();
        TOKEN = getTokenByRegister(memberService, tokenService);
    }

    private static String getTokenByRegister(MemberService memberService, TokenService tokenService) {
        Long registeredMemberId = memberService.registerMember("test@gmail.com", "1234");
        TokenResponse tokenResponse = tokenService.generateToken(registeredMemberId);
        return tokenResponse.token();
    }

    private Long addProduct30() {
        Long startId = 0L;
        for (int i = 1; i <= 30; i++) {
            if (i == 1) {
                startId = productService.addProduct("testProduct" + i, 100, "ImageUrl").id();
            }
            productService.addProduct("testProduct" + i, 100, "ImageUrl");
        }
        return startId;
    }

    @Test
    @DisplayName("위시리스트에 상품 추가")
    void wishListAdd() throws Exception {
        //Given
        WishListRequest newWishRequest = new WishListRequest(START_PRODUCT_ID, 100);
        String json = objectMapper.writeValueAsString(newWishRequest);

        //When
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                //Then
                .andExpect(
                        status().isCreated()
                );
    }

    @Test
    @DisplayName("위시리스트에 저장된 상품 추가 요청시 예외 던짐")
    void duplicatedProductAddThrowException() throws Exception {
        //Given
        Long memberId = tokenService.getMemberIdByToken(TOKEN);
        wishListService.addProductToWishList(memberId, START_PRODUCT_ID, 10);

        WishListRequest sameProductRequest = new WishListRequest(START_PRODUCT_ID, 100);
        String json = objectMapper.writeValueAsString(sameProductRequest);

        //When
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                //Then
                .andExpectAll(
                        status().isConflict(),
                        content().contentType(MediaType.APPLICATION_PROBLEM_JSON),
                        jsonPath("title").value("ProductId: " + START_PRODUCT_ID + " already exist in your wishlist")
                );
    }

    @Test
    @DisplayName("저장하려는 상품ID가 상품DB에 없을시 예외 던짐")
    void noProductThrowException() throws Exception {
        //Given
        WishListRequest noExistProductRequest = new WishListRequest(START_PRODUCT_ID - 1, 100);
        String json = objectMapper.writeValueAsString(noExistProductRequest);

        //When
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                //Then
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_PROBLEM_JSON),
                        jsonPath("title").value("Product not found")
                );
    }


    @Test
    @DisplayName("위시리스트에 저장된 수량 수정")
    void updateProductAmount() throws Exception {
        //Given
        Long memberId = tokenService.getMemberIdByToken(TOKEN);
        wishListService.addProductToWishList(memberId, START_PRODUCT_ID, 10);

        WishListRequest amountUpdateRequest = new WishListRequest(START_PRODUCT_ID, 99999);
        String json = objectMapper.writeValueAsString(amountUpdateRequest);

        //When
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(URL)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                //Then
                .andExpect(
                        status().isOk()
                ).andReturn();
        Page<WishProductResponse> wishProductsByMemberId = wishListService.getWishProductsByMemberId(tokenService.getMemberIdByToken(TOKEN), PageRequest.of(0, 10));
        for (WishProductResponse wishProductResponse : wishProductsByMemberId) {
            System.out.println(wishProductResponse.toString());
        }

        System.out.println(mvcResult.getResponse().getContentAsString());

    }

    @Test
    @DisplayName("위시리스트에 저장된 상품 삭제")
    void deleteProduct() throws Exception {
        //Given
        Long memberId = tokenService.getMemberIdByToken(TOKEN);
        wishListService.addProductToWishList(memberId, START_PRODUCT_ID, 10);

        WishListRequest deleteRequest = new WishListRequest(START_PRODUCT_ID, 0);
        String json = objectMapper.writeValueAsString(deleteRequest);

        //When
        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                //Then
                .andExpect(
                        status().isOk()
                );
    }

    @Test
    @DisplayName("위시리스트에 없는 상품 수정 요청시 예외 던짐")
    void updateThrow() throws Exception {
        //Given
        Long memberId = tokenService.getMemberIdByToken(TOKEN);
        wishListService.addProductToWishList(memberId, START_PRODUCT_ID, 10);

        WishListRequest notExistRequest = new WishListRequest(START_PRODUCT_ID + 1, 0);
        String json = objectMapper.writeValueAsString(notExistRequest);

        //When
        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                //Then
                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_PROBLEM_JSON),
                        jsonPath("title").value("Wish not found")
                );
    }

    @Test
    @DisplayName("위시리스트에 없는 상품 삭제 요청시 예외 던짐")
    void deleteThrow() throws Exception {
        //Given
        Long memberId = tokenService.getMemberIdByToken(TOKEN);
        wishListService.addProductToWishList(memberId, START_PRODUCT_ID, 10);

        WishListRequest notExistRequest = new WishListRequest(START_PRODUCT_ID + 1, 0);
        String json = objectMapper.writeValueAsString(notExistRequest);

        //When
        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                //Then
                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_PROBLEM_JSON),
                        jsonPath("title").value("Wish not found")
                );
    }

    @Test
    @DisplayName("위시리스트 페이지네이션 확인")
    void wishPagination() throws Exception {
        //Given
        addProduct30ToWish(START_PRODUCT_ID);

        //When
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(URL)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "productName,desc"))
                //Then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.size").value(5),
                        jsonPath("$.sort.sorted").value(true),
                        jsonPath("number").value(0)
                ).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    private void addProduct30ToWish(Long startProductId) {
        Long memberId = tokenService.getMemberIdByToken(TOKEN);
        for (Long productId = startProductId; productId <= 30; productId++) {
            wishListService.addProductToWishList(memberId, productId, 10);
        }
    }

}
