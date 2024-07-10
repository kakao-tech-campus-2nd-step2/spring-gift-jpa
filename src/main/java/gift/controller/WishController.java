package gift.controller;

import gift.common.exception.ProductNotFoundException;
import gift.common.exception.WishCanNotModifyException;
import gift.common.exception.WishNotFoundException;
import gift.common.validation.LoginUser;
import gift.controller.dto.request.WishRequest;
import gift.controller.dto.response.WishResponse;
import gift.model.Product;
import gift.model.User;
import gift.model.Wish;
import gift.model.repository.ProductRepository;
import gift.model.repository.WishRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Wish", description = "Wish관련 API")
@RestController
@RequestMapping("/api/wishes")
public class WishController {
    private final ProductRepository productRepository;
    private final WishRepository wishRepository;

    public WishController(ProductRepository productRepository, WishRepository wishRepository) {
        this.productRepository = productRepository;
        this.wishRepository = wishRepository;
    }

    @Operation(summary = "위시리스트 추가", description = "위시리스트를 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "위시리스트 추가 성공"),
            @ApiResponse(responseCode = "404", description = "일치하는 위시리스트를 찾을 수 없음")
    })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void saveWish(@RequestBody WishRequest wishRequest,
                         @LoginUser User loginUser
    ) {
        Product product = productRepository.find(wishRequest.productId())
                .orElseThrow(() -> ProductNotFoundException.of(wishRequest.productId()));

        Wish wish = wishRequest.toModel(loginUser.getId());
        wishRepository.save(wish);
    }

    @Operation(summary = "위시리스트 수정", description = "위시리스트를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "위시리스트 수정 성공"),
            @ApiResponse(responseCode = "404", description = "일치하는 위시리스트를 찾을 수 없음")
    })
    @PutMapping("/{wishId}")
    @ResponseStatus(HttpStatus.OK)
    public void modifyWish(@PathVariable("wishId") Long wishId,
                           @RequestBody WishRequest wishRequest,
                           @LoginUser User loginUser
    ) {
        Wish wish = wishRepository.findByIdAndUserId(loginUser.getId(), wishId)
                .orElseThrow(() -> WishNotFoundException.of(wishId));

        if (!wish.getProductId().equals(wishRequest.productId())) {
            throw new WishCanNotModifyException();
        }

        Wish newWish = wishRequest.toModel(wishId, loginUser.getId());
        wishRepository.save(newWish);
    }

    @Operation(summary = "위시리스트 목록 조회", description = "위시리스트 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "위시리스트 목록 조회 성공"),
    })
    @GetMapping()
    public ResponseEntity<List<WishResponse>> getWishList(@LoginUser User loginUser) {
        List<Wish> wishes = wishRepository.findWishesByUserId(loginUser.getId());

        List<WishResponse> responses = wishes.stream()
                .map(WishResponse::fromModel)
                .toList();

        return ResponseEntity.ok()
                .body(responses);
    }

    @Operation(summary = "위시리스트 상세 조회", description = "위시리스트 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "위시리스트 상세 조회 성공"),
            @ApiResponse(responseCode = "404", description = "위시리스트를 찾을 수 없음")
    })
    @GetMapping("/{wishId}")
    public ResponseEntity<WishResponse> getWishDetail(@PathVariable("wishId") Long wishId,
                                                      @LoginUser User loginUser
    ) {
        Wish wish = wishRepository.findByIdAndUserId(loginUser.getId(), wishId)
                .orElseThrow(() -> WishNotFoundException.of(wishId));

        return ResponseEntity.ok()
                .body(WishResponse.fromModel(wish));
    }

    @Operation(summary = "위시리스트 삭제", description = "위시리스트를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "위시리스트 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "위시리스트를 찾을 수 없음")
    })
    @DeleteMapping("/{wishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWish(@PathVariable("wishId") Long wishId,
                           @LoginUser User loginUser
    ) {
        Wish wish = wishRepository.findByIdAndUserId(loginUser.getId(), wishId)
                .orElseThrow(() -> WishNotFoundException.of(wishId));

        wish.delete();
        wishRepository.save(wish);
    }
}
