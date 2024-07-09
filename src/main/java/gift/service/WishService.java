package gift.service;

import gift.dto.wish.WishRequest;
import gift.dto.wish.WishResponse;
import gift.entity.Wish;
import gift.exception.wish.WishNotFoundException;
import gift.repository.WishRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final ProductService productService;

    public WishService(WishRepository wishRepository, ProductService productService) {
        this.wishRepository = wishRepository;
        this.productService = productService;
    }

    public List<WishResponse> getWishes(Long userId) {
        List<WishResponse> wishes = wishRepository.findByUserId(userId);

        if (wishes == null) {
            throw new WishNotFoundException("해당 사용자의 위시 리스트가 존재하지 않습니다.");
        }

        return wishes;
    }

    public List<WishResponse> addWish(Long userId, WishRequest wishRequest) {
        Wish wish = Wish.builder()
            .userId(userId)
            .productId(wishRequest.productId())
            .quantity(wishRequest.quantity())
            .build();
        productService.checkProductExist(wish.getProductId());

        return wishRepository.insert(wish);
    }

    public List<WishResponse> updateWishes(Long userId, List<WishRequest> requests) {
        for (WishRequest request : requests) {
            Wish wish = Wish.builder()
                    .id(getWishId(userId, request.productId()))
                    .userId(userId)
                    .productId(request.productId())
                    .quantity(request.quantity())
                    .build();
            updateWish(wish);
        }
        return wishRepository.findByUserId(userId);
    }

    public void updateWish(Wish wish) {
        if (wish.getQuantity() <= 0) {
            deleteWish(wish);
            return;
        }
        wishRepository.update(wish);
    }

    public List<WishResponse> deleteWishes(Long userId, List<WishRequest> requests) {
        for (WishRequest request : requests) {
            Wish wish = Wish.builder()
                .id(getWishId(userId, request.productId()))
                .userId(userId)
                .productId(request.productId())
                .quantity(request.quantity())
                .build();
            deleteWish(wish);
        }
        return wishRepository.findByUserId(userId);
    }

    public void deleteWish(Wish wish) {
        wishRepository.delete(wish);
    }

    private Long getWishId(Long userId, Long productId) {
        Long wishId = wishRepository.findWishId(userId, productId);
        if (wishId == null) {
            throw new WishNotFoundException("존재하지 않는 항목입니다.");
        }
        return wishId;
    }

}
