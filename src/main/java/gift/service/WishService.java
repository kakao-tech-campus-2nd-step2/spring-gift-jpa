package gift.service;

import gift.entity.Wish;
import gift.exception.BusinessException;
import gift.repository.WishRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final WishRepository wishRepository;

    @Autowired
    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public List<Wish> getWishlist(Long memberId) {
        return wishRepository.findByMemberId(memberId);
    }

    public Wish addWishlist(Wish wish) {
        Optional<Wish> wishlists = wishRepository.findByMemberIdAndProductId(wish.getMemberId(), wish.getProductId());
        if (wishlists.isEmpty()) {
            throw new BusinessException("요청한 Id에 해당하는 사용자를 찾을 수 없습니다.");
        }
        return wishRepository.save(wish);
    }

    public void deleteById(Long memberId, Long productId) {
        Optional<Wish> wish = wishRepository.findByMemberIdAndProductId(memberId, productId);
        wishRepository.delete(wish.get());
    }
}