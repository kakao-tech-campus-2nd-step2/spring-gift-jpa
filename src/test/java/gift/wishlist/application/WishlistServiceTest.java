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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void 위시리스트에_상품_추가() {
        // Given
        String memberEmail = "test@example.com";
        Long productId = 1L;
        Member member = new Member(memberEmail, "password");
        Product product = new Product(productId, "Product", 1000, "image");

        when(memberRepository.findByEmail(memberEmail)).thenReturn(Optional.of(member));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // When
        wishlistService.add(memberEmail, productId);

        // Then
        verify(wishlistRepository, times(1)).addWishlist(any(Wishlist.class));
    }

    @Test
    public void 위시리스트에_존재하지_않는_사용자_추가_실패() {
        // Given
        String memberEmail = "test@example.com";
        Long productId = 1L;

        when(memberRepository.findByEmail(memberEmail)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> wishlistService.add(memberEmail, productId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당 사용자가 존재하지 않습니다.");
    }

    @Test
    public void 위시리스트에_존재하지_않는_상품_추가_실패() {
        // Given
        String memberEmail = "test@example.com";
        Long productId = 1L;
        Member member = new Member(memberEmail, "password");

        when(memberRepository.findByEmail(memberEmail)).thenReturn(Optional.of(member));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> wishlistService.add(memberEmail, productId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당 상품이 존재하지 않습니다.");
    }

    @Test
    public void 사용자의_위시리스트_조회() {
        // Given
        String memberEmail = "test@example.com";
        Wishlist wishlist = new Wishlist(1L, memberEmail, 1L);

        when(wishlistRepository.findByMemberEmail(memberEmail)).thenReturn(List.of(wishlist));

        // When
        List<WishlistResponse> result = wishlistService.findAllByMember(memberEmail);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getMemberEmail()).isEqualTo(memberEmail);
    }

    @Test
    public void 위시리스트_삭제() {
        // Given
        Long wishlistId = 1L;
        Wishlist wishlist = new Wishlist(wishlistId, "test@example.com", 1L);

        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.of(wishlist));

        // When
        wishlistService.delete(wishlistId);

        // Then
        verify(wishlistRepository, times(1)).deleteWishlist(wishlistId);
    }

    @Test
    public void 존재하지_않는_위시리스트_삭제_실패() {
        // Given
        Long wishlistId = 1L;

        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> wishlistService.delete(wishlistId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당 위시리스트가 존재하지 않습니다.");
    }
}
