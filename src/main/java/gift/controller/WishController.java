package gift.controller;

import gift.authentication.LoginMember;
import gift.authentication.UserDetails;
import gift.dto.ApiResponse;
import gift.dto.WishAddRequestDto;
import gift.dto.WishResponseDto;
import gift.dto.WishUpdateRequestDto;
import gift.service.WishService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishes")
public class WishController {
    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addNewWish(@LoginMember UserDetails userDetails,
                                                  @RequestBody WishAddRequestDto request) {
        wishService.addWish(userDetails.id(), request);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "위시 리스트 등록에 성공하였습니다."));
    }

    @GetMapping
    public ResponseEntity<List<WishResponseDto>> getMemberWishList(
            @LoginMember UserDetails userDetails,
            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNum,
            @RequestParam(required = false, defaultValue = "10", value = "size") int size,
            @RequestParam(required = false, defaultValue = "id", value = "criteria") String criteria) {
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(Sort.Direction.ASC, criteria));
        return ResponseEntity.ok(wishService.getAllWishes(userDetails.id(), pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateWish(
            @LoginMember UserDetails userDetails,
            @PathVariable("id") Long id,
            @RequestBody WishUpdateRequestDto requestDto) {
        wishService.updateWish(userDetails.id(), id, requestDto.quantity());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "수량이 성공적으로 수정되었습니다."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteWish(
            @LoginMember UserDetails userDetails,
            @PathVariable("id") Long productId) {
        wishService.deleteWish(userDetails.id(), productId);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.NO_CONTENT, "성공적으로 삭제되었습니다."));
    }
}
