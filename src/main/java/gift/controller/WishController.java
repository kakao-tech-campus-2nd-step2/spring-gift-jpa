package gift.controller;

import gift.dto.WishRequestDto;
import gift.dto.WishResponseDto;
import gift.service.WishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishes")
@Tag(name = "Wishes", description = "위시리스트 관련 API")
public class WishController {
    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping
    @Operation(summary = "위시리스트 추가", description = "위시리스트에 새로운 상품을 추가합니다.")
    public ResponseEntity<WishResponseDto> addWish(@RequestBody WishRequestDto wishRequestDto) {
        WishResponseDto createdWish = wishService.addWish(1L, wishRequestDto); // 실제로는 사용자 ID를 동적으로 받아야 합니다.
        return new ResponseEntity<>(createdWish, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "위시리스트 조회", description = "사용자의 모든 위시리스트 항목을 조회합니다.")
    public ResponseEntity<List<WishResponseDto>> getWishesByUserId() {
        List<WishResponseDto> wishList = wishService.getWishesByUserId(1L); // 실제로는 사용자 ID를 동적으로 받아야 합니다.
        return new ResponseEntity<>(wishList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "위시리스트 항목 삭제", description = "위시리스트에서 특정 상품을 삭제합니다.")
    public ResponseEntity<Void> deleteWish(@PathVariable Long id) {
        wishService.deleteWish(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
