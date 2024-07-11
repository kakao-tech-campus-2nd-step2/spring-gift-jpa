package gift;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping
    public ResponseEntity<List<Wish>> getWishes(@LoginMember Member member) {
        if (member == null) {
            return ResponseEntity.status(403).build();
        }
        List<Wish> wishes = wishService.getWishesByMemberId(member.getId());
        return ResponseEntity.ok(wishes);
    }

    @PostMapping
    public ResponseEntity<String> addWish(@RequestBody @Valid WishRequest request, @LoginMember Member member) {
        if (member == null) {
            return ResponseEntity.status(403).build();
        }
        wishService.addWish(member.getId(), request.getProductId());
        return ResponseEntity.ok("Wish added successfully");
    }

    @DeleteMapping
    public ResponseEntity<String> removeWish(@RequestBody @Valid WishRequest request, @LoginMember Member member) {
        if (member == null) {
            return ResponseEntity.status(403).build();
        }
        wishService.removeWish(member.getId(), request.getProductId());
        return ResponseEntity.ok("Wish removed successfully");
    }
}
