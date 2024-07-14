package gift.domain.wishlist.service;

import gift.domain.member.entity.Member;
import gift.domain.member.repository.MemberRepository;
import gift.domain.product.entity.Product;
import gift.domain.product.repository.ProductRepository;
import gift.domain.wishlist.dto.WishRequest;
import gift.domain.wishlist.dto.WishResponse;
import gift.domain.wishlist.entity.Wish;
import gift.domain.wishlist.repository.WishRepository;
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

    public List<WishResponse> getWishesByMember(Member member, int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return wishRepository
            .findAllByMember(member, pageable)
            .stream()
            .map(this::entityToDto)
            .toList();
    }

    public WishResponse addWish(WishRequest wishRequest) {
        Wish wish = DtoToEntity(wishRequest);

        return entityToDto(wishRepository.save(wish));
    }

    public void deleteWish(Long id, Member member) {
        Wish wish = wishRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("not found entity"));

        if (wish.getMember().getId().equals(member.getId())) {
            wish.getMember().removeWish(wish);
            wish.getProduct().removeWish(wish);
            wishRepository.delete(wish);
        }
    }

    private WishResponse entityToDto(Wish wish) {
        return new WishResponse(wish.getId(), wish.getMember().getId(),
            wish.getProduct().getId());
    }

    private Wish DtoToEntity(WishRequest wishRequest) {
        Member member = memberRepository
            .findById(wishRequest.getMemberId())
            .orElseThrow(() -> new EntityNotFoundException("해당하는 멤버가 존재하지 않습니다."));

        Product product = productRepository
            .findById(wishRequest.getProductId())
            .orElseThrow(() -> new EntityNotFoundException("해당하는 상품이 존재하지 않습니다."));

        return new Wish(member, product);
    }
}
