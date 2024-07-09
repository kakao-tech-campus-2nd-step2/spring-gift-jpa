package gift.JpaDataTest;

import static org.assertj.core.api.Assertions.*;

import gift.domain.cart.CartItem;
import gift.domain.cart.CartItemService;
import gift.domain.cart.repository.JpaCartItemRepository;
import gift.domain.product.Product;
import gift.domain.product.repository.JpaProductRepository;
import gift.domain.user.User;
import gift.domain.user.UserService;
import gift.domain.user.dto.UserDTO;
import gift.domain.user.repository.JpaUserRepository;
import gift.global.jwt.JwtProvider;
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
    private CartItemService cartItemService;
    @Autowired
    private UserService userService;
    @Autowired
    private JpaUserRepository userRepository;
    @Autowired
    private JpaProductRepository productRepository;
    @Autowired
    private JwtProvider jwtProvider;

    private Long userId;

    @Test
    @Description("장바구니 조회")
    void getCartItems() {
        // given
        User user = new User("minji@example.com", "password1");
        User savedUser = userRepository.saveAndFlush(user);

        Product product1 = new Product("아이스 아메리카노 T", 4500, "https://example.com/image.jpg");
        Product product2 = new Product("아이스 카페모카 M", 4700, "https://example.com/image.jpg");

        productRepository.saveAndFlush(product1);
        productRepository.saveAndFlush(product2);

        cartItemRepository.save(new CartItem(savedUser.getId(), product1.getId()));
        cartItemRepository.save(new CartItem(savedUser.getId(), product2.getId()));
        // when
        List<CartItem> cartItems = cartItemRepository.findAllByUserId(savedUser.getId());
        // then
        assertThat(cartItems.size()).isEqualTo(2);
    }

    @Test
    @Description("장바구니 추가")
    void addCartItem() {
        // given
        User user = new User("minji@example.com", "password1");
        User savedUser = userRepository.saveAndFlush(user);

        Product product = new Product("아이스 아메리카노 T", 4500, "https://example.com/image.jpg");
        productRepository.saveAndFlush(product);

        cartItemRepository.save(new CartItem(savedUser.getId(), product.getId()));
        // when
        List<CartItem> cartItems = cartItemRepository.findAllByUserId(savedUser.getId());
        // then
        assertThat(cartItems.size()).isEqualTo(1);
        CartItem cartItem = cartItems.get(0);
        assertThat(cartItem.getUserId()).isEqualTo(savedUser.getId());
        assertThat(cartItem.getProductId()).isEqualTo(product.getId());
    }

    @Test
    @Description("장바구니 삭제")
    void deleteCartItem() {
        // given
        User user = new User("minji@example.com", "password1");
        User savedUser = userRepository.saveAndFlush(user);

        Product product = new Product("아이스 아메리카노 T", 4500, "https://example.com/image.jpg");
        productRepository.saveAndFlush(product);

        cartItemRepository.save(new CartItem(savedUser.getId(), product.getId()));
        // when
        cartItemRepository.deleteByUserIdAndProductId(savedUser.getId(), product.getId());
        List<CartItem> cartItems = cartItemRepository.findAllByUserId(userId);
        // then
        assertThat(cartItems.size()).isEqualTo(0);

    }
}
