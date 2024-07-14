package gift.service;

import gift.model.Member;
import gift.model.Product;
import gift.model.Wishlist;
import gift.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final MemberService memberService;
    private final ProductService productService;

    public WishlistService(WishlistRepository wishlistRepository, MemberService memberService,
        ProductService productService) {
        this.wishlistRepository = wishlistRepository;
        this.memberService = memberService;
        this.productService = productService;
    }

    @Transactional(readOnly = true)
    public List<Product> getWishlist(String email) {
        Member member = memberService.findMemberByEmail(email);
        List<Wishlist> wishlistItems = wishlistRepository.findByMember(member);
        return wishlistItems.stream()
            .map(Wishlist::getProduct)
            .toList();
    }

    @Transactional
    public void addWishlist(String email, Long productId) {
        Member member = memberService.findMemberByEmail(email);
        Product product = productService.findProductsById(productId);
        Wishlist wishlist = new Wishlist(null, member, product);
        wishlistRepository.save(wishlist);
    }

    @Transactional
    public void removeWishlist(String email, Long productId) {
        Member member = memberService.findMemberByEmail(email);
        Product product = productService.findProductsById(productId);
        wishlistRepository.deleteByMemberAndProduct(member, product);
    }
}