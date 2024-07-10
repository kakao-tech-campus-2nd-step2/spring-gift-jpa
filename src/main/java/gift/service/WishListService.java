package gift.service;

import gift.domain.Member;
import gift.domain.WishList;
import gift.entity.MemberEntity;
import gift.entity.ProductEntity;
import gift.entity.WishListEntity;
import gift.error.NotFoundException;
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
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(()->new NotFoundException("멤버가 존재하지 않습니다."));
        List<WishListEntity> wishListEntities =  wishListRepository.findByMemberEntity(memberEntity);
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
        MemberEntity memberEntity = memberRepository.findById(wishList.getMemberId())
                .orElseThrow(() -> new NotFoundException("멤버가 존재하지 않습니다."));
        ProductEntity productEntity = productRepository.findById(wishList.getProductId())
                .orElseThrow(() -> new NotFoundException("상품이 존재하지 않습니다."));
        return new WishListEntity(memberEntity, productEntity);
    }

}
