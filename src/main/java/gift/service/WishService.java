package gift.service;

import gift.model.Wish;
import gift.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {

    private final WishRepository wishRepository;

    @Autowired
    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public List<Wish> getWishesByMemberId(Long memberId) {
        return wishRepository.findByMemberId(memberId);
    }

    public Wish addWish(Wish wish, Long memberId) {
        wish.setMemberId(memberId);
        return wishRepository.save(wish);
    }

    public void deleteWish(Long memberId, Long id) {
        wishRepository.deleteByMemberIdAndId(memberId, id);
    }
}
