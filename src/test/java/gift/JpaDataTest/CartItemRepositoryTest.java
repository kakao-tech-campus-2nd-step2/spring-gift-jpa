package gift.JpaDataTest;

import static org.assertj.core.api.Assertions.*;

import gift.domain.cart.CartItem;
import gift.domain.cart.CartItemService;
import gift.domain.cart.repository.JpaCartItemRepository;
import gift.domain.user.UserService;
import gift.domain.user.dto.UserDTO;
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
    private JwtProvider jwtProvider;

    private Long userId;

    @BeforeEach
    @Description("민지의 계정으로 로그인한 후 jwt 받음")
    public void login() {
        UserDTO userDTO = new UserDTO("minji@example.com", "password1");
        String token = userService.login(userDTO);
        userId = jwtProvider.getId(token);
    }

    @Test
    @Description("장바구니 조회")
    void getCartItems() {
        // given

        // when
        List<CartItem> cartItems = cartItemRepository.findAllByUserId(userId);
        // then
        assertThat(cartItems.size()).isEqualTo(1);
        CartItem cartItem = cartItems.get(0);
        assertThat(cartItem.getUserId()).isEqualTo(userId);
    }

    @Test
    @Description("장바구니 추가")
    void addCartItem() {
        // given
        CartItem cartItem = new CartItem(userId, 2L);
        // when
        cartItemRepository.save(cartItem);
        List<CartItem> cartItems = cartItemRepository.findAllByUserId(userId);
        for (CartItem item : cartItems) {
            System.out.println("item = " + item);
        }
        // then
        assertThat(cartItems.size()).isEqualTo(2);
        assertThat(cartItems.get(1).getUserId()).isEqualTo(userId);
        assertThat(cartItems.get(1).getProductId()).isEqualTo(2L);
    }

    @Test
    @Description("장바구니 삭제")
    void deleteCartItem() {
        // given

        // when
        cartItemRepository.deleteByUserIdAndProductId(userId, 3L);
        List<CartItem> cartItems = cartItemRepository.findAllByUserId(userId);
        // then
        assertThat(cartItems.size()).isEqualTo(0);

    }
}
