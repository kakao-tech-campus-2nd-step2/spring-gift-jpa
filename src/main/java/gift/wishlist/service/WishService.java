package gift.wishlist.service;

import gift.member.domain.Member;
import gift.wishlist.domain.Wish;
import gift.wishlist.dto.WishRequest;
import gift.member.entity.MemberEntity;
import gift.product.entity.ProductEntity;
import gift.wishlist.entity.WishEntity;
import gift.member.repository.MemberRepository;
import gift.product.repository.ProductRepository;
import gift.wishlist.repository.WishRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public WishService(WishRepository wishRepository, MemberRepository memberRepository,
        ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;

    }

    public List<Wish> getWishesByMember(Member member, int pageNo, int pageSize) {
        MemberEntity memberEntity = memberRepository.findById(member.getId())
            .orElseThrow(() -> new EntityNotFoundException("멤버가 없습니다."));
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return wishRepository
            .findAllByMemberEntity(memberEntity, pageable)
            .stream()
            .map(this::entityToDomain)
            .toList();
    }

    public Wish addWish(WishRequest wishRequest) {
        WishEntity wishEntity = DtoToEntity(wishRequest);

        return entityToDomain(wishRepository.save(wishEntity));
    }

    public void deleteWish(Long id, Member member) {
        WishEntity wishEntity = wishRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("not found entity"));

        if (wishEntity.getMemberEntity().getId().equals(member.getId())) {
            wishEntity.getMemberEntity().removeWishEntity(wishEntity);
            wishEntity.getProductEntity().removeWishEntity(wishEntity);
            wishRepository.delete(wishEntity);
        }
    }

    private Wish entityToDomain(WishEntity wishEntity) {
        return new Wish(wishEntity.getId(), wishEntity.getMemberEntity().getId(),
            wishEntity.getProductEntity().getId());
    }

    private WishEntity DtoToEntity(WishRequest wishRequest) {
        MemberEntity memberEntity = memberRepository
            .findById(wishRequest.getMemberId())
            .orElseThrow(() -> new EntityNotFoundException("해당하는 멤버가 존재하지 않습니다."));

        ProductEntity productEntity = productRepository
            .findById(wishRequest.getProductId())
            .orElseThrow(() -> new EntityNotFoundException("해당하는 상품이 존재하지 않습니다."));

        return new WishEntity(memberEntity, productEntity);
    }
}
