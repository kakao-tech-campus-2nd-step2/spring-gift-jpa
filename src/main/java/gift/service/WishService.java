package gift.service;

import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import gift.repository.MemberRepository;
import gift.repository.WishRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final MemberService memberService;
    private final ProductService productService;

    @Autowired
    public WishService(WishRepository wishRepository, MemberService memberService, ProductService productService) {
        this.wishRepository = wishRepository;
        this.memberService = memberService;
        this.productService = productService;
    }

    public List<Wish> getWishlist(Long memberId) {
        return wishRepository.findByMemberId(memberId);
    }

    public Wish addWishlist(Long memberId, Long productId, int quantity) {
        Optional<Wish> wishlists = wishRepository.findByMemberIdAndProductId(memberId,
            productId);
        Member member = memberService.getMemberById(memberId);
        Product product = productService.findById(productId);
        Wish wish = new Wish(member, product, quantity);

        if (wishlists.isPresent()) {
            Wish existingWish = wishlists.get();
            existingWish.setQuantity(quantity);
            return wishRepository.save(existingWish);
        }
        return wishRepository.save(wish);
    }


    public void deleteById(Long memberId, Long productId) {
        Optional<Wish> wish = wishRepository.findByMemberIdAndProductId(memberId, productId);
        wishRepository.delete(wish.get());
    }
}