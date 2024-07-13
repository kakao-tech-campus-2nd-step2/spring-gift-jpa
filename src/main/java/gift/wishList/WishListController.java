package gift.wishList;

import gift.annotation.LoginUser;
import gift.product.Product;
import gift.product.ProductRepository;
import gift.user.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishes")
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    //생성
    @PostMapping
    @Transactional
    public WishListDTO addWishes(@LoginUser User user, @RequestBody WishListDTO wishListDTO) {
        return wishListService.addWish(wishListDTO, user);
    }

    //조회(userid)
    @GetMapping
    @Transactional
    public List<WishListDTO> getWishesByUser(@LoginUser User user) {
        return wishListService.findByUser(user);
    }

    //수정(count) -> 0이면 삭제
    @PutMapping("/{id}")
    @Transactional
    public WishListDTO updateWishesCount(@PathVariable long id, @RequestBody CountDTO count) {
        if (count.count == 0) {
            wishListService.deleteByID(id);
            return null;
        }
        return wishListService.updateCount(count, id);
    }

    //삭제(id)
    @DeleteMapping("/{id}")
    @Transactional
    public WishListDTO deleteWishes(@PathVariable long id) {
        wishListService.deleteByID(id);
        return null;
    }

    @GetMapping("/pages")
    @Transactional
    public ResponseEntity<Page<WishListDTO>> getWishListsPage(@LoginUser User user,
                                                              @RequestParam(required = false, defaultValue = "0", value = "page") int page,
                                                              @RequestParam(required = false, defaultValue = "10", value = "size") @Min(1) @Max(20) int size,
                                                              @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy,
                                                              @RequestParam(required = false, defaultValue = "asc", value = "sortDirection") String sortDirection) {

        return ResponseEntity.ok(wishListService.getWishListsPages(page, size, user, sortBy, sortDirection));
    }

}
