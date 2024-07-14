package gift.wishlist.controller;

import gift.global.dto.ApiResponseDto;
import gift.global.dto.PageRequestDto;
import gift.wishlist.dto.WishListRequestDto;
import gift.wishlist.dto.WishListResponseDto;
import gift.wishlist.service.WishListService;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 개인의 wish list db를 조작해서 결과를 가져오는 api 컨트롤러
@RestController
@RequestMapping("/api/users/{user_id}/wishlist")
public class WishListApiController {

    private final WishListService wishListService;

    public WishListApiController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    // 전체 목록에서 제품 추가 시
    @PostMapping("/products/{product_id}")
    public ApiResponseDto addWishProduct(@RequestBody WishListRequestDto wishListRequestDto) {
        wishListService.insertWishProduct(wishListRequestDto);
        return new ApiResponseDto(ApiResponseDto.SUCCESS);
    }

    // 한 유저의 위시 페이지 가져오기
    @GetMapping("/products")
    public List<WishListResponseDto> getWishProducts(@PathVariable(name = "user_id") long userId,
        @RequestParam(name = "page-no") int pageNumber,
        @RequestParam(name = "sorting-state") int sortingState) {
        PageRequestDto pageRequestDto = new PageRequestDto(pageNumber, sortingState);
        return wishListService.readWishProducts(userId, pageRequestDto);
    }

    // + 버튼 눌러서 하나 증가.
    // 변경 요청을 보내서 pk를 제외한 모든 요소(quantity)만 바뀌므로 put이라고 생각했습니다.
    // 또한, put만으로 의미를 전달하기 어렵다고 생각해서 increase를 넣었습니다.
    @PutMapping("/products/{product_id}/increase")
    public ApiResponseDto increaseWishProduct(
        @RequestBody WishListRequestDto wishListRequestDto) {
        wishListService.increaseWishProduct(wishListRequestDto);
        return new ApiResponseDto(ApiResponseDto.SUCCESS);
    }

    // - 버튼 눌러서 하나 감소
    @PutMapping("/products/{product_id}/decrease")
    public ApiResponseDto decreaseWishProduct(
        @RequestBody WishListRequestDto wishListRequestDto) {
        wishListService.decreaseWishProduct(wishListRequestDto);
        return new ApiResponseDto(ApiResponseDto.SUCCESS);
    }

    // 삭제 버튼 눌러서 삭제
    @DeleteMapping("/products/{product_id}")
    public ApiResponseDto deleteWishProduct(
        @RequestBody WishListRequestDto wishListRequestDto) {
        wishListService.deleteWishProduct(wishListRequestDto);
        return new ApiResponseDto(ApiResponseDto.SUCCESS);
    }
}
