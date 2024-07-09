package gift.wishlist.service;

import gift.common.exception.WishNotFoundException;
import gift.wishlist.dto.WishRequest;
import gift.wishlist.dto.WishResponse;
import gift.wishlist.model.Wish;
import gift.wishlist.repository.WishRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class WishService {
    private final WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public WishResponse addWish(Long memberId, WishRequest request) {
        Wish wish = new Wish(memberId, request.getProductId());
        wishRepository.save(wish);
        return new WishResponse(wish.getId(), wish.getProductId());
    }

    public List<WishResponse> getWishes(Long memberId) {
        List<Wish> wishes = wishRepository.findByMemberId(memberId);
        return wishes.stream()
            .map(wish -> new WishResponse(wish.getId(), wish.getProductId()))
            .collect(Collectors.toList());
    }

    @Transactional
    public void deleteWishByProductId(Long memberId, Long productId) {
        List<Wish> wishes = wishRepository.findByMemberIdAndProductId(memberId, productId);
        if(wishes.isEmpty()) {
            throw new WishNotFoundException("Wishlist에 Product ID: " + productId + "인 상품은 존재하지 않습니다.");
        }
        wishRepository.deleteByMemberIdAndProductId(memberId, productId);
    }

    @Transactional
    public void deleteWishById(Long id) {
        if(!wishRepository.existsById(id)) {
            throw new WishNotFoundException("Wishlist에 ID: " + id + "인 상품은 존재하지 않습니다.");
        }
        wishRepository.deleteById(id);
    }

}
