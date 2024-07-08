package gift.domain.cart;

import gift.domain.product.Product;
import java.util.List;

public interface CartItemRepository {

    boolean isExistsInCart(Long userId, Long productId);

    void addCartItem(CartItem cartItem);

    List<CartItem> getCartItemsByUserId(Long userId);

    void deleteCartItem(Long userId, Long productId);
}
