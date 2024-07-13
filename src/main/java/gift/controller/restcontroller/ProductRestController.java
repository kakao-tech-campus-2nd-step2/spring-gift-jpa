package gift.controller.restcontroller;

import gift.controller.dto.request.ProductRequest;
import gift.controller.dto.response.PagingResponse;
import gift.controller.dto.response.ProductResponse;
import gift.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "Product", description = "상품 API")
@RestController
@RequestMapping("/api/v1")
public class ProductRestController {
    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    @Operation(summary = "전체 상품 조회", description = "전체 상품을 조회합니다.")
    public ResponseEntity<PagingResponse<ProductResponse>> getProducts(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ProductResponse> responses = productService.findAllProductPaging(pageable);
        return ResponseEntity.ok().body(PagingResponse.from(responses));
    }

    @GetMapping("/product/{id}")
    @Operation(summary = "상품 조회", description = "특정 상품을 조회합니다.")
    public ResponseEntity<ProductResponse> getProduct(
            @PathVariable("id") @NotNull @Min(1) Long id
    ) {
        ProductResponse response = productService.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/product")
    @Operation(summary = "상품 추가", description = "상품을 추가합니다.")
    public ResponseEntity<Long> createProduct(
            UriComponentsBuilder uriBuilder,
            @Valid @RequestBody ProductRequest request
    ) {
        Long id = productService.save(request);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                uriBuilder.path("/api/v1/product/{id}")
                        .buildAndExpand(id)
                        .toUri()
        );
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(id);
    }

    @PutMapping("/product/{id}")
    @Operation(summary = "상품 수정", description = "상품을 수정합니다.")
    public ResponseEntity<Long> updateProduct(
            @PathVariable("id") @NotNull @Min(1) Long id,
            @Valid @RequestBody ProductRequest request
    ) {
        productService.updateById(id, request);
        return ResponseEntity.ok().body(id);
    }

    @DeleteMapping("/product/{id}")
    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다.")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable("id") @NotNull @Min(1) Long id
    ) {
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
