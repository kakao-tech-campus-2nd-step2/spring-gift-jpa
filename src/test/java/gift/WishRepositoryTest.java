package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.model.wish.Wish;
import gift.repository.WishRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class WishRepositoryTest {

//    @Autowired
//    private WishRepository wishRepository;
//
//    @Test
//    @DisplayName("위시 리스트 등록")
//    void register() {
//        Wish wish = new Wish(null, 1L, 1L, 3);
//        Wish actual = wishRepository.save(wish);
//        assertAll(
//            () -> assertThat(actual.getId()).isNotNull(),
//            () -> assertThat(actual.getProductId()).isEqualTo(wish.getProductId()),
//            () -> assertThat(actual.getUserId()).isEqualTo(wish.getUserId()),
//            () -> assertThat(actual.getCount()).isEqualTo(wish.getCount())
//        );
//    }
//
//    @Test
//    @DisplayName("위시 리스트 조회")
//    void findWish() {
//        List<Wish> wishes = wishRepository.findAllByUserId(1L);
//        assertThat(wishes).isNotNull();
//    }
//
//    @Test
//    @DisplayName("위시 리스트 삭졔")
//    void delete() {
//        wishRepository.deleteByProductId(1L);
//        Optional<Wish> wish = wishRepository.findByProductIdAndUserId(1L, 1L);
//        assertThat(wish).isEmpty();
//    }
}
