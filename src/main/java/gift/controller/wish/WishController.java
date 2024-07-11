package gift.controller.wish;

import gift.controller.member.MemberDto;
import gift.login.LoginMember;
import gift.service.WishService;
import java.util.List;
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
@RequestMapping("/wishes")
public class WishController {
    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<WishRequest>> getAllWishes(@LoginMember MemberDto member, @PathVariable String email) {
        if (!member.email().equals(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.OK)
            .body(wishService.findAll(email));
    }

    @PostMapping
    public ResponseEntity<WishRequest> addWish(@LoginMember MemberDto member, @RequestBody WishRequest wish) {
        return ResponseEntity.status(HttpStatus.CREATED).body(wishService.update(member.email(), wish));
    }

    @PutMapping("/{email}/{productId}")
    public ResponseEntity<WishRequest> putWish(@LoginMember MemberDto member, @PathVariable Long productId, @RequestBody WishRequest wish) {
        return ResponseEntity.status(HttpStatus.OK).body(wishService.update(member.email(), wish));
    }

    @DeleteMapping("/{email}/{productId}")
    public ResponseEntity<Void> deleteProduct(@LoginMember MemberDto member, @PathVariable Long productId) {
        System.out.println("called");
        wishService.delete(member.email(), productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}

