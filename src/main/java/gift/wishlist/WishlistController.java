package gift.wishlist;

import gift.exception.InvalidProduct;
import gift.exception.UnAuthorizationException;
import gift.login.LoginMember;
import gift.logout.TokenValidator;
import gift.member.Member;
import gift.product.ProductDao;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistDao wishlistDao;
    private final ProductDao productDao;
    private final TokenValidator tokenValidator;

    public WishlistController(WishlistDao wishlistDao, ProductDao productDao, TokenValidator tokenValidator) {
        this.wishlistDao = wishlistDao;
        this.productDao = productDao;
        this.tokenValidator = tokenValidator;
    }

    @PostMapping
    public void create(@RequestBody WishRequestDto request, @LoginMember Member member, @RequestHeader("Authorization") String authHeader)
        throws UnAuthorizationException {
        String token = authHeader.replace("Bearer ", "");
        tokenValidator.validateToken(token);
        wishlistDao.insertWish(member,request.productId());
    }

    @GetMapping("/admin") // 관리자만 장바구니 테이블을 전체 볼 수 있어야 함 // 수정해야겠다
    public List<Long> getWishlist() {
        List<Long> wishProducts = wishlistDao.findAllWish();
        return wishProducts;
    }

    @DeleteMapping("/{id}")
    public HttpEntity<String> deleteWish(@PathVariable(name="id") Long wishId, @RequestHeader("Authorization") String authHeader)
        throws UnAuthorizationException {
        String token = authHeader.replace("Bearer ", "");
        tokenValidator.validateToken(token);

        if (wishlistDao.findProductById(wishId).isEmpty()) {
            throw new InvalidProduct("잘못된 접근입니다");
        }
        wishlistDao.deleteWish(wishId);
        return ResponseEntity.ok("장바구니에서 제거되었습니다");
    }

}
