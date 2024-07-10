package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import gift.domain.WishList;
import gift.repository.wish.WishListRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class WishListRepositoryTest {
    @Autowired
    private WishListRepository wishListRepository;

    private WishList wish1;
    private WishList wish2;

    @BeforeEach
    void setup() {
        wish1 = new WishList(1L, 1L, 2);
        wish2 = new WishList(1L, 3L, 3);
        wishListRepository.save(wish1);
        wishListRepository.save(wish2);
    }

    @DisplayName("위시리스트 정보 저장 테스트")
    @Test
    void save() {
        // given
        WishList wish3 = new WishList(1L, 1L, 2);
        // when
        WishList savedWish = wishListRepository.save(wish3);
        // then
        Assertions.assertAll(
            () -> assertThat(savedWish.getId()).isNotNull(),
            () -> assertThat(savedWish.getQuantity()).isEqualTo(wish3.getQuantity())
        );
    }

    @DisplayName("id에 따른 위시 리스트 찾기 테스트")
    @Test
    void findbyid() {
        // given
        Long id = wish1.getId();

        // when
        Optional<WishList> findWish = wishListRepository.findById(id);
        Long findId = findWish.get().getId();
        // then
        assertThat(findId).isEqualTo(id);
    }

    @DisplayName("위시 리스트 삭제 기능 테스트")
    @Test
    void deletebyid() {
        // given
        Long deleteId = wish2.getId();
        // when
        wishListRepository.deleteById(deleteId);
        List<WishList> savedWish = wishListRepository.findAll();
        // then
        assertThat(savedWish.size()).isEqualTo(1);
    }
}