package gift.service;

import gift.DTO.ProductResponse;
import gift.domain.Product;
import gift.domain.Member;
import gift.domain.Wishlist;
import gift.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductService productService;
    private final MemberService memberService;

    @Autowired
    public WishlistService(WishlistRepository wr, ProductService ps, MemberService ms) {
        wishlistRepository = wr;
        productService = ps;
        memberService = ms;
    }

    public List<Product> getWishlistByEmail(String email, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Wishlist> wishes = wishlistRepository.findByMember_Email(email, pageable);
        return wishes.stream().map(wish -> wish.getProduct()).toList();
    }

    @Transactional
    public void addWishlist(String email, Long productId) {
        ProductResponse productResponse = productService.getProductById(productId);
        Member member = memberService.getMemberByEmail(email);
        Product product = new Product(
                        productResponse.getName(),
                        productResponse.getPrice(),
                        productResponse.getImageUrl()
                    );

        Wishlist wish = new Wishlist(member, product);
        wishlistRepository.save(wish);
    }

    @Transactional
    public void removeWishlist(String email, Long productId) {
        Wishlist wish = wishlistRepository.findByMember_EmailAndProduct_Id(email, productId)
            .orElseThrow(() -> new RuntimeException("Wish Not Found"));
        wishlistRepository.delete(wish);
    }
}
