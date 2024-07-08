package gift.service;

import gift.domain.Member;
import gift.domain.Wish;
import gift.dto.WishRequest;
import gift.repository.WishRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishService {
    private final WishRepository wishRepository;
    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }
    public List<Wish> getWishesByMember(Member member) {
        return wishRepository.findByMemberId(member.getId());
    }

    public void addWish(WishRequest wishRequest, Member member) {
        Wish wish = new Wish(wishRequest.getProductId(), member.getId());
        wishRepository.save(wish);
    }

    public void deleteWish(Long id, Member member) {
        wishRepository.delete(id, member.getId());
    }
}
