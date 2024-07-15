package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gift.model.Member;
import gift.model.Product;
import gift.model.Wish;
import gift.repository.WishRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
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

    @InjectMocks
    private WishService wishService;

    private Wish wish;
    private Product product;
    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member("test@email.com", "testPassword");
        wish = new Wish(new Product("product", 10000, "image.jpg"), member);
        wish.setId(1L);
    }

    @Test
    @DisplayName("회원 위시 리스트 탐색")
    public void getWishesByMemberTest() throws Exception {
        // given
        when(wishRepository.findByMember(member)).thenReturn(List.of(wish));

        // when
        var wishesByMember = wishService.getWishesByMember(member);

        // then
        assertThat(wishesByMember).isEqualTo(List.of(wish));

        // verify
        verify(wishRepository, times(1)).findByMember(member);
    }

    @Test
    @DisplayName("위시 리스트 추가")
    public void addWishTest() throws Exception {
        // given
        when(wishRepository.save(wish)).thenReturn(wish);

        // when
        var addedWish = wishService.addWish(wish);

        // then
        assertThat(addedWish).isEqualTo(wish);

        // verify
        verify(wishRepository, times(1)).save(wish);
    }

    @Test
    @DisplayName("위시 리스트 삭제")
    public void removeWishTest() throws Exception {
        // given
        when(wishRepository.deleteByIdAndMember(wish.getId(), member)).thenReturn(1);

        // when
        var result = wishRepository.deleteByIdAndMember(wish.getId(), member);

        // then
        assertThat(result).isEqualTo(1);

        // verify
        verify(wishRepository, times(1)).deleteByIdAndMember(wish.getId(), member);
    }
}