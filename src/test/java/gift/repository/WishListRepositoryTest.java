package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import gift.model.product.Product;
import gift.model.user.User;
import gift.model.wishlist.WishList;
import gift.repository.product.ProductRepository;
import gift.repository.user.UserRepository;

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
    private WishListRepository wishListRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;
    @Autowired
    public WishListRepositoryTest(WishListRepository wishListRepository,
        ProductRepository productRepository, UserRepository userRepository) {
        this.wishListRepository = wishListRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    private WishList wish1;
    private WishList wish2;
    private User user1, user2;
    private Product product1, product2;

    @BeforeEach
    void setup() {
        product1 = new Product("아메리카노", 4500, "americano");
        product2 = new Product("가방", 120000, "bag");
        user1 = new User("kakao", "kakao@google.com", "password", "user");
        user2 = new User("naver", "naver@google.com", "password", "user");
        userRepository.save(user1);
        userRepository.save(user2);
        productRepository.save(product1);
        productRepository.save(product2);

        wish1 = new WishList(user1, product1, 2);
        wish2 = new WishList(user2, product2, 3);
        wishListRepository.save(wish1);
        wishListRepository.save(wish2);
    }

    @DisplayName("위시리스트 정보 저장 테스트")
    @Test
    void save() {
        // given
        WishList wish3 = new WishList(user1, product2, 2);
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
