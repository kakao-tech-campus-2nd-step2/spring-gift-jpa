package gift.controller;

import gift.dto.WishPatchDto;
import gift.dto.WishRequestDto;
import gift.dto.WishResponseDto;
import gift.service.JwtProvider;
import gift.service.WishService;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishes")
public class WishController {
    private final JwtProvider jwtProvider;
    private final WishService wishService;
    public WishController(JwtProvider jwtProvider, WishService wishService) {
        this.jwtProvider = jwtProvider;
        this.wishService = wishService;
    }

    @PostMapping
    public ResponseEntity<Void> addWish(@RequestHeader("Authorization") String fullToken, @RequestBody WishRequestDto wishRequestDto){
        String userEmail = jwtProvider.getMemberEmail(fullToken.substring(7));
        wishService.save(userEmail,wishRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<WishResponseDto>> wishList(@RequestHeader("Authorization") String fullToken){
        String userEmail = jwtProvider.getMemberEmail(fullToken.substring(7));
        return ResponseEntity.status(HttpStatus.OK).body(wishService.findByEmail(userEmail));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteWish(@RequestHeader("Authorization") String fullToken, @PathVariable Long productId){
        String userEmail = jwtProvider.getMemberEmail(fullToken.substring(7));
        wishService.deleteWish(userEmail,productId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<Void> updateWish(@RequestHeader("Authorization") String fullToken, @PathVariable Long productId,@RequestBody
        WishPatchDto wishPatchDto){
        String userEmail = jwtProvider.getMemberEmail(fullToken.substring(7));
        wishService.updateWish(userEmail,productId,wishPatchDto.getQuantity());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/page")
    public ResponseEntity<List<WishResponseDto>> findWishesByMemberId(
        @RequestHeader("Authorization") String fullToken,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
        String userEmail = jwtProvider.getMemberEmail(fullToken.substring(7));
        List<WishResponseDto> wishes = wishService.findByEmailPage(userEmail,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(wishes);
    }

}
