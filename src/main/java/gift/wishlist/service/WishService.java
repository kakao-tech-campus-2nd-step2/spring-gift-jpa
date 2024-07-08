package gift.wishlist.service;

import gift.common.exception.WishNotFoundException;
import gift.wishlist.dto.WishRequest;
import gift.wishlist.dto.WishResponse;
import gift.wishlist.model.Wish;
import gift.wishlist.repository.WishRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class WishService {
    private final WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public void addWish(Long memberId, WishRequest request) {
        Wish wish = new Wish();
        wish.setMemberId(memberId);
        wish.setProductName(request.getProductName());
        wishRepository.save(wish);
    }

    public List<WishResponse> getWishes(Long memberId) {
        List<Wish> wishes = wishRepository.findByMemberId(memberId);
        return wishes.stream()
            .map(wish -> new WishResponse(wish.getId(), wish.getProductName()))
            .collect(Collectors.toList());
    }

    public void deleteWishByProductName(Long memberId, String productName) {
        if(!wishRepository.findByName(memberId, productName).isPresent()) {
            throw new WishNotFoundException("Wishlist에 상품명: " + productName + "인 상품은 존재하지 않습니다.");
        }
        wishRepository.deleteByProductName(memberId,productName);
    }

}
