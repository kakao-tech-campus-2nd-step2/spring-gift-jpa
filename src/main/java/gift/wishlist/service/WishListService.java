package gift.wishlist.service;

import gift.product.model.dto.Product;
import gift.product.service.ProductService;
import gift.user.model.dto.User;
import gift.user.service.UserService;
import gift.wishlist.model.WishListRepository;
import gift.wishlist.model.dto.AddWishRequest;
import gift.wishlist.model.dto.Wish;
import gift.wishlist.model.dto.WishListResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;
    private final UserService userService;
    private final ProductService productService;

    public WishListService(WishListRepository wishListRepository, UserService userService,
                           ProductService productService) {
        this.wishListRepository = wishListRepository;
        this.userService = userService;
        this.productService = productService;
    }

    @Transactional(readOnly = true)
    public List<WishListResponse> getWishList(Long userId) {
        List<Wish> wishes = wishListRepository.findWishesByUserIdAndIsActiveTrue(userId);
        if (wishes.isEmpty()) {
            throw new EntityNotFoundException("WishList");
        }
        return wishes.stream()
                .map(w -> new WishListResponse(
                        w.getId(),
                        w.getProduct().getId(),
                        w.getProduct().getName(),
                        w.getProduct().getPrice(),
                        w.getProduct().getImageUrl(),
                        w.getQuantity()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addWish(Long userId, AddWishRequest addWishRequest) {
        User user = userService.findUser(userId);
        Product product = productService.findProduct(addWishRequest.productId());
        Wish wish = new Wish(user, product, addWishRequest.quantity());
        wishListRepository.save(wish);
    }

    @Transactional
    public void updateWishQuantity(Long userId, Long wishId, int quantity) {
        Wish wish = wishListRepository.findByIdAndUserIdAndIsActiveTrue(wishId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Wish"));
        wish.setQuantity(quantity);
        wishListRepository.save(wish);
    }

    @Transactional
    public void deleteWish(Long userId, Long wishId) {
        Wish wish = wishListRepository.findByIdAndUserIdAndIsActiveTrue(wishId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Wish"));
        wish.setActive(false);
        wishListRepository.save(wish);
    }
}
