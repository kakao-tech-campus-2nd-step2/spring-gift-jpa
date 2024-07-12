package gift.repository;

//import gift.model.*;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//class WishRepositoryTest {
//
//    @Autowired
//    private WishRepository wishRepository;
//
//    @BeforeEach
//    void setUp() {
//        Wish wish1 = new Wish(1L, 1L, 2);
//        Wish wish2 = new Wish(1L, 2L, 5);
//        Wish wish3 = new Wish(2L, 3L, 1);
//        wishRepository.saveAll(List.of(wish1, wish2, wish3));
//    }
//
//    @Test
//    void findByUserIdTest(){
//        //given
//        Long userId = 1L;
//        //when
//        List<Wish> wishes = wishRepository.findByUserId(userId);
//        //then
//        assertAll(
//                ()->assertThat(wishes).hasSize(2),
//                () -> assertThat(wishes).extracting(Wish::getUserId).containsOnly(userId)
//        );
//    }
//
//    @Test
//    void deleteByUserIdAndGiftIdTest(){
//        //given
//        Long userId =1L;
//        Long giftId = 1L;
//
//        //when
//        wishRepository.deleteByUserIdAndGiftId(userId,giftId);
//
//        //then
//        List<Wish> remainingGifts = wishRepository.findByUserId(userId);
//        assertAll(
//                () -> assertThat(remainingGifts).hasSize(1),
//                () -> assertThat(remainingGifts).extracting(Wish::getGiftId).doesNotContain(giftId)
//        );
//
//
//
//
//    }
//
//
//
//
//
//
//}