package gift.service;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public WishListService(WishListRepository wishListRepository, MemberRepository memberRepository,
        ProductRepository productRepository) {
        this.wishListRepository = wishListRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    //해당 사용자의 위시리스트 조회(기존)
    @Transactional(readOnly = true)
    public List<WishList> getWishListItems(Long memberId) {
        Optional<MemberEntity> memberEntity = memberRepository.findById(memberId);
        if (memberEntity.isEmpty()) {
            throw new NotFoundException("멤버가 존재하지 않습니다.");
        }
        List<WishListEntity> wishListEntities = wishListRepository.findByMemberEntity(memberEntity);
        return wishListEntities.stream()
            .map(this::entityToDto)
            .collect(Collectors.toList());
    }

    //해당 사용자의 위시리스트 조회(페이지 네이션)
    @Transactional(readOnly = true)
    public Page<WishList> getWishListItems(Long memberId, int page, int size) {
        Optional<MemberEntity> memberEntity = memberRepository.findById(memberId);
        if (memberEntity.isEmpty()) {
            throw new NotFoundException("멤버가 존재하지 않습니다.");
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<WishListEntity> wishListEntities = wishListRepository.findByMemberEntity(memberEntity, pageable);
        return wishListEntities.map(this::entityToDto);
    }

    //위시리스트 추가
    @Transactional
    public void addWishListItem(WishList item) {
        wishListRepository.save(dtoToEntity(item));
    }

    //위시리스트 삭제
    @Transactional
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
