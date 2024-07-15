package gift.controller;

import gift.auth.LoginUser;
import gift.domain.User;
import gift.dto.common.apiResponse.ApiResponseBody.SuccessBody;
import gift.dto.common.apiResponse.ApiResponseGenerator;
import gift.dto.requestDTO.WishRequestDTO;
import gift.dto.responseDTO.WishListResponseDTO;
import gift.dto.responseDTO.WishResponseDTO;
import gift.service.AuthService;
import gift.service.WishService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<SuccessBody<WishListResponseDTO>> getAllWishes(@LoginUser User user) {
        WishListResponseDTO wishListResponseDTO = wishService.getAllWishes(user.getId());
        return ApiResponseGenerator.success(HttpStatus.OK, "위시리스트를 조회했습니다.", wishListResponseDTO);
    }

    @GetMapping("/page")
    public ResponseEntity<SuccessBody<WishListResponseDTO>> getAllWishPages(@LoginUser User user,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "0") int size,
        @RequestParam(value = "criteria", defaultValue = "id") String criteria) {
        WishListResponseDTO wishListResponseDTO = wishService.getAllWishes(user.getId(), page, size, criteria);
        return ApiResponseGenerator.success(HttpStatus.OK, "위시리스트를 조회했습니다.", wishListResponseDTO);
    }

    @PostMapping()
    public ResponseEntity<SuccessBody<Long>> addWishes(@LoginUser User user,
        @RequestBody WishRequestDTO wishRequestDTO) {
        authService.authorizeUser(user, wishRequestDTO.userId());
        Long wishInsertedId = wishService.addWish(wishRequestDTO);
        return ApiResponseGenerator.success(HttpStatus.CREATED, "위시리스트를 추가했습니다.", wishInsertedId);
    }

    @DeleteMapping("/{wishId}")
    public ResponseEntity<SuccessBody<Long>> deleteWishes(@LoginUser User user,
        @PathVariable Long wishId) {
        WishResponseDTO wishResponseDTO = wishService.getOneWish(wishId);
        authService.authorizeUser(user, wishResponseDTO.userId());

        Long wishDeletedId = wishService.deleteWish(wishId);
        return ApiResponseGenerator.success(HttpStatus.OK, "위시리스트를 삭제했습니다.", wishDeletedId);
    }
}
