package gift.feat.product.contoller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gift.feat.product.domain.Product;
import gift.feat.product.domain.SearchType;
import gift.feat.product.contoller.dto.ProductRequestDto;
import gift.feat.product.contoller.dto.ProductResponseDto;
import gift.feat.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductRestController {

	private final ProductService productService;


	@PostMapping("/api/v1/product")
	@ResponseBody
	public ResponseEntity<Long> registerProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
		// 등록된 상품의 ID를 반환.
		return ResponseEntity.ok(productService.saveProduct(productRequestDto));
	}

	// 모든 상품을 페이징해서 조회
	@GetMapping("/api/v1/product")
	@ResponseBody
	public ResponseEntity<Page<ProductResponseDto>> getProductsWithPaging(
		@RequestParam(required = false) SearchType searchType,
		@RequestParam(required = false) String searchValue,
		@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
	) {
		return ResponseEntity.ok(productService.getProductsWithPaging(pageable, searchType, searchValue).map(ProductResponseDto::from));
	}




	@GetMapping("/api/v1/product/{id}")
	@ResponseBody
	public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id) {
		Product product = productService.getProductById(id);
		return ResponseEntity.ok(ProductResponseDto.from(product));
	}

	@PutMapping("/api/v1/product/{id}")
	@ResponseBody
	public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequestDto productRequestDto) {

		// 수정된 상품 ID를 같이 반환한다.
		return ResponseEntity.ok(productService.updateProduct(id, productRequestDto));
	}

	@DeleteMapping("/api/v1/product/{id}")
	@ResponseBody
	public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return ResponseEntity.ok().build();
	}
}