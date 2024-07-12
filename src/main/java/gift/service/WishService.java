package gift.service;

import gift.model.Wish;
import gift.repository.WishRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public List<Wish> getWishesByMemberId(Long memberId) {
        return wishRepository.findByMemberId(memberId);
    }

    public Wish addWish(Wish wish) {
        return wishRepository.save(wish);
    }

    public boolean removeWish(Long id, Long memberId) {
        return wishRepository.deleteByIdAndMemberId(id, memberId) > 0;
    }
}
