package gift.service;

import gift.model.Member;
import gift.model.Product;
import gift.model.Wishlist;
import gift.repository.MemberRepository;
import gift.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductService productService;
    private final MemberService memberService;

    public WishlistService(WishlistRepository wishlistRepository, ProductService productService,
        MemberService memberService) {
        this.wishlistRepository = wishlistRepository;
        this.productService = productService;
        this.memberService = memberService;
    }

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