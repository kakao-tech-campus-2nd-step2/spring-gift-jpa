package gift.Controller;

import gift.DTO.WishDTO;
import gift.Entity.WishEntity;
import gift.Service.WishService;
import gift.Service.UserService;
import gift.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishes")
public class WishController {

    @Autowired
    private WishService wishService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public Page<WishDTO> getAllWishes(Pageable pageable) {
        return wishService.getWishes(pageable);
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
