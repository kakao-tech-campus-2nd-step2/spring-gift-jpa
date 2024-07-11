package gift.controller;

import gift.service.WishlistService;
import gift.model.Wishlist;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    //멤버 id로 해당 멤버의 위시리스트 가져옴
    @GetMapping("/getAllWishlist")
    public List<Wishlist> getWishlistController(HttpServletRequest request) throws AuthenticationException {
        return wishlistService.getWishlistController(request);
    }

    //위시리스트 상품 추가
    @PostMapping("/addWishlist/{productid}")
    public void postWishlist(@PathVariable Long productid, HttpServletRequest request) throws AuthenticationException {
        wishlistService.postWishlist(productid, request);
    }

    //위시리스크 상품 wishlist id 받아와 삭제
    @DeleteMapping("/deleteWishlist/{id}")
    public void deleteProductController(@PathVariable Long id){
        wishlistService.deleteProduct(id);
    }
}