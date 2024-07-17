package gift.service;

import gift.domain.WishList.WishList;
import gift.domain.WishList.WishListRequest;
import gift.domain.member.Member;
import gift.domain.product.Product;
import gift.repository.WishListRepository;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public List<Product> findByMemberId(Long memberId) {
        return wishListRepository.findByMember(new Member(memberId))
            .stream()
            .map(WishList::getProductId)
            .toList();
    }

    public List<Product> findByMemberId(Long memberId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return wishListRepository.findByMember(new Member(memberId), pageable)
            .getContent()
            .stream()
            .map(WishList::getProductId)
            .toList();
    }

    public void save(Long memberId, WishListRequest wishListRequest) {
        wishListRepository.save(wishListRequest.toWishList(memberId));
    }

    public void delete(Long memberId, WishListRequest wishListRequest) {
        WishList wishList = wishListRepository.findByMemberAndProduct(new Member(memberId),
            new Product(wishListRequest.productId())).orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "해당 위시 리스트를 찾을 수 없습니다"));
        wishListRepository.delete(wishList);
    }
}
