package gift.controller.wish;

import gift.controller.auth.AuthController;
import gift.controller.auth.LoginResponse;
import gift.login.LoginMember;
import gift.service.WishService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/wishes")
public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public ResponseEntity<List<WishResponse>> getAllWishes(@LoginMember LoginResponse loginMember) {
        AuthController.validateAdmin(loginMember);
        return ResponseEntity.status(HttpStatus.OK).body(wishService.findAll());
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<List<WishResponse>> getWishes(@LoginMember LoginResponse loginMember,
        @PathVariable UUID memberId) {
        AuthController.validateUserOrAdmin(loginMember, memberId);
        return ResponseEntity.status(HttpStatus.OK).body(wishService.findAllByMemberId(memberId));
    }

    @PostMapping("/{memberId}")
    public ResponseEntity<WishResponse> createWish(@LoginMember LoginResponse loginMember,
        @PathVariable UUID memberId, @RequestBody WishCreateRequest wish) {
        AuthController.validateUserOrAdmin(loginMember, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(wishService.save(memberId, wish));
    }

    @PutMapping("/{memberId}/{productId}")
    public ResponseEntity<WishResponse> updateWish(@LoginMember LoginResponse loginMember,
        @PathVariable UUID memberId, @PathVariable UUID productId, @RequestBody WishUpdateRequest wish) {
        AuthController.validateUserOrAdmin(loginMember, memberId);
        return ResponseEntity.status(HttpStatus.OK).body(wishService.update(memberId, productId, wish));
    }

    @DeleteMapping("/{memberId}/{productId}")
    public ResponseEntity<Void> deleteProduct(@LoginMember LoginResponse loginMember,
        @PathVariable UUID memberId, @PathVariable UUID productId) {
        AuthController.validateUserOrAdmin(loginMember, memberId);
        wishService.delete(memberId, productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}

