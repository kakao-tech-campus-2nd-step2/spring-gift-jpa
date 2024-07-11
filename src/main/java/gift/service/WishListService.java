package gift.service;


import gift.model.product.Product;
import gift.model.user.User;
import gift.model.wishlist.WishList;
import gift.model.wishlist.WishRequest;
import gift.model.wishlist.WishResponse;
import gift.repository.product.ProductRepository;
import gift.repository.user.UserRepository;
import gift.repository.wish.WishListRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public WishListService(WishListRepository wishListRepository,
        ProductRepository productRepository,
        UserRepository userRepository) {
        this.wishListRepository = wishListRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<WishResponse> getWishListByUserId(Long userId) {
        return wishListRepository.findAllByUserId(userId).stream()
            .map(wishList -> WishResponse.from(wishList, wishList.getProduct()))
            .collect(Collectors.toList());
    }

    public WishResponse addWishList(Long userId, WishRequest wishRequest) {
        User user = userRepository.findById(userId).orElseThrow();
        Product product = productRepository.findById(wishRequest.productId()).orElseThrow();
        WishList wishList = wishRequest.toEntity(user, product);
        return WishResponse.from(wishListRepository.save(wishList), product);

    }

    public WishResponse updateProductQuantity(Long wishId, Long userId, int quantity) {
        WishList wishList = wishListRepository.findByIdAndUserId(wishId, userId).orElseThrow();
        wishList.setQuantity(quantity);
        return WishResponse.from(wishListRepository.save(wishList), wishList.getProduct());
    }


    public void removeWishList(Long userId, Long productId) {
        wishListRepository.deleteByUserIdAndAndProductId(userId, productId);
    }
}

