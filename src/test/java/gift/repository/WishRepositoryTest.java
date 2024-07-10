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
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Test
    @DisplayName("userId로 Wish 찾기 테스트")
    void findByUserId() {
        Product product = new Product("original", 1000, "img");
        UserInfo userInfo = new UserInfo("kakaocampus@gmail.com", "kakao2024");
        productRepository.save(product);
        userInfoRepository.save(userInfo);

        Wish wish = new Wish(product, userInfo, 1L);
        wishRepository.save(wish);

        List<Wish> byUserId = wishRepository.findByUserInfoId(wish.getUserInfo().getId());

        assertThat(byUserId).isNotEmpty();
        assertThat(byUserId.getFirst().getUserInfo()).isEqualTo(wish.getUserInfo());
        assertThat(byUserId.getFirst().getProduct()).isEqualTo(wish.getProduct());
    }

    @Test
    @DisplayName("위시 아이템 수량 업데이트")
    void updateWishQuantity() {
        Product product = new Product("pak", 1000, "jpg");
        UserInfo userInfo = new UserInfo("kakaocampus@gmail.com", "kakao2024");
        productRepository.save(product);
        userInfoRepository.save(userInfo);
        Wish wish = new Wish(product, userInfo, 1L);
        Wish savedWish = wishRepository.save(wish);

        savedWish.setQuantity(5L);
        wishRepository.save(savedWish);

        Wish updatedWish = wishRepository.findByUserInfoIdAndProductId(wish.getUserInfo().getId(),
            wish.getProduct().getId());

        assertThat(updatedWish.getQuantity()).isEqualTo(5L);
    }

    @Test
    @DisplayName("존재하지 않는 위시 아이템 조회")
    void findNonExistentWish() {
        Wish wish = wishRepository.findByUserInfoIdAndProductId(999L, 999L);
        assertThat(wish).isNull();
    }

    @Test
    @DisplayName("여러 사용자의 위시 리스트 테스트")
    void multipleUserWishLists() {
        // Given
        Product product1 = new Product("pak", 1000, "jpg");
        Product product2 = new Product("jeong", 2000, "png");
        Product product3 = new Product("woo", 3000, "gif");
        UserInfo userInfo1 = new UserInfo("kakaocampus@gmail.com", "kakao2024");
        UserInfo userInfo2 = new UserInfo("kakao@gmail.com", "kakao");
        UserInfo userInfo3 = new UserInfo("campus@gmail.com", "2024");

        productRepository.saveAll(Arrays.asList(product1, product2, product3));
        userInfoRepository.saveAll(Arrays.asList(userInfo1, userInfo2, userInfo3));
        Wish wish1 = new Wish(product1, userInfo1, 1L);
        Wish wish2 = new Wish(product2, userInfo2, 2L);
        Wish wish3 = new Wish(product3, userInfo3, 3L);

        wishRepository.saveAll(Arrays.asList(wish1, wish2, wish3));

        // When
        List<Wish> wishesForUser1 = wishRepository.findByUserInfoId(wish1.getUserInfo().getId());
        List<Wish> wishesForUser2 = wishRepository.findByUserInfoId(wish2.getUserInfo().getId());
        List<Wish> wishesForUser3 = wishRepository.findByUserInfoId(wish3.getUserInfo().getId());

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
        Product product = new Product("original", 1000, "img");
        UserInfo userInfo = new UserInfo("kakaocampus@gmail.com", "kakao2024");
        productRepository.save(product);
        userInfoRepository.save(userInfo);
        Wish wish = new Wish(product, userInfo, 1L);
        wishRepository.save(wish);
        List<Wish> byUserId = wishRepository.findByUserInfoId(wish.getUserInfo().getId());

        assertThat(byUserId).isNotNull();
        assertThat(wishRepository.existsByUserInfoIdAndProductId(wish.getUserInfo().getId(),
            wish.getProduct().getId())).isTrue();

        wishRepository.deleteByProductIdAndUserInfoId(wish.getProduct().getId(),
            wish.getUserInfo().getId());

        assertThat(wishRepository.findByUserInfoId(wish.getUserInfo().getId())).isEmpty();
        assertThat(wishRepository.existsByUserInfoIdAndProductId(wish.getUserInfo().getId(),
            wish.getProduct().getId())).isFalse();
    }

    @Test
    @DisplayName("유저아이디와 제품아이디로 위시리스트 찾기")
    void findByUserIdAndProductId() {
        Product product = new Product("original", 1000, "img");
        UserInfo userInfo = new UserInfo("kakaocampus@gmail.com", "kakao2024");
        productRepository.save(product);
        userInfoRepository.save(userInfo);
        Wish wish = new Wish(product, userInfo, 1L);
        wishRepository.save(wish);

        Wish byUserIdAndProductId = wishRepository.findByUserInfoIdAndProductId(
            wish.getUserInfo().getId(), wish.getProduct().getId());

        assertThat(byUserIdAndProductId).isNotNull();
        assertThat(byUserIdAndProductId.getProduct().getId()).isEqualTo(wish.getProduct().getId());
        assertThat(byUserIdAndProductId.getUserInfo().getId()).isEqualTo(
            wish.getUserInfo().getId());
        assertThat(byUserIdAndProductId.getQuantity()).isEqualTo(1L);

    }

    @Test
    @DisplayName("위시 아이템 삭제")
    void deleteAndRecreateWish() {
        Product product = new Product("original", 1000, "img");
        UserInfo userInfo = new UserInfo("kakaocampus@gmail.com", "kakao2024");
        productRepository.save(product);
        userInfoRepository.save(userInfo);

        Wish wish = new Wish(product, userInfo, 1L);
        wishRepository.save(wish);

        wishRepository.deleteByProductIdAndUserInfoId(product.getId(), userInfo.getId());
        assertThat(wishRepository.findByUserInfoIdAndProductId(product.getId(),
            userInfo.getId())).isNull();

    }

}
