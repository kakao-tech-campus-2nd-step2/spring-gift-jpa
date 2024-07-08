package gift.domain.cart;

import gift.domain.product.repository.JdbcTemplateProductRepository;
import gift.domain.product.Product;
import gift.domain.product.repository.ProductRepository;
import gift.global.exception.BusinessException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {

    private final JdbcTemplate jdbcTemplate;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartItemService(
        JdbcTemplate jdbcTemplate,
        JdbcTemplateCartItemRepository jdbcTemplateCartRepository,
        JdbcTemplateProductRepository jdbcTemplateProductRepository
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.cartItemRepository = jdbcTemplateCartRepository;
        this.productRepository = jdbcTemplateProductRepository;
    }

    /**
     * 장바구니에 상품 ID 추가
     */
    public void addCartItem(Long userId, Long productId) {
        if (cartItemRepository.isExistsInCart(userId, productId)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "해당 상품이 장바구니에 이미 존재합니다.");
        }

        CartItem cartItem = new CartItem(userId, productId);
        cartItemRepository.addCartItem(cartItem);
    }

    /**
     * 장바구니 상품 조회
     */
    public List<Product> getProductsInCartByUserId(Long userId) {
        List<Long> cartItemIds = cartItemRepository.getCartItemsByUserId(userId)
            .stream()
            .map(CartItem::getProductId)
            .collect(Collectors.toList());

        List<Product> products = productRepository.getProductsByIds(cartItemIds);

        return products;
    }

    /**
     * 장바구니 상품 삭제
     */
    public void deleteCartItem(Long userId, Long productId) {
        cartItemRepository.deleteCartItem(userId, productId);
    }
}
