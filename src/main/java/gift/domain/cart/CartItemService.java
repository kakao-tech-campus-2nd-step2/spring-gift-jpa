package gift.domain.cart;

import gift.domain.product.Product;
import gift.domain.product.JpaProductRepository;

import gift.domain.user.User;
import gift.domain.user.JpaUserRepository;
import gift.global.exception.BusinessException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {

    private final JdbcTemplate jdbcTemplate;
    private final JpaProductRepository productRepository;
    private final JpaCartItemRepository cartItemRepository;
    private final JpaUserRepository userRepository;
    public CartItemService(
        JdbcTemplate jdbcTemplate,
        JpaProductRepository jpaProductRepository,
        JpaCartItemRepository jpaCartItemRepository,
        JpaUserRepository jpaUserRepository
    ) {
        this.userRepository = jpaUserRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.cartItemRepository = jpaCartItemRepository;
        this.productRepository = jpaProductRepository;
    }

    /**
     * 장바구니에 상품 ID 추가
     */
    public void addCartItem(Long userId, Long productId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다"));
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new BusinessException(HttpStatus.BAD_REQUEST, "상품을 찾을 수 없습니다"));

        if (cartItemRepository.existsByUserAndProduct(user, product)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "해당 상품이 장바구니에 이미 존재합니다.");
        }

        CartItem cartItem = new CartItem(user, product);
        cartItemRepository.save(cartItem);
    }

    /**
     * 장바구니 상품 조회
     */
    public List<Product> getProductsInCartByUserId(Long userId) {
        List<CartItem> cartItems = cartItemRepository.findAllByUserId(userId);

        return cartItems.stream()
            .map(CartItem::getProduct)
            .collect(Collectors.toList());
    }

    /**
     * 장바구니 상품 삭제
     */
    public void deleteCartItem(Long userId, Long productId) {
        cartItemRepository.deleteByUserIdAndProductId(userId, productId);
    }
}
