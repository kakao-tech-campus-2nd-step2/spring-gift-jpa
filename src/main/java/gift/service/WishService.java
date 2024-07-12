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
        return wishRepository
            .findAllByMemberEntity(new MemberEntity(member.getId(),member.getEmail(), member.getPassword()))
            .stream()
            .map(this::entityToDomain)
            .toList();
    }

    public Wish addWish(WishRequest wishRequest) {
        return entityToDomain(wishRepository.save(DtoToEntity(wishRequest)));
    }

    public void deleteWish(Long id, Member member) {
        WishEntity wishEntity = wishRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("not found entity"));

        if (wishEntity.getMemberEntity().getId().equals( member.getId())) {
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
