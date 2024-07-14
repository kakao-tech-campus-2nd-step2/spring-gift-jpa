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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishes")
public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping()
    public ResponseEntity<List<Wish>> getItems(@LoginMember Long memberId, @RequestParam int page,
        @RequestParam int size, @RequestParam String criterion, @RequestParam String direction) {

        return ResponseEntity.ok()
            .body(wishService.getItems(memberId, page, size, criterion, direction));
    }

    @PostMapping()
    public ResponseEntity<Void> add(@RequestBody WishRequest wishRequest, @LoginMember Long memberId) {
        wishService.add(memberId, wishRequest);
        return ResponseEntity.created(URI.create("/api/wishes/" + memberId)).build();
    }

    @PutMapping()
    public ResponseEntity<Void> update(@RequestBody WishRequest wishRequest, @LoginMember Long memberId) {
        wishService.update(memberId, wishRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> delete(@RequestBody WishRequest wishRequest, @LoginMember Long memberId) {
        wishService.delete(memberId, wishRequest);
        return ResponseEntity.noContent().build();
    }
}
