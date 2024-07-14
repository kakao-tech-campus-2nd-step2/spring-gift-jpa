package gift.wish.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gift.member.domain.Member;
import gift.member.persistence.MemberRepository;
import gift.product.domain.Product;
import gift.product.exception.ProductNotFoundException;
import gift.product.persistence.ProductRepository;
import gift.wish.domain.Wish;
import gift.wish.persistence.WishRepository;
import gift.wish.service.dto.WishParam;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WishServiceTest {
    @Mock
    private WishRepository wishRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private WishService wishService;

    private Product createProduct() {
        try {
            Constructor<Product> constructor = Product.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Member createMember() {
        try {
            Constructor<Member> constructor = Member.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("WishService Wish 생성 테스트[성공]")
    void saveWishTest() {
        //given
        WishParam wishParam = mock(WishParam.class);
        Product product = createProduct();
        Member member = createMember();
        Wish wish = new Wish(10, product, member);

        //when
        when(wishParam.productId()).thenReturn(1L);
        when(wishParam.memberId()).thenReturn(1L);
        when(wishParam.amount()).thenReturn(10);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(memberRepository.getReferenceById(1L)).thenReturn(member);
        when(wishRepository.save(any(Wish.class))).thenReturn(wish);

        Long wishId = wishService.saveWish(wishParam);

        assertEquals(wish.getId(), wishId);
        verify(wishRepository).save(any(Wish.class));
    }

    @Test
    @DisplayName("WishService Wish 생성 테스트[실패]")
    void saveWishWithNoProductTest() {
        // given
        WishParam wishParam = mock(WishParam.class);
        when(wishParam.productId()).thenReturn(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(ProductNotFoundException.class, () -> wishService.saveWish(wishParam));
    }
}