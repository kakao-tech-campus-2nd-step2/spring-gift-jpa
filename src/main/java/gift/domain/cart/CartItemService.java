package gift.domain.cart;

import gift.domain.cart.repository.JpaCartItemRepository;
import gift.domain.product.Product;
import gift.domain.product.repository.JpaProductRepository;

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
    public CartItemService(
        JdbcTemplate jdbcTemplate,
        JpaProductRepository jpaProductRepository,
        JpaCartItemRepository jpaCartItemRepository
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.cartItemRepository = jpaCartItemRepository;
        this.productRepository = jpaProductRepository;
    }

    /**
     * 장바구니에 상품 ID 추가
     */
    public void addCartItem(Long userId, Long productId) {
        if (cartItemRepository.existsByUserIdAndProductId(userId, productId)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "해당 상품이 장바구니에 이미 존재합니다.");
        }

        CartItem cartItem = new CartItem(userId, productId);
        cartItemRepository.save(cartItem);
    }

    /**
     * 장바구니 상품 조회
     */
    public List<Product> getProductsInCartByUserId(Long userId) {
        List<Long> productIds = cartItemRepository.findAllByUserId(userId)
            .stream()
            .map(CartItem::getProductId)
            .collect(Collectors.toList());

        System.out.println("userId = " + userId);
        List<Product> products = productRepository.findAllById(productIds);
        return products;
    }

    /**
     * 장바구니 상품 삭제
     */
    public void deleteCartItem(Long userId, Long productId) {
        cartItemRepository.deleteByUserIdAndProductId(userId, productId);
    }
}
