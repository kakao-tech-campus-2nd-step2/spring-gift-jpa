package gift.controller;

import gift.dto.WishRequestDto;
import gift.dto.WishResponseDto;
import gift.service.JwtUtil;
import gift.service.WishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishes")
public class WishController {
    private final WishService wishService;
    private final JwtUtil jwtUtil;

    public WishController(WishService wishService, JwtUtil jwtUtil) {
        this.wishService = wishService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<Void> addWish(@RequestHeader("Authorization") String token, @RequestBody WishRequestDto wishRequest){
        String userEmail = jwtUtil.extractEmail(token.substring(7));
        wishService.save(userEmail, wishRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<WishResponseDto>> getWishList(@RequestHeader("Authorization") String token){
        String userEmail = jwtUtil.extractEmail(token.substring(7));
        return ResponseEntity.status(HttpStatus.OK).body(wishService.findByUserEmail(userEmail));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeWish(@RequestHeader("Authorization") String token, @PathVariable Long id){
        String userEmail = jwtUtil.extractEmail(token.substring(7));
        wishService.delete(userEmail,id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateWishQuantity(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody WishRequestDto wishRequest){
        String userEmail = jwtUtil.extractEmail(token.substring(7));
        wishService.updateQuantity(userEmail, id, wishRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
