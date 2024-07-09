package gift.service;

import gift.domain.Wish;
import gift.dto.WishRequest;
import gift.dto.WishResponse;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishService {

    private WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public List<WishResponse> getWishes(Long memberId) {
        return wishRepository.findByMemberId(memberId).stream()
                .map(wish -> new WishResponse(wish.getId(), wish.getProductName(), wish.getMemberId()))
                .collect(Collectors.toList());
    }

    public WishResponse addWish(WishRequest wishRequest, Long memberId) {
        Wish wish = new Wish(wishRequest.getProductName(), memberId);
        wishRepository.save(wish);
        return new WishResponse(wish.getId(), wish.getProductName(), wish.getMemberId());
    }

    public void removeWish(Long wishId) {
        wishRepository.deleteById(wishId);
    }
}
