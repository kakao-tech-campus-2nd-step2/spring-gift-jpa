package gift.wishList;

import gift.annotation.LoginUser;
import gift.product.Product;
import gift.product.ProductRepository;
import gift.user.User;
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

}
