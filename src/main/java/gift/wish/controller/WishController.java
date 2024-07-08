package gift.wish.controller;

import gift.auth.domain.AuthInfo;
import gift.auth.service.AuthService;
import gift.global.response.ResultCode;
import gift.global.response.ResultResponseDto;
import gift.global.response.SimpleResultResponseDto;
import gift.global.security.Login;
import gift.global.utils.ResponseHelper;
import gift.wish.domain.Wish;
import gift.wish.dto.WishRequestDto;
import gift.wish.dto.WishUpdateRequestDto;
import gift.wish.service.WishService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishes")
public class WishController {
    private final WishService wishService;
    private final AuthService authService;

    public WishController(WishService wishService, AuthService authService) {
        this.wishService = wishService;
        this.authService = authService;
    }

    @GetMapping("")
    public ResponseEntity<ResultResponseDto<List<Wish>>> getAllWishes(@Login AuthInfo authInfo) {
        List<Wish> wishes = wishService.getAllWishesByMember(authService.getMemberById(authInfo.memberId()));
        return ResponseHelper.createResponse(ResultCode.GET_ALL_WISHES_SUCCESS, wishes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultResponseDto<Wish>> getWishById(@PathVariable(name = "id") Long id, @Login AuthInfo authInfo) {
        Wish wish = wishService.getWishById(id);
        return ResponseHelper.createResponse(ResultCode.GET_ALL_WISHES_SUCCESS, wish);
    }

    @PostMapping("")
    public ResponseEntity<SimpleResultResponseDto> createWish(@Login AuthInfo authInfo, @RequestBody WishRequestDto wishRequestDto) {
        wishService.createWish(wishRequestDto.toWishServiceDto(authInfo.memberId()));
        return ResponseHelper.createSimpleResponse(ResultCode.CREATE_WISH_SUCCESS);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SimpleResultResponseDto> updateWish(@PathVariable(name = "id") Long id, @Login AuthInfo authInfo,
                                                              @RequestBody WishRequestDto wishRequestDto) {
        wishService.updateWish(wishRequestDto.toWishServiceDto(id, authInfo.memberId()));
        return ResponseHelper.createSimpleResponse(ResultCode.UPDATE_WISH_SUCCESS);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SimpleResultResponseDto> deleteMember(@PathVariable(name = "id") Long id) {
        wishService.deleteWish(id);
        return ResponseHelper.createSimpleResponse(ResultCode.DELETE_WISH_SUCCESS);
    }
}
