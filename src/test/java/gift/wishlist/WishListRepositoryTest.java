package gift.wishlist;

import static org.assertj.core.api.Assertions.assertThat;

import gift.product.model.ProductRepository;
import gift.product.model.dto.Product;
import gift.user.model.UserRepository;
import gift.user.model.dto.User;
import gift.wishlist.model.WishListRepository;
import gift.wishlist.model.dto.Wish;
import gift.wishlist.model.dto.WishListResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WishListRepositoryTest {

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Product product;

    @BeforeEach
    public void setUp() {
        user = new User("aabb@kakao.com", "1234", "USER", "aaaa");
        product = new Product("Test", 1000, "url");

        user = userRepository.save(user);
        product = productRepository.save(product);

        Wish wish = new Wish(user, product, 1);
        wishListRepository.save(wish);
    }

    @Test
    public void testFindWishesByUserId() {
        System.out.println("user = " + user.getId());
        // 테스트 시작
        List<WishListResponse> results = wishListRepository.findWishesByUserId(user.getId());

        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getProductName()).isEqualTo(product.getName());
        assertThat(results.get(0).getProductId()).isEqualTo(product.getId());
    }
}
