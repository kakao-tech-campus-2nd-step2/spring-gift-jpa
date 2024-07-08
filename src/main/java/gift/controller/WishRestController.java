package gift.controller;

import gift.annotation.LoginUser;
import gift.dto.UserResponseDto;
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
public class WishRestController {
    private final WishService wishService;

    public WishRestController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping
    public ResponseEntity<Void> addWish(@LoginUser UserResponseDto userResponseDto, @RequestBody WishRequestDto wishRequest){
        wishService.save(userResponseDto, wishRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<WishResponseDto>> getWishList(@LoginUser UserResponseDto userResponseDto){
        return ResponseEntity.status(HttpStatus.OK).body(wishService.findByUserEmail(userResponseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeWish(@LoginUser UserResponseDto userResponseDto, @PathVariable Long id){
        wishService.delete(userResponseDto, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateWishQuantity(@LoginUser UserResponseDto userResponseDto, @PathVariable Long id, @RequestBody WishRequestDto wishRequest){
        wishService.updateQuantity(userResponseDto, id, wishRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
