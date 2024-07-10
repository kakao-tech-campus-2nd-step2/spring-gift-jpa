package gift.wishList;

import gift.annotation.LoginUser;
import gift.product.Product;
import gift.product.ProductRepository;
import gift.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/wishes")
public class WishListController {

    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;

    public WishListController(WishListRepository wishListRepository, ProductRepository productRepository) {
        this.wishListRepository = wishListRepository;
        this.productRepository = productRepository;
    }

    //생성
    @PostMapping
    public ResponseEntity<?> addWishes(@LoginUser User user, @RequestBody WishListDTO wishListDTO) {
        Product product = productRepository.findById(wishListDTO.getProductID()).orElseThrow();
        WishList wishList = wishListRepository.save(new WishList(user, product, wishListDTO.getCount()));
        return ResponseEntity.ok(wishList);
    }

    //조회(userid)
    @GetMapping
    public ResponseEntity<?> getWishesByUserID(@LoginUser User user) {
        List<WishList> wishLists = wishListRepository.findByUser(user);
        return ResponseEntity.ok(wishLists);
    }

    //수정(count) -> 0이면 삭제
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateWishesCount(@PathVariable long id, @RequestBody CountDTO count) {
        if (count.count == 0) {
            wishListRepository.deleteById(id);
            return ResponseEntity.ok(null);
        }
        Optional<WishList> wishList = wishListRepository.findById(id);
        wishList.ifPresent(wish -> wish.setCount(count.getCount()));
        return ResponseEntity.ok(wishList);
    }

    //삭제(id)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWishes(@PathVariable long id) {
        wishListRepository.deleteById(id);
        return ResponseEntity.ok(null);
    }

}
