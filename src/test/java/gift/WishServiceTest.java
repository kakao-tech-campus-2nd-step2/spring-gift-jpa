package gift;

import gift.dto.WishResponse;
import gift.model.Member;
import gift.model.Product;
import gift.model.Wish;
import gift.repository.WishRepository;
import gift.service.WishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class WishServiceTest {

    @Mock
    private WishRepository wishRepository;

    @InjectMocks
    private WishService wishService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWishesByMemberId() {
        //memberID로 위시리스트 조회
        Long memberId = 1L;

        Member member = Member.builder()
                .id(memberId)
                .email("test@example.com")
                .password("password")
                .build();

        Product product = Product.builder()
                .id(1L)
                .name("Test Product")
                .price(1000)
                .imageurl("https://t1.kakaocdn.net/kakaocorp/kakaocorp/admin/5f9c58c2017800001.png")
                .build();

        List<Wish> wishes = new ArrayList<>();
        wishes.add(Wish.builder().id(1L).member(member).product(product).build());

        when(wishRepository.findByMemberId(memberId)).thenReturn(wishes);

        List<WishResponse> result = wishService.getWishesByMemberId(memberId);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getProductName()).isEqualTo("Test Product");
        verify(wishRepository, times(1)).findByMemberId(memberId);
    }

    @Test
    void testAddWish() {
        //새로운 위시리스트 추가
        Member member = Member.builder()
                .id(1L)
                .email("test@example.com")
                .password("password")
                .build();

        Product product = Product.builder()
                .id(1L)
                .name("Test Product")
                .price(1000)
                .imageurl("https://t1.kakaocdn.net/kakaocorp/kakaocorp/admin/5f9c58c2017800001.png")
                .build();

        Wish wish = Wish.builder()
                .member(member)
                .product(product)
                .build();

        when(wishRepository.save(any(Wish.class))).thenReturn(wish);

        Wish result = wishService.addWish(member, product);

        assertThat(result.getMember()).isEqualTo(member);
        assertThat(result.getProduct()).isEqualTo(product);
        verify(wishRepository, times(1)).save(any(Wish.class));
    }

    @Test
    void testDeleteWish() {
        //위시리스트 삭제
        Long wishId = 1L;

        Wish wish = Wish.builder().id(wishId).build();
        when(wishRepository.existsById(wishId)).thenReturn(true);
        doNothing().when(wishRepository).deleteById(wishId);

        wishService.deleteWish(wishId);

        verify(wishRepository, times(1)).existsById(wishId);
        verify(wishRepository, times(1)).deleteById(wishId);
    }
}