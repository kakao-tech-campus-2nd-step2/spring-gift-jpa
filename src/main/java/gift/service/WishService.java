package gift.service;

import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import gift.dto.wish.WishRequestDTO;
import gift.dto.wish.WishResponseDTO;
import gift.exception.ForbiddenRequestException;
import gift.exception.NoSuchFieldException;
import gift.exception.NoSuchProductException;
import gift.exception.NoSuchWishException;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import gift.util.pagenation.PageInfoDTO;
import gift.util.pagenation.PageableGenerator;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<WishResponseDTO> getWishes(String email, PageInfoDTO pageInfoDTO) {
        Pageable pageable = PageableGenerator.generatePageable(pageInfoDTO);

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(NoSuchFieldException::new);

        return wishRepository.findAllByMember(member, pageable)
                .stream()
                .map(WishResponseDTO::from)
                .toList();
    }

    public WishResponseDTO addWish(String email, WishRequestDTO wishRequestDTO) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(NoSuchFieldException::new);

        Product product = productRepository.findById(wishRequestDTO.productId())
                .orElseThrow(NoSuchProductException::new);

        Wish wish = wishRepository.save(new Wish(
                member,
                product
        ));

        member.addWish(wish);
        product.addWish(wish);

        return WishResponseDTO.from(wish);
    }

    public void deleteWish(String email, long wishId) {
        if (!isOwner(email, wishId)) {
            throw new ForbiddenRequestException("user is not owner of wish");
        }

        Wish wish = wishRepository.findById(wishId)
                        .orElseThrow(NoSuchWishException::new);

        wish.getOwner().removeWish(wish);
        wish.getProduct().removeWish(wish);
        wishRepository.delete(wish);

    }

    private boolean isOwner(String email, long wishId) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(NoSuchFieldException::new);

        Member wishOwner = wishRepository.findById(wishId)
                .orElseThrow(NoSuchProductException::new)
                .getOwner();

        return wishOwner.equals(member);
    }
}
