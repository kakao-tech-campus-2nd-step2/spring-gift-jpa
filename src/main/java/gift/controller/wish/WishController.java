package gift.controller.wish;

import gift.annotation.TokenEmail;
import gift.dto.wish.WishRequestDTO;
import gift.dto.wish.WishResponseDTO;
import gift.service.WishService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class WishController {
    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping("/api/wishes")
    public ResponseEntity<List<WishResponseDTO>> getWishes(@TokenEmail String email) {
        return ResponseEntity.ok(wishService.getWishes(email));
    }

    @PostMapping("/api/wishes")
    public ResponseEntity<WishResponseDTO> addWish(@TokenEmail String email, @RequestBody WishRequestDTO wishRequestDTO) {
        return makeCreatedResponse(wishService.addWish(email, wishRequestDTO));
    }

    @DeleteMapping("/api/wishes/{wishId}")
    public ResponseEntity<Void> deleteWish(@TokenEmail String email, @PathVariable long wishId) {
        wishService.deleteWish(email, wishId);

        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<WishResponseDTO> makeCreatedResponse(WishResponseDTO wish) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/api/wishes/"+ wish.id())
                .build()
                .toUri();

        return ResponseEntity.created(location).body(wish);
    }
}
