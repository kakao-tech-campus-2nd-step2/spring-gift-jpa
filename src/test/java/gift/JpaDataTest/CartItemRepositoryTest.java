package gift.JpaDataTest;

import static org.assertj.core.api.Assertions.*;

import gift.domain.cart.CartItem;
import gift.domain.cart.JpaCartItemRepository;
import gift.domain.product.Product;
import gift.domain.product.JpaProductRepository;
import gift.domain.user.User;
import gift.domain.user.JpaUserRepository;
import java.util.List;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CartItemRepositoryTest {

    @Autowired
    private JpaCartItemRepository cartItemRepository;
    @Autowired
    private JpaUserRepository userRepository;
    @Autowired
    private JpaProductRepository productRepository;

    private User user;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        User user = new User("minji@example.com", "password1");
        User savedUser = userRepository.saveAndFlush(user);
        this.user = savedUser;

        Product product1 = new Product("아이스 아메리카노 T", 4500, "https://example.com/image.jpg");
        Product product2 = new Product("아이스 카페모카 M", 4700, "https://example.com/image.jpg");
        Product savedProduct1 = productRepository.save(product1);
        Product savedProduct2 = productRepository.save(product2);
        this.product1 = savedProduct1;
        this.product2 = savedProduct2;
    }


    @Test
    @Description("장바구니 조회")
    void getCartItems() {
        // when
        cartItemRepository.save(new CartItem(user, product1));
        cartItemRepository.save(new CartItem(user, product2));
        List<CartItem> cartItems = cartItemRepository.findAllByUserId(user.getId());
        // then
        assertThat(cartItems.size()).isEqualTo(2);
    }

    @Test
    @Description("장바구니 추가")
    void addCartItem() {
        // when
        cartItemRepository.save(new CartItem(user, product1));
        List<CartItem> cartItems = cartItemRepository.findAllByUser(user);
        // then
        assertThat(cartItems.size()).isEqualTo(1);
        CartItem cartItem = cartItems.get(0);
        assertThat(cartItem.getUser()).isEqualTo(user);
        assertThat(cartItem.getProduct()).isEqualTo(product1);
    }

    @Test
    @Description("장바구니 삭제")
    void deleteCartItem() {
        // when
        cartItemRepository.save(new CartItem(user, product1));
        cartItemRepository.deleteByUserIdAndProductId(user.getId(), product1.getId());
        List<CartItem> cartItems = cartItemRepository.findAllByUserId(user.getId());
        // then
        assertThat(cartItems.size()).isEqualTo(0);

    }
}
