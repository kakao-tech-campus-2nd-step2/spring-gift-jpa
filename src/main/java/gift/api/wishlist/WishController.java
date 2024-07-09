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
public class WishController {

    private final WishDao wishDao;

    public WishController(WishDao wishDao) {
        this.wishDao = wishDao;
    }

    @GetMapping()
    public ResponseEntity<List<Wish>> getItems(@LoginMember Long id) {
        return ResponseEntity.ok().body(wishDao.getAllWishes(id));
    }

    @PostMapping()
    public ResponseEntity<Void> add(@RequestBody WishRequest wishRequest, @LoginMember Long id) {
        wishDao.insert(wishRequest, id);
        return ResponseEntity.created(URI.create("/api/wishes/" + id)).build();
    }

    @PutMapping()
    public ResponseEntity<Void> update(@RequestBody WishRequest wishRequest, @LoginMember Long id) {
        if (wishRequest.quantity() == 0) {
            wishDao.delete(wishRequest, id);
            return ResponseEntity.noContent().build();
        }
        wishDao.update(wishRequest, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> delete(@RequestBody WishRequest wishRequest, @LoginMember Long id) {
        wishDao.delete(wishRequest, id);
        return ResponseEntity.noContent().build();
    }
}
