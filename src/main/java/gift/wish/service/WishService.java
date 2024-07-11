package gift.wish.service;

import gift.member.domain.Member;
import gift.member.exception.MemberNotFoundException;
import gift.member.repository.MemberRepository;
import gift.member.service.MemberService;
import gift.product.domain.Product;
import gift.product.repository.ProductRepository;
import gift.product.service.ProductService;
import gift.wish.domain.Wish;
import gift.wish.dto.WishServiceDto;
import gift.wish.exception.WishNotFoundException;
import gift.wish.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final MemberService memberService;
    private final ProductService productService;

    public WishService(WishRepository wishRepository, MemberService memberService, ProductService productService) {
        this.wishRepository = wishRepository;
        this.memberService = memberService;
        this.productService = productService;
    }

    public List<Wish> getAllWishesByMember(Member member) {
        return wishRepository.findAllByMemberId(member.getId());
    }

    public Wish getWishById(Long id) {
        return wishRepository.findById(id)
                .orElseThrow(WishNotFoundException::new);
    }

    public void createWish(WishServiceDto wishServiceDto) {
        wishRepository.save(getWishByWishServiceDto(wishServiceDto));
    }

    public void updateWish(WishServiceDto wishServiceDto) {
        validateWishExists(wishServiceDto.id());
        wishRepository.save(getWishByWishServiceDto(wishServiceDto));
    }

    public void deleteWish(Long id) {
        validateWishExists(id);
        wishRepository.deleteById(id);
    }

    private Wish getWishByWishServiceDto(WishServiceDto wishServiceDto) {
        Member member = memberService.getMemberById(wishServiceDto.memberId());
        Product product = productService.getProductById(wishServiceDto.productId());
        return wishServiceDto.toWish(member, product);
    }

    private void validateWishExists(Long id) {
        wishRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);
    }
}
