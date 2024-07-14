package gift.wishes;

import gift.jwt.Login;
import gift.member.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/wish")
public class WishController {
    private final WishService wishService;

    public WishController(WishService wishService){
        this.wishService = wishService;
    }

    @GetMapping()
    public ResponseEntity<?> getWishList(@Login UserDTO userDTO){
        return ResponseEntity.ok(wishService.findByMemberId(userDTO.getUserId()));
    }

    @PostMapping()
    public ResponseEntity<?> createWish(@RequestBody WishRequest wishRequest, @Login UserDTO userDTO){
        wishService.createWish(userDTO.getUserId(), wishRequest.getProductId(),
            wishRequest.getQuantity());
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<?> updateQuantity(@RequestBody WishRequest wishRequest, @Login UserDTO userDTO){
        wishService.updateQuantity(userDTO.getUserId(), wishRequest.getProductId(),
            wishRequest.getQuantity());
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteWish(@RequestBody Long id, @Login UserDTO userDTO){
        wishService.deleteWish(id, userDTO.getUserId());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<?> getWishListPage(@Login UserDTO userDTO, @RequestParam(value="page", defaultValue="0")int page){
        return ResponseEntity.ok(wishService.getWishPage(userDTO.getUserId(), page));
    }
}
