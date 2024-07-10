package gift.service;

import gift.common.exception.ExistWishException;
import gift.common.exception.ProductNotFoundException;
import gift.common.exception.UserNotFoundException;
import gift.common.exception.WishNotFoundException;
import gift.model.product.Product;
import gift.model.product.ProductListResponse;
import gift.model.product.ProductResponse;
import gift.model.user.User;
import gift.model.wish.Wish;
import gift.model.wish.WishListResponse;
import gift.model.wish.WishRequest;
import gift.model.wish.WishResponse;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import gift.repository.WishRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class WishService {

    private final WishRepository wishRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public WishService(WishRepository wishRepository, ProductRepository productRepository,
        UserRepository userRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }


    public WishListResponse findAllWish(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Wish> wishList = wishRepository.findByUser(user);

        List<WishResponse> responseList = wishList.stream()
            .map(wish -> WishResponse.from(wish,
                productRepository.findById(wish.getProduct().getId()).orElseThrow(
                    ProductNotFoundException::new)))
            .toList();

        WishListResponse responses = new WishListResponse(responseList);
        return responses;
    }

    @Transactional
    public void addWistList(Long userId, WishRequest wishRequest) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Product product = productRepository.findById(wishRequest.productId())
            .orElseThrow(ProductNotFoundException::new);

        if (wishRepository.existsByProductAndUser(product, user)) {
            throw new ExistWishException();
        }

        wishRepository.save(wishRequest.toEntity(user, product, wishRequest.count()));
    }

    @Transactional
    public void updateWishList(Long userId, WishRequest wishRequest) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Product product = productRepository.findById(wishRequest.productId())
            .orElseThrow(ProductNotFoundException::new);

        Wish wish = wishRepository.findByProductAndUser(product, user)
            .orElseThrow(WishNotFoundException::new);

        if (wishRequest.count() == 0) {
            deleteWishList(userId, wishRequest.productId());
        } else {
            wish.updateWish(wishRequest.count());
        }
    }

    @Transactional
    public void deleteWishList(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Product product = productRepository.findById(productId)
            .orElseThrow(ProductNotFoundException::new);

        if (!wishRepository.existsByProductAndUser(product, user)) {
            throw new WishNotFoundException();
        }

        wishRepository.deleteByProductAndUser(product, user);
    }
}
