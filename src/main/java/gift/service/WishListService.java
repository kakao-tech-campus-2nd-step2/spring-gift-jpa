package gift.service;

import gift.domain.Member;
import gift.domain.WishList;
import gift.entity.MemberEntity;
import gift.entity.ProductEntity;
import gift.entity.WishListEntity;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishListRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishListService {


    private final WishListRepository wishListRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public WishListService(WishListRepository wishListRepository, MemberRepository memberRepository, ProductRepository productRepository) {
        this.wishListRepository = wishListRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
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
        return new WishList(wishListEntity.getMemberEntity().getId(), wishListEntity.getProductEntity().getPrice());
    }

    private WishListEntity dtoToEntity(WishList wishList) {
        Optional<MemberEntity> memberEntity = memberRepository.findById(wishList.getMemberId());
        Optional<ProductEntity> productEntity = productRepository.findById(wishList.getProductId());
        return new WishListEntity(memberEntity, productEntity);
    }

}
