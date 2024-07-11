package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import gift.domain.Member;
import gift.domain.Wish;
import gift.dto.WishRequest;
import gift.entity.WishEntity;
import gift.repository.WishRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
    @DisplayName("getWishesByMember 테스트")
    void getWishesByMember() {
        // given
        Member savedMember = new Member(1L, "email@google.co.kr", "password");
        WishEntity wish1 = new WishEntity(1L, 1L);
        WishEntity wish2 = new WishEntity(1L, 2L);

        WishEntity spyWish1 = spy(wish1);
        WishEntity spyWish2 = spy(wish2);
        Wish expected1 = spyWish1.toWish();
        Wish expected2 = spyWish2.toWish();

        List<WishEntity> wishList = Arrays.asList(spyWish1, spyWish2);
        List<Wish> expected = Arrays.asList(expected1, expected2);

        doReturn(wishList).when(wishRepository).findAllByMemberId(savedMember.getId());
        doReturn(expected.get(0)).when(spyWish1).toWish();
        doReturn(expected.get(1)).when(spyWish2).toWish();

        // when
        List<Wish> actual = wishService.getWishesByMember(savedMember);

        // then
        assertThat(actual).isEqualTo(expected);
    }
    @Test
    @DisplayName("위시 리스트 추가 테스트")
    void addWish() {
        // given
        WishRequest wishRequest = new WishRequest(1L, 1L);
        Member savedMember = new Member(1L, "email@google.com", "password");
        WishEntity wishEntity = new WishEntity(wishRequest.getMemberId(), wishRequest.getProductId());
        WishEntity spyWishEntity = spy(wishEntity);

        Wish expected = new Wish(1L, savedMember.getId(), wishRequest.getProductId());

        doReturn(spyWishEntity).when(wishRepository).save(any(WishEntity.class));
        doReturn(expected).when(spyWishEntity).toWish();
        // when
        Wish actual = wishService.addWish(wishRequest);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("위시 리시트 삭제 테스트")
    void deleteWish() {
        Long id = 1L;
        Member savedMember = new Member(1L, "email@google.co.kr", "password");
        WishEntity wishEntity = new WishEntity(1L, 1L);

        doReturn(Optional.of(wishEntity)).when(wishRepository).findById(id);
        wishService.deleteWish(id, savedMember);
        verify(wishRepository, times(1)).delete(wishEntity);
    }
}