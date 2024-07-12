package gift.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.dto.request.WishListRequest;
import gift.dto.response.TokenResponse;
import gift.service.MemberService;
import gift.service.ProductService;
import gift.service.TokenService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WishListControllerTest {

    private static final String URL = "/api/wishlist";
    private static String TOKEN;
    private static Long PRODUCT_ID;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void setup(@Autowired ProductService productService,
                      @Autowired MemberService memberService,
                      @Autowired TokenService tokenService) {
        dummyProductAdd(productService);
        TOKEN = getTokenByRegister(memberService, tokenService);
    }

    private static void dummyProductAdd(ProductService productService) {
        PRODUCT_ID = productService.addProduct("SoySource", 1000, "soyImage").id();
        productService.addProduct("Carrot", 200, "carrotImage");
    }

    private static String getTokenByRegister(MemberService memberService, TokenService tokenService) {
        Long registeredMemberId = memberService.registerMember("test@gmail.com", "1234");
        TokenResponse tokenResponse = tokenService.generateToken(registeredMemberId);
        return tokenResponse.token();
    }

    @Test
    @Order(1)
    @DisplayName("위시리스트에 상품 추가")
    void wishListAdd() throws Exception {
        //Given
        WishListRequest wishListRequest = new WishListRequest(PRODUCT_ID, 100);
        String json = objectMapper.writeValueAsString(wishListRequest);

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
    @Order(2)
    @DisplayName("위시리스트에 저장된 상품 추가 요청시 예외 던짐")
    void duplicatedProductAddThrowException() throws Exception {
        //Given
        WishListRequest sameProductRequest = new WishListRequest(PRODUCT_ID, 100);
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
                        jsonPath("title").value("ProductId: " + PRODUCT_ID + " already exist in your wishlist")
                );
    }

    @Test
    @Order(3)
    @DisplayName("저장하려는 상품ID가 상품DB에 없을시 예외 던짐")
    void noProductThrowException() throws Exception {
        //Given
        WishListRequest noExistProductRequest = new WishListRequest(-1L, 100);
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
    @Order(4)
    @DisplayName("위시리스트에 추가된 상품들 조회")
    void getWishListProducts() throws Exception {
        //When
        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                )
                //Then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$[0].productName").value("SoySource"),
                        jsonPath("$[0].productAmount").value(100)
                );
    }

    @Test
    @Order(5)
    @DisplayName("위시리스트에 저장된 수량 수정")
    void updateProductAmount() throws Exception {
        //Given
        WishListRequest amountUpdateRequest = new WishListRequest(PRODUCT_ID, 99999);
        String json = objectMapper.writeValueAsString(amountUpdateRequest);

        //When
        mockMvc.perform(MockMvcRequestBuilders.put(URL)
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
    @Order(6)
    @DisplayName("수정 되었는지 확인")
    void updateCheck() throws Exception {
        //When
        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                )
                //Then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$[0].productName").value("SoySource"),
                        jsonPath("$[0].productAmount").value(99999)
                );
    }

    @Test
    @Order(7)
    @DisplayName("위시리스트에 저장된 상품 삭제")
    void deleteProduct() throws Exception {
        //Given
        WishListRequest amountUpdateRequest = new WishListRequest(PRODUCT_ID, 0);
        String json = objectMapper.writeValueAsString(amountUpdateRequest);

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
        WishListRequest amountUpdateRequest = new WishListRequest(PRODUCT_ID, 99999);
        String json = objectMapper.writeValueAsString(amountUpdateRequest);

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
        WishListRequest amountUpdateRequest = new WishListRequest(PRODUCT_ID, 0);
        String json = objectMapper.writeValueAsString(amountUpdateRequest);

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

}
