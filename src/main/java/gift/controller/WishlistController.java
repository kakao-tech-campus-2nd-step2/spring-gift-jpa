package gift.controller;

import gift.annotation.LoginUser;
import gift.entity.Member;
import gift.entity.Wish;
import gift.repository.MemberRepository;
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
    MemberRepository memberRepository;

    public WishlistController(WishlistService wishlistService,MemberRepository memberRepository){
        this.wishlistService=wishlistService;
        this.memberRepository= memberRepository;
    }
    @GetMapping()
    public ResponseEntity<List<Wish>> getWishlist(@LoginUser String email){
        Member member =memberRepository.findByEmail(email);
        List<Wish> wishlist = wishlistService.getWishlist(member.getId());
        return new ResponseEntity<>(wishlist,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addWishlist(@RequestBody Wish wish, @LoginUser String email){
        Member member = memberRepository.findByEmail(email);
        wishlistService.addWishlist(wish);
        return new ResponseEntity<>("위시리스트 상품 추가 완료", HttpStatus.OK);
    }

    @DeleteMapping("/{wishId}")
    public ResponseEntity<String> deleteWishlist(@PathVariable("wishId") long wishId){
        wishlistService.deleteWishlist(wishId);
        return new ResponseEntity<>("위시리스트 상품 삭제 완료", HttpStatus.NO_CONTENT);
    }
}
