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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WishListControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    TokenService tokenService;
    @Autowired
    private ObjectMapper objectMapper;

    private static String TOKEN;

    @BeforeAll
    public static void setup(@Autowired ProductService productService,
                             @Autowired MemberService memberService,
                             @Autowired TokenService tokenService) {
        dummyProductAdd(productService);
        TOKEN = getTokenByRegister(memberService, tokenService);
    }

    private static void dummyProductAdd(ProductService productService) {
        productService.addProduct("SoySource", 1000, "soyImage");
        productService.addProduct("Carrot", 200, "carrotImage");
    }

    private static String getTokenByRegister(MemberService memberService, TokenService tokenService) {
        Long registeredMemberId = memberService.registerMember("test@gmail.com", "1234");
        TokenResponse tokenResponse = tokenService.generateToken(registeredMemberId);
        return tokenResponse.token();
    }

    private static final String URL = "/api/wishlist";

    @Test
    @Order(1)
    void addProductToWishList() throws Exception {
        //Given
        WishListRequest wishListRequest = new WishListRequest(1L, 100);
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
    void getWishProducts() throws Exception {
        //When
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(URL)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                )
                //Then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$[0].productName").value("SoySource"),
                        jsonPath("$[0].productAmount").value(100)
                )
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @Order(3)
    void updateWishProductAmount() throws Exception {
        //Given
        WishListRequest amountUpdateRequest = new WishListRequest(1L, 99999);
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
    @Order(4)
    void updateCheck() throws Exception {
        //When
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(URL)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                )
                //Then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$[0].productName").value("SoySource"),
                        jsonPath("$[0].productAmount").value(99999)
                ).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @Order(5)
    void deleteWishProduct() throws Exception {
        //Given
        WishListRequest amountUpdateRequest = new WishListRequest(1L, 0);
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

}
