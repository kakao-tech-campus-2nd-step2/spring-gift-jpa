package gift.wishes;

import gift.member.Member;
import gift.member.MemberService;
import gift.product.Product;
import gift.product.ProductService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final ProductService productService;
    private final MemberService memberService;

    public WishService(WishRepository wishRepository, ProductService productService,
        MemberService memberService) {
        this.wishRepository = wishRepository;
        this.productService = productService;
        this.memberService = memberService;
    }

    public List<Wish> findByMemberId(Long memberId) {
        return wishRepository.findByMemberId(memberId);
    }

    public Page<Wish> getWishPage(Long memberId, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Wish> allWishPage = wishRepository.findByMemberId(memberId, pageable);

        return allWishPage;
    }

    public void createWish(Long memberId, Long productId, Long quantity) {
        Member findMember = memberService.findById(memberId);
        Product findProduct = productService.findById(productId);

        wishRepository.save(new Wish(findMember, findProduct, quantity));
    }

    public void updateQuantity(Long id, Long memberId, Long quantity) {
        if (quantity == 0) {
            deleteWish(id, memberId);
            return;
        }
        Member member = memberService.findById(memberId);
        Wish wish = wishRepository.findByIdAndMember(id, member).orElseThrow();
        wish.updateQuantity(quantity);
    }

    public void deleteWish(Long id, Long memberId) {
        Member member = memberService.findById(memberId);
        wishRepository.deleteByIdAndMember(id, member);
    }
}
