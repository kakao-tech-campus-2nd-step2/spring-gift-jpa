package gift.service;

import gift.domain.Member;
import gift.domain.Wish;
import gift.dto.WishRequest;
import gift.entity.MemberEntity;
import gift.entity.ProductEntity;
import gift.entity.WishEntity;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public WishService(WishRepository wishRepository, MemberRepository memberRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;

    }

    public List<Wish> getWishesByMember(Member member) {
        MemberEntity memberEntity = memberRepository.findById(member.getId()).orElseThrow(()-> new EntityNotFoundException("멤버가 없습니다."));
        return wishRepository
            .findAllByMemberEntity(memberEntity)
            .stream()
            .map(this::entityToDomain)
            .toList();
    }

    public Wish addWish(WishRequest wishRequest) {
        WishEntity wishEntity = DtoToEntity(wishRequest);
        MemberEntity memberEntity = memberRepository.findById(wishRequest.getMemberId()).orElseThrow(() -> new EntityNotFoundException("[Wish 추가 실패] 해당하는 멤버가 존재하지 않습니다."));
        ProductEntity productEntity = productRepository.findById(wishRequest.getProductId()).orElseThrow(() -> new EntityNotFoundException("[Wish 추가 실패] 해당하는 상품이 존재하지 않습니다."));

        wishEntity.updateMemberEntity(memberEntity);
        wishEntity.updateProductEntity(productEntity);
        return entityToDomain(wishRepository.save(wishEntity));
    }

    public void deleteWish(Long id, Member member) {
        WishEntity wishEntity = wishRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("not found entity"));

        if (wishEntity.getMemberEntity().getId().equals( member.getId())) {
            wishEntity.getMemberEntity().removeWishEntity(wishEntity);
            wishEntity.getProductEntity().removeWishEntity(wishEntity);
            wishRepository.delete(wishEntity);
        }
    }

    private Wish entityToDomain(WishEntity wishEntity){
        return new Wish(wishEntity.getId(), wishEntity.getMemberEntity().getId(), wishEntity.getProductEntity().getId());
    }
    private WishEntity DtoToEntity(WishRequest wishRequest){
        MemberEntity memberEntity = memberRepository
            .findById(wishRequest.getMemberId())
            .orElseThrow(()->new EntityNotFoundException("not found entity"));

        ProductEntity productEntity = productRepository
            .findById(wishRequest.getProductId())
            .orElseThrow(()->new EntityNotFoundException("not found entity"));

        return new WishEntity(memberEntity, productEntity);
    }
}
