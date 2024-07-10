package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.domain.Product;
import gift.domain.UserInfo;
import gift.domain.Wish;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
public class WishRepositoryTest {
    @Autowired
    private WishRepository wishRepository;

    @BeforeEach
    void setUp() {
        wishRepository.deleteAll();
    }

    @Test
    @DisplayName("userId로 Wish 찾기 테스트")
    void findByUserId() {
        Wish wish = new Wish(new Product("original", 1000, "img"),
            new UserInfo("kakaocampus@gmail.com","kakao2024"),1L);
        wish = wishRepository.save(wish);
        Long userId = wish.getUserInfo().getId();

        List<Wish> byUserId = wishRepository.findByUserInfo_Id(userId);

        assertThat(byUserId).isNotEmpty();
        assertThat(byUserId.get(0).getUserInfo()).isEqualTo(wish.getUserInfo());
        assertThat(byUserId.get(0).getProduct()).isEqualTo(wish.getProduct());
    }

    @Test
    @DisplayName("위시 아이템 수량 업데이트")
    void updateWishQuantity() {
        Wish wish = new Wish(new Product("pak", 1000, "jpg"),
            new UserInfo("kakaocampus@gmail.com", "kakao2024"), 1L);
        wish = wishRepository.save(wish);
        Long userId = wish.getUserInfo().getId();
        Long productId = wish.getProduct().getId();

        wish.setQuantity(5L);
        wishRepository.save(wish);

        Wish updatedWish = wishRepository.findByUserInfo_IdAndProduct_Id(userId, productId);
        assertThat(updatedWish).isNotNull();
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
        Wish wish1 = wishRepository.save(new Wish(new Product("pak", 1000, "jpg"),
            new UserInfo("kakaocampus@gmail.com", "kakao2024"), 1L));
        Wish wish2 = wishRepository.save(new Wish(new Product("jeong", 2000, "png"),
            new UserInfo("kakao@gmail.com", "kakao"), 2L));
        Wish wish3 = wishRepository.save(new Wish(new Product("woo", 3000, "gif"),
            new UserInfo("campus@gmail.com", "2024"), 3L));

        List<Wish> wishesForUser1 = wishRepository.findByUserInfo_Id(wish1.getUserInfo().getId());
        List<Wish> wishesForUser2 = wishRepository.findByUserInfo_Id(wish2.getUserInfo().getId());
        List<Wish> wishesForUser3 = wishRepository.findByUserInfo_Id(wish3.getUserInfo().getId());

        assertThat(wishesForUser1).hasSize(1);
        assertThat(wishesForUser1.get(0).getUserInfo()).isEqualTo(wish1.getUserInfo());
        assertThat(wishesForUser1.get(0).getProduct()).isEqualTo(wish1.getProduct());
        assertThat(wishesForUser1.get(0).getQuantity()).isEqualTo(1L);

        assertThat(wishesForUser2).hasSize(1);
        assertThat(wishesForUser2.get(0).getUserInfo()).isEqualTo(wish2.getUserInfo());
        assertThat(wishesForUser2.get(0).getProduct()).isEqualTo(wish2.getProduct());
        assertThat(wishesForUser2.get(0).getQuantity()).isEqualTo(2L);

        assertThat(wishesForUser3).hasSize(1);
        assertThat(wishesForUser3.get(0).getUserInfo()).isEqualTo(wish3.getUserInfo());
        assertThat(wishesForUser3.get(0).getProduct()).isEqualTo(wish3.getProduct());
        assertThat(wishesForUser3.get(0).getQuantity()).isEqualTo(3L);

        assertThat(wishRepository.findAll()).hasSize(3);
    }

    @Test
    @DisplayName("유저아이디와 제품아이디로 위시리스트 삭제하기,존재하는지 확인하기")
    void deleteByProductIdAndUserId(){
        Wish wish = new Wish(new Product("original", 1000, "img"),
            new UserInfo("kakaocampus@gmail.com","kakao2024"),1L);
        wish = wishRepository.save(wish);
        Long userId = wish.getUserInfo().getId();
        Long productId = wish.getProduct().getId();

        List<Wish> byUserId = wishRepository.findByUserInfo_Id(userId);
        assertThat(byUserId).isNotEmpty();
        assertThat(wishRepository.existsByUserInfo_IdAndProduct_Id(userId, productId)).isTrue();

        wishRepository.deleteByProduct_IdAndUserInfo_Id(productId, userId);

        assertThat(wishRepository.findByUserInfo_Id(userId)).isEmpty();
        assertThat(wishRepository.existsByUserInfo_IdAndProduct_Id(userId, productId)).isFalse();
    }

    @Test
    @DisplayName("유저아이디와 제품아이디로 위시리스트 찾기")
    void findByUserIdAndProductId() {
        Wish wish = new Wish(new Product("original", 1000, "img"),
            new UserInfo("kakaocampus@gmail.com","kakao2024"),1L);
        wish = wishRepository.save(wish);
        Long userId = wish.getUserInfo().getId();
        Long productId = wish.getProduct().getId();

        Wish foundWish = wishRepository.findByUserInfo_IdAndProduct_Id(userId, productId);

        assertThat(foundWish).isNotNull();
        assertThat(foundWish.getProduct().getId()).isEqualTo(productId);
        assertThat(foundWish.getUserInfo().getId()).isEqualTo(userId);
        assertThat(foundWish.getQuantity()).isEqualTo(1L);

        Wish nonExistentWish = wishRepository.findByUserInfo_IdAndProduct_Id(999L, 999L);
        assertThat(nonExistentWish).isNull();
    }

    @Test
    @DisplayName("위시 아이템 삭제 후 재생성")
    void deleteAndRecreateWish() {
        Wish wish = new Wish(new Product("original", 1000, "img"),
            new UserInfo("kakaocampus@gmail.com","kakao2024"),1L);
        wish = wishRepository.save(wish);
        Long userId = wish.getUserInfo().getId();
        Long productId = wish.getProduct().getId();

        wishRepository.deleteByProduct_IdAndUserInfo_Id(productId, userId);
        assertThat(wishRepository.findByUserInfo_IdAndProduct_Id(userId, productId)).isNull();

        Wish newWish = new Wish(new Product("pak", 1000, "jpg"),
            new UserInfo("kakaocampus@gmail.com", "kakao2024"), 2L);
        newWish = wishRepository.save(newWish);

        Wish recreatedWish = wishRepository.findByUserInfo_IdAndProduct_Id(newWish.getUserInfo().getId(), newWish.getProduct().getId());
        assertThat(recreatedWish).isNotNull();
        assertThat(recreatedWish.getQuantity()).isEqualTo(2L);
    }
}
