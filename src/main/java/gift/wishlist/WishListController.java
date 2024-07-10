package gift.wishlist;

import gift.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
public class WishListController {

    private final WishListService wishListService;
    private final UserService userService;

    @Autowired
    public WishListController(WishListService wishListService, UserService userService) {
        this.wishListService = wishListService;
        this.userService = userService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<WishListDTO>> getWishList(@PathVariable("email") String email,
        HttpServletRequest request) {
        wishListService.extractEmailFromTokenAndValidate(request, email);
        List<WishListDTO> wishLists = wishListService.getWishListsByUserId(userService.findUserByEmail(email).id());
        return ResponseEntity.ok(wishLists);
    }

    @PostMapping("/{email}")
    public ResponseEntity<String> addWishList(@PathVariable("email") String email,
        HttpServletRequest request, @RequestBody WishListDTO wishListDTO) throws NotFoundException {
        wishListService.extractEmailFromTokenAndValidate(request, email);
        wishListService.addWishList(wishListDTO, email);
        return ResponseEntity.status(HttpStatus.CREATED).body("위시리스트에 추가되었습니다.");
    }

    @PutMapping("/{email}/{productId}")
    public ResponseEntity<String> updateWishList(@PathVariable("email") String email,
        @PathVariable("productId") long productId, HttpServletRequest request,
        @RequestBody WishListDTO wishListDTO) throws NotFoundException {
        wishListService.extractEmailFromTokenAndValidate(request, email);
        wishListService.updateWishList(userService.findUserByEmail(email).id(), productId, wishListDTO.getNum());
        return ResponseEntity.ok().body("업데이트 성공!");
    }

    @DeleteMapping("/{email}/{productId}")
    public ResponseEntity<String> deleteWishList(@PathVariable("email") String email,
        @PathVariable("productId") long productId, HttpServletRequest request)
        throws NotFoundException {
        wishListService.extractEmailFromTokenAndValidate(request, email);
        wishListService.deleteWishList(userService.findUserByEmail(email).id(), productId);
        return ResponseEntity.ok().body("삭제되었습니다.");
    }
}
