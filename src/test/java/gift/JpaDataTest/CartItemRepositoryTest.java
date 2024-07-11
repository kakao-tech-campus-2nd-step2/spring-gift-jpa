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

        cartItemRepository.save(new CartItem(user, product1));
        cartItemRepository.save(new CartItem(user, product2));
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
        Product savedProduct = productRepository.saveAndFlush(product);

        cartItemRepository.save(new CartItem(user, product));
        // when
        List<CartItem> cartItems = cartItemRepository.findAllByUser(savedUser);
        // then
        assertThat(cartItems.size()).isEqualTo(1);
        CartItem cartItem = cartItems.get(0);
        System.out.println("cartItem.getUser() = " + cartItem.getUser());
        System.out.println("savedUser = " + savedUser);
        assertThat(cartItem.getUser()).isEqualTo(savedUser);
        assertThat(cartItem.getProduct()).isEqualTo(savedProduct);
    }

    @Test
    @Description("장바구니 삭제")
    void deleteCartItem() {
        // given
        User user = new User("minji@example.com", "password1");
        User savedUser = userRepository.saveAndFlush(user);

        Product product = new Product("아이스 아메리카노 T", 4500, "https://example.com/image.jpg");
        Product savedProduct = productRepository.saveAndFlush(product);

        cartItemRepository.save(new CartItem(savedUser, savedProduct));
        // when
        cartItemRepository.deleteByUserIdAndProductId(savedUser.getId(), product.getId());
        List<CartItem> cartItems = cartItemRepository.findAllByUserId(savedUser.getId());
        // then
        assertThat(cartItems.size()).isEqualTo(0);

    }
}
