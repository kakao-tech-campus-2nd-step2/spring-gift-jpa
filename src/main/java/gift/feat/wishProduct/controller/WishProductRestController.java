package gift.feat.wishProduct.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gift.core.authorization.UserDetails;
import gift.feat.wishProduct.controller.Dto.WishProductResponseDto;
import gift.feat.wishProduct.Service.WishProductService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/wishlist")
public class WishProductRestController {
	private final WishProductService wishProductService;

	@Autowired
	public WishProductRestController(WishProductService wishProductService) {
		this.wishProductService = wishProductService;
	}

	// 위시리스트에 상품 추가
	@PostMapping("")
	public ResponseEntity<Long> addWishList(@RequestAttribute("USER") UserDetails userDetails, @RequestParam Long productId) {
		return ResponseEntity.ok(wishProductService.save(productId, userDetails.getUserId()).getId());
	}

	//로그인한 유저의 위시리스트 조회
	@GetMapping("")
	public ResponseEntity<List<WishProductResponseDto>> getWishList(@RequestAttribute("USER") UserDetails userDetails) {
		List<WishProductResponseDto> wishList = wishProductService.getByUserId(userDetails.getUserId());
		return ResponseEntity.ok(wishList);
	}

	//로그인한 유저의 위시리스트를 페이징 해서 조회
	@GetMapping("/page")
	public ResponseEntity<Page<WishProductResponseDto>> getWishListWithPage(
		@RequestAttribute("USER") UserDetails userDetails,
		@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
	) {
		return ResponseEntity.ok(wishProductService.getByUserId(userDetails.getUserId(),pageable));
	}

}
