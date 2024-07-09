package gift.service;

import gift.repository.WishRepository;
import gift.vo.Wish;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishRepository wishRepository;

    public WishlistService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public List<Wish> getWishProductList(Long memberId) {
        return wishRepository.findByMemberId(memberId);
    }

    public void addWishProduct(Wish wish) {
        wishRepository.save(wish);
    }

    public void deleteWishProduct(Integer id) {
        wishRepository.deleteById(id);
    }

}
