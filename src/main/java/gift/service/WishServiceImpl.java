package gift.service;

import gift.model.Wish;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishServiceImpl implements WishService {

    private final WishRepository wishRepository;

    public WishServiceImpl(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    @Override
    public List<Wish> getWishesByMemberId(Long memberId) {
        return wishRepository.findByMemberId(memberId);
    }

    @Override
    public Wish addWish(Wish wish) {
        return wishRepository.save(wish);
    }

    @Override
    public boolean removeWish(Long id, Long memberId) {
        return wishRepository.deleteByIdAndMemberId(id, memberId);
    }
}
