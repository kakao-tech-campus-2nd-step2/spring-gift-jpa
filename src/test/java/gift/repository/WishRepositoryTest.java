package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.domain.Product;
import gift.domain.UserInfo;
import gift.domain.Wish;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
public class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @Test
    @DisplayName("userId로 Wish 찾기 테스트")
    void findByUserId() {
        Wish wish = new Wish(new Product("original", 1000, "img"),
            new UserInfo("kakaocampus@gmail.com", "kakao2024"), 1L);

        wishRepository.save(wish);

        List<Wish> byUserId = wishRepository.findByUserInfo_Id(wish.getId());

        assertThat(byUserId).isNotEmpty();
        assertThat(byUserId.getFirst().getUserInfo()).isEqualTo(wish.getUserInfo());
        assertThat(byUserId.getFirst().getProduct()).isEqualTo(wish.getProduct());
    }

    @Test
    @DisplayName("위시 아이템 수량 업데이트")
    void updateWishQuantity() {
        Product product = new Product("pak", 1000, "jpg");
        UserInfo userInfo = new UserInfo("kakaocampus@gmail.com", "kakao2024");
        Wish wish = new Wish(product, userInfo, 1L);
        Wish savedWish = wishRepository.save(wish);

        savedWish.setQuantity(5L);
        wishRepository.save(savedWish);

        Wish updatedWish = wishRepository.findByUserInfo_IdAndProduct_Id(wish.getUserInfo().getId(),
            wish.getProduct().getId());
        assertThat(updatedWish.getQuantity()).isEqualTo(5L);
    }

    @Test
    @DisplayName("존재하지 않는 위시 아이템 조회")
    void findNonExistentWish() {
        Wish wish = wishRepository.findByUserInfo_IdAndProduct_Id(999L, 999L);
        assertThat(wish).isNull();
    }

    @Test
    @DisplayName("여러 사용자의 위시 리스트 테스트")
    void multipleUserWishLists() {
        // Given
        Wish wish1 = new Wish(new Product("pak", 1000, "jpg"),
            new UserInfo("kakaocampus@gmail.com", "kakao2024"), 1L);
        Wish wish2 = new Wish(new Product("jeong", 2000, "png"),
            new UserInfo("kakao@gmail.com", "kakao"), 2L);
        Wish wish3 = new Wish(new Product("woo", 3000, "gif"),
            new UserInfo("campus@gmail.com", "2024"), 3L);

        wishRepository.saveAll(Arrays.asList(wish1, wish2, wish3));

        // When
        List<Wish> wishesForUser1 = wishRepository.findByUserInfo_Id(wish1.getUserInfo().getId());
        List<Wish> wishesForUser2 = wishRepository.findByUserInfo_Id(wish2.getUserInfo().getId());
        List<Wish> wishesForUser3 = wishRepository.findByUserInfo_Id(wish3.getUserInfo().getId());

        // Then
        assertThat(wishesForUser1).hasSize(1);
        assertThat(wishesForUser1.getFirst().getUserInfo()).isEqualTo(wish1.getUserInfo());
        assertThat(wishesForUser1.getFirst().getProduct()).isEqualTo(wish1.getProduct());
        assertThat(wishesForUser1.getFirst().getQuantity()).isEqualTo(1L);

        assertThat(wishesForUser2).hasSize(1);
        assertThat(wishesForUser2.getFirst().getUserInfo()).isEqualTo(wish2.getUserInfo());
        assertThat(wishesForUser2.getFirst().getProduct()).isEqualTo(wish2.getProduct());
        assertThat(wishesForUser2.getFirst().getQuantity()).isEqualTo(2L);

        assertThat(wishesForUser3).hasSize(1);
        assertThat(wishesForUser3.getFirst().getUserInfo()).isEqualTo(wish3.getUserInfo());
        assertThat(wishesForUser3.getFirst().getProduct()).isEqualTo(wish3.getProduct());
        assertThat(wishesForUser3.getFirst().getQuantity()).isEqualTo(3L);

        // 전체 Wish 개수 검증
        assertThat(wishRepository.findAll()).hasSize(3);
    }

    @Test
    @DisplayName("유저아이디와 제품아이디로 위시리스트 삭제하기,존재하는지 확인하기")
    void deleteByProductIdAndUserId() {
        Wish wish = new Wish(new Product("original", 1000, "img"),
            new UserInfo("kakaocampus@gmail.com", "kakao2024"), 1L);
        wishRepository.save(wish);
        List<Wish> byUserId = wishRepository.findByUserInfo_Id(wish.getUserInfo().getId());

        assertThat(byUserId).isNotNull();
        assertThat(wishRepository.existsByUserInfo_IdAndProduct_Id(wish.getUserInfo().getId(),
            wish.getProduct().getId())).isTrue();

        wishRepository.deleteByProduct_IdAndUserInfo_Id(wish.getProduct().getId(),
            wish.getUserInfo().getId());

        assertThat(wishRepository.findByUserInfo_Id(wish.getUserInfo().getId())).isEmpty();
        assertThat(wishRepository.existsByUserInfo_IdAndProduct_Id(wish.getUserInfo().getId(),
            wish.getProduct().getId())).isFalse();
    }

    @Test
    @DisplayName("유저아이디와 제품아이디로 위시리스트 찾기")
    void findByUserIdAndProductId() {
        Wish wish = new Wish(new Product("original", 1000, "img"),
            new UserInfo("kakaocampus@gmail.com", "kakao2024"), 1L);
        wishRepository.save(wish);

        Wish byUserIdAndProductId = wishRepository.findByUserInfo_IdAndProduct_Id(
            wish.getUserInfo().getId(), wish.getProduct().getId());

        assertThat(byUserIdAndProductId).isNotNull();
        assertThat(byUserIdAndProductId.getProduct().getId()).isEqualTo(wish.getProduct().getId());
        assertThat(byUserIdAndProductId.getUserInfo().getId()).isEqualTo(
            wish.getUserInfo().getId());
        assertThat(byUserIdAndProductId.getQuantity()).isEqualTo(1L);

    }

    @Test
    @DisplayName("위시 아이템 삭제 후 재생성")
    void deleteAndRecreateWish() {
        Product product = new Product("original", 1000, "img");
        Wish wish = new Wish(product,
            new UserInfo("kakaocampus@gmail.com", "kakao2024"), 1L);
        wishRepository.save(wish);

        wishRepository.deleteByProduct_IdAndUserInfo_Id(1L, 1L);
        assertThat(wishRepository.findByUserInfo_IdAndProduct_Id(1L, 1L)).isNull();

        Wish newWish = new Wish(new Product("pak", 1000, "jpg"),
            new UserInfo("kakaocampus@gmail.com", "kakao2024"), 1L);
        wishRepository.save(newWish);

        Wish recreatedWish = wishRepository.findByUserInfo_IdAndProduct_Id(2L, 2L);
        assertThat(recreatedWish).isNotNull();
        assertThat(recreatedWish.getQuantity()).isEqualTo(1L);
    }

}
