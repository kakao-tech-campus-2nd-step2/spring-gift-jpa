package gift.service;

import gift.common.exception.ExistWishException;
import gift.common.exception.ProductNotFoundException;
import gift.common.exception.WishNotFoundException;
import gift.model.product.Product;
import gift.model.product.ProductListResponse;
import gift.model.product.ProductResponse;
import gift.model.wish.Wish;
import gift.model.wish.WishListResponse;
import gift.model.wish.WishRequest;
import gift.model.wish.WishResponse;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final ProductRepository productRepository;

    public WishService(WishRepository wishRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
    }


    public WishListResponse findAllWish(Long userId) {
        List<Wish> wishList = wishRepository.findAllByUserId(userId);
        List<WishResponse> responseList = wishList.stream()
            .map(wish -> WishResponse.from(wish,
                productRepository.findById(wish.getProductId()).orElseThrow(
                    ProductNotFoundException::new)))
            .toList();
        WishListResponse responses = new WishListResponse(responseList);
        return responses;
    }

    public void addWistList(Long userId, WishRequest wishRequest) {
        if (wishRepository.existsByProductIdAndUserId(wishRequest.productId(), userId)) {
            throw new ExistWishException();
        } else {
            wishRepository.save(wishRequest.toEntity(userId, wishRequest.count()));
        }
    }

    @Transactional
    public void updateWishList(Long userId, WishRequest wishRequest) {
        Wish wish = wishRepository.findByProductIdAndUserId(wishRequest.productId(), userId)
            .orElseThrow(WishNotFoundException::new);
        if (wishRequest.count() == 0) {
            deleteWishList(userId, wishRequest.productId());
        } else {
            wish.updateWish(wishRequest.count());
        }
    }

    @Transactional
    public void deleteWishList(Long userId, Long productId) {
        if (wishRepository.existsByProductIdAndUserId(productId, userId)) {
            wishRepository.deleteByProductIdAndUserId(productId, userId);
        } else {
            throw new WishNotFoundException();
        }
    }
}
