package gift.api.wishlist;

import gift.global.LoginMember;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishes")
public class WishListController {

    private final WishListDao wishListDao;

    public WishListController(WishListDao wishListDao) {
        this.wishListDao = wishListDao;
    }

    @GetMapping()
    public ResponseEntity<List<WishList>> getItems(@LoginMember Long id) {
        return ResponseEntity.ok().body(wishListDao.getAllWishes(id));
    }

    @PostMapping()
    public ResponseEntity<Void> add(@RequestBody WishListRequest wishListRequest, @LoginMember Long id) {
        wishListDao.insert(wishListRequest, id);
        return ResponseEntity.created(URI.create("/api/wishes/" + id)).build();
    }

    @PutMapping()
    public ResponseEntity<Void> update(@RequestBody WishListRequest wishListRequest, @LoginMember Long id) {
        if (wishListRequest.quantity() == 0) {
            wishListDao.delete(wishListRequest, id);
            return ResponseEntity.noContent().build();
        }
        wishListDao.update(wishListRequest, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> delete(@RequestBody WishListRequest wishListRequest, @LoginMember Long id) {
        wishListDao.delete(wishListRequest, id);
        return ResponseEntity.noContent().build();
    }
}
