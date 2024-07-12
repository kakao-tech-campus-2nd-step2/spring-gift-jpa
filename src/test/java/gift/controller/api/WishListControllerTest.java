package gift.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.dto.request.WishListRequest;
import gift.dto.response.TokenResponse;
import gift.service.MemberService;
import gift.service.ProductService;
import gift.service.TokenService;
import gift.service.WishListService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class WishListControllerTest {

    private static final String URL = "/api/wishlist";
    private static final Long FIRST_PRODUCT_ID = 1L;
    private static final Long MEMBER_ID = 1L;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WishListService wishListService;

    private static String token;

    private static String getTokenByRegister(MemberService memberService, TokenService tokenService) {
        Long registeredMemberId = memberService.registerMember("test@gmail.com", "1234");
        TokenResponse tokenResponse = tokenService.generateToken(registeredMemberId);
        return tokenResponse.token();
    }

    @BeforeAll
    static void setup(@Autowired ProductService productService,
                      @Autowired TokenService tokenService,
                      @Autowired MemberService memberService) {
        //상품 15개 등록
        for (int i = 0; i < 15; i++) {
            productService.addProduct("testProduct" + i, 100, "ImageUrl");
        }
        token = getTokenByRegister(memberService, tokenService);
        tokenService.getMemberIdByToken(token);
    }

    @Test
    @Transactional
    @DisplayName("위시리스트에 상품 추가")
    void wishListAdd() throws Exception {
        //Given
        WishListRequest newWishRequest = new WishListRequest(FIRST_PRODUCT_ID, 100);
        String json = objectMapper.writeValueAsString(newWishRequest);

        //When
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                //Then
                .andExpect(
                        status().isCreated()
                );
    }

    @Test
    @Transactional
    @DisplayName("위시리스트에 이미 저장된 상품을 중복 POST요청시 예외 던짐")
    void duplicatedProductAddThrowException() throws Exception {
        //Given
        wishListService.addProductToWishList(MEMBER_ID, FIRST_PRODUCT_ID, 10);

        WishListRequest request = new WishListRequest(FIRST_PRODUCT_ID, 10);
        String json = objectMapper.writeValueAsString(request);

        //When
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                //Then
                .andExpectAll(
                        status().isConflict(),
                        content().contentType(MediaType.APPLICATION_PROBLEM_JSON),
                        jsonPath("title").value("ProductId: " + FIRST_PRODUCT_ID + " already exist in your wishlist")
                );
    }

    @Test
    @DisplayName("저장하려는 상품ID가 상품DB에 없을시 예외 던짐")
    void noProductThrowException() throws Exception {
        //Given
        WishListRequest noExistProductRequest = new WishListRequest(-1L, 100);
        String json = objectMapper.writeValueAsString(noExistProductRequest);

        //When
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
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
    @Transactional
    @DisplayName("위시리스트에 저장된 수량 수정")
    void updateProductAmount() throws Exception {
        //Given
        wishListService.addProductToWishList(MEMBER_ID, FIRST_PRODUCT_ID, 10);

        WishListRequest request = new WishListRequest(FIRST_PRODUCT_ID, 2080);
        String json = objectMapper.writeValueAsString(request);

        //When
        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                //Then
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    @Transactional
    @DisplayName("위시리스트에 저장된 상품 삭제")
    void deleteProduct() throws Exception {
        //Given
        wishListService.addProductToWishList(MEMBER_ID, FIRST_PRODUCT_ID, 10);

        WishListRequest request = new WishListRequest(FIRST_PRODUCT_ID, 0);
        String json = objectMapper.writeValueAsString(request);

        //When
        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
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
        WishListRequest notExistAtWishRequest = new WishListRequest(1L, 500);
        String json = objectMapper.writeValueAsString(notExistAtWishRequest);

        //When
        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
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
        WishListRequest request = new WishListRequest(1L, 111);
        String json = objectMapper.writeValueAsString(request);

        //When
        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
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
    @Transactional
    @DisplayName("위시리스트 페이지네이션 확인")
    void wishPagination() throws Exception {
        //Given
        addProduct15ToWish();

        //When
        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
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
                );
    }

    private void addProduct15ToWish() {
        for (long productId = 1L; productId <= 15; productId++) {
            wishListService.addProductToWishList(MEMBER_ID, productId, 10);
        }
    }

}
