package gift.api.wishlist;

import gift.global.LoginMember;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
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

    private final EntityManager entityManager;
    private final WishRepository wishRepository;

    public WishController(EntityManager entityManager, WishRepository wishRepository) {
        this.entityManager = entityManager;
        this.wishRepository = wishRepository;
    }

    @GetMapping()
    public ResponseEntity<List<Wish>> getItems(@LoginMember Long memberId) {
        return ResponseEntity.ok().body(wishRepository.findByMemberId(memberId));
    }

    @PostMapping()
    public ResponseEntity<Void> add(@RequestBody WishRequest wishRequest, @LoginMember Long memberId) {
        wishRepository.save(new Wish(memberId, wishRequest.productId(), wishRequest.quantity()));
        return ResponseEntity.created(URI.create("/api/wishes/" + memberId)).build();
    }

    @Transactional
    @PutMapping()
    public ResponseEntity<Void> update(@RequestBody WishRequest wishRequest, @LoginMember Long memberId) {
        if (wishRequest.quantity() == 0) {
            wishRepository.deleteByMemberIdAndProductId(memberId, wishRequest.productId());
            return ResponseEntity.noContent().build();
        }
        Wish wish = entityManager.find(Wish.class, new WishId(memberId, wishRequest.productId()));
        wish.setQuantity(wishRequest.quantity());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> delete(@RequestBody WishRequest wishRequest, @LoginMember Long memberId) {
        wishRepository.deleteByMemberIdAndProductId(memberId, wishRequest.productId());
        return ResponseEntity.noContent().build();
    }
}
