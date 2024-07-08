package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.domain.Wish;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class WishsRepositoryTest {
    @Autowired
    private WishsRepository wishsRepository;



    @Test
    @DisplayName("유저아이디로 위시 리스트 찾기")
    void findByUserId() {
        Wish wish1 = new Wish(1L, 1L, 1L);
        Wish wish2 = new Wish(2L, 1L, 2L);
        Wish wish3 = new Wish(3L, 2L, 3L);

        wishsRepository.saveAll(Arrays.asList(wish1, wish2, wish3));

        List<Wish> userWishes = wishsRepository.findByUserId(1L);

        assertThat(userWishes).hasSize(2);
        assertThat(userWishes).extracting(Wish::getProductId).containsExactlyInAnyOrder(1L, 2L);
    }

    @Test
    @DisplayName("위시 아이템 수량 업데이트")
    void updateWishQuantity() {
        Wish wish = new Wish(1L, 1L, 1L);
        Wish savedWish = wishsRepository.save(wish);

        savedWish.setQuantity(5L);
        wishsRepository.save(savedWish);

        Wish updatedWish = wishsRepository.findByUserIdAndProductId(1L, 1L);
        assertThat(updatedWish.getQuantity()).isEqualTo(5L);
    }
    @Test
    @DisplayName("존재하지 않는 위시 아이템 조회")
    void findNonExistentWish() {
        Wish wish = wishsRepository.findByUserIdAndProductId(999L, 999L);
        assertThat(wish).isNull();
    }
    @Test
    @DisplayName("여러 사용자의 위시 리스트 테스트")
    void multipleUserWishLists() {
        Wish wish1 = new Wish(1L, 1L, 1L);
        Wish wish2 = new Wish(1L, 2L, 1L);
        Wish wish3 = new Wish(2L, 1L, 1L);
        wishsRepository.saveAll(Arrays.asList(wish1, wish2, wish3));

        List<Wish> user1Wishes = wishsRepository.findByUserId(1L);
        List<Wish> user2Wishes = wishsRepository.findByUserId(2L);

        assertThat(user1Wishes).hasSize(2);
        assertThat(user2Wishes).hasSize(1);
    }
    @Test
    @DisplayName("유저아이디와 제품아이디로 위시리스트 삭제하기,존재하는지 확인하기")
    void deleteByProductIdAndUserId(){
        Wish wish = new Wish(1L,1L,1L);
        wishsRepository.save(wish);
        List<Wish> byUserId = wishsRepository.findByUserId(1L);

        assertThat(byUserId).isNotNull();
        assertThat(wishsRepository.existsByUserIdAndProductId(1L, 1L)).isTrue();


        wishsRepository.deleteByProductIdAndUserId(1L,1L);

        assertThat(wishsRepository.findByUserId(1L)).isEmpty();
        assertThat(wishsRepository.existsByUserIdAndProductId(1L, 1L)).isFalse();
    }

    @Test
    @DisplayName("유저아이디와 제품아이디로 위시리스트 찾기")
    void findByUserIdAndProductId() {
        Wish wish = new Wish(1L, 1L, 1L);
        wishsRepository.save(wish);

        Wish byUserIdAndProductId = wishsRepository.findByUserIdAndProductId(1L, 1L);

        assertThat(byUserIdAndProductId).isNotNull();
        assertThat(byUserIdAndProductId.getProductId()).isEqualTo(1L);
        assertThat(byUserIdAndProductId.getUserId()).isEqualTo(1L);
        assertThat(byUserIdAndProductId.getQuantity()).isEqualTo(1L);

        Wish nonExistentWish = wishsRepository.findByUserIdAndProductId(2L, 2L);
        assertThat(nonExistentWish).isNull();
    }
    @Test
    @DisplayName("위시 아이템 삭제 후 재생성")
    void deleteAndRecreateWish() {
        Wish wish = new Wish(1L, 1L, 1L);
        wishsRepository.save(wish);

        wishsRepository.deleteByProductIdAndUserId(1L, 1L);
        assertThat(wishsRepository.findByUserIdAndProductId(1L, 1L)).isNull();

        Wish newWish = new Wish(1L, 1L, 2L);
        wishsRepository.save(newWish);

        Wish recreatedWish = wishsRepository.findByUserIdAndProductId(1L, 1L);
        assertThat(recreatedWish).isNotNull();
        assertThat(recreatedWish.getQuantity()).isEqualTo(2L);
    }

}
