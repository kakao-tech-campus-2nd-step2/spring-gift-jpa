package gift.controller.restcontroller;

import gift.common.annotation.LoginMember;
import gift.controller.dto.request.WishInsertRequest;
import gift.controller.dto.request.WishPatchRequest;
import gift.controller.dto.response.PagingResponse;
import gift.controller.dto.response.WishResponse;
import gift.service.WishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Wishes", description = "장바구니 API")
@RestController
@RequestMapping("/api/v1/wishes")
public class WishesRestController {
    private final WishService wishService;

    public WishesRestController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping("")
    @Operation(summary = "위시리스트 추가", description = "위시리스트를 추가합니다.")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Void> insertWish(
            @Valid @RequestBody WishInsertRequest request,
            @Parameter(hidden = true) @NotNull @LoginMember Long memberId) {
        wishService.save(request, 1, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("")
    @Operation(summary = "위시리스트 조회", description = "위시리스트를 조회합니다.")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<PagingResponse<WishResponse>> getWishes(
            @Parameter(hidden = true) @NotNull @LoginMember Long memberId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<WishResponse> responses = wishService.findAllWishPagingByMemberId(memberId, pageable);
        return ResponseEntity.ok().body(PagingResponse.from(responses));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "위시리스트 수정", description = "위시리스트를 수정합니다.")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Integer> updateWish(
            @PathVariable("id") Long id,
            @Valid @RequestBody WishPatchRequest request,
            @Parameter(hidden = true) @NotNull @LoginMember Long memberId
    ) {
        wishService.update(id, request, memberId);
        return ResponseEntity.ok().body(request.productCount());
    }

    @DeleteMapping("{product_id}")
    @Operation(summary = "위시리스트 삭제", description = "위시리스트를 삭제합니다.")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Void> deleteWish(
            @PathVariable("product_id") @NotNull @Min(1) Long productId,
            @Parameter(hidden = true) @NotNull @LoginMember Long memberId
    ) {
        wishService.deleteByProductId(productId, memberId);
        return ResponseEntity.ok().build();
    }
}
