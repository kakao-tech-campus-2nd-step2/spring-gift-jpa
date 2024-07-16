package gift.controller.wish;

import gift.controller.auth.AuthController;
import gift.controller.auth.LoginResponse;
import gift.login.LoginMember;
import gift.service.WishService;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/wishes")
public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public ResponseEntity<Page<WishResponse>> getAllWishes(@LoginMember LoginResponse loginMember,
        @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        AuthController.validateAdmin(loginMember);
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(wishService.findAll(pageable));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<Page<WishResponse>> getWishes(@LoginMember LoginResponse loginMember,
        @PathVariable UUID memberId, @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {
        AuthController.validateUserOrAdmin(loginMember, memberId);
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK)
            .body(wishService.findAllByMemberId(memberId, pageable));
    }

    @PostMapping("/{memberId}")
    public ResponseEntity<WishResponse> createWish(@LoginMember LoginResponse loginMember,
        @PathVariable UUID memberId, @RequestBody WishCreateRequest wish) {
        AuthController.validateUserOrAdmin(loginMember, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(wishService.save(memberId, wish));
    }

    @PutMapping("/{memberId}/{productId}")
    public ResponseEntity<WishResponse> updateWish(@LoginMember LoginResponse loginMember,
        @PathVariable UUID memberId, @PathVariable UUID productId,
        @RequestBody WishUpdateRequest wish) {
        AuthController.validateUserOrAdmin(loginMember, memberId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(wishService.update(memberId, productId, wish));
    }

    @DeleteMapping("/{memberId}/{productId}")
    public ResponseEntity<Void> deleteProduct(@LoginMember LoginResponse loginMember,
        @PathVariable UUID memberId, @PathVariable UUID productId) {
        AuthController.validateUserOrAdmin(loginMember, memberId);
        wishService.delete(memberId, productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}

