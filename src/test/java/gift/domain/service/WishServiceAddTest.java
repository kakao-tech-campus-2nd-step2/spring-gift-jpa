package gift.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import gift.domain.dto.request.WishRequest;
import gift.domain.dto.response.WishAddResponse;
import gift.domain.entity.Member;
import gift.domain.entity.Product;
import gift.domain.entity.Wish;
import gift.domain.exception.ProductNotFoundException;
import gift.domain.repository.WishRepository;
import gift.global.util.HashUtil;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class WishServiceAddTest {

    @InjectMocks
    private WishService wishService;

    @Mock
    private WishRepository wishRepository;

    @Mock
    private ProductService productService;

    private Product product;
    private Member member;
    private Wish wish;
    private WishRequest wishRequest;

    @BeforeEach
    void beforeEach() {
        product = new Product("name", 10000, "image.png");
        member = new Member("test@example.com", HashUtil.hashCode("password"), "user");
        wish = new Wish(product, member, 3L);
        wishRequest = new WishRequest(1L, 5L);
        ReflectionTestUtils.setField(product, "id", 1L);
        ReflectionTestUtils.setField(member, "id", 1L);
        ReflectionTestUtils.setField(wish, "id", 1L);
    }

    @Test
    @DisplayName("위시리스트 추가 - 존재하지 않는 상품인 경우")
    void addWishlist_ProductNotFoundException() {
        //given
        given(productService.getProductById(eq(product.getId()))).willThrow(ProductNotFoundException.class);

        //when & then
        assertThatThrownBy(() -> wishService.addWishlist(member, wishRequest)).isInstanceOf(ProductNotFoundException.class);
        then(productService).should(times(1)).getProductById(any(Long.class));
        then(wishRepository).should(never()).findWishesByMember(any(Member.class));
        then(wishRepository).should(never()).delete(any(Wish.class));
        then(wishRepository).should(never()).save(any(Wish.class));
    }

    @Test
    @DisplayName("위시리스트 추가 - 아이템 없고 추가 개수가 0 이하인 경우")
    void addWishlist_resultNopeTest() {
        //given
        given(productService.getProductById(eq(product.getId()))).willReturn(product);
        given(wishRepository.findWishByMemberAndProduct(eq(member), eq(product))).willReturn(Optional.empty());
        WishAddResponse expected = new WishAddResponse("nope", 0L);

        int repeat = 4;
        for (int i = 0; i < 4; i++) {
            //given
            Long quantity = i - 3L;
            wishRequest = new WishRequest(1L, quantity);

            //when
            WishAddResponse actual = wishService.addWishlist(member, wishRequest);

            //then
            assertThat(actual).isEqualTo(expected);
        }

        //verify
        then(productService).should(times(repeat)).getProductById(any(Long.class));
        then(wishRepository).should(times(repeat)).findWishByMemberAndProduct(any(Member.class), any(Product.class));
        then(wishRepository).should(never()).delete(any(Wish.class));
        then(wishRepository).should(never()).save(any(Wish.class));
    }

    @Test
    @DisplayName("위시리스트 추가 - 아이템 없고 추가 개수가 1 이상인 경우")
    void addWishlist_resultCreateTest() {
        //given
        given(productService.getProductById(eq(product.getId()))).willReturn(product);
        given(wishRepository.findWishByMemberAndProduct(eq(member), eq(product))).willReturn(Optional.empty());
        WishAddResponse expected = new WishAddResponse("create", wishRequest.quantity());

        //when
        WishAddResponse actual = wishService.addWishlist(member, wishRequest);

        //then
        assertThat(actual).isEqualTo(expected);
        then(productService).should(times(1)).getProductById(any(Long.class));
        then(wishRepository).should(times(1)).findWishByMemberAndProduct(any(Member.class), any(Product.class));
        then(wishRepository).should(never()).delete(any(Wish.class));
        then(wishRepository).should(times(1)).save(any(Wish.class));
    }

    @Test
    @DisplayName("위시리스트 추가 - 아이템 있고 수량 변화 후 결과가 양수인 경우")
    void addWishlist_resultAddTest() {
        //given
        given(productService.getProductById(eq(product.getId()))).willReturn(product);
        given(wishRepository.findWishByMemberAndProduct(eq(member), eq(product))).willReturn(Optional.of(wish));
        WishAddResponse expected = new WishAddResponse("add", wish.getQuantity() + wishRequest.quantity());

        //when
        WishAddResponse actual = wishService.addWishlist(member, wishRequest);

        //then
        assertThat(actual).isEqualTo(expected);
        then(productService).should(times(1)).getProductById(any(Long.class));
        then(wishRepository).should(times(1)).findWishByMemberAndProduct(any(Member.class), any(Product.class));
        then(wishRepository).should(never()).delete(any(Wish.class));
        then(wishRepository).should(never()).save(any(Wish.class));
    }

    @Test
    @DisplayName("위시리스트 추가 - 아이템 있고 수량 변화 후 0 이하인 경우")
    void addWishlist_resultDeleteTest() {
        //given
        given(productService.getProductById(eq(product.getId()))).willReturn(product);
        given(wishRepository.findWishByMemberAndProduct(eq(member), eq(product))).willReturn(Optional.of(wish));
        wishRequest = new WishRequest(1L, -5L);
        WishAddResponse expected = new WishAddResponse("delete", 0L);

        //when
        WishAddResponse actual = wishService.addWishlist(member, wishRequest);

        //then
        assertThat(actual).isEqualTo(expected);
        then(productService).should(times(1)).getProductById(any(Long.class));
        then(wishRepository).should(times(1)).findWishByMemberAndProduct(any(Member.class), any(Product.class));
        then(wishRepository).should(times(1)).delete(any(Wish.class));
        then(wishRepository).should(never()).save(any(Wish.class));
    }
}