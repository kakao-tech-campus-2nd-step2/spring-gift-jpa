package gift.wishList;

import gift.annotation.LoginUser;
import gift.product.Product;
import gift.product.ProductRepository;
import gift.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @Transactional
    public ResponseEntity<?> addWishes(@LoginUser User user, @RequestBody WishListDTO wishListDTO) {
        Product product = productRepository.findById(wishListDTO.getProductID()).orElseThrow();
        WishList wishList = new WishList(wishListDTO.getCount());
        user.addWishList(wishList);
        product.addWishList(wishList);
        wishListRepository.save(wishList);
        return ResponseEntity.ok(new WishListDTO(wishList));
    }

    //조회(userid)
    @GetMapping
    @Transactional
    public ResponseEntity<?> getWishesByUserID(@LoginUser User user) {
        List<WishList> wishLists = wishListRepository.findByUser(user);
        List<WishListDTO> wishListDTOS = new ArrayList<>();
        for (WishList wishList : wishLists) {
            wishListDTOS.add(new WishListDTO(wishList));
        }
        return ResponseEntity.ok(wishListDTOS);
    }

    //수정(count) -> 0이면 삭제
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateWishesCount(@PathVariable long id, @RequestBody CountDTO count) {
        if (count.count == 0) {
            WishList wishList = wishListRepository.findById(id).orElseThrow();
            wishList.getUser().removeWishList(wishList);
            wishList.getProduct().removeWishList(wishList);
            wishListRepository.deleteById(id);

            return ResponseEntity.ok(null);
        }
        WishList wishList = wishListRepository.findById(id).orElseThrow();
        wishList.setCount(count.getCount());

        return ResponseEntity.ok(new WishListDTO(wishList));
    }

    //삭제(id)
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteWishes(@PathVariable long id) {
        WishList wishList = wishListRepository.findById(id).orElseThrow();
        wishList.getUser().removeWishList(wishList);
        wishList.getProduct().removeWishList(wishList);
        wishListRepository.deleteById(id);

        return ResponseEntity.ok(null);
    }

}
