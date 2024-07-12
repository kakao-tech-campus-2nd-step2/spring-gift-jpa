package gift.controller;

import gift.annotation.LoginUser;
import gift.dto.WishDTO;
import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import gift.service.MemberService;
import gift.service.ProductService;
import gift.service.WishlistService;
import org.springframework.data.domain.Page;
import java.util.List;

import org.apache.juli.logging.Log;
g
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
    MemberService memberService;
    ProductService productService;

    public WishlistController(WishlistService wishlistService, MemberService memberService, ProductService productService) {
        this.wishlistService = wishlistService;
        this.memberService = memberService;
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<List<Wish>> getWishlist(@LoginUser String email) {
        List<Wish> wishlist = wishlistService.getWishlistByEmail(email);
        return new ResponseEntity<>(wishlist, HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<Wish>> getWishlistPage(@PathVariable("page") int page, @LoginUser String email) {
        Page<Wish> wishes = wishlistService.getWishPage(email,page);
        return new ResponseEntity<>(wishes,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addWishlist(@RequestBody WishDTO wishDTO, @LoginUser String email) {
        Member member = memberService.findById(wishDTO.getMemberId());
        Product product = productService.findById(wishDTO.getProductId());
        Wish wish = wishDTO.toEntity(member, product);
        wishlistService.addWishlist(wish, email);
        return new ResponseEntity<>("위시리스트 상품 추가 완료", HttpStatus.OK);
    }

    @DeleteMapping("/{wishId}")
    public ResponseEntity<String> deleteWishlist(@PathVariable("wishId") long wishId,
                                                 @LoginUser String email) {
        wishlistService.deleteWishlist(wishId, email);
        return new ResponseEntity<>("위시리스트 상품 삭제 완료", HttpStatus.NO_CONTENT);
    }
}
