package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import gift.domain.Member;
import gift.domain.Wish;
import gift.dto.WishRequest;
import gift.repository.WishRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WishServiceTest {
    @InjectMocks
    WishService wishService;
    @Mock
    WishRepository wishRepository;
    @Test
    void getWishesByMemberTest() {
        // given
        Member savedMember = new Member(1L, "email@google.co.kr", "password");
        Wish wish1 = new Wish(1L, 1L, 1L);
        Wish wish2 = new Wish(2L, 2L, 2L);
        List<Wish> wishList = Arrays.asList(wish1, wish2);

        doReturn(wishList).when(wishRepository).findByMemberId(savedMember.getId());

        // when
        List<Wish> actualwishList = wishService.getWishesByMember(savedMember);

        // then
        assertThat(actualwishList).isEqualTo(wishList);
    }
    @Test
    @DisplayName("위시 리스트 추가 테스트")
    void addWishTest() {
        // given
        WishRequest wishRequest = new WishRequest(1L);
        Member savedMember = new Member(1L, "email@google.com", "password");
        Wish wish = new Wish(1L, wishRequest.getProductId(), savedMember.getId());

        doReturn(wish).when(wishRepository).save(wishRequest, savedMember.getId());

        // when
        Wish actualWish = wishService.addWish(wishRequest, savedMember);

        // then
        assertThat(actualWish).isEqualTo(wish);
    }

    @Test
    @DisplayName("위시 리시트 삭제 테스트")
    void deleteWishTest() {
        Long id = 1L;
        Member savedMember = new Member(1L, "email@google.co.kr", "password");

        wishService.deleteWish(id, savedMember);

        verify(wishRepository, times(1)).delete(id, savedMember.getId());
    }
}