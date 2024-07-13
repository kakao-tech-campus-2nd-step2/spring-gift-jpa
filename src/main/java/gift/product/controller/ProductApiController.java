package gift.product.controller;

import gift.global.dto.ApiResponseDto;
import gift.product.dto.ProductRequestDto;
import gift.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductApiController {

    private final ProductService productService;

    @Autowired
    public ProductApiController(ProductService productService) {
        this.productService = productService;
    }

    // 제품을 추가하는 핸들러
    @PostMapping("/product")
    public ApiResponseDto createProduct(
        @RequestBody @Valid ProductRequestDto productRequestDto) {
        productService.insertProduct(productRequestDto);

        return new ApiResponseDto(ApiResponseDto.SUCCESS);
    }

    // 페이지 내의 제품을 조회하는 핸들러. resolver에 의해 쓸 데가 없어졌습니다.
//    @GetMapping("/products/pages/{page-no}")
//    public int readUserProducts(@PathVariable(name = "page-no") int pageNumber,
//        @RequestParam(name = "sorting-state") int sortingState) {
//        return productService.selectProducts();
//    }

    // id가 i인 상품을 수정하는 핸들러
    @PutMapping("/products/{product-id}")
    public ApiResponseDto updateProduct(@PathVariable(name = "product-id") long productId,
        @RequestBody @Valid ProductRequestDto productRequestDto) {
        // service를 호출해서 제품 수정
        productService.updateProduct(productId, productRequestDto);

        return new ApiResponseDto(ApiResponseDto.SUCCESS);
    }

    // id가 i인 상품을 삭제하는 핸들러
    @DeleteMapping("/products/{product-id}")
    public ApiResponseDto deleteProduct(@PathVariable(name = "product-id") long productId) {
        // service를 사용해서 하나의 제품 제거
        productService.deleteProduct(productId);

        return new ApiResponseDto(ApiResponseDto.SUCCESS);
    }
}
