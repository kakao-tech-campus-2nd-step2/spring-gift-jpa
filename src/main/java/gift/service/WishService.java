package gift.service;

import gift.dto.wishlist.WishResponseDto;
import gift.dto.wishlist.WishRequestDto;
import gift.entity.Wish;
import gift.exception.wish.WishNotFoundException;
import gift.mapper.WishMapper;
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

    public List<WishResponseDto> getWishes(Long userId) {
        List<WishResponseDto> wishes = wishRepository.findByUserId(userId);

        if (wishes == null) {
            throw new WishNotFoundException("해당 사용자의 위시 리스트가 존재하지 않습니다.");
        }

        return wishRepository.findByUserId(userId);
    }

    public List<WishResponseDto> addWish(Long userId, WishRequestDto wishRequest) {
        Wish wish = WishMapper.toWish(userId, wishRequest);

        productService.checkProductExist(wish.productId());

        return wishRepository.insert(wish);
    }

    public List<WishResponseDto> updateWishes(Long userId, List<WishRequestDto> wishRequests) {
        for (WishRequestDto wishRequest : wishRequests) {
            Long wishId = getWishId(userId, wishRequest.productId());
            Wish wish = WishMapper.toWish(wishId, userId, wishRequest);
            updateWish(wish);
        }
        return wishRepository.findByUserId(userId);
    }

    public void updateWish(Wish wish) {
        if (wish.quantity() <= 0) {
            deleteWish(wish);
            return;
        }
        wishRepository.update(wish);
    }

    public List<WishResponseDto> deleteWishes(Long userId, List<WishRequestDto> wishRequests) {
        for (WishRequestDto wishRequest : wishRequests) {
            Long wishId = wishRepository.findWishId(userId, wishRequest.productId());
            Wish wish = WishMapper.toWish(wishId, userId, wishRequest);
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
