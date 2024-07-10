package gift.Controller;

import gift.Entity.WishEntity;
import gift.Service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishes")
public class WishController {

    @Autowired
    private WishService wishService;

    @GetMapping
    public List<WishEntity> getAllWishes() {
        return wishService.findAllWishes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WishEntity> getWishById(@PathVariable Long id) {
        return wishService.findWishById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public WishEntity createWish(@RequestBody WishEntity wishEntity) {
        return wishService.saveWish(wishEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWish(@PathVariable Long id) {
        wishService.deleteWish(id);
        return ResponseEntity.noContent().build();
    }
}
