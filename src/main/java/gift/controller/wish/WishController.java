package gift.controller.wish;

import gift.auth.Authorization;
import gift.auth.Authenticate;
import gift.auth.LoginInfo;
import gift.controller.wish.dto.WishRequest.DeleteWishRequest;
import gift.controller.wish.dto.WishRequest.AddWishRequest;
import gift.controller.wish.dto.WishRequest.UpdateWishRequest;
import gift.controller.wish.dto.WishResponse.WishListResponse;
import gift.model.product.ProductDao;
import gift.model.user.Role;
import gift.model.wish.WishDao;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishes")
public class WishController {

    private final WishDao wishDao;
    private final ProductDao productDao;

    @Autowired
    public WishController(WishDao wishDao, ProductDao productDao) {
        this.wishDao = wishDao;
        this.productDao = productDao;
    }

    @Authorization(role = Role.USER)
    @PostMapping("")
    public ResponseEntity<String> addWish(
        @Authenticate LoginInfo loginInfo,
        @Valid @RequestBody AddWishRequest request
    ) {
        wishDao.findByProductIdAndUserId(request.productId(), loginInfo.userId())
            .ifPresent(wish -> {
                throw new IllegalArgumentException("Wish already exists.");
            });

        wishDao.insert(request.toEntity(loginInfo.userId()));
        return ResponseEntity.ok().body("Wish insert successfully.");
    }

    @Authorization(role = Role.USER)
    @DeleteMapping("")
    public ResponseEntity<String> deleteWish(
        @Authenticate LoginInfo loginInfo,
        @RequestBody @Valid DeleteWishRequest request
    ) {
        var wish = wishDao.findByProductIdAndUserId(request.productId(), loginInfo.userId())
            .orElseThrow(() -> new IllegalArgumentException("Wish not found."));
        wishDao.deleteById(wish.getId());
        return ResponseEntity.ok().body("Wish removed successfully.");
    }

    @Authorization(role = Role.USER)
    @GetMapping("")
    public ResponseEntity<List<WishListResponse>> getWishes(@Authenticate LoginInfo loginInfo) {
        var wishes = wishDao.findAll(loginInfo.userId());
        var response = wishes.stream()
            .map(wish -> WishListResponse.from(wish, productDao.findById(wish.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found."))))
            .toList();
        return ResponseEntity.ok().body(response);
    }

    @Authorization(role = Role.USER)
    @PatchMapping("")
    public ResponseEntity<String> updateWish(
        @Authenticate LoginInfo loginInfo,
        @Valid @RequestBody UpdateWishRequest request
    ) {
        var wish = wishDao.findByProductIdAndUserId(request.productId(), loginInfo.userId())
            .orElseThrow(() -> new IllegalArgumentException("Wish not found."));
        wishDao.updateCount(loginInfo.userId(), wish.getProductId(), request.count());
        return ResponseEntity.ok().body("Wish updated successfully.");
    }
}
