package gift.service;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import gift.repository.WishRepository;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WishServiceTest {

    @Mock
    private WishRepository wishRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private WishService wishService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddWish() {
        Member member = new Member();
        member.setId(1L);
        Product product = new Product();
        product.setName("Test Product");

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(productRepository.findByName("Test Product")).thenReturn(Optional.of(product));
        when(wishRepository.existsByMemberAndProduct(member, product)).thenReturn(false);

        wishService.addWish(1L, "Test Product");

        verify(wishRepository, times(1)).save(any(Wish.class));
    }

    @Test
    void testRemoveWish() {
        Member member = new Member();
        member.setId(1L);
        Product product = new Product();
        product.setName("Test Product");

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(productRepository.findByName("Test Product")).thenReturn(Optional.of(product));

        wishService.removeWish(1L, "Test Product");

        verify(wishRepository, times(1)).deleteByMemberAndProduct(member, product);
    }
}
