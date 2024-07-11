package gift.controller;

import gift.annotation.LoginMember;
import gift.entity.Wish;
import gift.dto.WishlistRequestDto;
import gift.service.WishService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishlist")
public class WishController {

    private WishService wishService;

    @Autowired
    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public ResponseEntity<List<Wish>> getAllByMemberId(@LoginMember Long memberId) {
        return new ResponseEntity<>(wishService.getWishlist(memberId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addWishlist(@LoginMember Long memberId, @RequestBody WishlistRequestDto wishlistRequestDto) {
        System.out.println(memberId);
        wishService.addWishlist(new Wish(memberId, wishlistRequestDto.getProductId(), wishlistRequestDto.getQuantity()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteWishlist(@LoginMember Long memberId, @PathVariable Long productId) {
        wishService.deleteById(memberId, productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}