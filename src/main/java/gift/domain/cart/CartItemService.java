package gift.domain.cart;

import gift.domain.product.JpaProductRepository;
import gift.domain.product.Product;
import gift.domain.user.JpaUserRepository;
import gift.domain.user.User;

import gift.global.exception.BusinessException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartItemService {

    private final JpaProductRepository productRepository;
    private final JpaCartItemRepository cartItemRepository;
    private final JpaUserRepository userRepository;

    public CartItemService(
        JpaProductRepository jpaProductRepository,
        JpaCartItemRepository jpaCartItemRepository,
        JpaUserRepository jpaUserRepository
    ) {
        this.userRepository = jpaUserRepository;
        this.cartItemRepository = jpaCartItemRepository;
        this.productRepository = jpaProductRepository;
    }

    /**
     * 장바구니에 상품 ID 추가
     */
    @Transactional
    public int addCartItem(Long userId, Long productId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다"));
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new BusinessException(HttpStatus.BAD_REQUEST, "상품을 찾을 수 없습니다"));

        // 기존에 존재하면 update
        if (cartItemRepository.existsByUserAndProduct(user, product)) {
            CartItem existsCartItem = cartItemRepository.findByUserIdAndProductId(userId, productId).get();
            existsCartItem.addOneMore();
            return existsCartItem.getCount();
        }
        // 기존에 없었으면 new
        CartItem cartItem = new CartItem(user, product);
        cartItemRepository.save(cartItem);
        return cartItem.getCount();
    }

    /**
     * 장바구니 상품 조회 - 페이징(매개변수별)
     */
    public Page<Product> getProductsInCartByUserIdAndPageAndSort(Long userId, int page, int size,
        Sort sort) {
        List<Long> productIds = cartItemRepository.findAllByUserId(userId).stream()
            .map(cartItem -> cartItem.getProduct().getId())
            .collect(Collectors.toList());

        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Product> productsPage = productRepository.findAllByIdIn(productIds,
            pageRequest); // 영속성 컨텍스트에 이미 존재

        List<Product> products = productsPage.getContent().stream()
            .map(product -> {
                return Product.createProductFromProxy(product);
            })
            .toList();

        // 새 Page 객체 생성
        return PageableExecutionUtils.getPage(products, pageRequest,
            productsPage::getTotalElements);
    }

    /**
     * 장바구니 상품 삭제
     */
    public void deleteCartItem(Long userId, Long productId) {
        cartItemRepository.deleteByUserIdAndProductId(userId, productId);
    }

}
