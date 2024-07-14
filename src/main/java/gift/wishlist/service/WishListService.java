package gift.wishlist.service;

import gift.product.model.dto.Product;
import gift.product.service.ProductService;
import gift.user.model.dto.AppUser;
import gift.user.service.UserService;
import gift.wishlist.model.WishListRepository;
import gift.wishlist.model.dto.AddWishRequest;
import gift.wishlist.model.dto.Wish;
import gift.wishlist.model.dto.WishListResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        List<Wish> wishes = wishListRepository.findWishesByAppUserIdAndIsActiveTrue(userId);
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

    @Transactional(readOnly = true)
    public Page<WishListResponse> getWishList(Long userId, Pageable pageable) {
        Page<Wish> wishes = wishListRepository.findWishesByAppUserIdAndIsActiveTrue(userId, pageable);
        return wishes.map(w -> new WishListResponse(
                w.getId(),
                w.getProduct().getId(),
                w.getProduct().getName(),
                w.getProduct().getPrice(),
                w.getProduct().getImageUrl(),
                w.getQuantity()));
    }

    @Transactional
    public void addWish(Long userId, AddWishRequest addWishRequest) {
        AppUser appUser = userService.findUser(userId);
        Product product = productService.findProduct(addWishRequest.productId());
        Wish wish = new Wish(appUser, product, addWishRequest.quantity());
        wishListRepository.save(wish);
    }

    @Transactional
    public void updateWishQuantity(Long userId, Long wishId, int quantity) {
        Wish wish = wishListRepository.findByIdAndAppUserIdAndIsActiveTrue(wishId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Wish"));
        wish.setQuantity(quantity);
        wishListRepository.save(wish);
    }

    @Transactional
    public void deleteWish(Long userId, Long wishId) {
        Wish wish = wishListRepository.findByIdAndAppUserIdAndIsActiveTrue(wishId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Wish"));
        wish.setActive(false);
        wishListRepository.save(wish);
    }
}
