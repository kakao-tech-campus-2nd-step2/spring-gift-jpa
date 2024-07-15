package gift.controller;

import gift.auth.LoginUser;
import gift.domain.Product;
import gift.domain.User;
import gift.dto.common.apiResponse.ApiResponseBody.SuccessBody;
import gift.dto.common.apiResponse.ApiResponseGenerator;
import gift.dto.requestDTO.ProductRequestDTO;
import gift.dto.responseDTO.ProductListResponseDTO;
import gift.dto.responseDTO.ProductResponseDTO;
import gift.service.AuthService;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;
    private final AuthService authService;

    public ProductController(ProductService productService, AuthService authService) {
        this.productService = productService;
        this.authService = authService;
    }

    @GetMapping("/products")
    public ResponseEntity<SuccessBody<ProductListResponseDTO>> getAllProducts() {
        ProductListResponseDTO productListResponseDTO = productService.getAllProducts();
        return ApiResponseGenerator.success(HttpStatus.OK, "모든 상품을 조회했습니다.",
            productListResponseDTO);
    }

    @GetMapping("/products/page")
    public ResponseEntity<SuccessBody<ProductListResponseDTO>> getAllProductPages(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "8") int size,
        @RequestParam(value = "criteria", defaultValue = "id") String criteria) {
        ProductListResponseDTO productListResponseDTO = productService.getAllProducts(page, size, criteria);
        return ApiResponseGenerator.success(HttpStatus.OK, "모든 상품을 조회했습니다.",
            productListResponseDTO);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<SuccessBody<ProductResponseDTO>> getOneProduct(
        @PathVariable("id") Long productId) {
        ProductResponseDTO productResponseDTO = productService.getOneProduct(productId);
        return ApiResponseGenerator.success(HttpStatus.OK, "id : " + productId + " 상품을 조회했습니다.",
            productResponseDTO);
    }

    @PostMapping("/product")
    public ResponseEntity<SuccessBody<Long>> addProduct(
        @Valid @RequestBody ProductRequestDTO productRequestDTO,
        @LoginUser User user) {

        authService.authorizeAdminUser(user, productRequestDTO.name());
        Long productId = productService.addProduct(productRequestDTO);
        return ApiResponseGenerator.success(HttpStatus.CREATED, "상품이 등록되었습니다.", productId);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<SuccessBody<Long>> updateProduct(@PathVariable("id") Long productId,
        @RequestBody
        ProductRequestDTO productRequestDTO) {
        Long updatedProductId = productService.updateProduct(productId, productRequestDTO);
        return ApiResponseGenerator.success(HttpStatus.OK, "상품이 수정되었습니다.", updatedProductId);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<SuccessBody<Long>> deleteProduct(@PathVariable("id") Long productId) {
        Long deletedProductId = productService.deleteProduct(productId);
        return ApiResponseGenerator.success(HttpStatus.OK, "상품이 삭제되었습니다.", deletedProductId);
    }
}
