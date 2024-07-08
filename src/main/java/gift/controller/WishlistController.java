package gift.controller;

import gift.annotation.LoginUser;
import gift.domain.ProductWithQuantity;
import gift.domain.Wishlist;
import gift.service.WishlistService;
import java.util.List;
import org.apache.juli.logging.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService){
        this.wishlistService=wishlistService;
    }
    @GetMapping()
    public ResponseEntity<List<Wishlist>> getWishlist(@LoginUser String email){
        List<Wishlist> wishlist = wishlistService.getWishlist(email);
        return new ResponseEntity<>(wishlist,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addWishlist(@RequestBody ProductWithQuantity productWithQuantity, @LoginUser String email){
        wishlistService.addWishlist(productWithQuantity,email);
        return new ResponseEntity<>("위시리스트 상품 추가 완료", HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteWishlist(@PathVariable("productId") long productId, @LoginUser String email){
        wishlistService.deleteProductInWishlist(productId,email);
        return new ResponseEntity<>("위시리스트 상품 삭제 완료", HttpStatus.NO_CONTENT);
    }
}
