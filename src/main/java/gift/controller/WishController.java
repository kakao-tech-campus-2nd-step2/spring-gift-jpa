package gift.controller;

import gift.auth.LoginUser;
import gift.domain.User;
import gift.dto.requestDTO.WishRequestDTO;
import gift.dto.responseDTO.WishListResponseDTO;
import gift.dto.responseDTO.WishResponseDTO;
import gift.service.AuthService;
import gift.service.WishService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishes")
public class WishController {
    private final WishService wishService;
    private AuthService authService;

    public WishController(WishService wishService, AuthService authService) {
        this.wishService = wishService;
        this.authService = authService;
    }

    @GetMapping()
    public ResponseEntity<WishListResponseDTO> getAllWishes(@LoginUser User user){
        WishListResponseDTO wishListResponseDTO = wishService.getAllWishes(user.getId());
        return ResponseEntity.ok(wishListResponseDTO);
    }

    @PostMapping()
    public ResponseEntity<Long> addWishes(@LoginUser User user, @RequestBody WishRequestDTO wishRequestDTO){
        authService.authorizeUser(user, wishRequestDTO.userId());
        Long wishInsertedId = wishService.addWish(wishRequestDTO);
        return ResponseEntity.ok(wishInsertedId);
    }

    @DeleteMapping("/{wishId}")
    public ResponseEntity<Long> deleteWishes(@LoginUser User user, @PathVariable Long wishId){
        WishResponseDTO wishResponseDTO = wishService.getOneWish(wishId);
        authService.authorizeUser(user, wishResponseDTO.userId());

        Long wishDeletedId = wishService.deleteWish(wishId);
        return ResponseEntity.ok(wishDeletedId);
    }
}
