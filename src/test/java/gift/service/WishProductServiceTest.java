package gift.service;

import gift.dto.WishProductAddRequest;
import gift.dto.WishProductUpdateRequest;
import gift.exception.ForeignKeyConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WishProductServiceTest {

    @Autowired
    private WishProductService wishProductService;

    private final Long managerId = 1L;
    private final Long memberId = 2L;

    private final Long product1Id = 1L;
    private final Long product2Id = 2L;

    @Test
    @DisplayName("위시 리스트 상품 추가하기")
    void addProduct1ToManager() {
        var wishProductAddRequest = new WishProductAddRequest(product1Id, 5);

        Assertions.assertThat(wishProductService.getWishProducts(managerId).size()).isEqualTo(0);

        var wishProduct = wishProductService.addWishProduct(wishProductAddRequest, managerId);

        Assertions.assertThat(wishProductService.getWishProducts(managerId).size()).isEqualTo(1);

        wishProductService.deleteWishProduct(wishProduct.id());
    }

    @Test
    @DisplayName("위시 리스트 상품 삭제하기")
    void addProduct1AndProduct2ToManagerAndRemoveWishProduct2() {
        var wishProduct1AddRequest = new WishProductAddRequest(product1Id, 5);
        var wishProduct2AddRequest = new WishProductAddRequest(product2Id, 5);

        Assertions.assertThat(wishProductService.getWishProducts(managerId).size()).isEqualTo(0);

        var wishProduct1 = wishProductService.addWishProduct(wishProduct1AddRequest, managerId);
        var wishProduct2 = wishProductService.addWishProduct(wishProduct2AddRequest, managerId);

        Assertions.assertThat(wishProductService.getWishProducts(managerId).size()).isEqualTo(2);

        wishProductService.deleteWishProduct(wishProduct2.id());

        Assertions.assertThat(wishProductService.getWishProducts(managerId).size()).isEqualTo(1);

        wishProductService.deleteWishProduct(wishProduct1.id());
    }

    @Test
    @DisplayName("위시리스트 수량 0으로 변경하기")
    void addProduct1AndProduct2ToManagerAndUpdateWishProduct2WithZeroCount() {
        var wishProductAddRequest = new WishProductAddRequest(product1Id, 5);

        Assertions.assertThat(wishProductService.getWishProducts(managerId).size()).isEqualTo(0);

        var wishProduct = wishProductService.addWishProduct(wishProductAddRequest, managerId);

        Assertions.assertThat(wishProductService.getWishProducts(managerId).size()).isEqualTo(1);

        var wishProductUpdateRequest = new WishProductUpdateRequest(0);

        wishProductService.updateWishProduct(wishProduct.id(), wishProductUpdateRequest);

        Assertions.assertThat(wishProductService.getWishProducts(managerId).size()).isEqualTo(0);
    }

    @Test
    @DisplayName("이용자끼리 위시 리스트가 다르다")
    void addProduct1AndProduct2ToManagerAndAddProduct2AndProduct3ToMember() {
        var wishProduct1AddRequest = new WishProductAddRequest(product1Id, 5);
        var wishProduct2AddRequest = new WishProductAddRequest(product2Id, 5);

        var managerWishProduct1 = wishProductService.addWishProduct(wishProduct1AddRequest, managerId);
        var managerWishProduct2 = wishProductService.addWishProduct(wishProduct2AddRequest, managerId);

        Assertions.assertThat(wishProductService.getWishProducts(managerId).size()).isEqualTo(2);
        Assertions.assertThat(wishProductService.getWishProducts(memberId).size()).isEqualTo(0);

        wishProductService.deleteWishProduct(managerWishProduct1.id());
        wishProductService.deleteWishProduct(managerWishProduct2.id());
    }

    @Test
    @DisplayName("존재하지 않는 상품 ID 로 위시 리스트 상품 추가 요청시 예외 발생")
    void addWishProductFailWithInvalidProductId() {
        var invalidWishProductAddRequest = new WishProductAddRequest(10L, 5);

        Assertions.assertThatThrownBy(() -> wishProductService.addWishProduct(invalidWishProductAddRequest, memberId))
                .isInstanceOf(ForeignKeyConstraintViolationException.class);
    }

    @Test
    @DisplayName("이미 존재하는 상품 위시 리스트에 추가시 수량 변경")
    void addWishProductAlreadyExistProduct() {
        var wishProduct1AddRequest = new WishProductAddRequest(product1Id, 5);

        wishProductService.addWishProduct(wishProduct1AddRequest, memberId);
        var wishProduct = wishProductService.addWishProduct(wishProduct1AddRequest, memberId);

        var wishProducts = wishProductService.getWishProducts(memberId);

        Assertions.assertThat(wishProducts.size()).isEqualTo(1);
        Assertions.assertThat(wishProducts.get(0).count()).isEqualTo(10);

        wishProductService.deleteWishProduct(wishProduct.id());
    }
}
