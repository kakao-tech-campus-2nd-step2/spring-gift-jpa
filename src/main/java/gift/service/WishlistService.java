package gift.service;

import gift.domain.Product;
import gift.domain.Member;
import gift.domain.Wishlist;
import gift.repository.WishlistRepository;
import java.util.List;
import java.util.Optional;
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
    public WishlistService(WishlistRepository wr, ProductService ps, MemberService us) {
        wishlistRepository = wr;
        productService = ps;
        memberService = us;
    }

    public List<Product> getWishlistByEmail(String email, Integer pageNumber, Integer pageSize) {
        // 1. 사용자 이메일을 기반으로 Wishlist 레파지토리에서 product를 가져온다
        // 2. 리스트에 들어있는 id들을 Product 레파지토리에서 검색하여 상품 목록 리턴
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Wishlist> wishes = wishlistRepository.findByMember_Email(email, pageable);
        return wishes.stream().map(wish -> wish.getProduct()).toList();
    }

    public void addWishlist(String email, Long productId) {
        Optional<Product> product = productService.getProductById(productId);
        product.orElseThrow(() -> new RuntimeException("Invalid Product ID"));
        // 사용자 이메일과 제품 ID를 사용하여 위시리스트에 추가

        Optional<Member> user = memberService.getMemberByEmail(email);
        user.orElseThrow(() -> new RuntimeException("Invalid Email"));
        Wishlist wish = new Wishlist(user.get(), product.get());
        wishlistRepository.save(wish);
    }

    public void removeWishlist(String email, Long productId) {
        Wishlist wish = wishlistRepository.findByMember_EmailAndProduct_Id(email, productId)
            .orElseThrow(() -> new RuntimeException("Wish Not Found"));
        wishlistRepository.delete(wish);
    }
}
