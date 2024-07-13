package gift.service;

import gift.dto.ProductRequest;
import gift.dto.RegisterRequest;
import gift.dto.WishProductAddRequest;
import gift.dto.WishProductUpdateRequest;
import gift.exception.NotFoundElementException;
import gift.model.MemberRole;
import gift.reflection.AuthTestReflectionComponent;
import gift.service.auth.AuthService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class WishProductServiceTest {

    @Autowired
    private WishProductService wishProductService;
    @Autowired
    private ProductService productService;
    @Autowired
    private AuthService authService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private AuthTestReflectionComponent authTestReflectionComponent;
    private Long managerId;
    private Long memberId;
    private Long product1Id;
    private Long product2Id;

    @BeforeEach
    @DisplayName("멤버, 상품 기본 데이터 세팅하기")
    void addBaseData() {
        var registerManagerRequest = new RegisterRequest("관리자", "admin@naver.com", "password", "ADMIN");
        var registerMemberRequest = new RegisterRequest("멤버", "'member@naver.com'", "password", "MEMBER");
        managerId = authTestReflectionComponent.getMemberIdWithToken(authService.register(registerManagerRequest).token());
        memberId = authTestReflectionComponent.getMemberIdWithToken(authService.register(registerMemberRequest).token());
        var product1Request = new ProductRequest("Apple 정품 아이폰 15", 1700000, "https://lh5.googleusercontent.com/proxy/M33I-cZvIHdtsY_uyd5R-4KXJ8uZBBAgVw4bmZagF1T5krxkC6AHpxPUvU_02yDsRljgOHwa-cUTlhgYG_bSNJbbmnf6k9OOPRQyvPf5m4nD");
        var product2Request = new ProductRequest("Apple 정품 2024 아이패드 에어 11 M2칩", 900000, "https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcThcspVP4EUYTEiUD0udG3dzUZDZOQH9eopFO7_7zZmIafSouktNeyQn8jzKwYTMxcQwaWN_iglo8LAus6DJTG_ogEaU_tHSOtNL3wiYJhYqisdTuMRT2o97h503C6gWd9BxV8_ow&usqp=CAc");
        product1Id = productService.addProduct(product1Request, MemberRole.MEMBER).id();
        product2Id = productService.addProduct(product2Request, MemberRole.MEMBER).id();
    }

    @AfterEach
    @DisplayName("이미 있는 데이터를 지워서 예외가 발생하지 않도록 설정")
    void deleteBaseData() {
        memberService.deleteMember(managerId);
        memberService.deleteMember(memberId);
        productService.deleteProduct(product1Id);
        productService.deleteProduct(product2Id);
    }

    @Test
    @DisplayName("위시 리스트 상품 추가하기")
    void addProduct1ToManager() {
        //given
        var wishProductAddRequest = new WishProductAddRequest(product1Id, 5);
        Assertions.assertThat(wishProductService.getWishProducts(managerId, PageRequest.of(0, 10)).size()).isEqualTo(0);
        //when
        var wishProduct = wishProductService.addWishProduct(wishProductAddRequest, managerId);
        //then
        Assertions.assertThat(wishProductService.getWishProducts(managerId, PageRequest.of(0, 10)).size()).isEqualTo(1);

        wishProductService.deleteWishProduct(wishProduct.id());
    }

    @Test
    @DisplayName("위시 리스트 상품 삭제하기")
    void addProduct1AndProduct2ToManagerAndRemoveWishProduct2() {
        //given
        var wishProductAddRequest = new WishProductAddRequest(product1Id, 5);
        var wishProduct = wishProductService.addWishProduct(wishProductAddRequest, managerId);
        Assertions.assertThat(wishProductService.getWishProducts(managerId, PageRequest.of(0, 10)).size()).isEqualTo(1);
        //when
        wishProductService.deleteWishProduct(wishProduct.id());
        //then
        Assertions.assertThat(wishProductService.getWishProducts(managerId, PageRequest.of(0, 10)).size()).isEqualTo(0);
    }

    @Test
    @DisplayName("위시리스트 수량 0으로 변경하기")
    void addProduct1AndProduct2ToManagerAndUpdateWishProduct2WithZeroCount() {
        //given
        var wishProductAddRequest = new WishProductAddRequest(product1Id, 5);
        var wishProduct = wishProductService.addWishProduct(wishProductAddRequest, managerId);
        Assertions.assertThat(wishProductService.getWishProducts(managerId, PageRequest.of(0, 10)).size()).isEqualTo(1);
        var wishProductUpdateRequest = new WishProductUpdateRequest(0);
        //when
        wishProductService.updateWishProduct(wishProduct.id(), wishProductUpdateRequest);
        //then
        Assertions.assertThat(wishProductService.getWishProducts(managerId, PageRequest.of(0, 10)).size()).isEqualTo(0);
    }

    @Test
    @DisplayName("이용자끼리 위시 리스트가 다르다")
    void addProduct1AndProduct2ToManagerAndAddProduct2AndProduct3ToMember() {
        //given
        var wishProduct1AddRequest = new WishProductAddRequest(product1Id, 5);
        var wishProduct2AddRequest = new WishProductAddRequest(product2Id, 5);
        var managerWishProduct1 = wishProductService.addWishProduct(wishProduct1AddRequest, managerId);
        var managerWishProduct2 = wishProductService.addWishProduct(wishProduct2AddRequest, managerId);
        //when
        var memberWishProducts = wishProductService.getWishProducts(memberId, PageRequest.of(0, 10));
        //then
        Assertions.assertThat(memberWishProducts.size()).isEqualTo(0);

        wishProductService.deleteWishProduct(managerWishProduct1.id());
        wishProductService.deleteWishProduct(managerWishProduct2.id());
    }

    @Test
    @DisplayName("존재하지 않는 상품 ID 로 위시 리스트 상품 추가 요청시 예외 발생")
    void addWishProductFailWithInvalidProductId() {
        //given
        var invalidWishProductAddRequest = new WishProductAddRequest(10L, 5);
        //then
        Assertions.assertThatThrownBy(() -> wishProductService.addWishProduct(invalidWishProductAddRequest, memberId))
                .isInstanceOf(NotFoundElementException.class);
    }

    @Test
    @DisplayName("이미 존재하는 상품 위시 리스트에 추가시 수량 변경")
    void addWishProductAlreadyExistProduct() {
        //given
        var wishProduct1AddRequest = new WishProductAddRequest(product1Id, 5);
        wishProductService.addWishProduct(wishProduct1AddRequest, memberId);
        //when
        var wishProduct = wishProductService.addWishProduct(wishProduct1AddRequest, memberId);
        //then
        var wishProducts = wishProductService.getWishProducts(memberId, PageRequest.of(0, 10));
        Assertions.assertThat(wishProducts.size()).isEqualTo(1);
        Assertions.assertThat(wishProducts.get(0).count()).isEqualTo(10);

        wishProductService.deleteWishProduct(wishProduct.id());
    }

    @Test
    @DisplayName("2개의 상품이 추가된 상황에서 size 가 1인 페이지로 조회하면 결과의 길이는 1이다.")
    void getProductsWishPageSize1() {
        //given
        var wishProduct1AddRequest = new WishProductAddRequest(product1Id, 5);
        var wishProduct1 = wishProductService.addWishProduct(wishProduct1AddRequest, memberId);
        var wishProduct2AddRequest = new WishProductAddRequest(product2Id, 5);
        var wishProduct2 = wishProductService.addWishProduct(wishProduct2AddRequest, memberId);
        //when
        var wishProducts = wishProductService.getWishProducts(memberId, PageRequest.of(0, 1));
        //then
        Assertions.assertThat(wishProducts.size()).isEqualTo(1);

        wishProductService.deleteWishProduct(wishProduct1.id());
        wishProductService.deleteWishProduct(wishProduct2.id());
    }
}
