package gift.wishlist.application;

import gift.exception.type.NotFoundException;
import gift.member.domain.Member;
import gift.member.domain.MemberRepository;
import gift.product.domain.Product;
import gift.product.domain.ProductRepository;
import gift.wishlist.domain.Wishlist;
import gift.wishlist.domain.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class WishlistServiceTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private WishlistService wishlistService;

    private Long memberId;
    private Long productId;
    private Long wishlistId;
    private String memberEmail;
    private Member member;
    private Product product;
    private Wishlist wishlist;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        memberId = 1L;
        productId = 1L;
        wishlistId = 1L;
        memberEmail = "test@example.com";
        member = new Member(memberId, memberEmail, "password");
        product = new Product(productId, "Product", 1000, "image");
        wishlist = new Wishlist(wishlistId, member, product);
    }

    @Test
    public void 위시리스트에_상품_추가() {
        // Given
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // When
        wishlistService.save(memberId, productId);

        // Then
        verify(wishlistRepository, times(1)).save(any(Wishlist.class));
    }

    @Test
    public void 위시리스트에_존재하지_않는_사용자_추가_실패() {
        // Given
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> wishlistService.save(memberId, productId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당 사용자가 존재하지 않습니다.");
    }

    @Test
    public void 위시리스트에_존재하지_않는_상품_추가_실패() {
        // Given
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> wishlistService.save(memberId, productId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당 상품이 존재하지 않습니다.");
    }

    @Test
    public void 사용자의_위시리스트_조회() {
        // Given
        when(wishlistRepository.findByMemberId(memberId)).thenReturn(List.of(wishlist));

        // When
        List<WishlistResponse> result = wishlistService.findByMemberId(memberId);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getMemberId()).isEqualTo(memberId);
    }

    @Test
    public void 위시리스트_삭제() {
        // Given
        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.of(wishlist));

        // When
        wishlistService.delete(wishlistId);

        // Then
        verify(wishlistRepository, times(1)).delete(wishlist);
    }

    @Test
    public void 존재하지_않는_위시리스트_삭제_실패() {
        // Given
        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> wishlistService.delete(wishlistId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당 위시리스트가 존재하지 않습니다.");
    }
}
