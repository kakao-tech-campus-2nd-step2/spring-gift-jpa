package gift.wishlist.service;

import gift.member.domain.Member;
import gift.member.repository.MemberRepository;
import gift.product.domain.Product;
import gift.product.domain.ProductEntity;
import gift.wishlist.domain.WishList;
import gift.wishlist.domain.WishListEntity;
import gift.wishlist.repository.WishListRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public List<WishList> getWishListItems(Long memberId) {
        List<WishListEntity> wishListEntities =  wishListRepository.findByMemberId(memberId);
        return wishListEntities.stream()
            .map(this::entityToDto)
            .collect(Collectors.toList());
    }

    public void addWishListItem(WishList item) {
        wishListRepository.save(dtoToEntity(item));
    }

    public void deleteWishListItem(Long id) {
        wishListRepository.deleteById(id);
    }

    private WishList entityToDto(WishListEntity wishListEntity) {
        return new WishList(wishListEntity.getMemberId(), wishListEntity.getProductId());
    }

    private WishListEntity dtoToEntity(WishList wishList) {
        return new WishListEntity(wishList.getMemberId(), wishList.getProductId());
    }

}
