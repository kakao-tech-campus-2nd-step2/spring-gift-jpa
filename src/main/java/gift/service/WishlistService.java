package gift.service;

import gift.model.Member;
import gift.model.Product;
import gift.model.Wishlist;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final MemberRepository memberRepository;
    private final ProductService productService;

    public WishlistService(WishlistRepository wishlistRepository, ProductService productService, MemberRepository memberRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productService = productService;
        this.memberRepository = memberRepository;
    }

    public List<Product> getWishlist(String email) {
        Member member = memberRepository.findByEmail(email);
        List<Wishlist> wishlistItems = wishlistRepository.findByMember(member);
        return wishlistItems.stream()
            .map(Wishlist::getProduct)
            .toList();
    }

    @Transactional
    public void addWishlist(String email, Long productId) {
        Member member = memberRepository.findByEmail(email);
        Product product = productService.findProductsById(productId);
        Wishlist wishlist = new Wishlist(null, member, product);
        wishlistRepository.save(wishlist);
    }

    @Transactional
    public void removeWishlist(String email, Long productId) {
        Member member = memberRepository.findByEmail(email);
        Product product = productService.findProductsById(productId);
        wishlistRepository.deleteByMemberAndProduct(member, product);
    }
}