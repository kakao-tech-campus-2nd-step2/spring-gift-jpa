package gift.service;

import gift.domain.product.Product;
import gift.repository.WishListRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public List<Product> read(Long memberId) {
        return wishListRepository.findProductListbyMemberId(memberId);
    }

    public void create(Long memberId, Long productId) {
        wishListRepository.insert(memberId, productId);
    }

    public void delete(Long memberId, Long productId) {
        wishListRepository.delete(memberId, productId);
    }
}
