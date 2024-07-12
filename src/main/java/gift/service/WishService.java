package gift.service;

import gift.exception.ResourceNotFoundException;
import gift.model.Member;
import gift.model.Product;
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

    public List<Wish> getWishesByMember(Member member) {
        return wishRepository.findByMember(member);
    }

    public List<Wish> getWishesByProductId(Product product) {
        return wishRepository.findByProductId(product.getId());
    }

    public Wish addWish(Wish wish) {
        return wishRepository.save(wish);
    }

    public boolean removeWish(Long id, Member member) {
        return wishRepository.deleteByIdAndMember(id, member) > 0;
    }
}
