package gift.service;

import gift.domain.WishList.WishList;
import gift.domain.WishList.WishListRequest;
import gift.domain.product.Product;
import gift.repository.ProductRepository;
import gift.repository.WishListRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;

    public WishListService(WishListRepository wishListRepository,
        ProductRepository productRepository) {
        this.wishListRepository = wishListRepository;
        this.productRepository = productRepository;
    }

    public List<Product> findByMemberId(Long memberId) {
        List<Long> productIdList = wishListRepository.findByMemberId(memberId)
            .stream()
            .map(WishList::getProductId)
            .toList();
        return productRepository.findByIdIn(productIdList);
    }

    public void save(Long memberId, WishListRequest wishListRequest) {
        wishListRepository.save(wishListRequest.toWishList(memberId));
    }

    public void delete(Long memberId, WishListRequest wishListRequest) {
        WishList wishList = wishListRepository.findByMemberIdAndProductId(memberId,
            wishListRequest.productId()).orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "해당 위시 리스트를 찾을 수 없습니다"));
        wishListRepository.delete(wishList);
    }
}
