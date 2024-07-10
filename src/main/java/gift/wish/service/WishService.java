package gift.wish.service;

import gift.product.domain.Product;
import gift.product.exception.ProductNotFoundException;
import gift.product.persistence.ProductRepository;
import gift.wish.application.dto.request.WishRequest;
import gift.wish.application.dto.response.WishResponse;
import gift.wish.domain.Wish;
import gift.wish.exception.WishCanNotModifyException;
import gift.wish.exception.WishNotFoundException;
import gift.wish.persistence.WishRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final ProductRepository productRepository;

    public WishService(WishRepository wishRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
    }

    public void saveWish(WishRequest wishRequest, final Long userId) {
        Product product = productRepository.findById(wishRequest.productId())
                .orElseThrow(() -> ProductNotFoundException.of(wishRequest.productId()));

        Wish wish = wishRequest.toModel(userId);
        wishRepository.save(wish);
    }

    public void updateWish(Long wishId, WishRequest wishRequest, final Long userId) {
        Wish wish = wishRepository.findByIdAndUserId(wishId, userId)
                .orElseThrow(() -> WishNotFoundException.of(wishId));

        if (!wish.getProductId().equals(wishRequest.productId())) {
            throw new WishCanNotModifyException();
        }

        Wish newWish = wishRequest.toModel(wishId, userId);
        wishRepository.save(newWish);
    }

    public List<WishResponse> getWishList(final Long userId) {
        List<Wish> wishes = wishRepository.findWishesByUserId(userId);

        List<WishResponse> responses = wishes.stream()
                .map(WishResponse::fromModel)
                .toList();

        return responses;
    }

    public WishResponse getWish(final Long wishId, final Long userId) {
        Wish wish = wishRepository.findByIdAndUserId(wishId, userId)
                .orElseThrow(() -> WishNotFoundException.of(wishId));

        return WishResponse.fromModel(wish);
    }

    public void deleteWish(final Long wishId, final Long userId) {
        Wish wish = wishRepository.findByIdAndUserId(wishId, userId)
                .orElseThrow(() -> WishNotFoundException.of(wishId));

        wish.delete();
        wishRepository.save(wish);
    }
}
