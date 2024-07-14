package gift.wishlist;

import gift.user.UserService;
import gift.util.PageUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
public class WishListApiController {

    private final WishListService wishListService;
    private final UserService userService;

    public WishListApiController(WishListService wishListService, UserService userService) {
        this.wishListService = wishListService;
        this.userService = userService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<Page<WishListDTO>> getWishList(@PathVariable("email") String email,
        HttpServletRequest request,
        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
        @RequestParam(value = "size", required = false, defaultValue = "10") int size,
        @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
        @RequestParam(value = "sortDirection", required = false, defaultValue = "asc") String sortDirection) {
        wishListService.extractEmailFromTokenAndValidate(request, email);
        size = PageUtil.validateSize(size);
        sortBy = PageUtil.validateSortBy(sortBy, Arrays.asList("id", "productId", "num"));
        Direction direction = PageUtil.validateDirection(sortDirection);
        Page<WishListDTO> wishLists = wishListService.getWishListsByUserId(
            userService.findUserByEmail(email).id(), page, size, direction, sortBy);
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
        wishListService.updateWishList(userService.findUserByEmail(email).id(), productId,
            wishListDTO.getNum());
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
