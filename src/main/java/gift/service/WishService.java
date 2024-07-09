package gift.service;

import gift.domain.Wish;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {
    private final WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public void addWish(Long memberId, String productName) {
        if (!wishRepository.existsByMemberIdAndProductName(memberId, productName)) {
            Wish wish = new Wish(memberId, productName);
            wishRepository.save(wish);
        }
    }

    public List<Wish> getWishes(Long memberId) {
        return wishRepository.findByMemberId(memberId);
    }

    public void removeWish(Long memberId, String productName) {
        wishRepository.deleteByMemberIdAndProductName(memberId, productName);
    }
}
