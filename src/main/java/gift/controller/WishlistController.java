package gift.controller;

import gift.annotation.LoginUser;
import gift.domain.Wishlist;
import gift.dto.WishlistRequestDto;
import gift.dto.WishlistResponseDto;
import gift.service.UserService;
import gift.service.WishlistService;
import java.util.List;
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
public class WishlistController {
    private final WishlistService wishlistService;
    private final UserService userService;

    public WishlistController(WishlistService wishlistService, UserService userService) {
        this.wishlistService = wishlistService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Wishlist>> getAllWishlists(@LoginUser String email) {
        Long userId = userService.getUserId(email);
        return new ResponseEntity<>(wishlistService.getWishlist(userId), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<WishlistResponseDto> addWishlist (@LoginUser String email, @RequestBody WishlistRequestDto requestDto) {
        Long userId = userService.getUserId(email);
        WishlistResponseDto wishlistResponseDto = new WishlistResponseDto(userId, requestDto.getProductId(), requestDto.getQuantity());
        if(wishlistService.addWishlist(new Wishlist(userId, requestDto.getProductId(), requestDto.getQuantity()))) {
            return new ResponseEntity<>(wishlistResponseDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(wishlistResponseDto, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Long> deleteWishlist(@PathVariable Long productId, @LoginUser String email) {
        Long userId = userService.getUserId(email);
        if(wishlistService.deleteWishlist(userId, productId)){
            return new ResponseEntity<>(userId, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
