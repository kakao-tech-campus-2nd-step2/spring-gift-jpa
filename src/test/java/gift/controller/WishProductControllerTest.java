package gift.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gift.dto.ProductRequest;
import gift.dto.ProductResponse;
import gift.dto.RegisterRequest;
import gift.dto.WishProductAddRequest;
import gift.dto.WishProductResponse;
import gift.dto.WishProductUpdateRequest;
import gift.model.MemberRole;
import gift.reflection.AuthTestReflectionComponent;
import gift.service.MemberService;
import gift.service.ProductService;
import gift.service.WishProductService;
import gift.service.auth.AuthService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
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
    @Autowired
    private AuthTestReflectionComponent authTestReflectionComponent;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;
    private String managerToken;
    private String memberToken;
    private Long managerId;
    private Long memberId;
    private Long product1Id;
    private Long product2Id;

    @BeforeEach
    @DisplayName("관리자, 이용자의 토큰 값 세팅하기")
    void setBaseData() {
        var registerManagerRequest = new RegisterRequest("관리자", "admin@naver.com", "password", "ADMIN");
        var registerMemberRequest = new RegisterRequest("멤버", "'member@naver.com'", "password", "MEMBER");
        managerToken = authService.register(registerManagerRequest).token();
        memberToken = authService.register(registerMemberRequest).token();
        managerId = authTestReflectionComponent.getMemberIdWithToken(managerToken);
        memberId = authTestReflectionComponent.getMemberIdWithToken(memberToken);
        var product1Request = new ProductRequest("Apple 정품 아이폰 15", 1700000, "https://lh5.googleusercontent.com/proxy/M33I-cZvIHdtsY_uyd5R-4KXJ8uZBBAgVw4bmZagF1T5krxkC6AHpxPUvU_02yDsRljgOHwa-cUTlhgYG_bSNJbbmnf6k9OOPRQyvPf5m4nD");
        var product2Request = new ProductRequest("Apple 정품 2024 아이패드 에어 11 M2칩", 900000, "https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcThcspVP4EUYTEiUD0udG3dzUZDZOQH9eopFO7_7zZmIafSouktNeyQn8jzKwYTMxcQwaWN_iglo8LAus6DJTG_ogEaU_tHSOtNL3wiYJhYqisdTuMRT2o97h503C6gWd9BxV8_ow&usqp=CAc");
        product1Id = productService.addProduct(product1Request, MemberRole.MEMBER).id();
        product2Id = productService.addProduct(product2Request, MemberRole.MEMBER).id();
    }

    @AfterEach
    @DisplayName("이미 있는 데이터 지워 beforeEach 에서 예외가 발생하지 않도록 설정")
    void deleteBaseData() {
        memberService.deleteMember(managerId);
        memberService.deleteMember(memberId);
        productService.deleteProduct(product1Id);
        productService.deleteProduct(product2Id);
    }

    @Test
    @DisplayName("잘못된 수량으로 된 위시 리스트 상품 추가하기")
    void addWishProductFailWithZeroCount() throws Exception {
        //given
        var postRequest = post("/api/wishes/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new WishProductAddRequest(1L, 0)));
        //when
        var result = mockMvc.perform(postRequest);
        //then
        result.andExpect(status().isBadRequest())
                .andExpect(content().string("상품의 수량은 반드시 1개 이상이어야 합니다."));
    }

    @Test
    @DisplayName("위시 리스트 상품 추가하기")
    void addWishProductSuccess() throws Exception {
        //given
        var postRequest = post("/api/wishes/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new WishProductAddRequest(product1Id, 10)));
        //when
        var result = mockMvc.perform(postRequest);
        //then
        result.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("위시 리스트 상품 조회하기")
    void readWishProductSuccess() throws Exception {
        //given
        var wishProduct = wishProductService
                .addWishProduct(new WishProductAddRequest(product1Id, 10), memberId);
        var getRequest = get("/api/wishes")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken);
        //when
        var readResult = mockMvc.perform(getRequest);
        //then
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
        //given
        var wishProductAddRequest = new WishProductAddRequest(product1Id, 10);
        var wishProduct = wishProductService.addWishProduct(wishProductAddRequest, memberId);
        var postRequest = post("/api/wishes/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(wishProductAddRequest));
        //when
        var addResult = mockMvc.perform(postRequest);
        //then
        addResult.andExpect(status().isCreated());
        var wishProducts = wishProductService.getWishProducts(memberId, PageRequest.of(0, 10));
        Assertions.assertThat(wishProducts.size()).isEqualTo(1);
        Assertions.assertThat(wishProducts.get(0).count()).isEqualTo(20);

        wishProductService.deleteWishProduct(wishProduct.id());
    }

    @Test
    @DisplayName("이용자끼리의 위시리스트가 다르다")
    void addWishProductAndReadMemberAndManagerSuccess() throws Exception {
        //given
        var wishProduct1AddRequest = new WishProductAddRequest(product1Id, 10);
        var wishProduct2AddRequest = new WishProductAddRequest(product2Id, 10);
        wishProductService.addWishProduct(wishProduct1AddRequest, memberId);
        wishProductService.addWishProduct(wishProduct2AddRequest, memberId);
        var getRequest = get("/api/wishes")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + managerToken);
        //when
        var managerReadResult = mockMvc.perform(getRequest);
        //then
        var managerWishResult = managerReadResult.andExpect(status().isOk()).andReturn();
        var managerWishLength = managerWishResult.getResponse().getContentLength();
        Assertions.assertThat(managerWishLength).isEqualTo(0);
        var memberWishProducts = wishProductService.getWishProducts(memberId, PageRequest.of(0, 10));
        Assertions.assertThat(memberWishProducts.size()).isEqualTo(2);

        for (var wishProduct : memberWishProducts) {
            wishProductService.deleteWishProduct(wishProduct.id());
        }
    }

    @Test
    @DisplayName("위시 리스트 수량 변경하기")
    void addWishProductAndUpdateCountSuccess() throws Exception {
        //given
        var wishProduct = wishProductService
                .addWishProduct(new WishProductAddRequest(product1Id, 10), memberId);
        var putRequest = put("/api/wishes/update/" + wishProduct.id())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new WishProductUpdateRequest(30)));
        //when
        var result = mockMvc.perform(putRequest);
        //then
        result.andExpect(status().isNoContent());
        var wishProducts = wishProductService.getWishProducts(memberId, PageRequest.of(0, 10));
        Assertions.assertThat(wishProducts.size()).isEqualTo(1);
        Assertions.assertThat(wishProducts.get(0).count()).isEqualTo(30);

        wishProductService.deleteWishProduct(wishProduct.id());
    }

    @Test
    @DisplayName("위시 리스트 상품 추가후 수량 0으로 변경하기")
    void addWishProductAndUpdateCountZeroSuccess() throws Exception {
        //given
        var wishProduct = wishProductService
                .addWishProduct(new WishProductAddRequest(product1Id, 10),
                        memberId);
        var putRequest = put("/api/wishes/update/" + wishProduct.id())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new WishProductUpdateRequest(0)));
        //when
        var result = mockMvc.perform(putRequest);
        //then
        result.andExpect(status().isNoContent());
        var wishProducts = wishProductService.getWishProducts(memberId, PageRequest.of(0, 10));
        Assertions.assertThat(wishProducts.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("위시 리스트 상품 11개인 경우에 조회시 10개만 조회된다.")
    void getWishProductsWithPageable() throws Exception {
        //given
        var productResponseList = new ArrayList<ProductResponse>();
        for (int i = 0; i < 11; i++) {
            var productRequest = new ProductRequest("상품이름", 10000, "이미지");
            var productResponse = productService.addProduct(productRequest, MemberRole.MEMBER);
            productResponseList.add(productResponse);
            var wishProduct = new WishProductAddRequest(productResponse.id(), 10);
            wishProductService.addWishProduct(wishProduct, memberId);
        }
        var getRequest = get("/api/wishes?page=-1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken);
        //when
        var getResult = mockMvc.perform(getRequest);
        //then
        var wishProductResult = getResult.andExpect(status().isOk()).andReturn();
        var wishProductListString = wishProductResult.getResponse().getContentAsString();
        var wishProductList = objectMapper.readValue(wishProductListString, new TypeReference<List<WishProductResponse>>() {
        });
        Assertions.assertThat(wishProductList.size()).isEqualTo(10);

        for (var product : productResponseList) {
            productService.deleteProduct(product.id());
        }
    }

    @Test
    @DisplayName("잘못된 정렬 데이터가 올 경우 예외를 던진다.")
    void getWishProductsInvalidPageSort() throws Exception {
        //given
        var getRequest = get("/api/wishes?sort=id,asc")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken);
        //when
        var getResult = mockMvc.perform(getRequest);
        //then
        getResult.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("잘못된 크기 데이터가 올 경우 예외를 던진다.")
    void getWishProductsInvalidPageSize() throws Exception {
        //given
        var getRequest = get("/api/wishes?size=3")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken);
        //when
        var getResult = mockMvc.perform(getRequest);
        //then
        getResult.andExpect(status().isBadRequest());
    }
}
