package gift.service;

import gift.model.Member;
import gift.model.Product;
import gift.model.Wish;
import gift.repository.WishRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;

@Service
public class WishService {
    private final MemberService memberService;
    private final ProductService productService;
    private final WishRepository wishRepository;

    public WishService(MemberService memberService, ProductService productService, WishRepository wishRepository) {
        this.memberService = memberService;
        this.productService = productService;
        this.wishRepository = wishRepository;
    }

    public List<Wish> getWishlistController(HttpServletRequest request) throws AuthenticationException {
        long memberId = memberService.getIdByToken(request);
        return wishRepository.findByMember_Id(memberId);
    }

    public void postWishlist(Long productId, HttpServletRequest request) throws AuthenticationException {
        Member member = memberService.getMemberByAuth(request);
        Product product = productService.getProductById(productId);
        Wish wish = new Wish(member, product);
        wishRepository.save(wish);
    }

    public void deleteProduct(Long id){
        wishRepository.deleteById(id);
    }
}
