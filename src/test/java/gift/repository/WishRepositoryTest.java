package gift.repository;

import gift.model.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @BeforeEach
    void setUp() {
        UserGift userGift1 = new UserGift(1L, 1L, 2);
        UserGift userGift2 = new UserGift(1L, 2L, 5);
        UserGift userGift3 = new UserGift(2L, 3L, 1);
        wishRepository.saveAll(List.of(userGift1, userGift2, userGift3));
    }

    @Test
    void findByUserIdTest(){
        //given
        Long userId = 1L;
        //when
        List<UserGift> userGifts = wishRepository.findByUserId(userId);
        //then
        assertAll(
                ()->assertThat(userGifts).hasSize(2),
                () -> assertThat(userGifts).extracting(UserGift::getUserId).containsOnly(userId)
        );
    }

    @Test
    void deleteByUserIdAndGiftIdTest(){
        //given
        Long userId =1L;
        Long giftId = 1L;

        //when
        wishRepository.deleteByUserIdAndGiftId(userId,giftId);

        //then
        List<UserGift> remainingGifts = wishRepository.findByUserId(userId);
        assertAll(
                () -> assertThat(remainingGifts).hasSize(1),
                () -> assertThat(remainingGifts).extracting(UserGift::getGiftId).doesNotContain(giftId)
        );




    }






}