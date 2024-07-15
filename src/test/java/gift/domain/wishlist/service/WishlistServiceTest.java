package gift.domain.wishlist.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

import gift.domain.product.dao.ProductJpaRepository;
import gift.domain.product.entity.Product;
import gift.domain.user.entity.Role;
import gift.domain.user.entity.User;
import gift.domain.wishlist.dao.WishlistJpaRepository;
import gift.domain.wishlist.dto.WishItemDto;
import gift.domain.wishlist.entity.WishItem;
import gift.exception.InvalidProductInfoException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@AutoConfigureMockMvc
@SpringBootTest
class WishlistServiceTest {

    @Autowired
    private WishlistService wishlistService;

    @MockBean
    private WishlistJpaRepository wishlistJpaRepository;

    @MockBean
    private ProductJpaRepository productJpaRepository;


    private static final User user = new User(1L, "testUser", "test@test.com", "test123", Role.USER);
    private static final Product product = new Product(1L, "아이스 카페 아메리카노 T", 4500, "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110563]_20210426095937947.jpg");


    @Test
    @DisplayName("위시리스트 추가 성공")
    void create_success() {
        // given
        WishItemDto wishItemDto = new WishItemDto(null, 1L);
        given(productJpaRepository.findById(anyLong())).willReturn(Optional.of(product));

        WishItem wishItem = wishItemDto.toWishItem(user, product);
        wishItem.setId(1L);
        given(wishlistJpaRepository.save(any(WishItem.class))).willReturn(wishItem);

        // when
        WishItem savedWishItem = wishlistService.create(wishItemDto, user);

        // then
        assertAll(
            () -> assertThat(savedWishItem).isNotNull(),
            () -> assertThat(savedWishItem.getId()).isEqualTo(1L),
            () -> assertThat(savedWishItem.getUserId()).isEqualTo(wishItem.getUserId()),
            () -> assertThat(savedWishItem.getProductId()).isEqualTo(wishItem.getUserId())
        );
    }

    @Test
    @DisplayName("위시리스트 추가 서비스 실패 - 존재하지 않는 상품 ID")
    void create_fail_product_id_error() {
        // given
        WishItemDto wishItemDto = new WishItemDto(null, 2L);
        given(productJpaRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> wishlistService.create(wishItemDto, user))
            .isInstanceOf(InvalidProductInfoException.class)
            .hasMessage("error.invalid.product.id");
    }

    @Test
    @DisplayName("위시리스트 조회 성공")
    void readAll_success() {
        // given
        Product product2 = new Product(2L, "아이스 카페 라떼 T", 4500, "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110563]_20210426095937947.jpg");

        List<WishItem> wishItemList = List.of(
            new WishItem(1L, user, product),
            new WishItem(2L, user, product2)
        );

        given(wishlistJpaRepository.findAllByUserId(eq(user.getId()), any(Pageable.class)))
            .willReturn(new PageImpl<>(wishItemList));

        // when
        Page<WishItem> wishItems = wishlistService.readAll(PageRequest.of(0, 5), user);

        // then
        assertAll(
            () -> assertThat(wishItems.getSize()).isEqualTo(2),
            () -> assertThat(wishItems.getTotalElements()).isEqualTo(2),
            () -> assertThat(wishItems.getTotalPages()).isEqualTo(1)
        );
    }

    @Test
    @DisplayName("위시리스트 삭제 성공")
    void delete_success() {
        // given
        WishItem wishItem = new WishItem(1L, user, product);
        given(wishlistJpaRepository.findById(anyLong())).willReturn(Optional.of(wishItem));
        willDoNothing().given(wishlistJpaRepository).delete(any(WishItem.class));

        // when
        wishlistService.delete(1L);

        // then
        Page<WishItem> wishlist = wishlistService.readAll(PageRequest.of(0, 5), user);
        assertThat(wishlist).isNull();
    }

    @Test
    @DisplayName("위시리스트 삭제 실패 - 존재하지 않는 항목 ID")
    void delete_fail_id_error() {
        // given
        given(wishlistJpaRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> wishlistService.delete(2L))
            .isInstanceOf(InvalidProductInfoException.class)
            .hasMessage("error.invalid.product.id");
    }

    @Test
    @DisplayName("위시리스트 사용자 ID로 삭제 성공")
    void deleteAllByUserId_success() {
        // given
        given(wishlistJpaRepository.findAllByUserId(eq(user.getId()), any(Pageable.class)))
            .willReturn(new PageImpl<>(Collections.EMPTY_LIST));
        willDoNothing().given(wishlistJpaRepository).deleteAllByUserId(anyLong());

        // when
        wishlistService.deleteAllByUserId(user);

        // then
        Page<WishItem> wishlist = wishlistService.readAll(PageRequest.of(0, 5), user);
        assertThat(wishlist).isEmpty();
    }
}