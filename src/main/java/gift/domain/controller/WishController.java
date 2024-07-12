package gift.domain.controller;

import gift.domain.dto.WishAddResponseDto;
import gift.domain.dto.WishListResponseDto;
import gift.domain.dto.WishUpdateResponseDto;
import gift.domain.entity.User;
import gift.domain.annotation.ValidUser;
import gift.domain.dto.WishDeleteRequestDto;
import gift.domain.dto.WishRequestDto;
import gift.domain.service.WishService;
import gift.global.response.BasicResponse;
import gift.global.response.SuccessResponse;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public ResponseEntity<WishListResponseDto> getWishlist(@ValidUser User user) {
        return SuccessResponse.ok(new WishListResponseDto(HttpStatus.OK, wishService.getWishlist(user)));
    }

    @PostMapping
    public ResponseEntity<WishAddResponseDto> addWish(@ValidUser User user, @Valid @RequestBody WishRequestDto wishRequestDto) {
        return SuccessResponse.ok(new WishAddResponseDto(HttpStatus.OK, wishService.addWishlist(user, wishRequestDto)));
    }

    @PutMapping
    public ResponseEntity<WishUpdateResponseDto> updateWish(@ValidUser User user, @Valid @RequestBody WishRequestDto wishRequestDto) {
        return SuccessResponse.ok(new WishUpdateResponseDto(HttpStatus.OK, wishService.updateWishlist(user, wishRequestDto)));
    }

    @DeleteMapping
    public ResponseEntity<BasicResponse> deleteWish(@ValidUser User user, @RequestBody WishDeleteRequestDto wishDeleteRequestDto) {
        wishService.deleteWishlist(user, wishDeleteRequestDto);
        return SuccessResponse.of(HttpStatus.NO_CONTENT);
    }
}
