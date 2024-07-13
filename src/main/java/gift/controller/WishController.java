package gift.controller;

import gift.annotation.LoginMember;
import gift.dto.WishResponseDto;
import gift.dto.WishRequestDto;
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

    private final WishService wishService;

    @Autowired
    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public ResponseEntity<List<WishResponseDto>> getAllByMemberId(@LoginMember Long memberId) {
        List<WishResponseDto> wishList = wishService.getWishlist(memberId);
        return new ResponseEntity<>(wishList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addWishlist(@LoginMember Long memberId,
        @RequestBody WishRequestDto wishlistRequestDto) {
        wishService.addWishlist(memberId, wishlistRequestDto.getProductId(), wishlistRequestDto.getQuantity());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteWishlist(@LoginMember Long memberId,
        @PathVariable Long productId) {
        wishService.deleteById(memberId, productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}