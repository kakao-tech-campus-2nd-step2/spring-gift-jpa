package gift.wishList;

import gift.annotation.LoginUser;
import gift.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishes")
public class WishListController {

    private final WishListRepository wishListRepository;

    public WishListController(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    //생성
    @PostMapping
    public ResponseEntity<?> addWishes(@LoginUser User user, @RequestBody WishListDTO wishListDTO){
        wishListRepository.insertWishList(user.getId(), wishListDTO);
        return ResponseEntity.ok(null);
    }

    //조회(userid)
    @GetMapping
    public ResponseEntity<?> getWishesByUserID(@LoginUser User user){
        System.out.println(user.getId());
        List<WishList> wishLists = wishListRepository.findWishListsByUserID(user.getId());
        return ResponseEntity.ok(wishLists);
    }

    //수정(count) -> 0이면 삭제
    @PutMapping("/{id}")
    public ResponseEntity<?> updateWishesCount(@PathVariable long id, @RequestBody CountDTO count){
        if(count.count == 0){
            wishListRepository.deleteWishList(id);
            return ResponseEntity.ok(null);
        }
        wishListRepository.updateWishList(id, count.count);
        return ResponseEntity.ok(null);
    }

    //삭제(id)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWishes(@PathVariable long id){
        wishListRepository.deleteWishList(id);
        return ResponseEntity.ok(null);
    }

}
